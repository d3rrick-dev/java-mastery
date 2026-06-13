package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 5: Joining Strings
 *
 * THEORY:
 * Collectors.joining() concatenates stream elements into a single String.
 * It's more efficient than manual StringBuilder concatenation in streams.
 *
 * METHODS:
 * - Collectors.joining(CharSequence delimiter)
 * - Collectors.joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
 *
 * WHY IT EXISTS:
 * - Efficient string concatenation in streams
 * - Avoid manual StringBuilder in forEach
 * - Clean, readable syntax
 */

public class Lesson05_JoiningStrings {

    public static void main(String[] args) {
        System.out.println("=== LESSON 5: Joining Strings ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC JOINING
        // ============================================
        System.out.println("--- 1. Basic Joining ---");

        // Join names with comma
        String names = employees.stream()
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        System.out.println("Names: " + names);

        // ============================================
        // 2. JOINING WITH PREFIX AND SUFFIX
        // ============================================
        System.out.println("\n--- 2. Joining with Prefix/Suffix ---");

        // Join with brackets
        String namesWithBrackets = employees.stream()
                .map(Employee::name)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Names with brackets: " + namesWithBrackets);

        // Join with newlines
        String namesWithNewlines = employees.stream()
                .map(Employee::name)
                .collect(Collectors.joining("\n", "Employees:\n", "\nEnd"));
        System.out.println("Names with newlines:\n" + namesWithNewlines);

        // ============================================
        // 3. JOINING WITH FILTER
        // ============================================
        System.out.println("\n--- 3. Joining with Filter ---");

        // Join only active employee names
        String activeNames = employees.stream()
                .filter(Employee::active)
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        System.out.println("Active names: " + activeNames);

        // Join only engineer names
        String engineerNames = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .map(Employee::name)
                .collect(Collectors.joining(" | "));
        System.out.println("Engineer names: " + engineerNames);

        // ============================================
        // 4. JOINING WITH GROUPING
        // ============================================
        System.out.println("\n--- 4. Joining with Grouping ---");

        // Group by department, join names
        Map<String, String> deptNames = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(
                                Employee::name,
                                Collectors.joining(", ")
                        )
                ));
        System.out.println("Department -> Names:");
        deptNames.forEach((dept, namesList) ->
                System.out.println("  " + dept + ": " + namesList));

        // ============================================
        // 5. JOINING WITH PARTITIONING
        // ============================================
        System.out.println("\n--- 5. Joining with Partitioning ---");

        // Partition by active, join names
        Map<Boolean, String> activeNamesJoined = employees.stream()
                .collect(Collectors.partitioningBy(
                        Employee::active,
                        Collectors.mapping(
                                Employee::name,
                                Collectors.joining(", ")
                        )
                ));
        System.out.println("Active: " + activeNamesJoined.get(true));
        System.out.println("Inactive: " + activeNamesJoined.get(false));

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Create a report string
        String report = employees.stream()
                .map(emp -> emp.name() + " (" + emp.department() + ") - $" + emp.salary())
                .collect(Collectors.joining("\n", "Employee Report:\n", "\n\nTotal: " + employees.size() + " employees"));
        System.out.println(report);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Using StringBuilder manually in forEach
        StringBuilder sb = new StringBuilder();
        employees.stream()
                .map(Employee::name)
                .forEach(name -> sb.append(name).append(", "));
        System.out.println("Manual StringBuilder: " + sb);

        // Better: Use joining()
        String better = employees.stream()
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        System.out.println("Using joining(): " + better);

        // Mistake 2: Forgetting that joining() on empty stream returns empty string
        String empty = employees.stream()
                .filter(emp -> emp.id() > 999)
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        System.out.println("Empty stream result: '" + empty + "'");
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
