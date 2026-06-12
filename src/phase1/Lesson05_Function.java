package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * LESSON 5: Function<T, R>
 *
 * THEORY:
 * Function<T, R> is a functional interface that accepts one argument and produces a result.
 * It's used for TRANSFORMATION - converting one type to another.
 *
 * METHOD: R apply(T t)
 *
 * WHY IT EXISTS:
 * - Used for mapping/transforming data in Streams
 * - Can be composed with andThen() and compose()
 * - Replaces verbose transformation code
 *
 * SPECIALIZED VARIANTS:
 * - UnaryOperator<T>  : Function<T, T> (same input/output type)
 * - BiFunction<T,U,R> : Two inputs, one output
 */

public class Lesson05_Function {

    public static void main(String[] args) {
        System.out.println("=== LESSON 5: Function<T, R> ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC FUNCTION
        // ============================================
        System.out.println("--- 1. Basic Function ---");

        // Function to get employee name
        Function<Employee, String> getName = Employee::name;
        System.out.println("First employee name: " + getName.apply(employees.get(0)));

        // Function to get salary
        Function<Employee, BigDecimal> salary = Employee::salary;
        System.out.println("First employee salary: " + salary.apply(employees.get(0)));

        // Function to convert salary to string with currency
        Function<BigDecimal, String> formatCurrency = salary2 -> "$" + salary2.toString();
        System.out.println("Formatted salary: " + formatCurrency.apply(salary.apply(employees.get(0))));

        // ============================================
        // 2. FUNCTION COMPOSITION
        // ============================================
        System.out.println("\n--- 2. Function Composition ---");

        // andThen() - apply first function, then second
        Function<Employee, String> nameAndSalary = getName.andThen(name -> name + " earns " + salary.apply(employees.get(0)));
        // Note: andThen chains functions where output of first becomes input of second
        // Since getName returns String, we can't chain salary after it directly

        // Better example: Transform Employee -> Salary -> String
        Function<Employee, BigDecimal> empToSalary = Employee::salary;
        Function<BigDecimal, String> salaryToString = s -> "$" + s;
        Function<Employee, String> empToSalaryString = empToSalary.andThen(salaryToString);

        System.out.println("Employee salary as string: " + empToSalaryString.apply(employees.get(0)));

        // compose() - apply second function first, then first
        Function<String, Integer> length = String::length;
        Function<Integer, String> toString = Object::toString;
        Function<String, String> lengthAsString = length.compose(toString);
        // compose means: toString.apply(length.apply("Hello")) = "5"

        // ============================================
        // 3. FUNCTION WITH STREAMS
        // ============================================
        System.out.println("\n--- 3. Function with Streams ---");

        // map() uses Function internally
        List<String> names = employees.stream()
                .map(Employee::name)
                .toList();
        System.out.println("Names: " + names);

        // Transform to salary strings
        List<String> salaryStrings = employees.stream()
                .map(emp -> "$" + emp.salary())
                .toList();
        System.out.println("Salaries: " + salaryStrings);

        // ============================================
        // 4. UNARYOPERATOR (Function<T, T>)
        // ============================================
        System.out.println("\n--- 4. UnaryOperator ---");

        java.util.function.UnaryOperator<String> toUpperCase = String::toUpperCase;
        java.util.function.UnaryOperator<String> addPrefix = s -> "Mr./Ms. " + s;

        // Chain UnaryOperators
        java.util.function.UnaryOperator<String> formalName = toUpperCase.andThen(addPrefix);
        // or: addPrefix.compose(toUpperCase)

        List<String> formalNames = employees.stream()
                .map(Employee::name)
                .map(formalName)
                .toList();
        System.out.println("Formal names: " + formalNames);

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge: Transform Employee Data ---");

        // Challenge: Create a function that takes Employee and returns
        // "Name works in Department and earns $Salary"
        Function<Employee, String> employeeSummary = emp ->
                emp.name() + " works in " + emp.department() +
                " and earns $" + emp.salary();

        List<String> summaries = employees.stream()
                .map(employeeSummary)
                .toList();
        summaries.forEach(s -> System.out.println("  " + s));

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Wrong type in composition
        // Function<Employee, String> wrong = getName.andThen(salary); // ERROR: String -> BigDecimal
        Function<Employee, BigDecimal> correct = empToSalary.andThen(s -> s); // OK: BigDecimal -> BigDecimal

        // Mistake 2: Forgetting that andThen/compose return new Function
        Function<Employee, String> f1 = Employee::name;
        Function<String, String> f2 = String::toUpperCase;
        // f1.andThen(f2) returns Function<Employee, String>, not modifying f1
        Function<Employee, String> combined = f1.andThen(f2);
        System.out.println("Combined: " + combined.apply(employees.get(0)));
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
