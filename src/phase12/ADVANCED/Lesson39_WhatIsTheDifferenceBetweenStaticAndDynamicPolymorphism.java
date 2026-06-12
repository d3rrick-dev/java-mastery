package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between static and dynamic polymorphism?
 *
 * Difficulty: ADVANCED
 */

public class Lesson39_WhatIsTheDifferenceBetweenStaticAndDynamicPolymorphism {
    public static void main(String[] args) {
        System.out.println("=== STATIC VS DYNAMIC POLYMORPHISM ===\n");
        System.out.println("Static Polymorphism (Compile-time):");
        System.out.println("  - Also called method overloading");
        System.out.println("  - Resolved at compile time");
        System.out.println("  - Same method name, different parameters");
        System.out.println("  - Faster (no runtime overhead)");
        System.out.println("  - Example: print(int), print(String), print(int, int)");
        System.out.println();
        System.out.println("Dynamic Polymorphism (Runtime):");
        System.out.println("  - Also called method overriding");
        System.out.println("  - Resolved at runtime");
        System.out.println("  - Same method signature, different implementations");
        System.out.println("  - Uses virtual method invocation");
        System.out.println("  - Example: Animal.speak() overridden by Dog.speak()");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Resolution: compile-time vs runtime");
        System.out.println("  - Mechanism: overloading vs overriding");
        System.out.println("  - Performance: faster vs slightly slower");
        System.out.println("  - Flexibility: less vs more");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Static: multiple methods with same name, different params");
        System.out.println("  - Dynamic: same method, different behavior in subclasses");
    }
}
