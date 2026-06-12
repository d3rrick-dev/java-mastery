package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between aggregation and composition?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson26_WhatIsTheDifferenceBetweenAggregationAndComposition {
    public static void main(String[] args) {
        System.out.println("=== AGGREGATION VS COMPOSITION ===\n");
        System.out.println("Aggregation (has-a, weak relationship):");
        System.out.println("  - Child can exist independently of parent");
        System.out.println("  - Parent holds reference to child");
        System.out.println("  - Example: Department has Employees (employees can exist without department)");
        System.out.println();
        System.out.println("Composition (part-of, strong relationship):");
        System.out.println("  - Child cannot exist without parent");
        System.out.println("  - Parent creates and destroys child");
        System.out.println("  - Example: House has Rooms (rooms cannot exist without house)");
        System.out.println();
        System.out.println("Both are forms of association in OOP");
    }
}
