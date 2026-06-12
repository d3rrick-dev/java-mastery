package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * LESSON 6: Consumer<T>
 *
 * THEORY:
 * Consumer<T> is a functional interface that accepts one argument and returns nothing.
 * It's used for CONSUMPTION - performing operations on data without returning a value.
 *
 * METHOD: void accept(T t)
 *
 * WHY IT EXISTS:
 * - Used for side-effect operations (printing, logging, saving to DB)
 * - Used in forEach() terminal operation
 * - Can be chained with andThen()
 *
 * SPECIALIZED VARIANTS:
 * - BiConsumer<T, U> : Accepts two arguments, returns nothing
 */

public class Lesson06_Consumer {

    public static void main(String[] args) {
        System.out.println("=== LESSON 6: Consumer<T> ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC CONSUMER
        // ============================================
        System.out.println("--- 1. Basic Consumer ---");

        // Consumer to print employee name
        Consumer<Employee> printName = emp -> System.out.println(emp.name());
        printName.accept(employees.get(0));

        // Consumer to print full details
        Consumer<Employee> printDetails = emp ->
                System.out.println(emp.name() + " | " + emp.department() + " | " + emp.salary());
        printDetails.accept(employees.get(0));

        // ============================================
        // 2. CONSUMER CHAINING (andThen)
        // ============================================
        System.out.println("\n--- 2. Consumer Chaining ---");

        Consumer<Employee> printNameAndDept = emp ->
                System.out.println("Name: " + emp.name() + ", Dept: " + emp.department());

        Consumer<Employee> printSalary = emp ->
                System.out.println("Salary: " + emp.salary());

        // Chain consumers
        Consumer<Employee> printAll = printNameAndDept.andThen(emp ->
                System.out.println("Age: " + emp.age()));

        System.out.println("Chained consumer output:");
        printAll.accept(employees.get(0));

        // ============================================
        // 3. CONSUMER WITH STREAMS
        // ============================================
        System.out.println("\n--- 3. Consumer with Streams ---");

        // forEach uses Consumer internally
        System.out.println("All employees:");
        employees.stream()
                .forEach(emp -> System.out.println("  " + emp.name()));

        // Filter then consume
        System.out.println("\nHigh earners:");
        employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0)
                .forEach(emp -> System.out.println("  " + emp.name() + " - " + emp.salary()));

        // ============================================
        // 4. BICONSUMER
        // ============================================
        System.out.println("\n--- 4. BiConsumer ---");

        // BiConsumer takes two arguments
        java.util.function.BiConsumer<String, BigDecimal> printSalaryInfo =
                (name, salary) -> System.out.println(name + " earns " + salary);

        // Using with map entry
        System.out.println("Salary info:");
        employees.stream()
                .limit(3)
                .forEach(emp -> printSalaryInfo.accept(emp.name(), emp.salary()));

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge: Custom Logging ---");

        // Challenge: Create a consumer that logs employee info in a specific format
        Consumer<Employee> logger = emp -> {
            String status = emp.active() ? "ACTIVE" : "INACTIVE";
            System.out.println(String.format("[%s] %s (%s) - $%s - %s",
                    status, emp.name(), emp.department(),
                    emp.salary(), emp.age() + " years old"));
        };

        System.out.println("Employee log:");
        employees.forEach(logger);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Trying to return a value
        // Consumer<Employee> wrong = emp -> { return emp.name(); }; // ERROR: void return type

        // Mistake 2: Modifying external state (side effects)
        List<String> names = new ArrayList<>();
        employees.forEach(emp -> names.add(emp.name())); // Works but not pure FP
        System.out.println("Names collected: " + names);

        // Better: Use map() for transformation
        List<String> betterNames = employees.stream()
                .map(Employee::name)
                .toList();
        System.out.println("Better way: " + betterNames);
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
