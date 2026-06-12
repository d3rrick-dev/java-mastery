package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 2: Partitioning
 *
 * THEORY:
 * Partitioning is a special case of grouping where the classification function
 * returns a boolean, splitting data into two groups: true and false.
 *
 * METHOD: Collectors.partitioningBy(Predicate<T>)
 *         Returns: Map<Boolean, List<T>>
 *
 * WHY IT EXISTS:
 * - Split data into two groups based on a condition
 * - More efficient than groupingBy for boolean conditions
 * - Clearer intent than groupingBy with Boolean classifier
 *
 * VARIANTS:
 * - partitioningBy(Predicate)           : Simple partitioning
 * - partitioningBy(Predicate, Collector): Partitioning with downstream collector
 */

public class Lesson02_Partitioning {

    public static void main(String[] args) {
        System.out.println("=== LESSON 2: Partitioning ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC PARTITIONING
        // ============================================
        System.out.println("--- 1. Basic Partitioning ---");

        // Partition employees by active status
        Map<Boolean, List<Employee>> byActive = employees.stream()
                .collect(Collectors.partitioningBy(Employee::active));

        System.out.println("Active employees: " + byActive.get(true).size());
        System.out.println("Inactive employees: " + byActive.get(false).size());

        // ============================================
        // 2. PARTITIONING WITH DOWNSTREAM COLLECTOR
        // ============================================
        System.out.println("\n--- 2. Partitioning with Downstream Collector ---");

        // Partition by active, then get names
        Map<Boolean, List<String>> activeNames = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.mapping(Employee::name, Collectors.toList())
                ));
        System.out.println("Active names: " + activeNames.get(true));
        System.out.println("Inactive names: " + activeNames.get(false));

        // Partition by active, then count
        Map<Boolean, Long> activeCounts = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.counting()
                ));
        System.out.println("\nActive count: " + activeCounts.get(true));
        System.out.println("Inactive count: " + activeCounts.get(false));

        // ============================================
        // 3. PARTITIONING WITH SUM
        // ============================================
        System.out.println("\n--- 3. Partitioning with Sum ---");

        // Partition by active, sum salaries
        Map<Boolean, BigDecimal> activeSalarySum = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.mapping(
                                Employee::salary,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        System.out.println("Active total salary: $" + activeSalarySum.get(true));
        System.out.println("Inactive total salary: $" + activeSalarySum.get(false));

        // ============================================
        // 4. PARTITIONING WITH AVERAGE
        // ============================================
        System.out.println("\n--- 4. Partitioning with Average ---");

        // Partition by active, average age
        Map<Boolean, Double> activeAvgAge = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.averagingInt(Employee::age)
                ));
        System.out.println("Active avg age: " + String.format("%.1f", activeAvgAge.get(true)));
        System.out.println("Inactive avg age: " + String.format("%.1f", activeAvgAge.get(false)));

        // ============================================
        // 5. PARTITIONING WITH MAX/MIN
        // ============================================
        System.out.println("\n--- 5. Partitioning with Max/Min ---");

        // Partition by active, highest paid
        Map<Boolean, Optional<Employee>> highestPaidByActive = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.maxBy((e1, e2) -> e1.salary().compareTo(e2.salary()))
                ));
        System.out.println("Highest paid active: " +
                highestPaidByActive.get(true).map(e -> e.name() + " ($" + e.salary() + ")").orElse("N/A"));
        System.out.println("Highest paid inactive: " +
                highestPaidByActive.get(false).map(e -> e.name() + " ($" + e.salary() + ")").orElse("N/A"));

        // ============================================
        // 6. MULTI-LEVEL PARTITIONING
        // ============================================
        System.out.println("\n--- 6. Multi-level Partitioning ---");

        // Partition by active, then by department
        Map<Boolean, Map<String, List<Employee>>> activeAndDept = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.groupingBy(Employee::department)
                ));
        System.out.println("Active -> Department -> Employees:");
        activeAndDept.forEach((active, deptMap) -> {
            System.out.println("  " + active + ":");
            deptMap.forEach((dept, emps) ->
                    System.out.println("    " + dept + ": " + emps.size() + " employees"));
        });

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Partition employees by salary > 80000
        // and get summary for each group
        Map<Boolean, String> salaryPartitionSummary = employees.stream()
                .collect(Collectors.partitioningBy(
                        emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                emps -> {
                                    long count = emps.size();
                                    BigDecimal total = emps.stream()
                                            .map(Employee::salary)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    List<String> names = emps.stream()
                                            .map(Employee::name)
                                            .toList();
                                    return String.format("Count: %d, Total: $%s, Names: %s",
                                            count, total, names);
                                }
                        )
                ));
        System.out.println("High earners (>80k): " + salaryPartitionSummary.get(true));
        System.out.println("Low earners (<=80k): " + salaryPartitionSummary.get(false));

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using groupingBy for boolean conditions
        // Map<Boolean, List<Employee>> wrong = employees.stream()
        //     .collect(Collectors.groupingBy(Employee::active)); // Works but less clear
        Map<Boolean, List<Employee>> correct = employees.stream()
                .collect(Collectors.partitioningBy(Employee::active)); // Better
        System.out.println("Correct partitioning: " + correct.size() + " groups");

        // Mistake 2: Forgetting that partitioningBy always returns two keys
        // Even if one group is empty, both true and false keys exist
        Map<Boolean, List<Employee>> partition = employees.stream()
                .collect(Collectors.partitioningBy(emp -> emp.salary().compareTo(new BigDecimal("200000")) > 0));
        System.out.println("Partition keys: " + partition.keySet());
        System.out.println("High earners: " + partition.get(true).size());
        System.out.println("Low earners: " + partition.get(false).size());
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
