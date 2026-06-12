package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * LESSON 12: Stream Operations Summary
 *
 * This lesson provides a comprehensive summary of all stream operations
 * with a real-world example using employee data.
 */

public class Lesson12_StreamOperations_Summary {

    public static void main(String[] args) {
        System.out.println("=== LESSON 12: Stream Operations Summary ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // REAL-WORLD SCENARIO: Employee Analytics
        // ============================================
        System.out.println("--- Employee Analytics Report ---\n");

        // 1. Basic Statistics
        System.out.println("1. BASIC STATISTICS:");
        long totalEmployees = employees.stream().count();
        long activeEmployees = employees.stream().filter(Employee::active).count();
        long inactiveEmployees = employees.stream().filter(emp -> !emp.active()).count();
        System.out.println("   Total: " + totalEmployees);
        System.out.println("   Active: " + activeEmployees);
        System.out.println("   Inactive: " + inactiveEmployees);

        // 2. Salary Analysis
        System.out.println("\n2. SALARY ANALYSIS:");
        BigDecimal totalSalary = employees.stream()
                .map(Employee::salary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avgSalary = totalSalary.divide(BigDecimal.valueOf(totalEmployees));
        BigDecimal maxSalary = employees.stream()
                .map(Employee::salary)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal minSalary = employees.stream()
                .map(Employee::salary)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        System.out.println("   Total: $" + totalSalary);
        System.out.println("   Average: $" + avgSalary);
        System.out.println("   Max: $" + maxSalary);
        System.out.println("   Min: $" + minSalary);

        // 3. Department Analysis
        System.out.println("\n3. DEPARTMENT ANALYSIS:");
        Map<String, Long> deptCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()
                ));
        deptCounts.forEach((dept, count) ->
                System.out.println("   " + dept + ": " + count + " employees"));

        // 4. Department Salary Summary
        System.out.println("\n4. DEPARTMENT SALARY SUMMARY:");
        Map<String, BigDecimal> deptTotalSalary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(
                                Employee::salary,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        deptTotalSalary.forEach((dept, total) ->
                System.out.println("   " + dept + ": $" + total));

        // 5. Top Earners
        System.out.println("\n5. TOP 3 EARNERS:");
        employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .limit(3)
                .forEach(emp -> System.out.println("   " + emp.name() + " - $" + emp.salary()));

        // 6. Active Engineers
        System.out.println("\n6. ACTIVE ENGINEERS:");
        employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .filter(Employee::active)
                .map(Employee::name)
                .forEach(name -> System.out.println("   " + name));

        // 7. Age Analysis
        System.out.println("\n7. AGE ANALYSIS:");
        double avgAge = employees.stream()
                .mapToInt(Employee::age)
                .average()
                .orElse(0);
        Optional<Employee> oldest = employees.stream()
                .max((e1, e2) -> Integer.compare(e1.age(), e2.age()));
        Optional<Employee> youngest = employees.stream()
                .min((e1, e2) -> Integer.compare(e1.age(), e2.age()));
        System.out.println("   Average age: " + String.format("%.1f", avgAge));
        oldest.ifPresent(emp -> System.out.println("   Oldest: " + emp.name() + " (" + emp.age() + ")"));
        youngest.ifPresent(emp -> System.out.println("   Youngest: " + emp.name() + " (" + emp.age() + ")"));

        // 8. Check Conditions
        System.out.println("\n8. CONDITION CHECKS:");
        boolean anyHighEarner = employees.stream()
                .anyMatch(emp -> emp.salary().compareTo(new BigDecimal("100000")) > 0);
        boolean allHaveSalary = employees.stream()
                .allMatch(emp -> emp.salary().compareTo(BigDecimal.ZERO) > 0);
        boolean noOneInactive = employees.stream()
                .noneMatch(emp -> !emp.active());
        System.out.println("   Any high earner (>100k): " + anyHighEarner);
        System.out.println("   All have positive salary: " + allHaveSalary);
        System.out.println("   No inactive employees: " + noOneInactive);

        // 9. Complex Query
        System.out.println("\n9. COMPLEX QUERY:");
        System.out.println("   Active engineers earning > 80000:");
        employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .filter(Employee::active)
                .filter(emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0)
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .forEach(emp -> System.out.println("   " + emp.name() + " - $" + emp.salary()));

        // 10. Summary Report
        System.out.println("\n10. SUMMARY REPORT:");
        String report = employees.stream()
                .map(emp -> emp.name() + " (" + emp.department() + ") - $" + emp.salary())
                .collect(Collectors.joining("\n   "));
        System.out.println("   " + report);

        // ============================================
        // OPERATIONS QUICK REFERENCE
        // ============================================
        System.out.println("\n--- Operations Quick Reference ---");
        System.out.println("filter(p)    - Keep elements matching predicate");
        System.out.println("map(f)       - Transform each element");
        System.out.println("flatMap(f)   - Flatten nested structures");
        System.out.println("distinct()   - Remove duplicates");
        System.out.println("sorted()     - Sort elements");
        System.out.println("peek(c)      - Debug (perform action)");
        System.out.println("limit(n)     - Keep first n elements");
        System.out.println("skip(n)      - Skip first n elements");
        System.out.println("forEach(c)   - Perform action for each");
        System.out.println("collect(c)   - Collect to collection");
        System.out.println("reduce(op)   - Combine elements");
        System.out.println("count()      - Count elements");
        System.out.println("findFirst()  - Find first element");
        System.out.println("findAny()    - Find any element");
        System.out.println("anyMatch(p)  - Any element matches");
        System.out.println("allMatch(p)  - All elements match");
        System.out.println("noneMatch(p) - No elements match");
        System.out.println("min(c)       - Minimum element");
        System.out.println("max(c)       - Maximum element");
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
