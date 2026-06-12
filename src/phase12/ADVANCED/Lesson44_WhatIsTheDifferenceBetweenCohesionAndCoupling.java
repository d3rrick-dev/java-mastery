package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between cohesion and coupling?
 *
 * Difficulty: ADVANCED
 */

public class Lesson44_WhatIsTheDifferenceBetweenCohesionAndCoupling {
    public static void main(String[] args) {
        System.out.println("=== COHESION VS COUPLING ===\n");
        System.out.println("Cohesion:");
        System.out.println("  - Measure of how related elements within a module are");
        System.out.println("  - High cohesion: single responsibility, focused");
        System.out.println("  - Low cohesion: unrelated responsibilities mixed");
        System.out.println("  - Goal: maximize cohesion");
        System.out.println("  - Example: UserService handles only user-related operations");
        System.out.println();
        System.out.println("Coupling:");
        System.out.println("  - Measure of dependency between modules");
        System.out.println("  - Tight coupling: modules depend heavily on each other");
        System.out.println("  - Loose coupling: modules are independent");
        System.out.println("  - Goal: minimize coupling");
        System.out.println("  - Example: UserService uses interface, not concrete class");
        System.out.println();
        System.out.println("Design Principles:");
        System.out.println("  - High cohesion, low coupling = good design");
        System.out.println("  - Achieved through SOLID principles");
        System.out.println("  - Achieved through dependency injection");
        System.out.println("  - Achieved through interfaces and abstractions");
    }
}
