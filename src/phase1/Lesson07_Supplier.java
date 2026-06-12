package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * LESSON 7: Supplier<T>
 *
 * THEORY:
 * Supplier<T> is a functional interface that takes NO arguments and returns a value.
 * It's used for SUPPLYING data - generating or providing values on demand.
 *
 * METHOD: T get()
 *
 * WHY IT EXISTS:
 * - Used for lazy evaluation (value generated only when needed)
 * - Used for factory patterns
 * - Used in Stream.generate() for infinite streams
 * - Used for default values
 *
 * SPECIALIZED VARIANTS:
 * - BooleanSupplier  : get() -> boolean
 * - IntSupplier      : get() -> int
 * - LongSupplier     : get() -> long
 * - DoubleSupplier   : get() -> double
 */

public class Lesson07_Supplier {

    public static void main(String[] args) {
        System.out.println("=== LESSON 7: Supplier<T> ===\n");

        // ============================================
        // 1. BASIC SUPPLIER
        // ============================================
        System.out.println("--- 1. Basic Supplier ---");

        // Supplier that generates random numbers
        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("Random 1: " + randomSupplier.get());
        System.out.println("Random 2: " + randomSupplier.get());
        System.out.println("Random 3: " + randomSupplier.get());

        // Supplier that generates current timestamp
        Supplier<Long> timestampSupplier = System::currentTimeMillis;
        System.out.println("Timestamp: " + timestampSupplier.get());

        // ============================================
        // 2. SUPPLIER FOR OBJECT CREATION
        // ============================================
        System.out.println("\n--- 2. Supplier for Object Creation ---");

        // Supplier to create Employee objects
        Supplier<Employee> employeeSupplier = () ->
                new Employee(0, "New Employee", "TBD", BigDecimal.ZERO, 0, LocalDate.now(), false);

        Employee emp1 = employeeSupplier.get();
        Employee emp2 = employeeSupplier.get();
        System.out.println("Employee 1: " + emp1.name());
        System.out.println("Employee 2: " + emp2.name());

        // ============================================
        // 3. SUPPLIER WITH STREAMS
        // ============================================
        System.out.println("\n--- 3. Supplier with Streams ---");

        // Stream.generate() uses Supplier
        System.out.println("First 5 random numbers:");
        java.util.stream.Stream.generate(randomSupplier)
                .limit(5)
                .forEach(num -> System.out.println("  " + num));

        // Generate sequence
        Supplier<Integer> counter = new java.util.concurrent.atomic.AtomicInteger(0)::getAndIncrement;
        System.out.println("\nSequence 0-4:");
        java.util.stream.Stream.generate(counter)
                .limit(5)
                .forEach(num -> System.out.println("  " + num));

        // ============================================
        // 4. SUPPLIER FOR DEFAULT VALUES
        // ============================================
        System.out.println("\n--- 4. Supplier for Default Values ---");

        // Get default employee if list is empty
        Supplier<Employee> defaultEmployee = () ->
                new Employee(-1, "DEFAULT", "N/A", BigDecimal.ZERO, 0, LocalDate.now(), false);

        List<Employee> emptyList = new ArrayList<>();
        Employee emp = emptyList.stream()
                .findFirst()
                .orElseGet(defaultEmployee);  // Uses Supplier
        System.out.println("Default employee: " + emp.name());

        // ============================================
        // 5. SPECIALIZED SUPPLIERS
        // ============================================
        System.out.println("\n--- 5. Specialized Suppliers ---");

        // IntSupplier
        java.util.function.IntSupplier intSupplier = () -> (int) (Math.random() * 100);
        System.out.println("Random int: " + intSupplier.getAsInt());

        // BooleanSupplier
        java.util.function.BooleanSupplier boolSupplier = () -> Math.random() > 0.5;
        System.out.println("Random boolean: " + boolSupplier.getAsBoolean());

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge: Generate Test Data ---");

        // Challenge: Create a supplier that generates test Employee objects
        Supplier<Employee> testDataSupplier = () -> {
            String[] names = {"Test1", "Test2", "Test3"};
            String[] depts = {"IT", "HR", "Sales"};
            int idx = (int) (Math.random() * names.length);
            return new Employee(
                    (int) (Math.random() * 1000),
                    names[idx],
                    depts[idx],
                    new BigDecimal(50000 + Math.random() * 100000),
                    22 + (int) (Math.random() * 30),
                    LocalDate.now(),
                    Math.random() > 0.3
            );
        };

        System.out.println("Generated test employees:");
        java.util.stream.Stream.generate(testDataSupplier)
                .limit(3)
                .forEach(emp -> System.out.println("  " + emp));

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Supplier with parameters
        // Supplier<String> wrong = (s) -> s.toUpperCase(); // ERROR: Supplier takes no params
        java.util.function.Function<String, String> correct = String::toUpperCase;

        // Mistake 2: Not understanding lazy evaluation
        // Supplier is lazy - code inside only runs when get() is called
        Supplier<String> lazy = () -> {
            System.out.println("This prints only when get() is called");
            return "value";
        };
        System.out.println("Before get()");
        String val = lazy.get();  // Prints here
        System.out.println("After get(): " + val);
    }
}
