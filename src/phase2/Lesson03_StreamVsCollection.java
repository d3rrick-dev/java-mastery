package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 3: Stream vs Collection
 *
 * THEORY:
 * Collections and Streams are both ways to work with data, but they have
 * fundamentally different purposes and characteristics.
 *
 * COLLECTION:
 * - In-memory data structure
 * - Stores data
 * - External iteration (you control the loop)
 * - Can be modified (add/remove)
 * - Eager evaluation
 * - Can be iterated multiple times
 *
 * STREAM:
 * - Data pipeline/sequence
 * - Does NOT store data (views data from source)
 * - Internal iteration (Stream controls the loop)
 * - Cannot modify source (immutable view)
 * - Lazy evaluation (operations execute only when terminal op called)
 * - Can only be consumed once
 */

public class Lesson03_StreamVsCollection {

    public static void main(String[] args) {
        System.out.println("=== LESSON 3: Stream vs Collection ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. STORAGE
        // ============================================
        System.out.println("--- 1. Storage ---");

        // Collection: Stores data in memory
        List<Employee> collection = new ArrayList<>();
        collection.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        System.out.println("Collection size: " + collection.size());
        System.out.println("Collection stores: " + collection);

        // Stream: Does NOT store data, just a view
        java.util.stream.Stream<Employee> stream = employees.stream();
        // stream doesn't have size() - it's not a collection
        long count = stream.count();  // Terminal operation
        System.out.println("Stream count: " + count);
        System.out.println("Stream doesn't store data, it processes it");

        // ============================================
        // 2. ITERATION
        // ============================================
        System.out.println("\n--- 2. Iteration ---");

        // Collection: External iteration (YOU control the loop)
        System.out.println("Collection iteration (external):");
        for (Employee emp : employees) {
            System.out.println("  " + emp.name());
        }

        // Stream: Internal iteration (STREAM controls the loop)
        System.out.println("Stream iteration (internal):");
        employees.stream()
                .forEach(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 3. MUTABILITY
        // ============================================
        System.out.println("\n--- 3. Mutability ---");

        // Collection: Can be modified
        List<Employee> mutableList = new ArrayList<>(employees);
        mutableList.add(new Employee(99, "New Guy", "Temp", BigDecimal.ZERO, 0, LocalDate.now(), false));
        System.out.println("Collection after add: " + mutableList.size());

        // Stream: Cannot modify source
        java.util.stream.Stream<Employee> stream2 = employees.stream()
                .filter(emp -> emp.active());
        // stream2 doesn't have add() or remove() methods
        // It's a read-only view

        // ============================================
        // 4. EVALUATION
        // ============================================
        System.out.println("\n--- 4. Evaluation ---");

        // Collection: Eager (data is processed immediately)
        List<Employee> filteredCollection = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.active()) {
                filteredCollection.add(emp);  // Processed immediately
            }
        }
        System.out.println("Collection filtered: " + filteredCollection.size());

        // Stream: Lazy (operations build a pipeline, execute only on terminal)
        java.util.stream.Stream<Employee> lazyStream = employees.stream()
                .filter(emp -> emp.active())  // Not executed yet!
                .filter(emp -> "Engineering".equals(emp.department()));  // Not executed yet!

        // Only when we call a terminal operation does it execute
        long lazyCount = lazyStream.count();  // NOW it executes
        System.out.println("Stream filtered (lazy): " + lazyCount);

        // ============================================
        // 5. REUSABILITY
        // ============================================
        System.out.println("\n--- 5. Reusability ---");

        // Collection: Can be iterated multiple times
        System.out.println("First iteration:");
        for (Employee emp : employees) {
            System.out.println("  " + emp.name());
        }
        System.out.println("Second iteration:");
        for (Employee emp : employees) {
            System.out.println("  " + emp.name());
        }

        // Stream: Can only be consumed once
        java.util.stream.Stream<Employee> singleStream = employees.stream();
        singleStream.forEach(emp -> System.out.println("  " + emp.name()));
        // singleStream.forEach(...); // ERROR: stream already consumed

        // To reuse, create a new stream from the collection
        employees.stream().forEach(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 6. PERFORMANCE COMPARISON
        // ============================================
        System.out.println("\n--- 6. Performance ---");

        // For simple operations, collections may be faster (no overhead)
        // For complex operations, streams can be faster (parallelization, optimization)

        // Collection approach
        long start1 = System.nanoTime();
        int sum1 = 0;
        for (Employee emp : employees) {
            sum1 += emp.salary().intValue();
        }
        long time1 = System.nanoTime() - start1;

        // Stream approach
        long start2 = System.nanoTime();
        int sum2 = employees.stream()
                .mapToInt(emp -> emp.salary().intValue())
                .sum();
        long time2 = System.nanoTime() - start2;

        System.out.println("Collection time: " + time1 + " ns");
        System.out.println("Stream time: " + time2 + " ns");
        System.out.println("Results equal: " + (sum1 == sum2));

        // ============================================
        // 7. WHEN TO USE WHICH
        // ============================================
        System.out.println("\n--- 7. When to Use Which ---");

        System.out.println("Use Collection when:");
        System.out.println("- You need to store and modify data");
        System.out.println("- You need random access (get(index))");
        System.out.println("- You need to iterate multiple times");
        System.out.println("- Simple operations (no complex pipeline)");

        System.out.println("\nUse Stream when:");
        System.out.println("- You need to process/transform data");
        System.out.println("- You want declarative, functional style");
        System.out.println("- You need parallel processing");
        System.out.println("- Complex multi-step operations");
        System.out.println("- You want lazy evaluation");
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        return employees;
    }
}
