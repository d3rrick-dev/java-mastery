package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between for and for-each?
 *
 * Difficulty: BEGINNER
 */

public class Lesson28_WhatIsTheDifferenceBetweenForAndForEach {
    public static void main(String[] args) {
        System.out.println("=== FOR VS FOR-EACH ===\n");
        System.out.println("for: Traditional loop with index, can modify elements, can iterate backwards");
        System.out.println("for-each: Enhanced loop, no index, read-only, forward only");
        System.out.println();
        System.out.println("for (int i = 0; i < list.size(); i++) { }");
        System.out.println("for (Type item : collection) { }");
        System.out.println();
        System.out.println("Use for-each when: Don't need index, just reading");
        System.out.println("Use for when: Need index, need to modify, need direction control");
    }
}
