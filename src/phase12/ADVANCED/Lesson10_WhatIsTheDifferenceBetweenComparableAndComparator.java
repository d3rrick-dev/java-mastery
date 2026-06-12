package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Comparable and Comparator?
 *
 * Difficulty: ADVANCED
 */

public class Lesson10_WhatIsTheDifferenceBetweenComparableAndComparator {
    public static void main(String[] args) {
        System.out.println("=== COMPARABLE VS COMPARATOR ===\n");
        System.out.println("Comparable:");
        System.out.println("  - Interface in java.lang package");
        System.out.println("  - Defines natural ordering of objects");
        System.out.println("  - Method: int compareTo(T o)");
        System.out.println("  - Class implements Comparable<T>");
        System.out.println("  - Only ONE way to sort (natural order)");
        System.out.println();
        System.out.println("Comparator:");
        System.out.println("  - Interface in java.util package");
        System.out.println("  - Defines custom/external ordering");
        System.out.println("  - Method: int compare(T o1, T o2)");
        System.out.println("  - Separate class or lambda implements Comparator<T>");
        System.out.println("  - MULTIPLE ways to sort (different comparators)");
        System.out.println();
        System.out.println("When to use:");
        System.out.println("  - Comparable: when there's a natural/default ordering");
        System.out.println("  - Comparator: when you need multiple sorting strategies");
        System.out.println("  - Example: Employee (Comparable by ID), EmployeeSalaryComparator");
    }
}
