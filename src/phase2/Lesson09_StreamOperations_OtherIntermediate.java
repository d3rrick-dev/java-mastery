package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * LESSON 9: distinct(), sorted(), peek(), limit(), skip()
 *
 * THEORY:
 * These intermediate operations provide additional control over stream elements.
 *
 * distinct()  : Remove duplicates (uses equals() and hashCode())
 * sorted()    : Sort elements (natural order or custom comparator)
 * peek()      : Perform action on each element (mainly for debugging)
 * limit(n)    : Keep only first n elements (short-circuiting)
 * skip(n)     : Skip first n elements
 */

public class Lesson09_StreamOperations_OtherIntermediate {

    public static void main(String[] args) {
        System.out.println("=== LESSON 9: distinct(), sorted(), peek(), limit(), skip() ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. distinct() - REMOVE DUPLICATES
        // ============================================
        System.out.println("--- 1. distinct() ---");

        List<String> namesWithDups = List.of("Alice", "Bob", "Alice", "Charlie", "Bob", "Alice");
        List<String> uniqueNames = namesWithDups.stream()
                .distinct()
                .toList();
        System.out.println("With duplicates: " + namesWithDups);
        System.out.println("Distinct: " + uniqueNames);

        // distinct() uses equals() and hashCode()
        // For custom objects, ensure proper equals/hashCode implementation
        List<Employee> empsWithDups = new ArrayList<>(employees);
        empsWithDups.add(employees.get(0)); // Add duplicate Alice
        List<Employee> uniqueEmps = empsWithDups.stream()
                .distinct()
                .toList();
        System.out.println("Unique employees: " + uniqueEmps.size() + " (was " + empsWithDups.size() + ")");

        // ============================================
        // 2. sorted() - SORT ELEMENTS
        // ============================================
        System.out.println("\n--- 2. sorted() ---");

        // Natural order (names)
        List<String> sortedNames = employees.stream()
                .map(emp -> emp.name())
                .sorted()
                .toList();
        System.out.println("Sorted names: " + sortedNames);

        // Custom order (by salary descending)
        List<Employee> bySalaryDesc = employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .toList();
        System.out.println("By salary (high to low):");
        bySalaryDesc.forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // Custom order (by age ascending)
        List<Employee> byAgeAsc = employees.stream()
                .sorted((e1, e2) -> Integer.compare(e1.age(), e2.age()))
                .toList();
        System.out.println("By age (young to old):");
        byAgeAsc.forEach(emp -> System.out.println("  " + emp.name() + " - " + emp.age()));

        // ============================================
        // 3. peek() - DEBUGGING
        // ============================================
        System.out.println("\n--- 3. peek() ---");

        System.out.println("Using peek to debug pipeline:");
        employees.stream()
                .filter(emp -> emp.active())
                .peek(emp -> System.out.println("  After filter: " + emp.name()))
                .map(emp -> emp.name().toUpperCase())
                .peek(name -> System.out.println("  After map: " + name))
                .limit(2)
                .forEach(name -> System.out.println("  Final: " + name));

        // ============================================
        // 4. limit() - KEEP FIRST N
        // ============================================
        System.out.println("\n--- 4. limit() ---");

        // Top 3 highest paid
        List<Employee> top3 = employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .limit(3)
                .toList();
        System.out.println("Top 3 earners:");
        top3.forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // limit() is short-circuiting - stops after n elements
        System.out.println("\nFirst 2 active employees:");
        employees.stream()
                .filter(emp -> emp.active())
                .limit(2)
                .forEach(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 5. skip() - SKIP FIRST N
        // ============================================
        System.out.println("\n--- 5. skip() ---");

        // Skip first 2, take rest
        List<Employee> skip2 = employees.stream()
                .sorted((e1, e2) -> e1.salary().compareTo(e2.salary()))
                .skip(2)
                .toList();
        System.out.println("After skipping 2 lowest earners:");
        skip2.forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // Skip inactive, take next 2
        System.out.println("\nSkip inactive, take 2:");
        employees.stream()
                .filter(emp -> emp.active())
                .skip(1)  // Skip first active (Alice)
                .limit(2)  // Take next 2
                .forEach(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 6. COMBINING OPERATIONS
        // ============================================
        System.out.println("\n--- 6. Combining Operations ---");

        // Pagination example: page 2, size 2
        int page = 2;
        int size = 2;
        List<Employee> page2 = employees.stream()
                .sorted((e1, e2) -> e1.id() - e2.id())
                .skip((page - 1) * size)
                .limit(size)
                .toList();
        System.out.println("Page " + page + " (size " + size + "):");
        page2.forEach(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Get 3rd highest paid active engineer
        Optional<Employee> thirdHighest = employees.stream()
                .filter(emp -> emp.active())
                .filter(emp -> "Engineering".equals(emp.department()))
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .skip(2)  // Skip top 2
                .limit(1)  // Take 3rd
                .findFirst();

        thirdHighest.ifPresent(emp ->
                System.out.println("3rd highest paid engineer: " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using distinct() on unordered streams (still works but order not guaranteed)
        List<String> unordered = List.of("C", "A", "B", "A", "C");
        List<String> distinctUnordered = unordered.stream()
                .distinct()
                .toList();
        System.out.println("Distinct from unordered: " + distinctUnordered);

        // Mistake 2: Forgetting that sorted() is expensive (O(n log n))
        // Only sort when necessary
        // employees.stream().sorted().findFirst(); // Sorts ALL, then takes first
        // Better:
        employees.stream()
                .max((e1, e2) -> e1.salary().compareTo(e2.salary()))
                .ifPresent(emp -> System.out.println("Max without full sort: " + emp.name()));

        // Mistake 3: Using peek() for side effects
        // employees.stream().peek(emp -> saveToDb(emp)).toList(); // BAD
        // Use forEach() for side effects, peek() is for debugging
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
