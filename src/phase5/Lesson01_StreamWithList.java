package phase5;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 1: Stream with List
 *
 * THEORY:
 * Lists are the most common data structure used with Streams.
 * Streams provide a powerful way to process List data declaratively.
 *
 * COMMON OPERATIONS:
 * - Filtering lists
 * - Transforming lists
 * - Grouping list elements
 * - Aggregating list data
 * - Finding elements in lists
 */

public class Lesson01_StreamWithList {

    public static void main(String[] args) {
        System.out.println("=== LESSON 1: Stream with List ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. FILTERING LISTS
        // ============================================
        System.out.println("--- 1. Filtering Lists ---");

        // Filter active employees
        List<Employee> activeEmployees = employees.stream()
                .filter(Employee::active)
                .toList();
        System.out.println("Active employees: " + activeEmployees.size());

        // Filter with multiple conditions
        List<Employee> activeEngineers = employees.stream()
                .filter(Employee::active)
                .filter(emp -> "Engineering".equals(emp.department()))
                .toList();
        System.out.println("Active engineers: " + activeEngineers.size());

        // ============================================
        // 2. TRANSFORMING LISTS
        // ============================================
        System.out.println("\n--- 2. Transforming Lists ---");

        // Map to names
        List<String> names = employees.stream()
                .map(Employee::name)
                .toList();
        System.out.println("Names: " + names);

        // Map to upper case
        List<String> upperNames = employees.stream()
                .map(emp -> emp.name().toUpperCase())
                .toList();
        System.out.println("Upper names: " + upperNames);

        // ============================================
        // 3. GROUPING LISTS
        // ============================================
        System.out.println("\n--- 3. Grouping Lists ---");

        // Group by department
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::department));
        System.out.println("Employees by department:");
        byDept.forEach((dept, emps) ->
                System.out.println("  " + dept + ": " + emps.size()));

        // Group by active status
        Map<Boolean, List<Employee>> byActive = employees.stream()
                .collect(Collectors.partitioningBy(Employee::active));
        System.out.println("Active: " + byActive.get(true).size());
        System.out.println("Inactive: " + byActive.get(false).size());

        // ============================================
        // 4. AGGREGATING LISTS
        // ============================================
        System.out.println("\n--- 4. Aggregating Lists ---");

        // Count
        long total = employees.stream().count();
        long active = employees.stream().filter(Employee::active).count();
        System.out.println("Total: " + total + ", Active: " + active);

        // Sum
        BigDecimal totalSalary = employees.stream()
                .map(Employee::salary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total salary: $" + totalSalary);

        // Average
        double avgAge = employees.stream()
                .mapToInt(Employee::age)
                .average()
                .orElse(0);
        System.out.println("Average age: " + avgAge);

        // ============================================
        // 5. FINDING IN LISTS
        // ============================================
        System.out.println("\n--- 5. Finding in Lists ---");

        // Find first
        employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .findFirst()
                .ifPresent(emp -> System.out.println("First engineer: " + emp.name()));

        // Find any
        employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("100000")) > 0)
                .findAny()
                .ifPresent(emp -> System.out.println("High earner: " + emp.name()));

        // Check conditions
        boolean anyHighEarner = employees.stream()
                .anyMatch(emp -> emp.salary().compareTo(new BigDecimal("100000")) > 0);
        System.out.println("Any high earner: " + anyHighEarner);

        // ============================================
        // 6. SORTING LISTS
        // ============================================
        System.out.println("\n--- 6. Sorting Lists ---");

        // Sort by name
        List<String> sortedNames = employees.stream()
                .map(Employee::name)
                .sorted()
                .toList();
        System.out.println("Sorted names: " + sortedNames);

        // Sort by salary descending
        List<Employee> bySalaryDesc = employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .toList();
        System.out.println("By salary (high to low):");
        bySalaryDesc.forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Get top 3 highest paid active engineers
        List<Employee> top3 = employees.stream()
                .filter(Employee::active)
                .filter(emp -> "Engineering".equals(emp.department()))
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .limit(3)
                .toList();
        System.out.println("Top 3 active engineers:");
        top3.forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Modifying list during stream operation
        List<Employee> modifiable = new ArrayList<>(employees);
        try {
            modifiable.stream()
                    .forEach(emp -> {
                        if (emp.id() == 1) {
                            modifiable.remove(emp); // ConcurrentModificationException!
                        }
                    });
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName());
        }

        // Correct: Use filter
        List<Employee> filtered = employees.stream()
                .filter(emp -> emp.id() != 1)
                .toList();
        System.out.println("Filtered count: " + filtered.size());
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
