package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 3: Functional Interfaces
 *
 * THEORY:
 * A Functional Interface is an interface with exactly ONE abstract method.
 * It can have any number of default, static, and private methods.
 *
 * WHY IT EXISTS:
 * - Lambda expressions can only be used where a functional interface is expected
 * - Provides the target type for lambda expressions
 * - Java 8 introduced @FunctionalInterface annotation to enforce the single abstract method rule
 *
 * BUILT-IN FUNCTIONAL INTERFACES (java.util.function):
 * - Predicate<T>     : test(T t) -> boolean          (filtering)
 * - Function<T, R>   : apply(T t) -> R               (transformation)
 * - Consumer<T>      : accept(T t) -> void           (consumption)
 * - Supplier<T>      : get() -> T                    (supplying)
 * - BiFunction<T,U,R>: apply(T t, U u) -> R          (two-arg transformation)
 * - UnaryOperator<T> : apply(T t) -> T               (same-type transformation)
 * - BinaryOperator<T>: apply(T a, T b) -> T          (two-arg same-type)
 */

// Custom Functional Interface
@FunctionalInterface
interface MyCalculator {
    int calculate(int a, int b);  // Single abstract method

    // Can have default methods
    default void printResult(int result) {
        System.out.println("Result: " + result);
    }

    // Can have static methods
    static int add(int a, int b) {
        return a + b;
    }
}

// Another custom functional interface
@FunctionalInterface
interface StringValidator {
    boolean validate(String s);
}

public class Lesson03_FunctionalInterfaces {

    public static void main(String[] args) {
        System.out.println("=== LESSON 3: Functional Interfaces ===\n");

        // ============================================
        // 1. CUSTOM FUNCTIONAL INTERFACE
        // ============================================
        System.out.println("--- 1. Custom Functional Interface ---");

        // Using lambda with custom interface
        MyCalculator add = (a, b) -> a + b;
        MyCalculator multiply = (a, b) -> a * b;
        MyCalculator subtract = (a, b) -> a - b;

        System.out.println("10 + 5 = " + add.calculate(10, 5));
        System.out.println("10 * 5 = " + multiply.calculate(10, 5));
        System.out.println("10 - 5 = " + subtract.calculate(10, 5));

        // Using default method
        add.printResult(add.calculate(10, 5));

        // Using static method
        System.out.println("Static method: " + MyCalculator.add(10, 5));

        // ============================================
        // 2. BUILT-IN FUNCTIONAL INTERFACES
        // ============================================
        System.out.println("\n--- 2. Built-in Functional Interfaces ---");

        // Predicate - returns boolean
        java.util.function.Predicate<String> isEmpty = s -> s.isEmpty();
        System.out.println("Is empty: " + isEmpty.test(""));
        System.out.println("Is empty: " + isEmpty.test("hello"));

        // Function - transforms input to output
        java.util.function.Function<String, Integer> length = s -> s.length();
        System.out.println("Length of 'Java': " + length.apply("Java"));

        // Consumer - consumes input, returns nothing
        java.util.function.Consumer<String> printer = s -> System.out.println("Printing: " + s);
        printer.accept("Hello World");

        // Supplier - supplies output, takes no input
        java.util.function.Supplier<Double> random = () -> Math.random();
        System.out.println("Random number: " + random.get());

        // BiFunction - two inputs, one output
        java.util.function.BiFunction<String, String, String> concat = (a, b) -> a + " " + b;
        System.out.println("Concat: " + concat.apply("Hello", "World"));

        // ============================================
        // 3. FUNCTIONAL INTERFACE WITH STREAMS
        // ============================================
        System.out.println("\n--- 3. With Streams ---");
        List<Employee> employees = createSampleEmployees();

        // Predicate in filter
        List<Employee> engineers = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .toList();
        System.out.println("Engineers: " + engineers.size());

        // Function in map
        List<String> upperNames = employees.stream()
                .map(emp -> emp.name().toUpperCase())
                .toList();
        System.out.println("Upper names: " + upperNames);

        // Consumer in forEach
        employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0)
                .forEach(emp -> System.out.println("High earner: " + emp.name()));

        // ============================================
        // 4. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 4. Common Mistakes ---");

        // Mistake 1: Multiple abstract methods
        // @FunctionalInterface
        // interface BadInterface {
        //     void method1();
        //     void method2();  // ERROR: Two abstract methods
        // }

        // Mistake 2: Wrong parameter count
        // java.util.function.Function<String, Integer> wrong = (a, b) -> a.length(); // ERROR: Function takes 1 param
        java.util.function.BiFunction<String, String, Integer> correct = (a, b) -> (a + b).length();
        System.out.println("Combined length: " + correct.apply("Hello", "World"));

        // Mistake 3: Forgetting return type
        // java.util.function.Function<Integer, Integer> wrong = x -> { x * 2; }; // ERROR: missing return
        java.util.function.Function<Integer, Integer> correct2 = x -> x * 2;
        System.out.println("Double 5: " + correct2.apply(5));
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
