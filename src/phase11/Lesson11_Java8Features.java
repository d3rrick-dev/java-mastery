package phase11;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * LESSON 11: JAVA 8 FEATURES
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Java 8 (released 2014) was a major update with:
 * - Lambda expressions
 * - Stream API
 * - Functional interfaces
 * - Default methods
 * - Optional class
 * - New Date/Time API
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Functional programming support
 * - More concise code
 * - Better multicore utilization
 * - Modern API design
 */

public class Lesson11_Java8Features {

    public static void main(String[] args) {
        System.out.println("=== JAVA 8 FEATURES ===\n");

        // ============================================================
        // EXAMPLE 1: Lambda expressions
        // ============================================================
        System.out.println("--- Example 1: Lambda Expressions ---\n");

        // Before Java 8
        Runnable oldRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Old style: Running");
            }
        };

        // Java 8+
        Runnable newRunnable = () -> System.out.println("Lambda: Running");

        oldRunnable.run();
        newRunnable.run();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Functional interfaces
        // ============================================================
        System.out.println("--- Example 2: Functional Interfaces ---\n");

        // Predicate
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4));

        // Function
        Function<Integer, String> intToString = n -> "Number: " + n;
        System.out.println(intToString.apply(42));

        // Consumer
        Consumer<String> printer = s -> System.out.println("Print: " + s);
        printer.accept("Hello");

        // Supplier
        Supplier<Double> random = () -> Math.random();
        System.out.println("Random: " + random.get());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Stream API
        // ============================================================
        System.out.println("--- Example 3: Stream API ---\n");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Filter, map, collect
        List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .toList();

        System.out.println("Even squares: " + evenSquares);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Optional
        // ============================================================
        System.out.println("--- Example 4: Optional ---\n");

        Optional<String> optional = Optional.of("Hello");
        Optional<String> empty = Optional.empty();

        System.out.println("Value: " + optional.orElse("Default"));
        System.out.println("Empty: " + empty.orElse("Default"));
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Default methods
        // ============================================================
        System.out.println("--- Example 5: Default Methods ---\n");

        System.out.println("Interfaces can have default implementations:");
        System.out.println("  interface Vehicle {");
        System.out.println("    default void start() {");
        System.out.println("      System.out.println(\"Starting...\");");
        System.out.println("    }");
        System.out.println("  }");
        System.out.println();
    }

    // ============================================================
    // JAVA 8 FEATURES SUMMARY
    // ============================================================
    /*
     * Key Java 8 Features:
     *
     * 1. Lambda Expressions
     *    - (params) -> expression
     *    - (params) -> { statements; }
     *    - Enables functional programming
     *
     * 2. Functional Interfaces
     *    - Single abstract method interface
     *    - @FunctionalInterface annotation
     *    - Built-in: Predicate, Function, Consumer, Supplier
     *
     * 3. Stream API
     *    - Process sequences of elements
     *    - Lazy evaluation
     *    - Parallel support
     *
     * 4. Optional
     *    - Container for nullable values
     *    - Avoid NullPointerException
     *
     * 5. Default Methods
     *    - Methods with implementation in interfaces
     *    - Backward compatibility
     *
     * 6. New Date/Time API (java.time)
     *    - LocalDate, LocalTime, LocalDateTime
     *    - Immutable, thread-safe
     */
}
