package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 6: Nested Collections Processing
 *
 * THEORY:
 * Real-world data often comes in nested structures (e.g., Department -> Employees).
 * Streams provide elegant ways to process nested collections using flatMap().
 *
 * COMMON PATTERNS:
 * 1. List<List<T>> -> List<T> (flattening)
 * 2. Map<K, List<V>> -> process values
 * 3. Nested objects -> extract nested data
 */

public class Lesson06_NestedCollections {

    public static void main(String[] args) {
        System.out.println("=== LESSON 6: Nested Collections Processing ===\n");

        // ============================================
        // 1. FLATTENING NESTED LISTS
        // ============================================
        System.out.println("--- 1. Flattening Nested Lists ---");

        List<List<String>> nestedSkills = List.of(
                List.of("Java", "Spring", "SQL"),
                List.of("Python", "Django"),
                List.of("Java", "Kubernetes", "Docker"),
                List.of("HR", "Communication")
        );

        // Flatten to single list
        List<String> allSkills = nestedSkills.stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
        System.out.println("All unique skills: " + allSkills);

        // ============================================
        // 2. PROCESSING NESTED OBJECTS
        // ============================================
        System.out.println("\n--- 2. Processing Nested Objects ---");

        // Scenario: Department with employees
        List<Department> departments = createDepartments();

        // Get all employees from all departments
        List<Employee> allEmployees = departments.stream()
                .flatMap(dept -> dept.employees().stream())
                .toList();
        System.out.println("Total employees: " + allEmployees.size());

        // Get all employee names
        List<String> allNames = departments.stream()
                .flatMap(dept -> dept.employees().stream())
                .map(Employee::name)
                .toList();
        System.out.println("All names: " + allNames);

        // ============================================
        // 3. NESTED GROUPING
        // ============================================
        System.out.println("\n--- 3. Nested Grouping ---");

        // Group departments by location, then get employee names
        Map<String, List<String>> locationToNames = departments.stream()
                .collect(Collectors.groupingBy(
                        Department::location,
                        Collectors.flatMapping(
                                dept -> dept.employees().stream().map(Employee::name),
                                Collectors.toList()
                        )
                ));
        System.out.println("Location -> Employee Names:");
        locationToNames.forEach((loc, names) ->
                System.out.println("  " + loc + ": " + names));

        // ============================================
        // 4. NESTED FILTERING
        // ============================================
        System.out.println("\n--- 4. Nested Filtering ---");

        // Find all high earners across all departments
        List<Employee> highEarners = departments.stream()
                .flatMap(dept -> dept.employees().stream())
                .filter(emp -> emp.salary().compareTo(new BigDecimal("90000")) > 0)
                .toList();
        System.out.println("High earners:");
        highEarners.forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 5. NESTED AGGREGATION
        // ============================================
        System.out.println("\n--- 5. Nested Aggregation ---");

        // Calculate total salary per department
        Map<String, BigDecimal> deptTotalSalary = departments.stream()
                .collect(Collectors.toMap(
                        Department::name,
                        dept -> dept.employees().stream()
                                .map(Employee::salary)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));
        System.out.println("Department Total Salaries:");
        deptTotalSalary.forEach((dept, total) ->
                System.out.println("  " + dept + ": $" + total));

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Find the department with highest average salary
        String topDept = departments.stream()
                .collect(Collectors.toMap(
                        Department::name,
                        dept -> dept.employees().stream()
                                .map(Employee::salary)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(BigDecimal.valueOf(dept.employees().size()))
                ))
                .entrySet().stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(Map.Entry::getKey)
                .orElse("Unknown");
        System.out.println("Department with highest avg salary: " + topDept);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Using map() instead of flatMap() for nested collections
        // List<List<String>> wrong = departments.stream()
        //     .map(dept -> dept.employees().stream().map(Employee::name).toList())
        //     .toList(); // Returns List<List<String>>

        // Correct: Use flatMap()
        List<String> correct = departments.stream()
                .flatMap(dept -> dept.employees().stream().map(Employee::name))
                .toList();
        System.out.println("Correct flatMap result: " + correct.size() + " names");
    }

    // Helper record for nested collection example
    record Department(String name, String location, List<Employee> employees) {}

    private static List<Department> createDepartments() {
        List<Employee> engEmps = List.of(
                new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true),
                new Employee(2, "Bob", "Engineering", new BigDecimal("85000"), 28, LocalDate.of(2019, 5, 20), true)
        );
        List<Employee> salesEmps = List.of(
                new Employee(3, "Charlie", "Sales", new BigDecimal("75000"), 35, LocalDate.of(2018, 3, 10), true),
                new Employee(4, "Diana", "Sales", new BigDecimal("80000"), 25, LocalDate.of(2021, 7, 1), true)
        );
        List<Employee> hrEmps = List.of(
                new Employee(5, "Eve", "HR", new BigDecimal("65000"), 32, LocalDate.of(2017, 11, 5), false)
        );

        return List.of(
                new Department("Engineering", "Building A", engEmps),
                new Department("Sales", "Building B", salesEmps),
                new Department("HR", "Building C", hrEmps)
        );
    }
}
