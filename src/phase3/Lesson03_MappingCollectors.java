package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 3: Mapping Collectors
 *
 * THEORY:
 * Mapping collectors transform elements during collection operations.
 * They're used with groupingBy and partitioningBy to transform the grouped values.
 *
 * METHOD: Collectors.mapping(Function<T, R>, Collector)
 *
 * WHY IT EXISTS:
 * - Transform elements while grouping/partitioning
 * - Avoid post-processing after grouping
 * - More efficient than grouping then mapping separately
 */

public class Lesson03_MappingCollectors {

    public static void main(String[] args) {
        System.out.println("=== LESSON 3: Mapping Collectors ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. MAPPING WITH GROUPING
        // ============================================
        System.out.println("--- 1. Mapping with Grouping ---");

        // Group by department, map to names
        Map<String, List<String>> deptNames = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(Employee::name, Collectors.toList())
                ));
        System.out.println("Department -> Names:");
        deptNames.forEach((dept, names) ->
                System.out.println("  " + dept + ": " + names));

        // ============================================
        // 2. MAPPING WITH PARTITIONING
        // ============================================
        System.out.println("\n--- 2. Mapping with Partitioning ---");

        // Partition by active, map to names
        Map<Boolean, List<String>> activeNames = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.mapping(Employee::name, Collectors.toList())
                ));
        System.out.println("Active names: " + activeNames.get(true));
        System.out.println("Inactive names: " + activeNames.get(false));

        // ============================================
        // 3. MAPPING TO SET
        // ============================================
        System.out.println("\n--- 3. Mapping to Set ---");

        // Group by department, map to unique names (Set)
        Map<String, java.util.Set<String>> deptNameSet = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(Employee::name, Collectors.toSet())
                ));
        System.out.println("Department -> Unique Names:");
        deptNameSet.forEach((dept, names) ->
                System.out.println("  " + dept + ": " + names));

        // ============================================
        // 4. MAPPING WITH JOINING
        // ============================================
        System.out.println("\n--- 4. Mapping with Joining ---");

        // Group by department, join names with comma
        Map<String, String> deptNamesJoined = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(
                                Employee::name,
                                Collectors.joining(", ")
                        )
                ));
        System.out.println("Department -> Joined Names:");
        deptNamesJoined.forEach((dept, names) ->
                System.out.println("  " + dept + ": " + names));

        // ============================================
        // 5. MAPPING WITH COUNTING
        // ============================================
        System.out.println("\n--- 5. Mapping with Counting ---");

        // Group by department, count names (same as just counting employees)
        Map<String, Long> deptCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(
                                Employee::name,
                                Collectors.counting()
                        )
                ));
        System.out.println("Department -> Count:");
        deptCounts.forEach((dept, count) ->
                System.out.println("  " + dept + ": " + count));

        // ============================================
        // 6. MAPPING WITH SUMMING
        // ============================================
        System.out.println("\n--- 6. Mapping with Summing ---");

        // Group by department, sum salaries
        Map<String, BigDecimal> deptTotalSalary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(
                                Employee::salary,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        System.out.println("Department -> Total Salary:");
        deptTotalSalary.forEach((dept, total) ->
                System.out.println("  " + dept + ": $" + total));

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Group by department, get:
        // - Count of employees
        // - List of names
        // - Total salary
        // - Average salary

        Map<String, String> deptSummary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                emps -> {
                                    long count = emps.size();
                                    List<String> names = emps.stream()
                                            .map(Employee::name)
                                            .toList();
                                    BigDecimal total = emps.stream()
                                            .map(Employee::salary)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    BigDecimal avg = total.divide(BigDecimal.valueOf(count));
                                    return String.format("Count: %d, Names: %s, Total: $%s, Avg: $%s",
                                            count, names, total, avg);
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

        // Mistake 1: Using wrong collector type
        // Map<String, Integer> wrong = employees.stream()
        //     .collect(Collectors.groupingBy(Employee::department, Collectors.mapping(Employee::name, Collectors.counting())));
        // ERROR: counting() returns Long, not Integer

        // Correct:
        Map<String, Long> correctCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(Employee::name, Collectors.counting())
                ));
        System.out.println("Correct counts: " + correctCounts);

        // Mistake 2: Forgetting that mapping changes the value type
        // Without mapping: Map<String, List<Employee>>
        // With mapping: Map<String, List<String>> (or whatever the mapping function returns)
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
