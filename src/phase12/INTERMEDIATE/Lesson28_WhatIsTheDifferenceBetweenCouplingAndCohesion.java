package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between coupling and cohesion?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson28_WhatIsTheDifferenceBetweenCouplingAndCohesion {
    public static void main(String[] args) {
        System.out.println("=== COUPLING VS COHESION ===\n");
        System.out.println("Coupling:");
        System.out.println("  - Degree of dependency between classes/modules");
        System.out.println("  - Low coupling = good (classes independent)");
        System.out.println("  - High coupling = bad (changes ripple)");
        System.out.println("  - Achieved via interfaces, dependency injection");
        System.out.println();
        System.out.println("Cohesion:");
        System.out.println("  - Degree to which elements of a class belong together");
        System.out.println("  - High cohesion = good (single responsibility)");
        System.out.println("  - Low cohesion = bad (class does too much)");
        System.out.println("  - Achieved via single responsibility principle");
        System.out.println();
        System.out.println("Goal: Low coupling, high cohesion");
    }
}
