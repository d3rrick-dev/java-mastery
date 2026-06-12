package phase1;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 1: What is Functional Programming?
 *
 * THEORY:
 * Functional Programming (FP) is a programming paradigm that treats computation
 * as the evaluation of mathematical functions and avoids changing-state and mutable data.
 *
 * KEY CONCEPTS:
 * 1. Pure Functions - Output depends ONLY on input, no side effects
 * 2. Immutability - Data cannot be changed after creation
 * 3. First-class functions - Functions can be assigned to variables, passed as arguments, returned from other functions
 * 4. Higher-order functions - Functions that take other functions as parameters or return them
 * 5. Declarative style - Focus on WHAT to do, not HOW to do it
 *
 * WHY IT EXISTS:
 * - Reduces bugs by eliminating side effects
 * - Makes code more testable and maintainable
 * - Enables parallel processing more easily
 * - Java 8 introduced FP features to compete with modern languages like Scala, Kotlin
 */

public class Lesson01_WhatIsFunctionalProgramming {

    public static void main(String[] args) {
        System.out.println("=== LESSON 1: What is Functional Programming? ===\n");

        // ============================================
        // IMPERATIVE STYLE (What you already know)
        // ============================================
        System.out.println("--- IMPERATIVE STYLE ---");

        List<Employee> employees = createSampleEmployees();

        // Imperative: Tell the computer HOW to do it step by step
        List<Employee> highEarnersImperative = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.salary().compareTo(new BigDecimal("80000")) > 0) {
                highEarnersImperative.add(emp);
            }
        }

        System.out.println("Imperative - High earners:");
        highEarnersImperative.forEach(e -> System.out.println("  " + e));

        // ============================================
        // FUNCTIONAL STYLE (Declarative)
        // ============================================
        System.out.println("\n--- FUNCTIONAL STYLE ---");

        // Functional: Tell the computer WHAT you want
        List<Employee> highEarnersFunctional = employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0)
                .toList();

        System.out.println("Functional - High earners:");
        highEarnersFunctional.forEach(e -> System.out.println("  " + e));

        // ============================================
        // COMPARISON
        // ============================================
        System.out.println("\n--- COMPARISON ---");
        System.out.println("Imperative: for loop + if + manual list creation");
        System.out.println("Functional: stream().filter().toList()");
        System.out.println("\nBoth produce the SAME result, but functional is:");
        System.out.println("- More concise (less boilerplate)");
        System.out.println("- More readable (declarative)");
        System.out.println("- Easier to parallelize (parallelStream())");
        System.out.println("- Less error-prone (no manual loop management)");
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        return employees;
    }
}
