package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 2: Lambda Expressions
 *
 * THEORY:
 * A lambda expression is a concise way to represent an anonymous function.
 * It allows you to write inline implementations of functional interfaces.
 *
 * WHY IT EXISTS:
 * - Reduces boilerplate code (no need for anonymous inner classes)
 * - Enables functional programming in Java
 * - Makes code more readable and maintainable
 * - Essential for Stream API operations
 *
 * SYNTAX:
 * (parameters) -> expression
 * (parameters) -> { statements; }
 *
 * RULES:
 * 1. Parameters can be omitted if type can be inferred
 * 2. Parentheses can be omitted for single parameter
 * 3. Curly braces can be omitted for single expression
 * 4. Return keyword can be omitted for single expression
 */

public class Lesson02_LambdaExpressions {

    public static void main(String[] args) {
        System.out.println("=== LESSON 2: Lambda Expressions ===\n");

        // ============================================
        // 1. NO PARAMETERS
        // ============================================
        System.out.println("--- 1. No Parameters ---");
        Runnable noParams = () -> System.out.println("Hello from lambda!");
        noParams.run();

        // ============================================
        // 2. SINGLE PARAMETER (parentheses optional)
        // ============================================
        System.out.println("\n--- 2. Single Parameter ---");
        // With parentheses
        java.util.function.Consumer<String> withParens = (name) -> System.out.println("Hello, " + name);
        // Without parentheses (only for single parameter)
        java.util.function.Consumer<String> withoutParens = name -> System.out.println("Hello, " + name);
        withParens.accept("Alice");
        withoutParens.accept("Bob");

        // ============================================
        // 3. MULTIPLE PARAMETERS
        // ============================================
        System.out.println("\n--- 3. Multiple Parameters ---");
        java.util.function.BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("5 + 3 = " + add.apply(5, 3));

        // ============================================
        // 4. WITH BLOCK BODY (curly braces required)
        // ============================================
        System.out.println("\n--- 4. Block Body ---");
        java.util.function.BiFunction<Integer, Integer, Integer> multiply = (a, b) -> {
            int result = a * b;
            System.out.println("Multiplying " + a + " * " + b);
            return result;
        };
        System.out.println("Result: " + multiply.apply(4, 5));

        // ============================================
        // 5. LAMBDA WITH STREAMS - REAL WORLD EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Lambda with Streams ---");
        List<Employee> employees = createSampleEmployees();

        // Before Java 8: Anonymous inner class
        System.out.println("Before Java 8 (verbose):");
//        employees.forEach(new java.util.ArrayList<Employee>() {{
//            // This is just to show the old way - not practical
//        }});

        // Java 8+: Lambda
        System.out.println("Java 8+ with lambda:");
        employees.forEach(emp -> System.out.println("  " + emp.name() + " - " + emp.department()));

        // ============================================
        // 6. LAMBDA VARIATIONS COMPARISON
        // ============================================
        System.out.println("\n--- 6. Lambda Variations ---");

        // Filter with lambda
        List<Employee> engineers = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .toList();
        System.out.println("Engineers: " + engineers.size());

        // Map with lambda
        List<String> names = employees.stream()
                .map(emp -> emp.name().toUpperCase())
                .toList();
        System.out.println("Names: " + names);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Using return with expression body
        // java.util.function.Function<Integer, Integer> wrong = (x) -> { return x * 2; }; // OK with braces
        // java.util.function.Function<Integer, Integer> wrong2 = (x) -> return x * 2; // WRONG - no braces

        // Mistake 2: Modifying external variable (must be effectively final)
        // int counter = 0;
        // employees.forEach(emp -> counter++); // COMPILE ERROR - counter not effectively final

        // Correct way:
        final int[] counter = {0};
        employees.forEach(emp -> counter[0]++);
        System.out.println("Employee count via array: " + counter[0]);

        // Mistake 3: Wrong parameter types
        // employees.stream().filter((Employee emp) -> emp.salary().compareTo(...) > 0); // Redundant type
        employees.stream().filter(emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0); // Correct - inferred
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
