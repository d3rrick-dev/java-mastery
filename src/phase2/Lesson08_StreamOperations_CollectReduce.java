package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LESSON 8: collect() and reduce() - Deep Dive
 *
 * THEORY:
 * collect() and reduce() are terminal operations that combine elements.
 *
 * collect(Collector): Collects elements into a mutable result (List, Set, Map, etc.)
 * reduce(BinaryOperator): Combines elements into a single value
 */

public class Lesson08_StreamOperations_CollectReduce {

    public static void main(String[] args) {
        System.out.println("=== LESSON 8: collect() and reduce() ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. collect() - COLLECT TO LIST
        // ============================================
        System.out.println("--- 1. collect() to List ---");

        List<String> names = employees.stream()
                .map(emp -> emp.name())
                .collect(Collectors.toList());
        System.out.println("Names: " + names);

        // ============================================
        // 2. collect() - COLLECT TO SET
        // ============================================
        System.out.println("\n--- 2. collect() to Set ---");

        List<String> namesWithDups = List.of("Alice", "Bob", "Alice", "Charlie", "Bob");
        java.util.Set<String> uniqueNames = namesWithDups.stream()
                .collect(Collectors.toSet());
        System.out.println("Unique names: " + uniqueNames);

        // ============================================
        // 3. collect() - COLLECT TO MAP
        // ============================================
        System.out.println("\n--- 3. collect() to Map ---");

        java.util.Map<Integer, String> idToName = employees.stream()
                .collect(Collectors.toMap(
                        Employee::id,
                        Employee::name
                ));
        System.out.println("ID to Name: " + idToName);

        // ============================================
        // 4. collect() - COLLECT WITH JOINING
        // ============================================
        System.out.println("\n--- 4. collect() with Joining ---");

        String allNames = employees.stream()
                .map(emp -> emp.name())
                .collect(Collectors.joining(", "));
        System.out.println("All names: " + allNames);

        String allNamesWithBrackets = employees.stream()
                .map(emp -> emp.name())
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("All names with brackets: " + allNamesWithBrackets);

        // ============================================
        // 5. reduce() - COMBINE TO SINGLE VALUE
        // ============================================
        System.out.println("\n--- 5. reduce() ---");

        // Sum salaries
        BigDecimal totalSalary = employees.stream()
                .map(Employee::salary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total salary: " + totalSalary);

        // Concatenate names
        String concatenated = employees.stream()
                .map(Employee::name)
                .reduce("", (a, b) -> a + " | " + b);
        System.out.println("Concatenated: " + concatenated);

        // Find max salary
        BigDecimal maxSalary = employees.stream()
                .map(Employee::salary)
                .reduce(BigDecimal::max)
                .orElse(BigDecimal.ZERO);
        System.out.println("Max salary: " + maxSalary);

        // ============================================
        // 6. reduce() vs collect() COMPARISON
        // ============================================
        System.out.println("\n--- 6. reduce() vs collect() ---");

        // reduce: Returns a single value (immutable result)
        int sumReduce = employees.stream()
                .map(emp -> emp.age())
                .reduce(0, Integer::sum);
        System.out.println("Sum via reduce: " + sumReduce);

        // collect: Can return mutable result (like StringBuilder)
        StringBuilder sb = employees.stream()
                .map(emp -> emp.name())
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append);
        System.out.println("StringBuilder via collect: " + sb);

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Group employees by department and calculate total salary per department
        java.util.Map<String, BigDecimal> deptTotalSalary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(
                                Employee::salary,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        System.out.println("Department total salaries:");
        deptTotalSalary.forEach((dept, total) ->
                System.out.println("  " + dept + ": $" + total));

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using reduce() for mutable results
        // List<String> wrong = employees.stream()
        //     .map(emp -> emp.name())
        //     .reduce(new ArrayList<>(),
        //             (list, name) -> { list.add(name); return list; },
        //             (l1, l2) -> { l1.addAll(l2); return l1; });
        // This works but is NOT recommended - use collect() instead

        // Correct way with collect()
        List<String> correct = employees.stream()
                .map(emp -> emp.name())
                .collect(Collectors.toList());
        System.out.println("Correct collect: " + correct);

        // Mistake 2: Forgetting identity in reduce
        // employees.stream().map(emp -> emp.age()).reduce(Integer::sum); // Returns Optional<Integer>
        // Better with identity:
        int sumWithIdentity = employees.stream()
                .map(emp -> emp.age())
                .reduce(0, Integer::sum);
        System.out.println("Sum with identity: " + sumWithIdentity);
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
