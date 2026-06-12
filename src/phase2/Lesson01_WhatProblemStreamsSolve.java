package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 1: What Problem Do Streams Solve?
 *
 * THEORY:
 * Before Java 8, working with collections required verbose, error-prone loops.
 * Streams provide a declarative, functional approach to processing sequences of data.
 *
 * PROBLEMS STREAMS SOLVE:
 * 1. Verbose iteration code (for loops, iterators)
 * 2. External iteration (developer manages the loop)
 * 3. Difficult to parallelize (requires manual thread management)
 * 4. Hard to compose operations (nested loops, temporary collections)
 * 5. Error-prone (concurrent modification, off-by-one errors)
 *
 * WHAT STREAMS PROVIDE:
 * 1. Internal iteration (Stream handles the loop)
 * 2. Declarative style (what, not how)
 * 3. Easy parallelization (parallelStream())
 * 4. Lazy evaluation (operations only execute when needed)
 * 5. Composable operations (chain multiple operations)
 */

public class Lesson01_WhatProblemStreamsSolve {

    public static void main(String[] args) {
        System.out.println("=== LESSON 1: What Problem Do Streams Solve? ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // PROBLEM 1: Verbose Code
        // ============================================
        System.out.println("--- Problem 1: Verbose Code ---");

        // Task: Get names of active engineers earning > 80000

        // BEFORE (Java 7 and earlier):
        List<Employee> engineers = new ArrayList<>();
        for (Employee emp : employees) {
            if ("Engineering".equals(emp.department()) && emp.active()) {
                engineers.add(emp);
            }
        }

        List<String> names = new ArrayList<>();
        for (Employee eng : engineers) {
            if (eng.salary().compareTo(new BigDecimal("80000")) > 0) {
                names.add(eng.name().toUpperCase());
            }
        }

        System.out.println("Names (verbose): " + names);

        // AFTER (Java 8+ with Streams):
        List<String> streamNames = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()) && emp.active())
                .filter(emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0)
                .map(emp -> emp.name().toUpperCase())
                .toList();

        System.out.println("Names (stream): " + streamNames);

        // ============================================
        // PROBLEM 2: External Iteration
        // ============================================
        System.out.println("\n--- Problem 2: External Iteration ---");

        // BEFORE: Developer controls the iteration
        int totalSalary = 0;
        for (Employee emp : employees) {
            totalSalary += emp.salary().intValue(); // Manual accumulation
        }
        System.out.println("Total salary (manual): " + totalSalary);

        // AFTER: Stream controls the iteration
        int streamTotal = employees.stream()
                .mapToInt(emp -> emp.salary().intValue())
                .sum();
        System.out.println("Total salary (stream): " + streamTotal);

        // ============================================
        // PROBLEM 3: Parallelization Difficulty
        // ============================================
        System.out.println("\n--- Problem 3: Parallelization ---");

        // BEFORE: Requires manual thread management
        // - Create ExecutorService
        // - Split collection into chunks
        // - Submit tasks
        // - Combine results
        // - Handle exceptions
        // Very complex!

        // AFTER: Just add parallelStream()
        long count = employees.parallelStream()
                .filter(Employee::active)
                .count();
        System.out.println("Active employees (parallel): " + count);

        // ============================================
        // PROBLEM 4: Composing Operations
        // ============================================
        System.out.println("\n--- Problem 4: Composing Operations ---");

        // Task: Find average salary of senior engineers in Engineering

        // BEFORE: Multiple loops, temporary variables
        int engCount = 0;
        BigDecimal engTotal = BigDecimal.ZERO;
        for (Employee emp : employees) {
            if ("Engineering".equals(emp.department()) && emp.age() > 30 && emp.active()) {
                engTotal = engTotal.add(emp.salary());
                engCount++;
            }
        }
        BigDecimal avg = engCount > 0 ? engTotal.divide(BigDecimal.valueOf(engCount)) : BigDecimal.ZERO;
        System.out.println("Avg salary (manual): " + avg);

        // AFTER: Chain operations
        BigDecimal streamAvg = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .filter(emp -> emp.age() > 30)
                .filter(Employee::active)
                .map(Employee::salary)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(
                        employees.stream()
                                .filter(emp -> "Engineering".equals(emp.department()))
                                .filter(emp -> emp.age() > 30)
                                .filter(Employee::active)
                                .count()
                ));
        System.out.println("Avg salary (stream): " + streamAvg);

        // ============================================
        // PROBLEM 5: Error-Prone Code
        // ============================================
        System.out.println("\n--- Problem 5: Error-Prone Code ---");

        // BEFORE: ConcurrentModificationException risk
        // for (Employee emp : employees) {
        //     if (emp.salary().compareTo(new BigDecimal("50000")) < 0) {
        //         employees.remove(emp); // Throws ConcurrentModificationException!
        //     }
        // }

        // AFTER: Safe removal via filter
        List<Employee> filtered = employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("50000")) >= 0)
                .toList();
        System.out.println("Filtered count: " + filtered.size());

        // ============================================
        // KEY INSIGHT
        // ============================================
        System.out.println("\n--- Key Insight ---");
        System.out.println("Streams shift from 'HOW' to 'WHAT':");
        System.out.println("- HOW: for loop, if condition, add to list, increment counter");
        System.out.println("- WHAT: filter engineers, map to names, collect to list");
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        employees.add(new Employee(6, "Frank", "Engineering", new BigDecimal("110000"), 40, LocalDate.of(2015, 2, 28), true));
        return employees;
    }
}
