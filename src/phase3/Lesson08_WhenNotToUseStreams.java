package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 8: When NOT to Use Streams
 *
 * THEORY:
 * Streams are powerful but not always the best solution.
 * Understanding when NOT to use them is as important as knowing when to use them.
 *
 * WHEN TO AVOID STREAMS:
 * 1. Simple iterations (for-each is clearer)
 * 2. Performance-critical code (streams have overhead)
 * 3. Mutating operations (streams are designed for immutability)
 * 4. Complex state management (streams don't handle state well)
 * 5. Primitive arrays (specialized loops are faster)
 * 6. Debugging complex pipelines (harder to debug than loops)
 */

public class Lesson08_WhenNotToUseStreams {

    public static void main(String[] args) {
        System.out.println("=== LESSON 8: When NOT to Use Streams ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. SIMPLE ITERATIONS
        // ============================================
        System.out.println("--- 1. Simple Iterations ---");

        // Stream version (overkill for simple print)
        System.out.println("Stream version:");
        employees.stream()
                .forEach(emp -> System.out.println("  " + emp.name()));

        // Better: Simple for-each
        System.out.println("\nFor-each version (clearer):");
        for (Employee emp : employees) {
            System.out.println("  " + emp.name());
        }

        // ============================================
        // 2. PERFORMANCE-CRITICAL CODE
        // ============================================
        System.out.println("\n--- 2. Performance-Critical Code ---");

        // Stream version (has overhead)
        long start1 = System.nanoTime();
        int sum1 = employees.stream()
                .mapToInt(emp -> emp.age())
                .sum();
        long time1 = System.nanoTime() - start1;

        // Traditional loop (faster for small collections)
        long start2 = System.nanoTime();
        int sum2 = 0;
        for (Employee emp : employees) {
            sum2 += emp.age();
        }
        long time2 = System.nanoTime() - start2;

        System.out.println("Stream: " + time1 + " ns, result: " + sum1);
        System.out.println("Loop: " + time2 + " ns, result: " + sum2);
        System.out.println("Loop is " + String.format("%.2fx", (double) time1 / time2) + " faster");

        // ============================================
        // 3. MUTATING OPERATIONS
        // ============================================
        System.out.println("\n--- 3. Mutating Operations ---");

        // Streams are designed for immutability
        // If you need to modify state, streams are not ideal

        // Bad: Using stream to modify external list
        List<String> names = new ArrayList<>();
        employees.stream()
                .map(Employee::name)
                .forEach(names::add); // Side effect - not pure FP
        System.out.println("Names: " + names);

        // Better: Use collect()
        List<String> betterNames = employees.stream()
                .map(Employee::name)
                .toList();
        System.out.println("Better: " + betterNames);

        // ============================================
        // 4. COMPLEX STATE MANAGEMENT
        // ============================================
        System.out.println("\n--- 4. Complex State Management ---");

        // Streams don't handle complex state well
        // Example: Running total with conditions

        // Stream version (awkward)
        final int[] total = {0};
        employees.stream()
                .filter(Employee::active)
                .forEach(emp -> total[0] += emp.salary().intValue());
        System.out.println("Total (stream with side effect): " + total[0]);

        // Better: Use reduce
        int total2 = employees.stream()
                .filter(Employee::active)
                .map(emp -> emp.salary().intValue())
                .reduce(0, Integer::sum);
        System.out.println("Total (reduce): " + total2);

        // ============================================
        // 5. DEBUGGING COMPLEX PIPELINES
        // ============================================
        System.out.println("\n--- 5. Debugging Complex Pipelines ---");

        // Complex stream pipeline is hard to debug
        // employees.stream()
        //     .filter(...)
        //     .map(...)
        //     .filter(...)
        //     .map(...)
        //     .collect(...)
        // Hard to know which element failed

        // Better: Break into steps or use peek for debugging
        employees.stream()
                .filter(Employee::active)
                .peek(emp -> System.out.println("  After filter: " + emp.name()))
                .map(Employee::name)
                .peek(name -> System.out.println("  After map: " + name))
                .toList();

        // ============================================
        // 6. PRIMITIVE ARRAYS
        // ============================================
        System.out.println("\n--- 6. Primitive Arrays ---");

        int[] ages = {30, 28, 35, 25, 32};

        // Stream version (boxing overhead)
        long start3 = System.nanoTime();
        int sum3 = java.util.Arrays.stream(ages).sum();
        long time3 = System.nanoTime() - start3;

        // Traditional loop (no boxing)
        long start4 = System.nanoTime();
        int sum4 = 0;
        for (int age : ages) {
            sum4 += age;
        }
        long time4 = System.nanoTime() - start4;

        System.out.println("Stream: " + time3 + " ns, result: " + sum3);
        System.out.println("Loop: " + time4 + " ns, result: " + sum4);

        // ============================================
        // 7. COMMON PITFALLS
        // ============================================
        System.out.println("\n--- 7. Common Pitfalls ---");

        // Pitfall 1: Over-engineering simple tasks
        // Don't use streams for everything!

        // Pitfall 2: Ignoring performance
        // Streams have overhead - measure before optimizing

        // Pitfall 3: Side effects in streams
        // employees.stream().forEach(emp -> saveToDb(emp)); // BAD
        // Use forEach for side effects, but be aware it's not pure FP

        // Pitfall 4: Complex nested streams
        // Hard to read and maintain
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
