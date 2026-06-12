package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * LESSON 9: Optional
 *
 * THEORY:
 * Optional<T> is a container object which may or may not contain a non-null value.
 * It was introduced in Java 8 to prevent NullPointerException.
 *
 * WHY IT EXISTS:
 * - Eliminates NullPointerException (the billion-dollar mistake)
 * - Makes null-handling explicit and intentional
 * - Provides functional-style operations for presence/absence checks
 * - Forces developers to think about the "empty" case
 *
 * KEY METHODS:
 * - of(T value)           : Create Optional with non-null value
 * - ofNullable(T value)   : Create Optional, allows null
 * - empty()               : Create empty Optional
 * - isPresent()           : Check if value exists
 * - get()                 : Get value (throws if empty)
 * - orElse(T other)       : Return value or default
 * - orElseGet(Supplier)   : Return value or compute default
 * - orElseThrow()         : Return value or throw exception
 * - ifPresent(Consumer)   : Execute if value present
 * - map(Function)         : Transform if present
 * - flatMap(Function)     : Transform to Optional if present
 * - filter(Predicate)     : Filter if present
 */

public class Lesson09_Optional {

    public static void main(String[] args) {
        System.out.println("=== LESSON 9: Optional ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. CREATING OPTIONAL
        // ============================================
        System.out.println("--- 1. Creating Optional ---");

        // of() - value must not be null
        Optional<String> nonNull = Optional.of("Hello");
        System.out.println("Non-null: " + nonNull.get());

        // ofNullable() - value can be null
        Optional<String> maybeNull = Optional.ofNullable(getNameMaybeNull(true));
        System.out.println("Maybe null (present): " + maybeNull.orElse("Default"));

        Optional<String> maybeNull2 = Optional.ofNullable(getNameMaybeNull(false));
        System.out.println("Maybe null (empty): " + maybeNull2.orElse("Default"));

        // empty()
        Optional<String> empty = Optional.empty();
        System.out.println("Is empty present? " + empty.isPresent());

        // ============================================
        // 2. CHECKING PRESENCE
        // ============================================
        System.out.println("\n--- 2. Checking Presence ---");

        Optional<Employee> maybeEmp = findEmployee(employees, 1);
        System.out.println("Employee 1 present? " + maybeEmp.isPresent());

        Optional<Employee> noEmp = findEmployee(employees, 999);
        System.out.println("Employee 999 present? " + noEmp.isPresent());

        // ============================================
        // 3. RETRIEVING VALUES
        // ============================================
        System.out.println("\n--- 3. Retrieving Values ---");

        // orElse() - always evaluates default
        Employee emp1 = maybeEmp.orElse(getDefaultEmployee());
        System.out.println("orElse: " + emp1.name());

        // orElseGet() - lazy evaluation (only if empty)
        Employee emp2 = maybeEmp.orElseGet(() -> getDefaultEmployee());
        System.out.println("orElseGet: " + emp2.name());

        // orElseThrow() - throw exception if empty
        try {
            Employee emp3 = noEmp.orElseThrow(() -> new RuntimeException("Employee not found!"));
        } catch (RuntimeException e) {
            System.out.println("orElseThrow: " + e.getMessage());
        }

        // ============================================
        // 4. CONDITIONAL EXECUTION
        // ============================================
        System.out.println("\n--- 4. Conditional Execution ---");

        maybeEmp.ifPresent(emp ->
                System.out.println("Found: " + emp.name() + " - " + emp.department()));

        noEmp.ifPresent(emp ->
                System.out.println("This won't print: " + emp.name()));

        // ============================================
        // 5. TRANSFORMATION
        // ============================================
        System.out.println("\n--- 5. Transformation ---");

        // map() - transform value if present
        Optional<String> nameOpt = maybeEmp.map(Employee::name);
        System.out.println("Mapped name: " + nameOpt.orElse("Unknown"));

        // flatMap() - for nested Optional
        Optional<Optional<String>> nested = maybeEmp.map(emp -> Optional.of(emp.name()));
        Optional<String> flat = maybeEmp.flatMap(emp -> Optional.of(emp.name()));
        System.out.println("FlatMap result: " + flat.orElse("Unknown"));

        // ============================================
        // 6. FILTERING
        // ============================================
        System.out.println("\n--- 6. Filtering ---");

        Optional<Employee> highEarner = maybeEmp.filter(emp ->
                emp.salary().compareTo(new BigDecimal("80000")) > 0);
        System.out.println("High earner present? " + highEarner.isPresent());

        Optional<Employee> lowEarner = maybeEmp.filter(emp ->
                emp.salary().compareTo(new BigDecimal("80000")) < 0);
        System.out.println("Low earner present? " + lowEarner.isPresent());

        // ============================================
        // 7. OPTIONAL WITH STREAMS
        // ============================================
        System.out.println("\n--- 7. Optional with Streams ---");

        // Stream of Optional
        List<Optional<Employee>> optionalEmployees = List.of(
                Optional.of(employees.get(0)),
                Optional.empty(),
                Optional.of(employees.get(1)),
                Optional.empty()
        );

        // Filter present values
        List<Employee> presentEmps = optionalEmployees.stream()
                .flatMap(Optional::stream)  // Java 9+ - converts Optional to Stream
                .toList();
        System.out.println("Present employees: " + presentEmps.size());

        // Alternative for Java 8:
        List<Employee> presentEmpsJava8 = optionalEmployees.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        System.out.println("Present employees (Java 8): " + presentEmpsJava8.size());

        // ============================================
        // 8. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 8. Coding Challenge: Safe Employee Lookup ---");

        // Challenge: Find employee by ID, return formatted string or "Not Found"
        String result = findEmployee(employees, 1)
                .map(emp -> emp.name() + " - " + emp.department())
                .orElse("Not Found");
        System.out.println("Lookup result: " + result);

        // ============================================
        // 9. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 9. Common Mistakes ---");

        // Mistake 1: Using get() without checking
        // String val = noEmp.get(); // Throws NoSuchElementException

        // Mistake 2: Using isPresent() + get() (anti-pattern)
        // if (maybeEmp.isPresent()) {
        //     System.out.println(maybeEmp.get().name()); // Verbose
        // }
        // Better:
        maybeEmp.ifPresent(emp -> System.out.println("Better: " + emp.name()));

        // Mistake 3: Returning null instead of Optional
        // public Employee findEmployeeBad(int id) { return null; } // BAD
        // public Optional<Employee> findEmployeeGood(int id) { ... } // GOOD

        // Mistake 4: Optional in fields (controversial)
        // private Optional<String> name; // Generally discouraged for fields
        // Use Optional only for return types, not parameters or fields
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

    private static Optional<Employee> findEmployee(List<Employee> employees, int id) {
        return employees.stream()
                .filter(emp -> emp.id() == id)
                .findFirst();
    }

    private static String getNameMaybeNull(boolean returnValue) {
        return returnValue ? "Alice" : null;
    }

    private static Employee getDefaultEmployee() {
        return new Employee(0, "DEFAULT", "N/A", BigDecimal.ZERO, 0, LocalDate.now(), false);
    }
}
