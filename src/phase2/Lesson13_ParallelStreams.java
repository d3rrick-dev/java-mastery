package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 13: Parallel Streams
 *
 * THEORY:
 * Parallel streams use multiple threads to process data concurrently.
 * They can significantly improve performance for large datasets.
 *
 * HOW IT WORKS:
 * - Uses ForkJoinPool.commonPool() by default
 * - Splits data into chunks
 * - Processes chunks in parallel
 * - Combines results
 *
 * WHEN TO USE:
 * - Large datasets (10,000+ elements)
 * - CPU-intensive operations
 * - Independent operations (no shared state)
 *
 * WHEN NOT TO USE:
 * - Small datasets (overhead > benefit)
 * - I/O-bound operations (blocking I/O hurts parallelism)
 * - Ordered operations (forEachOrdered preserves order but reduces parallelism)
 */

public class Lesson13_ParallelStreams {

    public static void main(String[] args) {
        System.out.println("=== LESSON 13: Parallel Streams ===\n");

        List<Employee> employees = createLargeEmployeeList(1000);

        // ============================================
        // 1. BASIC PARALLEL STREAM
        // ============================================
        System.out.println("--- 1. Basic Parallel Stream ---");

        // Sequential stream
        long start1 = System.nanoTime();
        long count1 = employees.stream()
                .filter(emp -> emp.active())
                .count();
        long time1 = System.nanoTime() - start1;

        // Parallel stream
        long start2 = System.nanoTime();
        long count2 = employees.parallelStream()
                .filter(emp -> emp.active())
                .count();
        long time2 = System.nanoTime() - start2;

        System.out.println("Sequential: " + count1 + " in " + time1 / 1_000_000 + " ms");
        System.out.println("Parallel: " + count2 + " in " + time2 / 1_000_000 + " ms");
        System.out.println("Speedup: " + String.format("%.2fx", (double) time1 / time2));

        // ============================================
        // 2. PARALLEL WITH COMPLEX OPERATIONS
        // ============================================
        System.out.println("\n--- 2. Parallel with Complex Operations ---");

        // CPU-intensive: Calculate something for each employee
        start1 = System.nanoTime();
        BigDecimal total1 = employees.stream()
                .map(emp -> expensiveCalculation(emp.salary()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        time1 = System.nanoTime() - start1;

        start2 = System.nanoTime();
        BigDecimal total2 = employees.parallelStream()
                .map(emp -> expensiveCalculation(emp.salary()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        time2 = System.nanoTime() - start2;

        System.out.println("Sequential: " + total1 + " in " + time1 / 1_000_000 + " ms");
        System.out.println("Parallel: " + total2 + " in " + time2 / 1_000_000 + " ms");
        System.out.println("Speedup: " + String.format("%.2fx", (double) time1 / time2));

        // ============================================
        // 3. PARALLEL WITH ORDERED OPERATIONS
        // ============================================
        System.out.println("\n--- 3. Parallel with Ordered Operations ---");

        // forEach (order not guaranteed in parallel)
        System.out.println("forEach (parallel, order not guaranteed):");
        employees.parallelStream()
                .limit(5)
                .forEach(emp -> System.out.println("  " + emp.name()));

        // forEachOrdered (preserves encounter order)
        System.out.println("\nforEachOrdered (preserves order):");
        employees.parallelStream()
                .limit(5)
                .forEachOrdered(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 4. PARALLEL WITH COLLECT
        // ============================================
        System.out.println("\n--- 4. Parallel with Collect ---");

        // toList() preserves order in parallel
        List<String> names = employees.parallelStream()
                .map(emp -> emp.name())
                .limit(10)
                .toList();
        System.out.println("Names (parallel, ordered): " + names);

        // toSet() doesn't preserve order
        java.util.Set<String> depts = employees.parallelStream()
                .map(emp -> emp.department())
                .collect(java.util.stream.Collectors.toSet());
        System.out.println("Departments (parallel, unordered): " + depts);

        // ============================================
        // 5. PARALLEL WITH GROUPING
        // ============================================
        System.out.println("\n--- 5. Parallel with Grouping ---");

        Map<String, Long> deptCounts = employees.parallelStream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()
                ));
        System.out.println("Department counts (parallel): " + deptCounts);

        // ============================================
        // 6. CUSTOM THREAD POOL
        // ============================================
        System.out.println("\n--- 6. Custom Thread Pool ---");

        // By default, uses ForkJoinPool.commonPool()
        // You can create custom pool for more control
        java.util.concurrent.ForkJoinPool customPool = new java.util.concurrent.ForkJoinPool(4);
        try {
            long count = customPool.submit(() ->
                    employees.parallelStream()
                            .filter(emp -> emp.active())
                            .count()
            ).get();
            System.out.println("Custom pool count: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Process 100,000 employees in parallel
        List<Employee> largeList = createLargeEmployeeList(100_000);

        long start = System.nanoTime();
        BigDecimal totalSalary = largeList.parallelStream()
                .filter(emp -> emp.active())
                .map(emp -> expensiveCalculation(emp.salary()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long end = System.nanoTime() - start;

        System.out.println("Processed 100,000 employees in " + end / 1_000_000 + " ms");
        System.out.println("Total salary: $" + totalSalary);

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using parallel for small datasets
        List<Employee> smallList = createLargeEmployeeList(10);
        long smallStart = System.nanoTime();
        smallList.parallelStream().count();
        long smallEnd = System.nanoTime() - smallStart;
        System.out.println("Small list parallel time: " + smallEnd / 1_000_000 + " ms (overhead!)");

        // Mistake 2: Modifying shared state
        List<String> namesList = new ArrayList<>();
        try {
            employees.parallelStream()
                    .limit(10)
                    .forEach(emp -> namesList.add(emp.name())); // ConcurrentModificationException!
        } catch (Exception e) {
            System.out.println("Error modifying shared state: " + e.getClass().getSimpleName());
        }

        // Correct: Use collect()
        List<String> safeNames = employees.parallelStream()
                .limit(10)
                .map(emp -> emp.name())
                .toList();
        System.out.println("Safe parallel collect: " + safeNames);

        // Mistake 3: Assuming parallel is always faster
        System.out.println("\nParallel is NOT always faster!");
        System.out.println("- Small datasets: overhead > benefit");
        System.out.println("- Simple operations: overhead > benefit");
        System.out.println("- Ordered operations: synchronization overhead");
    }

    // Simulate expensive calculation
    private static BigDecimal expensiveCalculation(BigDecimal value) {
        try {
            Thread.sleep(1); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return value.multiply(new BigDecimal("1.1"));
    }

    private static List<Employee> createLargeEmployeeList(int count) {
        List<Employee> employees = new ArrayList<>();
        String[] depts = {"Engineering", "Sales", "HR", "Marketing", "Finance"};
        for (int i = 1; i <= count; i++) {
            employees.add(new Employee(
                    i,
                    "Employee" + i,
                    depts[i % depts.length],
                    new BigDecimal(50000 + (i % 100) * 1000),
                    22 + (i % 40),
                    LocalDate.now().minusDays(i % 3650),
                    i % 3 != 0 // 2/3 active
            ));
        }
        return employees;
    }
}
