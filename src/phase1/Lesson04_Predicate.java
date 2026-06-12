package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * LESSON 4: Predicate
 *
 * THEORY:
 * Predicate<T> is a functional interface that represents a boolean-valued function
 * of one argument. It tests a condition and returns true or false.
 *
 * METHOD: boolean test(T t)
 *
 * WHY IT EXISTS:
 * - Used extensively for filtering in Streams
 * - Can be combined with and(), or(), negate() for complex conditions
 * - Replaces verbose if-else conditions
 *
 * COMMON METHODS:
 * - test(T t)           : Evaluate predicate
 * - and(Predicate p)    : Logical AND
 * - or(Predicate p)     : Logical OR
 * - negate()            : Logical NOT
 * - isEqual(Object obj) : Equality check
 * - not(Predicate p)    : Static method for negation
 */

public class Lesson04_Predicate {

    public static void main(String[] args) {
        System.out.println("=== LESSON 4: Predicate ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC PREDICATE
        // ============================================
        System.out.println("--- 1. Basic Predicate ---");

        // Predicate to check if salary > 80000
        Predicate<Employee> highEarner = emp ->
                emp.salary().compareTo(new BigDecimal("80000")) > 0;

        System.out.println("High earners:");
        employees.stream()
                .filter(highEarner)
                .forEach(emp -> System.out.println("  " + emp.name() + " - " + emp.salary()));

        // ============================================
        // 2. PREDICATE COMBINATIONS
        // ============================================
        System.out.println("\n--- 2. Predicate Combinations ---");

        Predicate<Employee> isEngineer = emp -> "Engineering".equals(emp.department());
        Predicate<Employee> isActive = Employee::isActive;
        Predicate<Employee> isSenior = emp -> emp.age() > 30;

        // AND - Both conditions must be true
        System.out.println("Active Engineers:");
        employees.stream()
                .filter(isEngineer.and(isActive))
                .forEach(emp -> System.out.println("  " + emp.name()));

        // OR - Either condition true
        System.out.println("Engineers OR Seniors:");
        employees.stream()
                .filter(isEngineer.or(isSenior))
                .forEach(emp -> System.out.println("  " + emp.name()));

        // NEGATE - Opposite condition
        System.out.println("Non-Engineers:");
        employees.stream()
                .filter(isEngineer.negate())
                .forEach(emp -> System.out.println("  " + emp.name()));

        // Complex combination
        System.out.println("Active Senior Engineers:");
        employees.stream()
                .filter(isEngineer.and(isActive).and(isSenior))
                .forEach(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 3. PREDICATE WITH isEqual
        // ============================================
        System.out.println("\n--- 3. Predicate.isEqual ---");

        Predicate<Employee> isAlice = Predicate.isEqual(
                new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true)
        );

        // Note: This uses equals() method, so Employee needs proper equals() implementation
        // For demo, we'll use a simpler approach
        Predicate<String> isEmpty = Predicate.isEqual("");
        System.out.println("Is empty string: " + isEmpty.test(""));
        System.out.println("Is empty string: " + isEmpty.test("hello"));

        // ============================================
        // 4. PREDICATE NOT (static method)
        // ============================================
        System.out.println("\n--- 4. Predicate.not ---");

        // Using static not() method (Java 11+)
        employees.stream()
                .filter(Predicate.not(isActive))
                .forEach(emp -> System.out.println("Inactive: " + emp.name()));

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge: Filter Complex Conditions ---");

        // Challenge: Find employees who are:
        // - In Engineering OR Sales department
        // - AND salary > 70000
        // - AND age < 35

        Predicate<Employee> inEngOrSales = emp ->
                "Engineering".equals(emp.department()) || "Sales".equals(emp.department());
        Predicate<Employee> salaryAbove70k = emp ->
                emp.salary().compareTo(new BigDecimal("70000")) > 0;
        Predicate<Employee> ageBelow35 = emp -> emp.age() < 35;

        System.out.println("Qualified employees (Eng/Sales, >70k, <35):");
        employees.stream()
                .filter(inEngOrSales.and(salaryAbove70k).and(ageBelow35))
                .forEach(emp -> System.out.println("  " + emp.name() + " - " +
                        emp.department() + " - " + emp.salary()));

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Forgetting to return boolean
        // Predicate<Employee> wrong = emp -> { emp.salary(); }; // ERROR: must return boolean

        // Mistake 2: Using && instead of and()
        // employees.stream().filter(highEarner && isActive); // ERROR: can't use && on predicates
        employees.stream().filter(highEarner.and(isActive)); // Correct

        // Mistake 3: Not understanding short-circuit
        // and() and or() are short-circuiting for Predicate
        Predicate<Employee> alwaysFalse = emp -> false;
        // This won't evaluate the second predicate if first is false
        employees.stream().filter(alwaysFalse.and(isEngineer));
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
