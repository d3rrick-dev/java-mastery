package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 6: Lazy Evaluation
 *
 * THEORY:
 * Lazy evaluation means operations are not executed until their result is actually needed.
 * In Streams, all intermediate operations are lazy - they build a pipeline but don't execute.
 * Only when a terminal operation is called does the entire pipeline execute.
 *
 * WHY IT EXISTS:
 * - Performance: Skip unnecessary operations
 * - Short-circuiting: Stop early when possible (findFirst, anyMatch)
 * - Efficiency: Process only what's needed
 * - Infinite streams: Can work with infinite data (limit, findFirst)
 *
 * HOW IT WORKS:
 * 1. Intermediate ops build a pipeline (no execution)
 * 2. Terminal op triggers execution
 * 3. Elements flow through pipeline one at a time
 * 4. Short-circuiting can stop early
 */

public class Lesson06_LazyEvaluation {

    public static void main(String[] args) {
        System.out.println("=== LESSON 6: Lazy Evaluation ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. DEMONSTRATING LAZINESS
        // ============================================
        System.out.println("--- 1. Demonstrating Laziness ---");

        System.out.println("Creating stream pipeline (no execution yet)...");
        java.util.stream.Stream<Employee> pipeline = employees.stream()
                .filter(emp -> {
                    System.out.println("  Filtering: " + emp.name());
                    return emp.active();
                })
                .map(emp -> {
                    System.out.println("  Mapping: " + emp.name());
                    return emp.name().toUpperCase();
                })
                .filter(name -> {
                    System.out.println("  Filtering name: " + name);
                    return name.length() > 3;
                });

        System.out.println("Pipeline created. No filtering or mapping happened yet!");
        System.out.println("\nNow calling terminal operation (count)...");
        long count = pipeline.count();
        System.out.println("Count: " + count);

        // ============================================
        // 2. SHORT-CIRCUITING
        // ============================================
        System.out.println("\n--- 2. Short-Circuiting ---");

        // findFirst() stops after finding first match
        System.out.println("findFirst() - stops after first match:");
        employees.stream()
                .filter(emp -> {
                    System.out.println("  Checking: " + emp.name());
                    return emp.salary().compareTo(new BigDecimal("100000")) > 0;
                })
                .findFirst()
                .ifPresent(emp -> System.out.println("  Found: " + emp.name()));

        // anyMatch() stops after first match
        System.out.println("\nanyMatch() - stops after first match:");
        boolean hasHighEarner = employees.stream()
                .anyMatch(emp -> {
                    System.out.println("  Checking: " + emp.name());
                    return emp.salary().compareTo(new BigDecimal("100000")) > 0;
                });
        System.out.println("Has high earner: " + hasHighEarner);

        // ============================================
        // 3. INFINITE STREAMS
        // ============================================
        System.out.println("\n--- 3. Infinite Streams ---");

        // Stream.generate() creates infinite stream
        System.out.println("First 5 from infinite stream:");
        java.util.stream.Stream<Integer> infinite = java.util.stream.Stream.generate(() -> 1);
        infinite.limit(5)
                .forEach(num -> System.out.println("  " + num));

        // Stream.iterate() creates infinite stream
        System.out.println("\nFirst 5 even numbers:");
        java.util.stream.Stream<Integer> evens = java.util.stream.Stream.iterate(0, n -> n + 2);
        evens.limit(5)
                .forEach(num -> System.out.println("  " + num));

        // ============================================
        // 4. LAZY EVALUATION SAVES WORK
        // ============================================
        System.out.println("\n--- 4. Lazy Evaluation Saves Work ---");

        // Without short-circuiting (processes all)
        System.out.println("All matches (processes all):");
        long allMatches = employees.stream()
                .filter(emp -> {
                    System.out.println("  Checking: " + emp.name());
                    return emp.salary().compareTo(new BigDecimal("80000")) > 0;
                })
                .count();
        System.out.println("Count: " + allMatches);

        // With short-circuiting (stops early)
        System.out.println("\nFirst match (stops early):");
        employees.stream()
                .filter(emp -> {
                    System.out.println("  Checking: " + emp.name());
                    return emp.salary().compareTo(new BigDecimal("80000")) > 0;
                })
                .findFirst()
                .ifPresent(emp -> System.out.println("  Found: " + emp.name()));

        // ============================================
        // 5. PEEK FOR DEBUGGING LAZY EVALUATION
        // ============================================
        System.out.println("\n--- 5. peek() for Debugging ---");

        System.out.println("Using peek to see pipeline execution:");
        employees.stream()
                .filter(emp -> emp.active())
                .peek(emp -> System.out.println("  After filter: " + emp.name()))
                .map(emp -> emp.name().toUpperCase())
                .peek(name -> System.out.println("  After map: " + name))
                .limit(2)
                .forEach(name -> System.out.println("  Final: " + name));

        // ============================================
        // 6. ORDER OF OPERATIONS MATTERS
        // ============================================
        System.out.println("\n--- 6. Order of Operations ---");

        // Good: Filter first, then limit (more efficient)
        System.out.println("Filter then limit:");
        long start1 = System.nanoTime();
        long count1 = employees.stream()
                .filter(emp -> emp.active())
                .limit(2)
                .count();
        long time1 = System.nanoTime() - start1;

        // Bad: Limit first, then filter (less efficient)
        System.out.println("Limit then filter:");
        long start2 = System.nanoTime();
        long count2 = employees.stream()
                .limit(2)
                .filter(emp -> emp.active())
                .count();
        long time2 = System.nanoTime() - start2;

        System.out.println("Filter then limit: " + time1 + " ns, count: " + count1);
        System.out.println("Limit then filter: " + time2 + " ns, count: " + count2);
        System.out.println("Tip: Put filters BEFORE limit for better performance");

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Find first active engineer with salary > 90000
        System.out.println("Finding first active engineer with salary > 90000:");
        employees.stream()
                .filter(emp -> {
                    System.out.println("  Checking active: " + emp.name());
                    return emp.active();
                })
                .filter(emp -> {
                    System.out.println("  Checking dept: " + emp.name());
                    return "Engineering".equals(emp.department());
                })
                .filter(emp -> {
                    System.out.println("  Checking salary: " + emp.name() + " ($" + emp.salary() + ")");
                    return emp.salary().compareTo(new BigDecimal("90000")) > 0;
                })
                .findFirst()
                .ifPresent(emp -> System.out.println("  Found: " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Assuming all operations execute for all elements
        // With short-circuiting, not all elements are processed
        System.out.println("Short-circuiting example:");
        employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("200000")) > 0)
                .findFirst()
                .ifPresent(emp -> System.out.println("  Found: " + emp.name()));
        System.out.println("  (Only checked until first match, not all employees)");

        // Mistake 2: Using forEach for debugging (use peek instead)
        // employees.stream().filter(...).forEach(...); // Terminal - executes everything
        // employees.stream().filter(...).peek(...).findFirst(); // Better for debugging

        // Mistake 3: Not understanding that map is lazy too
        java.util.stream.Stream<Integer> lazyMap = employees.stream()
                .filter(emp -> emp.active())
                .map(emp -> {
                    System.out.println("  Mapping: " + emp.name());
                    return emp.age();
                });
        System.out.println("Map created but not executed");
        lazyMap.findFirst();  // NOW it executes
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        employees.add(new Employee(6, "Frank", "Sales", new BigDecimal("90000"), 40, LocalDate.of(2015, 2, 28), true));
        return employees;
    }
}
