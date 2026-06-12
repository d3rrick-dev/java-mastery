package phase9;

import java.util.*;

/**
 * LESSON 16: CLEAN CODE PRINCIPLES
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Clean code is code that is easy to read, understand, and modify.
 * Like a well-organized kitchen - everything has its place,
 * and anyone can find what they need.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Code is read more than written
 * - Reduces bugs
 * - Easier maintenance
 * - Better team collaboration
 */

public class Lesson16_CleanCodePrinciples {

    public static void main(String[] args) {
        System.out.println("=== CLEAN CODE PRINCIPLES ===\n");

        // ============================================================
        // EXAMPLE 1: Meaningful names
        // ============================================================
        System.out.println("--- Example 1: Meaningful Names ---\n");

        // Bad
        int d = 86400000;  // What is this?

        // Good
        int MILLISECONDS_PER_DAY = 86400000;
        System.out.println("Milliseconds per day: " + MILLISECONDS_PER_DAY);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Small functions
        // ============================================================
        System.out.println("--- Example 2: Small Functions ---\n");

        List<Employee> employees = createSampleEmployees();
        List<String> names = getEngineerNames(employees);
        System.out.println("Engineer names: " + names);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: DRY (Don't Repeat Yourself)
        // ============================================================
        System.out.println("--- Example 3: DRY Principle ---\n");

        // Bad: Repeated code
        // for (Employee e : employees) { if (e.getSalary() > 100000) ... }
        // for (Employee e : employees) { if (e.getSalary() > 100000) ... }

        // Good: Extract method
        List<Employee> highEarners = filterHighEarners(employees);
        System.out.println("High earners: " + highEarners.size());
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Comments - explain WHY, not WHAT
        // ============================================================
        System.out.println("--- Example 4: Good Comments ---\n");

        // Bad: // Loop through employees
        // for (Employee e : employees) { ... }

        // Good: // Use binary search because list is sorted by ID
        int index = Collections.binarySearch(
            employees.stream().map(Employee::id).toList(),
            1
        );
        System.out.println("Found at index: " + index);
        System.out.println();
    }

    // ============================================================
    // CLEAN CODE EXAMPLES
    // ============================================================

    static class Employee {
        private final int id;
        private final String name;
        private final String department;
        private final double salary;

        public Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public int id() { return id; }
        public String name() { return name; }
        public String department() { return department; }
        public double salary() { return salary; }

        @Override
        public String toString() {
            return name + " (" + department + ", $" + salary + ")";
        }
    }

    static List<Employee> createSampleEmployees() {
        return List.of(
            new Employee(1, "Alice", "Engineering", 120000),
            new Employee(2, "Bob", "Engineering", 110000),
            new Employee(3, "Charlie", "Marketing", 90000),
            new Employee(4, "Diana", "Engineering", 130000)
        );
    }

    // Small, focused function
    static List<String> getEngineerNames(List<Employee> employees) {
        return employees.stream()
            .filter(e -> "Engineering".equals(e.department()))
            .map(Employee::name)
            .toList();
    }

    // Reusable function (DRY)
    static List<Employee> filterHighEarners(List<Employee> employees) {
        return employees.stream()
            .filter(e -> e.salary() > 100000)
            .toList();
    }
}
