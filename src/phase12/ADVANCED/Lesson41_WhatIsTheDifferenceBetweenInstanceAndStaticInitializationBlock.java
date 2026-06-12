package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between instance and static initialization block?
 *
 * Difficulty: ADVANCED
 */

public class Lesson41_WhatIsTheDifferenceBetweenInstanceAndStaticInitializationBlock {
    public static void main(String[] args) {
        System.out.println("=== INSTANCE VS STATIC INITIALIZATION BLOCK ===\n");
        System.out.println("Instance Initialization Block:");
        System.out.println("  - Runs before constructor for each object creation");
        System.out.println("  - Can access instance variables");
        System.out.println("  - Executed in order of appearance");
        System.out.println("  - Runs every time object is created");
        System.out.println("  - Example: { this.count++; }");
        System.out.println();
        System.out.println("Static Initialization Block:");
        System.out.println("  - Runs once when class is loaded");
        System.out.println("  - Can only access static variables");
        System.out.println("  - Executed in order of appearance");
        System.out.println("  - Runs only once per classloader");
        System.out.println("  - Example: static { this.count = 0; }");
        System.out.println();
        System.out.println("Execution Order:");
        System.out.println("  1. Static variables and static blocks (in order)");
        System.out.println("  2. Instance variables and instance blocks (in order)");
        System.out.println("  3. Constructor body");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Static: class-level, runs once");
        System.out.println("  - Instance: object-level, runs per object");
    }
}
