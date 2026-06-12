package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

/**
 * LESSON 4: Summarizing Statistics
 *
 * THEORY:
 * Summarizing collectors provide statistical information about numeric data.
 * They calculate count, sum, min, max, and average in a single pass.
 *
 * METHODS:
 * - Collectors.summarizingInt(ToIntFunction)   : IntSummaryStatistics
 * - Collectors.summarizingLong(ToLongFunction) : LongSummaryStatistics
 * - Collectors.summarizingDouble(ToDoubleFunction) : DoubleSummaryStatistics
 *
 * WHY IT EXISTS:
 * - Get multiple statistics in one pass (efficient)
 * - Avoid multiple stream operations
 * - Built-in statistical calculations
 */

public class Lesson04_SummarizingStatistics {

    public static void main(String[] args) {
        System.out.println("=== LESSON 4: Summarizing Statistics ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC SUMMARIZING
        // ============================================
        System.out.println("--- 1. Basic Summarizing ---");

        // Get statistics for ages
        IntSummaryStatistics ageStats = employees.stream()
                .collect(Collectors.summarizingInt(Employee::age));

        System.out.println("Age Statistics:");
        System.out.println("  Count: " + ageStats.getCount());
        System.out.println("  Sum: " + ageStats.getSum());
        System.out.println("  Min: " + ageStats.getMin());
        System.out.println("  Max: " + ageStats.getMax());
        System.out.println("  Average: " + String.format("%.2f", ageStats.getAverage()));

        // ============================================
        // 2. SUMMARIZING WITH GROUPING
        // ============================================
        System.out.println("\n--- 2. Summarizing with Grouping ---");

        // Group by department, get age statistics
        Map<String, IntSummaryStatistics> deptAgeStats = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.summarizingInt(Employee::age)
                ));
        System.out.println("Department Age Statistics:");
        deptAgeStats.forEach((dept, stats) ->
                System.out.println("  " + dept + ": count=" + stats.getCount() +
                        ", avg=" + String.format("%.1f", stats.getAverage()) +
                        ", min=" + stats.getMin() +
                        ", max=" + stats.getMax()));

        // ============================================
        // 3. SUMMARIZING WITH PARTITIONING
        // ============================================
        System.out.println("\n--- 3. Summarizing with Partitioning ---");

        // Partition by active, get salary statistics
        Map<Boolean, IntSummaryStatistics> activeSalaryStats = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.summarizingInt(emp -> emp.salary().intValue())
                ));
        System.out.println("Active Salary Statistics:");
        System.out.println("  Active: count=" + activeSalaryStats.get(true).getCount() +
                ", avg=" + String.format("%.0f", activeSalaryStats.get(true).getAverage()));
        System.out.println("  Inactive: count=" + activeSalaryStats.get(false).getCount() +
                ", avg=" + String.format("%.0f", activeSalaryStats.get(false).getAverage()));

        // ============================================
        // 4. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 4. Coding Challenge ---");

        // Challenge: Get comprehensive statistics for each department
        Map<String, String> deptStatsSummary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                emps -> {
                                    IntSummaryStatistics ageStat = emps.stream()
                                            .collect(Collectors.summarizingInt(Employee::age));
                                    BigDecimal totalSalary = emps.stream()
                                            .map(Employee::salary)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    return String.format(
                                            "Count: %d, Avg Age: %.1f, Total Salary: $%s",
                                            ageStat.getCount(),
                                            ageStat.getAverage(),
                                            totalSalary
                                    );
                                }
                        )
                ));
        System.out.println("Department Statistics:");
        deptStatsSummary.forEach((dept, stats) ->
                System.out.println("  " + dept + ": " + stats));

        // ============================================
        // 5. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 5. Common Mistakes ---");

        // Mistake 1: Using summarizing when you only need one statistic
        // IntSummaryStatistics stats = employees.stream()
        //     .collect(Collectors.summarizingInt(Employee::age));
        // If you only need average:
        double avg = employees.stream()
                .collect(Collectors.averagingInt(Employee::age));
        System.out.println("Just average: " + avg);

        // Mistake 2: Forgetting that summarizing returns a statistics object
        // You need to call getCount(), getSum(), getMin(), getMax(), getAverage()
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
