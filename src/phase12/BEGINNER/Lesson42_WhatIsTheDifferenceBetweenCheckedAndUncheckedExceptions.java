package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between checked and unchecked exceptions?
 *
 * Difficulty: BEGINNER
 */

public class Lesson42_WhatIsTheDifferenceBetweenCheckedAndUncheckedExceptions {
    public static void main(String[] args) {
        System.out.println("=== CHECKED VS UNCHECKED EXCEPTIONS ===\n");
        System.out.println("Checked Exceptions:");
        System.out.println("  - Checked at compile time");
        System.out.println("  - Must be handled (try-catch) or declared (throws)");
        System.out.println("  - Extend Exception class");
        System.out.println("  - Examples: IOException, SQLException, ClassNotFoundException");
        System.out.println();
        System.out.println("Unchecked Exceptions (Runtime):");
        System.out.println("  - Checked at runtime");
        System.out.println("  - No mandatory handling");
        System.out.println("  - Extend RuntimeException class");
        System.out.println("  - Examples: NullPointerException, ArrayIndexOutOfBounds, IllegalArgumentException");
    }
}
