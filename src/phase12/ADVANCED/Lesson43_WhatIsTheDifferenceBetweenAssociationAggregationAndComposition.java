package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between association, aggregation, and composition?
 *
 * Difficulty: ADVANCED
 */

public class Lesson43_WhatIsTheDifferenceBetweenAssociationAggregationAndComposition {
    public static void main(String[] args) {
        System.out.println("=== ASSOCIATION VS AGGREGATION VS COMPOSITION ===\n");
        System.out.println("Association:");
        System.out.println("  - General relationship between two classes");
        System.out.println("  - Uses reference of one class in another");
        System.out.println("  - Can be one-to-one, one-to-many, many-to-many");
        System.out.println("  - No ownership implied");
        System.out.println("  - Example: Teacher teaches Student");
        System.out.println();
        System.out.println("Aggregation (Special Association):");
        System.out.println("  - HAS-A relationship");
        System.out.println("  - Weak relationship");
        System.out.println("  - Child can exist independently");
        System.out.println("  - Example: Department has Employees");
        System.out.println();
        System.out.println("Composition (Strong Association):");
        System.out.println("  - PART-OF relationship");
        System.out.println("  - Strong relationship");
        System.out.println("  - Child cannot exist without parent");
        System.out.println("  - Example: House has Rooms");
        System.out.println();
        System.out.println("Hierarchy:");
        System.out.println("  Association (general)");
        System.out.println("    -> Aggregation (weak)");
        System.out.println("    -> Composition (strong)");
    }
}
