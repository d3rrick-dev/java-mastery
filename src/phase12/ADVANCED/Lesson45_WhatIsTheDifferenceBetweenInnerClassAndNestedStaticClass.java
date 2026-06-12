package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between inner class and nested static class?
 *
 * Difficulty: ADVANCED
 */

public class Lesson45_WhatIsTheDifferenceBetweenInnerClassAndNestedStaticClass {
    public static void main(String[] args) {
        System.out.println("=== INNER CLASS VS NESTED STATIC CLASS ===\n");
        System.out.println("Inner Class (Non-static Nested Class):");
        System.out.println("  - Associated with instance of outer class");
        System.out.println("  - Can access all members of outer class (including private)");
        System.out.println("  - Requires outer class instance to create");
        System.out.println("  - Has implicit reference to outer class (this$0)");
        System.out.println("  - Example: Outer.Inner inner = outer.new Inner();");
        System.out.println();
        System.out.println("Nested Static Class:");
        System.out.println("  - NOT associated with outer class instance");
        System.out.println("  - Can only access static members of outer class");
        System.out.println("  - Can be created without outer class instance");
        System.out.println("  - No implicit reference to outer class");
        System.out.println("  - Example: Outer.StaticNested nested = new Outer.StaticNested();");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Instance: requires outer instance vs independent");
        System.out.println("  - Access: all members vs only static members");
        System.out.println("  - Memory: holds outer reference vs no reference");
        System.out.println("  - Usage: when inner needs outer state vs utility/helper");
    }
}
