package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * LESSON 2: Stream Lifecycle
 *
 * THEORY:
 * A Stream has three phases:
 * 1. CREATION - Source is created (collection, array, generator)
 * 2. INTERMEDIATE OPERATIONS - Transformations (lazy, return new Stream)
 * 3. TERMINAL OPERATION - Produces result (eager, consumes the Stream)
 *
 * KEY RULE: A Stream can only be consumed ONCE.
 * After a terminal operation, the Stream is closed and cannot be reused.
 */

public class Lesson02_StreamLifecycle {

    public static void main(String[] args) {
        System.out.println("=== LESSON 2: Stream Lifecycle ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. STREAM CREATION
        // ============================================
        System.out.println("--- 1. Stream Creation ---");

        // From Collection
        java.util.stream.Stream<Employee> stream1 = employees.stream();
        System.out.println("From collection: " + stream1.count());

        // From List.of() (Java 9+)
        java.util.stream.Stream<Employee> stream2 = Stream.of(
                new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true)
        );
        System.out.println("From List.of: " + stream2.count());

        // From array
        Employee[] empArray = employees.toArray(new Employee[0]);
        java.util.stream.Stream<Employee> stream3 = java.util.Arrays.stream(empArray);
        System.out.println("From array: " + stream3.count());

        // From Stream.generate() (infinite stream)
        java.util.stream.Stream<Integer> infiniteStream = java.util.stream.Stream.generate(() -> 1);
        System.out.println("First 3 from infinite: " +
                infiniteStream.limit(3).toList());

        // From Stream.iterate() (like a loop)
        java.util.stream.Stream<Integer> iteratedStream = java.util.stream.Stream.iterate(0, n -> n + 1);
        System.out.println("First 5 from iterate: " +
                iteratedStream.limit(5).toList());

        // ============================================
        // 2. INTERMEDIATE OPERATIONS (Lazy)
        // ============================================
        System.out.println("\n--- 2. Intermediate Operations (Lazy) ---");

        // These DON'T execute until a terminal operation is called
        java.util.stream.Stream<String> lazyStream = employees.stream()
                .filter(Employee::active)           // Intermediate
                .filter(emp -> "Engineering".equals(emp.department()))  // Intermediate
                .map(emp -> emp.name().toUpperCase());  // Intermediate

        // Nothing executed yet! No output, no processing
        System.out.println("Stream created but not executed");

        // Now execute with terminal operation
        List<String> result = lazyStream.toList();  // Terminal
        System.out.println("Result: " + result);

        // ============================================
        // 3. TERMINAL OPERATIONS (Eager)
        // ============================================
        System.out.println("\n--- 3. Terminal Operations (Eager) ---");

        // Terminal operations trigger execution
        long count = employees.stream()
                .filter(emp -> emp.active())
                .count();  // Terminal - executes the pipeline
        System.out.println("Active count: " + count);

        // ============================================
        // 4. STREAM CAN ONLY BE USED ONCE
        // ============================================
        System.out.println("\n--- 4. Stream Can Only Be Used Once ---");

        java.util.stream.Stream<Employee> singleUseStream = employees.stream()
                .filter(emp -> emp.active());

        // First use - OK
        long firstCount = singleUseStream.count();
        System.out.println("First count: " + firstCount);

        // Second use - throws IllegalStateException
        try {
            long secondCount = singleUseStream.count();
            System.out.println("Second count: " + secondCount);
        } catch (IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        // CORRECT: Create a new stream each time
        long correctFirst = employees.stream().filter(emp -> emp.active()).count();
        long correctSecond = employees.stream().filter(emp -> emp.active()).count();
        System.out.println("Correct - First: " + correctFirst + ", Second: " + correctSecond);

        // ============================================
        // 5. STREAM PIPELINE VISUALIZATION
        // ============================================
        System.out.println("\n--- 5. Stream Pipeline ---");

        // employees.stream()           -> Source
        //   .filter(...)               -> Intermediate
        //   .filter(...)               -> Intermediate
        //   .map(...)                  -> Intermediate
        //   .toList()                  -> Terminal

        List<String> pipelineResult = employees.stream()           // Source
                .filter(emp -> emp.active())                       // Intermediate 1
                .filter(emp -> "Engineering".equals(emp.department()))  // Intermediate 2
                .map(emp -> emp.name().toUpperCase())              // Intermediate 3
                .toList();                                         // Terminal
        System.out.println("Pipeline result: " + pipelineResult);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Reusing a stream
        java.util.stream.Stream<Employee> badStream = employees.stream();
        badStream.forEach(emp -> System.out.println(emp.name()));
        // badStream.forEach(emp -> System.out.println(emp.name())); // ERROR: stream already consumed

        // Mistake 2: Modifying source during stream operation
        List<Employee> modifiableList = new ArrayList<>(employees);
        try {
            modifiableList.stream()
                    .forEach(emp -> {
                        if (emp.id() == 1) {
                            modifiableList.remove(emp); // ConcurrentModificationException!
                        }
                    });
        } catch (Exception e) {
            System.out.println("Concurrent modification error: " + e.getClass().getSimpleName());
        }

        // Correct: Use filter to create new list
        List<Employee> safeFiltered = employees.stream()
                .filter(emp -> emp.id() != 1)
                .toList();
        System.out.println("Safe filtered count: " + safeFiltered.size());
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
