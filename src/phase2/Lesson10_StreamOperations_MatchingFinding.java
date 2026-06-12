package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * LESSON 10: anyMatch(), allMatch(), noneMatch(), findFirst(), findAny()
 *
 * THEORY:
 * These terminal operations check conditions or find elements.
 *
 * anyMatch(Predicate)  : Returns true if ANY element matches
 * allMatch(Predicate)  : Returns true if ALL elements match
 * noneMatch(Predicate) : Returns true if NO elements match
 * findFirst()          : Returns first element (encounters order)
 * findAny()            : Returns any matching element (parallel friendly)
 */

public class Lesson10_StreamOperations_MatchingFinding {

    public static void main(String[] args) {
        System.out.println("=== LESSON 10: Matching and Finding Operations ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. anyMatch() - ANY ELEMENT MATCHES
        // ============================================
        System.out.println("--- 1. anyMatch() ---");

        boolean anyActive = employees.stream()
                .anyMatch(Employee::active);
        System.out.println("Any active employee: " + anyActive);

        boolean anyHighEarner = employees.stream()
                .anyMatch(emp -> emp.salary().compareTo(new BigDecimal("100000")) > 0);
        System.out.println("Any high earner (>100k): " + anyHighEarner);

        boolean anyInHR = employees.stream()
                .anyMatch(emp -> "HR".equals(emp.department()));
        System.out.println("Any in HR: " + anyInHR);

        // ============================================
        // 2. allMatch() - ALL ELEMENTS MATCH
        // ============================================
        System.out.println("\n--- 2. allMatch() ---");

        boolean allActive = employees.stream()
                .allMatch(Employee::active);
        System.out.println("All active: " + allActive);

        boolean allHaveSalary = employees.stream()
                .allMatch(emp -> emp.salary().compareTo(BigDecimal.ZERO) > 0);
        System.out.println("All have positive salary: " + allHaveSalary);

        // ============================================
        // 3. noneMatch() - NO ELEMENTS MATCH
        // ============================================
        System.out.println("\n--- 3. noneMatch() ---");

        boolean noneInactive = employees.stream()
                .noneMatch(emp -> !emp.active());
        System.out.println("None inactive: " + noneInactive);

        boolean noneWithLowSalary = employees.stream()
                .noneMatch(emp -> emp.salary().compareTo(new BigDecimal("50000")) < 0);
        System.out.println("None with salary < 50k: " + noneWithLowSalary);

        // ============================================
        // 4. findFirst() - FIRST ELEMENT
        // ============================================
        System.out.println("\n--- 4. findFirst() ---");

        Optional<Employee> firstEngineer = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .findFirst();
        firstEngineer.ifPresent(emp ->
                System.out.println("First engineer: " + emp.name()));

        Optional<Employee> firstHighEarner = employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("90000")) > 0)
                .findFirst();
        firstHighEarner.ifPresent(emp ->
                System.out.println("First high earner: " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 5. findAny() - ANY MATCHING ELEMENT
        // ============================================
        System.out.println("\n--- 5. findAny() ---");

        Optional<Employee> anyEngineer = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .findAny();
        anyEngineer.ifPresent(emp ->
                System.out.println("Any engineer: " + emp.name()));

        // In parallel streams, findAny() may return different elements
        Optional<Employee> anyEngineerParallel = employees.parallelStream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .findAny();
        anyEngineerParallel.ifPresent(emp ->
                System.out.println("Any engineer (parallel): " + emp.name()));

        // ============================================
        // 6. findFirst() vs findAny()
        // ============================================
        System.out.println("\n--- 6. findFirst() vs findAny() ---");

        System.out.println("findFirst(): Guaranteed to return first element in encounter order");
        System.out.println("findAny(): May return any element (faster in parallel)");

        // ============================================
        // 7. SHORT-CIRCUITING BEHAVIOR
        // ============================================
        System.out.println("\n--- 7. Short-Circuiting ---");

        // anyMatch, allMatch, noneMatch, findFirst, findAny are short-circuiting
        // They stop processing as soon as result is determined

        System.out.println("anyMatch stops after first match:");
        employees.stream()
                .filter(emp -> {
                    System.out.println("  Checking: " + emp.name());
                    return emp.salary().compareTo(new BigDecimal("100000")) > 0;
                })
                .findFirst()
                .ifPresent(emp -> System.out.println("  Found: " + emp.name()));

        // ============================================
        // 8. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 8. Coding Challenge ---");

        // Challenge: Check if all engineers earn > 70000
        List<Employee> engineers = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .toList();

        boolean allEngineersHighEarners = engineers.stream()
                .allMatch(emp -> emp.salary().compareTo(new BigDecimal("70000")) > 0);
        System.out.println("All engineers earn > 70k: " + allEngineersHighEarners);

        // Challenge: Find if there's any inactive senior employee (age > 35)
        boolean anyInactiveSenior = employees.stream()
                .anyMatch(emp -> !emp.active() && emp.age() > 35);
        System.out.println("Any inactive senior: " + anyInactiveSenior);

        // ============================================
        // 9. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 9. Common Mistakes ---");

        // Mistake 1: Using findFirst() when order doesn't matter (use findAny for parallel)
        // employees.parallelStream().filter(...).findFirst(); // May be slower
        employees.parallelStream().filter(emp -> "Engineering".equals(emp.department()))
                .findAny(); // Better for parallel

        // Mistake 2: Forgetting that match operations are short-circuiting
        // allMatch stops at first false
        // anyMatch stops at first true
        // noneMatch stops at first match

        // Mistake 3: Not handling Optional
        Optional<Employee> maybeEmp = employees.stream()
                .filter(emp -> emp.id() == 999)
                .findFirst();
        // Employee emp = maybeEmp.get(); // Throws NoSuchElementException!
        Employee safe = maybeEmp.orElse(null);
        System.out.println("Safe find: " + safe);
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
