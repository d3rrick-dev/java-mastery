package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 8: Method References
 *
 * THEORY:
 * Method References provide a way to refer to a method without invoking it.
 * They are shorthand for lambda expressions that call a single method.
 *
 * WHY IT EXISTS:
 * - Makes code more concise and readable
 * - Reuses existing method implementations
 * - Clearer intent than lambdas for simple cases
 *
 * TYPES:
 * 1. Reference to a static method:     ClassName::staticMethod
 * 2. Reference to an instance method:  object::instanceMethod
 * 3. Reference to an instance method of arbitrary object: ClassName::instanceMethod
 * 4. Reference to a constructor:       ClassName::new
 */

public class Lesson08_MethodReferences {

    public static void main(String[] args) {
        System.out.println("=== LESSON 8: Method References ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. STATIC METHOD REFERENCE
        // ============================================
        System.out.println("--- 1. Static Method Reference ---");

        // Lambda: emp -> Employee.department(emp)
        // Method ref: Employee::department (if static)
        // For instance methods: emp -> emp.department() == Employee::department

        List<String> departments = employees.stream()
                .map(Employee::department)  // Instance method reference
                .distinct()
                .toList();
        System.out.println("Departments: " + departments);

        // Static method example
        java.util.function.Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println("ParseInt: " + parseInt.apply("42"));

        // ============================================
        // 2. INSTANCE METHOD REFERENCE (bound)
        // ============================================
        System.out.println("\n--- 2. Instance Method Reference (Bound) ---");

        String prefix = "EMP-";
        java.util.function.Function<String, String> addPrefix = prefix::concat;
        System.out.println("With prefix: " + addPrefix.apply("001"));

        // ============================================
        // 3. INSTANCE METHOD OF ARBITRARY OBJECT
        // ============================================
        System.out.println("\n--- 3. Instance Method of Arbitrary Object ---");

        // String::toLowerCase - applies to each element
        List<String> upperNames = employees.stream()
                .map(Employee::name)
                .map(String::toUpperCase)
                .toList();
        System.out.println("Upper names: " + upperNames);

        // ============================================
        // 4. CONSTRUCTOR REFERENCE
        // ============================================
        System.out.println("\n--- 4. Constructor Reference ---");

        // Supplier<Employee> that creates new Employee
        java.util.function.Supplier<Employee> employeeSupplier = () -> createSampleEmployees().getFirst();
        Employee newEmp = employeeSupplier.get();
        System.out.println("New employee: " + newEmp.name());

        // Function with parameters
        // java.util.function.Function<String, Integer> stringLength = String::new; // Not useful, but valid
        // Better example:
        java.util.function.Function<String, java.math.BigDecimal> bigDecimalSupplier = java.math.BigDecimal::new;
        System.out.println("BigDecimal: " + bigDecimalSupplier.apply("123.45"));

        // ============================================
        // 5. COMPARISON: LAMBDA vs METHOD REFERENCE
        // ============================================
        System.out.println("\n--- 5. Lambda vs Method Reference ---");

        // Lambda version
        List<String> namesLambda = employees.stream()
                .map(emp -> emp.name())
                .toList();

        // Method reference version (same result, more concise)
        List<String> namesMethodRef = employees.stream()
                .map(Employee::name)
                .toList();

        System.out.println("Both produce same result: " + namesLambda.equals(namesMethodRef));

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Print all active employees using method references
        System.out.println("Active employees:");
        employees.stream()
                .filter(Employee::isActive)
                .map(Employee::name)
                .forEach(name -> System.out.println("  " + name));

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Wrong method signature
        // employees.stream().map(Employee::name); // OK: getName() takes no args
        // employees.stream().map(Employee::setName); // ERROR: setName takes String arg

        // Mistake 2: Confusing instance method reference types
        // obj::method (bound) vs Class::method (unbound)
        Employee alice = employees.get(0);
        java.util.function.Supplier<String> bound = alice::name;  // Always returns Alice's name
        java.util.function.Function<Employee, String> unbound = Employee::name;  // Takes Employee param
        System.out.println("Bound: " + bound.get());
        System.out.println("Unbound with Alice: " + unbound.apply(alice));

        // Mistake 3: Constructor reference with wrong signature
        // Supplier<Employee> wrong = Employee::new; // OK: no-arg constructor
        // Function<Integer, Employee> alsoOk = Employee::new; // OK: if constructor takes int
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
