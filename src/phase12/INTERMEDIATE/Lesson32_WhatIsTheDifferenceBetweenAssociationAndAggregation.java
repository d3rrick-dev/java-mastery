package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between association and aggregation?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson32_WhatIsTheDifferenceBetweenAssociationAndAggregation {
    public static void main(String[] args) {
        System.out.println("=== ASSOCIATION VS AGGREGATION ===\n");
        System.out.println("Association:");
        System.out.println("  - General relationship between classes");
        System.out.println("  - Can be one-way or bidirectional");
        System.out.println("  - Both classes can exist independently");
        System.out.println("  - Example: Teacher and Student");
        System.out.println();
        System.out.println("Aggregation:");
        System.out.println("  - Special type of association (weak HAS-A)");
        System.out.println("  - Child can exist independently");
        System.out.println("  - Parent holds reference to child");
        System.out.println("  - Example: Department has Employees");
        System.out.println();
        System.out.println("Aggregation is a form of association");
    }
}
