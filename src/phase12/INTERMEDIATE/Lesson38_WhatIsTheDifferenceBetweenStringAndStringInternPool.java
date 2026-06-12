package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between String and String intern pool?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson38_WhatIsTheDifferenceBetweenStringAndStringInternPool {
    public static void main(String[] args) {
        System.out.println("=== STRING VS STRING INTERN POOL ===\n");
        System.out.println("String:");
        System.out.println("  - Object representing sequence of characters");
        System.out.println("  - Immutable");
        System.out.println("  - Created with new or literal");
        System.out.println();
        System.out.println("String Intern Pool:");
        System.out.println("  - Special memory area in heap (String pool)");
        System.out.println("  - Stores unique String literals");
        System.out.println("  - Reuses existing strings to save memory");
        System.out.println("  - intern() method returns pooled instance");
        System.out.println();
        System.out.println("String s1 = \"hello\";  // In pool");
        System.out.println("String s2 = new String(\"hello\");  // In heap");
        System.out.println("String s3 = s2.intern();  // Returns pooled \"hello\"");
    }
}
