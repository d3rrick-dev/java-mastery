package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * LESSON 11: min() and max()
 *
 * THEORY:
 * min() and max() find the minimum and maximum elements according to a Comparator.
 * They return Optional<T> because the stream might be empty.
 *
 * SYNTAX:
 * stream.min(Comparator) -> Optional<T>
 * stream.max(Comparator) -> Optional<T>
 *
 * WHY THEY EXIST:
 * - Find extreme values efficiently
 * - Avoid manual comparison loops
 * - Work with any comparable type
 */

public class Lesson11_StreamOperations_MinMax {

    public static void main(String[] args) {
        System.out.println("=== LESSON 11: min() and max() ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC min() and max()
        // ============================================
        System.out.println("--- 1. Basic min() and max() ---");

        // Highest salary
        Optional<Employee> highestPaid = employees.stream()
                .max((e1, e2) -> e1.salary().compareTo(e2.salary()));
        highestPaid.ifPresent(emp ->
                System.out.println("Highest paid: " + emp.name() + " - $" + emp.salary()));

        // Lowest salary
        Optional<Employee> lowestPaid = employees.stream()
                .min((e1, e2) -> e1.salary().compareTo(e2.salary()));
        lowestPaid.ifPresent(emp ->
                System.out.println("Lowest paid: " + emp.name() + " - $" + emp.salary()));

        // Oldest employee
        Optional<Employee> oldest = employees.stream()
                .max((e1, e2) -> Integer.compare(e1.age(), e2.age()));
        oldest.ifPresent(emp ->
                System.out.println("Oldest: " + emp.name() + " - " + emp.age() + " years"));

        // Youngest employee
        Optional<Employee> youngest = employees.stream()
                .min((e1, e2) -> Integer.compare(e1.age(), e2.age()));
        youngest.ifPresent(emp ->
                System.out.println("Youngest: " + emp.name() + " - " + emp.age() + " years"));

        // ============================================
        // 2. min()/max() WITH map()
        // ============================================
        System.out.println("\n--- 2. min()/max() with map() ---");

        // Highest salary value (not employee)
        Optional<BigDecimal> maxSalary = employees.stream()
                .map(Employee::salary)
                .max(BigDecimal::compareTo);
        System.out.println("Max salary value: $" + maxSalary.orElse(BigDecimal.ZERO));

        // Lowest salary value
        Optional<BigDecimal> minSalary = employees.stream()
                .map(Employee::salary)
                .min(BigDecimal::compareTo);
        System.out.println("Min salary value: $" + minSalary.orElse(BigDecimal.ZERO));

        // ============================================
        // 3. min()/max() WITH filter()
        // ============================================
        System.out.println("\n--- 3. min()/max() with filter() ---");

        // Highest paid engineer
        Optional<Employee> highestPaidEngineer = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .max((e1, e2) -> e1.salary().compareTo(e2.salary()));
        highestPaidEngineer.ifPresent(emp ->
                System.out.println("Highest paid engineer: " + emp.name() + " - $" + emp.salary()));

        // Lowest paid active employee
        Optional<Employee> lowestPaidActive = employees.stream()
                .filter(Employee::active)
                .min((e1, e2) -> e1.salary().compareTo(e2.salary()));
        lowestPaidActive.ifPresent(emp ->
                System.out.println("Lowest paid active: " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 4. HANDLING EMPTY STREAMS
        // ============================================
        System.out.println("\n--- 4. Handling Empty Streams ---");

        List<Employee> emptyList = new ArrayList<>();
        Optional<Employee> maxFromEmpty = emptyList.stream()
                .max((e1, e2) -> e1.salary().compareTo(e2.salary()));
        System.out.println("Max from empty: " + maxFromEmpty); // Optional.empty

        // Provide default value
        Employee defaultEmp = maxFromEmpty.orElse(null);
        System.out.println("Default: " + defaultEmp);

        // Or throw exception
        try {
            Employee emp = maxFromEmpty.orElseThrow(() ->
                    new RuntimeException("No employees found!"));
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Find top 3 highest paid employees
        System.out.println("Top 3 highest paid:");
        employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .limit(3)
                .forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // Challenge: Find department with highest average salary
        // First, calculate average per department
        java.util.Map<String, Double> avgByDept = employees.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Employee::department,
                        java.util.stream.Collectors.averagingInt(emp -> emp.salary().intValue())
                ));

        // Then find max
        Optional<String> highestAvgDept = avgByDept.entrySet().stream()
                .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                .map(java.util.Map.Entry::getKey);

        highestAvgDept.ifPresent(dept ->
                System.out.println("Department with highest avg salary: " + dept));

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using min/max without comparator for custom objects
        // employees.stream().max(); // ERROR: Employee doesn't implement Comparable
        employees.stream().max((e1, e2) -> e1.salary().compareTo(e2.salary())); // Correct

        // Mistake 2: Forgetting Optional handling
        Optional<Employee> maybeEmp = employees.stream()
                .filter(emp -> emp.id() == 999)
                .findFirst();
        // Employee emp = maybeEmp.get(); // Throws NoSuchElementException!
        Employee safe = maybeEmp.orElse(null);
        System.out.println("Safe get: " + safe);

        // Mistake 3: Using min/max when you need top N
        // employees.stream().max(...); // Only gives ONE element
        // For top N, use sorted().limit(N)
        List<Employee> top2 = employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .limit(2)
                .toList();
        System.out.println("Top 2: " + top2.size() + " employees");
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        employees.add(new Employee(6, "Frank", "Sales", new BigDecimal("90000"), 40, LocalDate.of(2015, 2, 28), true));
        return employees;
    }
}
