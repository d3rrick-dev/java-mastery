package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between association and dependency?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson27_WhatIsTheDifferenceBetweenAssociationAndDependency {
    public static void main(String[] args) {
        System.out.println("=== ASSOCIATION VS DEPENDENCY ===\n");
        System.out.println("Association:");
        System.out.println("  - Structural relationship between classes");
        System.out.println("  - One class uses another");
        System.out.println("  - Can be one-to-one, one-to-many, many-to-many");
        System.out.println("  - Example: Teacher teaches Students");
        System.out.println();
        System.out.println("Dependency:");
        System.out.println("  - Weaker relationship");
        System.out.println("  - One class depends on another temporarily");
        System.out.println("  - Usually method parameter or local variable");
        System.out.println("  - Example: Driver drives Car (method parameter)");
        System.out.println();
        System.out.println("Association is stronger, dependency is weaker");
    }
}
