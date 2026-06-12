package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between Comparable and Comparator?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson01_WhatIsTheDifferenceBetweenComparableAndComparator {
    public static void main(String[] args) {
        System.out.println("=== COMPARABLE VS COMPARATOR ===\n");
        System.out.println("Comparable:");
        System.out.println("  - Interface: java.lang.Comparable<T>");
        System.out.println("  - compareTo(T o) method");
        System.out.println("  - Natural ordering (single sort sequence)");
        System.out.println("  - Implemented by the class itself");
        System.out.println("  - Example: class Employee implements Comparable<Employee>");
        System.out.println();
        System.out.println("Comparator:");
        System.out.println("  - Interface: java.util.Comparator<T>");
        System.out.println("  - compare(T o1, T o2) method");
        System.out.println("  - External ordering (multiple sort sequences)");
        System.out.println("  - Implemented as separate class or lambda");
        System.out.println("  - Example: Comparator.comparing(Employee::salary)");
        System.out.println();
        System.out.println("Use Comparable for default/natural ordering");
        System.out.println("Use Comparator for multiple/ad-hoc ordering");
    }
}
