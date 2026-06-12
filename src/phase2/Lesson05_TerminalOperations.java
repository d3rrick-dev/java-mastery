package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * LESSON 5: Terminal Operations
 *
 * THEORY:
 * Terminal operations produce a result from a Stream pipeline.
 * They are EAGER - they trigger the execution of the entire pipeline.
 * After a terminal operation, the Stream is consumed and cannot be reused.
 *
 * COMMON TERMINAL OPERATIONS:
 * - forEach(Consumer)     : Perform action for each element
 * - collect(Collector)    : Collect results into a collection or other structure
 * - reduce(BinaryOperator): Combine elements into a single result
 * - count()               : Count elements
 * - findFirst()           : Find first element
 * - findAny()             : Find any element (parallel streams)
 * - anyMatch(Predicate)   : Check if any element matches
 * - allMatch(Predicate)   : Check if all elements match
 * - noneMatch(Predicate)  : Check if no element matches
 * - min(Comparator)       : Find minimum element
 * - max(Comparator)       : Find maximum element
 * - toArray()             : Convert to array
 */

public class Lesson05_TerminalOperations {

    public static void main(String[] args) {
        System.out.println("=== LESSON 5: Terminal Operations ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. forEach() - PERFORM ACTION FOR EACH ELEMENT
        // ============================================
        System.out.println("--- 1. forEach() ---");
        System.out.println("Syntax: stream.forEach(Consumer<T>) -> void");

        System.out.println("All employee names:");
        employees.stream()
                .map(emp -> emp.name())
                .forEach(name -> System.out.println("  " + name));

        // ============================================
        // 2. collect() - COLLECT INTO COLLECTION
        // ============================================
        System.out.println("\n--- 2. collect() ---");
        System.out.println("Syntax: stream.collect(Collector) -> R");

        // Collect to List
        List<String> names = employees.stream()
                .map(emp -> emp.name())
                .collect(Collectors.toList());
        System.out.println("Names list: " + names);

        // Collect to Set (removes duplicates)
        Set<String> departments = employees.stream()
                .map(emp -> emp.department())
                .collect(Collectors.toSet());
        System.out.println("Departments set: " + departments);

        // Collect to Map
        Map<Integer, String> idToName = employees.stream()
                .collect(Collectors.toMap(
                        Employee::id,
                        Employee::name
                ));
        System.out.println("ID to Name map: " + idToName);

        // ============================================
        // 3. reduce() - COMBINE ELEMENTS
        // ============================================
        System.out.println("\n--- 3. reduce() ---");
        System.out.println("Syntax: stream.reduce(BinaryOperator) -> Optional<T>");
        System.out.println("Syntax: stream.reduce(identity, BinaryOperator) -> T");

        // Sum salaries using reduce
        BigDecimal totalSalary = employees.stream()
                .map(Employee::salary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total salary: " + totalSalary);

        // Concatenate names
        String allNames = employees.stream()
                .map(Employee::name)
                .reduce("", (a, b) -> a + ", " + b);
        System.out.println("All names: " + allNames);

        // ============================================
        // 4. count() - COUNT ELEMENTS
        // ============================================
        System.out.println("\n--- 4. count() ---");
        System.out.println("Syntax: stream.count() -> long");

        long total = employees.stream().count();
        long active = employees.stream().filter(Employee::active).count();
        long engineers = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .count();
        System.out.println("Total: " + total + ", Active: " + active + ", Engineers: " + engineers);

        // ============================================
        // 5. findFirst() / findAny() - FIND ELEMENT
        // ============================================
        System.out.println("\n--- 5. findFirst() / findAny() ---");
        System.out.println("Syntax: stream.findFirst() -> Optional<T>");
        System.out.println("Syntax: stream.findAny() -> Optional<T>");

        Optional<Employee> firstEngineer = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .findFirst();
        firstEngineer.ifPresent(emp -> System.out.println("First engineer: " + emp.name()));

        Optional<Employee> anyEngineer = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .findAny();
        anyEngineer.ifPresent(emp -> System.out.println("Any engineer: " + emp.name()));

        // ============================================
        // 6. anyMatch() / allMatch() / noneMatch()
        // ============================================
        System.out.println("\n--- 6. Matching Operations ---");

        boolean anyActive = employees.stream()
                .anyMatch(Employee::active);
        System.out.println("Any active: " + anyActive);

        boolean allActive = employees.stream()
                .allMatch(Employee::active);
        System.out.println("All active: " + allActive);

        boolean noneInactive = employees.stream()
                .noneMatch(emp -> !emp.active());
        System.out.println("None inactive: " + noneInactive);

        boolean anyHighEarner = employees.stream()
                .anyMatch(emp -> emp.salary().compareTo(new BigDecimal("100000")) > 0);
        System.out.println("Any high earner (>100k): " + anyHighEarner);

        // ============================================
        // 7. min() / max() - FIND EXTREMES
        // ============================================
        System.out.println("\n--- 7. min() / max() ---");
        System.out.println("Syntax: stream.min(Comparator) -> Optional<T>");
        System.out.println("Syntax: stream.max(Comparator) -> Optional<T>");

        Optional<Employee> highestPaid = employees.stream()
                .max((e1, e2) -> e1.salary().compareTo(e2.salary()));
        highestPaid.ifPresent(emp -> System.out.println("Highest paid: " + emp.name() + " - " + emp.salary()));

        Optional<Employee> lowestPaid = employees.stream()
                .min((e1, e2) -> e1.salary().compareTo(e2.salary()));
        lowestPaid.ifPresent(emp -> System.out.println("Lowest paid: " + emp.name() + " - " + emp.salary()));

        Optional<Employee> oldest = employees.stream()
                .max((e1, e2) -> Integer.compare(e1.age(), e2.age()));
        oldest.ifPresent(emp -> System.out.println("Oldest: " + emp.name() + " - " + emp.age() + " years"));

        // ============================================
        // 8. toArray() - CONVERT TO ARRAY
        // ============================================
        System.out.println("\n--- 8. toArray() ---");
        System.out.println("Syntax: stream.toArray() -> Object[]");
        System.out.println("Syntax: stream.toArray(IntFunction) -> T[]");

        Object[] objArray = employees.stream()
                .map(emp -> emp.name())
                .toArray();
        System.out.println("Object array: " + java.util.Arrays.toString(objArray));

        String[] nameArray = employees.stream()
                .map(emp -> emp.name())
                .toArray(String[]::new);
        System.out.println("String array: " + java.util.Arrays.toString(nameArray));

        // ============================================
        // 9. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 9. Coding Challenge ---");

        // Challenge: Get summary statistics
        System.out.println("Employee Summary:");
        System.out.println("Total: " + employees.stream().count());
        System.out.println("Active: " + employees.stream().filter(Employee::active).count());
        System.out.println("Avg age: " +
                employees.stream()
                        .mapToInt(Employee::age)
                        .average()
                        .orElse(0));
        System.out.println("Max salary: " +
                employees.stream()
                        .map(Employee::salary)
                        .max(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO));

        // ============================================
        // 10. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 10. Common Mistakes ---");

        // Mistake 1: Using terminal operation in middle of pipeline
        // employees.stream()
        //     .filter(emp -> emp.active())
        //     .forEach(emp -> System.out.println(emp.name()));  // Terminal!
        //     .filter(emp -> "Engineering".equals(emp.department()))  // ERROR: stream already consumed
        //     .toList();

        // Mistake 2: Forgetting Optional handling
        Optional<Employee> maybeEmp = employees.stream()
                .filter(emp -> emp.id() == 999)
                .findFirst();
        // Employee emp = maybeEmp.get(); // Throws NoSuchElementException if empty!
        // Better:
        Employee safeEmp = maybeEmp.orElse(null);
        System.out.println("Safe get: " + safeEmp);

        // Mistake 3: Using findAny() when order matters
        // findAny() may return different elements in parallel streams
        // Use findFirst() when order is important
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
