package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between String pool and String object?
 *
 * Difficulty: ADVANCED
 */

public class Lesson27_WhatIsTheDifferenceBetweenStringPoolAndStringObject {
    public static void main(String[] args) {
        System.out.println("=== STRING POOL VS STRING OBJECT ===\n");
        System.out.println("String Pool:");
        System.out.println("  - Special memory area in heap (String literal pool)");
        System.out.println("  - Stores unique String literals");
        System.out.println("  - Reuses existing strings to save memory");
        System.out.println("  - Created by JVM at class loading");
        System.out.println("  - Example: String s1 = \"hello\"; // goes to pool");
        System.out.println();
        System.out.println("String Object (new String()):");
        System.out.println("  - Created in heap memory (NOT in pool)");
        System.out.println("  - Always creates new object even if content exists");
        System.out.println("  - Uses more memory");
        System.out.println("  - Example: String s2 = new String(\"hello\"); // new object");
        System.out.println();
        System.out.println("Memory Comparison:");
        System.out.println("  - String s1 = \"hello\"; // 1 object in pool");
        System.out.println("  - String s2 = \"hello\"; // 0 new objects (reuses)");
        System.out.println("  - String s3 = new String(\"hello\"); // 1 object in heap");
        System.out.println("  - String s4 = new String(\"hello\"); // 1 more object in heap");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - s1 == s2 is TRUE (same pool reference)");
        System.out.println("  - s3 == s4 is FALSE (different heap objects)");
        System.out.println("  - s1.equals(s3) is TRUE (same content)");
    }
}
