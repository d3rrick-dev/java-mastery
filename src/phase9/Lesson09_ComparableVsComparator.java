package phase9;

import java.util.*;

/**
 * LESSON 9: COMPARABLE vs COMPARATOR
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Comparable: Natural ordering (class defines its own comparison)
 * Comparator: External ordering (separate class defines comparison)
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Comparable: Single natural order (e.g., Employee by ID)
 * - Comparator: Multiple orderings (by name, by salary, etc.)
 * - Flexibility for different sorting needs
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Employee sorting:
 * - Comparable: by employee ID (natural)
 * - Comparator: by name, by salary, by department
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API results:
 * - Default: by ID (Comparable)
 * - User choice: by date, by relevance (Comparator)
 */

public class Lesson09_ComparableVsComparator {

    public static void main(String[] args) {
        System.out.println("=== COMPARABLE vs COMPARATOR ===\n");

        // ============================================================
        // EXAMPLE 1: Comparable (natural ordering)
        // ============================================================
        System.out.println("--- Example 1: Comparable ---\n");

        List<EmployeeComparable> employees = new ArrayList<>();
        employees.add(new EmployeeComparable(3, "Charlie"));
        employees.add(new EmployeeComparable(1, "Alice"));
        employees.add(new EmployeeComparable(2, "Bob"));

        System.out.println("Before sort: " + employees);
        Collections.sort(employees);
        System.out.println("After sort: " + employees);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Comparator (external ordering)
        // ============================================================
        System.out.println("--- Example 2: Comparator ---\n");

        // Sort by name
        employees.sort(new NameComparator());
        System.out.println("By name: " + employees);

        // Sort by name reverse
        employees.sort(Comparator.reverseOrder());
        System.out.println("By name reverse: " + employees);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Lambda comparator
        // ============================================================
        System.out.println("--- Example 3: Lambda Comparator ---\n");

        employees.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        System.out.println("By name (lambda): " + employees);

        employees.sort(Comparator.comparing(e -> e.getName()));
        System.out.println("By name (method ref): " + employees);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Multiple comparators
        // ============================================================
        System.out.println("--- Example 4: Multiple Comparators ---\n");

        List<EmployeeComparable> emps = new ArrayList<>();
        emps.add(new EmployeeComparable(1, "Alice", 50000));
        emps.add(new EmployeeComparable(2, "Bob", 60000));
        emps.add(new EmployeeComparable(3, "Charlie", 50000));

        // Sort by salary, then by name
        emps.sort(Comparator
            .comparing(EmployeeComparable::getSalary)
            .thenComparing(EmployeeComparable::getName));

        System.out.println("By salary then name: " + emps);
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Comparable vs Comparator comparison
        // ============================================================
        System.out.println("--- Example 5: Comparison ---\n");

        System.out.println("Comparable:");
        System.out.println("  - Interface: compareTo(T o)");
        System.out.println("  - Single natural ordering");
        System.out.println("  - Class implements it");
        System.out.println("  - Used by Collections.sort()");
        System.out.println();
        System.out.println("Comparator:");
        System.out.println("  - Interface: compare(T o1, T o2)");
        System.out.println("  - Multiple orderings possible");
        System.out.println("  - Separate class or lambda");
        System.out.println("  - Used by sort(Comparator)");
        System.out.println();
    }

    // ============================================================
    // HELPER CLASSES
    // ============================================================

    static class EmployeeComparable implements Comparable<EmployeeComparable> {
        private int id;
        private String name;
        private double salary;

        public EmployeeComparable(int id, String name) {
            this(id, name, 0);
        }

        public EmployeeComparable(int id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getSalary() { return salary; }

        @Override
        public int compareTo(EmployeeComparable other) {
            return Integer.compare(this.id, other.id);
        }

        @Override
        public String toString() {
            return "Employee{id=" + id + ", name='" + name + "'}";
        }
    }

    static class NameComparator implements Comparator<EmployeeComparable> {
        @Override
        public int compare(EmployeeComparable e1, EmployeeComparable e2) {
            return e1.getName().compareTo(e2.getName());
        }
    }
}
