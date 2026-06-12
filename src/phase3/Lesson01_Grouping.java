package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * LESSON 1: Grouping
 *
 * THEORY:
 * Grouping is a powerful collector that groups elements by a classification function.
 * It's like SQL's GROUP BY clause.
 *
 * METHOD: Collectors.groupingBy(Function<T, K>)
 *         Returns: Map<K, List<T>>
 *
 * WHY IT EXISTS:
 * - Categorize data into groups
 * - Replace manual grouping loops
 * - Enable further processing per group
 *
 * VARIANTS:
 * - groupingBy(Function)           : Simple grouping
 * - groupingBy(Function, Collector): Grouping with downstream collector
 * - groupingByConcurrent(Function) : Concurrent grouping (parallel streams)
 */

public class Lesson01_Grouping {

    public static void main(String[] args) {
        System.out.println("=== LESSON 1: Grouping ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC GROUPING
        // ============================================
        System.out.println("--- 1. Basic Grouping ---");

        // Group employees by department
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::department));

        System.out.println("Employees by department:");
        byDept.forEach((dept, emps) -> {
            System.out.println("  " + dept + ": " + emps.size() + " employees");
            emps.forEach(emp -> System.out.println("    - " + emp.name()));
        });

        // ============================================
        // 2. GROUPING WITH DOWNSTREAM COLLECTOR
        // ============================================
        System.out.println("\n--- 2. Grouping with Downstream Collector ---");

        // Group by department, then get names only
        Map<String, List<String>> deptNames = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(Employee::name, Collectors.toList())
                ));
        System.out.println("Department -> Names:");
        deptNames.forEach((dept, names) ->
                System.out.println("  " + dept + ": " + names));

        // Group by department, then count
        Map<String, Long> deptCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()
                ));
        System.out.println("\nDepartment -> Count:");
        deptCounts.forEach((dept, count) ->
                System.out.println("  " + dept + ": " + count));

        // ============================================
        // 3. GROUPING WITH SUM
        // ============================================
        System.out.println("\n--- 3. Grouping with Sum ---");

        // Group by department, sum salaries
        Map<String, BigDecimal> deptSalarySum = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(Employee::salary, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
        System.out.println("Department -> Total Salary:");
        deptSalarySum.forEach((dept, total) ->
                System.out.println("  " + dept + ": $" + total));

        // ============================================
        // 4. GROUPING WITH AVERAGE
        // ============================================
        System.out.println("\n--- 4. Grouping with Average ---");

        // Group by department, average age
        Map<String, Double> deptAvgAge = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.averagingInt(Employee::age)
                ));
        System.out.println("Department -> Avg Age:");
        deptAvgAge.forEach((dept, avg) ->
                System.out.println("  " + dept + ": " + String.format("%.1f", avg)));

        // ============================================
        // 5. GROUPING WITH MAX/MIN
        // ============================================
        System.out.println("\n--- 5. Grouping with Max/Min ---");

        // Group by department, highest paid employee name
        Map<String, Optional<Employee>> highestPaidByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.maxBy((e1, e2) -> e1.salary().compareTo(e2.salary()))
                ));
        System.out.println("Highest paid by department:");
        highestPaidByDept.forEach((dept, emp) ->
                System.out.println("  " + dept + ": " +
                        emp.map(e -> e.name() + " ($" + e.salary() + ")").orElse("N/A")));

        // ============================================
        // 6. MULTI-LEVEL GROUPING
        // ============================================
        System.out.println("\n--- 6. Multi-level Grouping ---");

        // Group by department, then by active status
        Map<String, Map<Boolean, List<Employee>>> deptAndActive = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.groupingBy(Employee::active)
                ));
        System.out.println("Department -> Active Status -> Employees:");
        deptAndActive.forEach((dept, statusMap) -> {
            System.out.println("  " + dept + ":");
            statusMap.forEach((active, emps) ->
                    System.out.println("    " + active + ": " + emps.size() + " employees"));
        });

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Group employees by department and get:
        // - Count
        // - Total salary
        // - Average salary
        // - List of names

        Map<String, String> deptSummary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                emps -> {
                                    long count = emps.size();
                                    BigDecimal total = emps.stream()
                                            .map(Employee::salary)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    BigDecimal avg = total.divide(BigDecimal.valueOf(count));
                                    List<String> names = emps.stream()
                                            .map(Employee::name)
                                            .toList();
                                    return String.format("Count: %d, Total: $%s, Avg: $%s, Names: %s",
                                            count, total, avg, names);
                                }
                        )
                ));
        System.out.println("Department Summary:");
        deptSummary.forEach((dept, summary) ->
                System.out.println("  " + dept + ": " + summary));

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using groupingBy when you just need filtering
        // employees.stream().collect(Collectors.groupingBy(emp -> emp.active())); // Returns Map<Boolean, List<Employee>>
        // Better for just filtering:
        List<Employee> activeEmps = employees.stream()
                .filter(Employee::active)
                .toList();

        // Mistake 2: Forgetting that groupingBy returns Map<K, List<T>>
        // Map<String, Employee> wrong = employees.stream()
        //     .collect(Collectors.groupingBy(Employee::department)); // ERROR: Map<String, List<Employee>>

        // Mistake 3: Not handling null keys
        // If classification function returns null, groupingBy throws NullPointerException
        // employees.stream().collect(Collectors.groupingBy(emp -> null)); // ERROR
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        employees.add(new Employee(6, "Frank", "Sales", new BigDecimal("90000"), 40, LocalDate.of(2015, 2, 28), true));
        employees.add(new Employee(7, "Grace", "HR", new BigDecimal("70000"), 27, LocalDate.of(2020, 8, 15), true));
        return employees;
    }
}
