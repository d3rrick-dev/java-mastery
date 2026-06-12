package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between public, default, and private methods in interface?
 *
 * Difficulty: ADVANCED
 */

public class Lesson28_WhatIsTheDifferenceBetweenPublicDefaultAndPrivateMethodsInInterface {
    public static void main(String[] args) {
        System.out.println("=== INTERFACE METHODS (JAVA 8+) ===\n");
        System.out.println("Public Abstract Methods:");
        System.out.println("  - Default method type in interfaces");
        System.out.println("  - Must be implemented by implementing classes");
        System.out.println("  - Example: void save();");
        System.out.println();
        System.out.println("Default Methods (Java 8+):");
        System.out.println("  - Have implementation in interface");
        System.out.println("  - Can be overridden by implementing classes");
        System.out.println("  - Used for backward compatibility");
        System.out.println("  - Example: default void delete() { /* implementation */ }");
        System.out.println();
        System.out.println("Private Methods (Java 9+):");
        System.out.println("  - Have implementation in interface");
        System.out.println("  - Cannot be overridden (not visible to implementors)");
        System.out.println("  - Used to share code between default methods");
        System.out.println("  - Example: private void validate() { /* implementation */ }");
        System.out.println();
        System.out.println("Static Methods (Java 8+):");
        System.out.println("  - Belong to interface, not instance");
        System.out.println("  - Cannot be overridden");
        System.out.println("  - Called via interface name");
        System.out.println("  - Example: static void helper() { /* implementation */ }");
    }
}
