package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between throw and throws keyword?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson39_WhatIsTheDifferenceBetweenThrowAndThrowsKeyword {
    public static void main(String[] args) {
        System.out.println("=== THROW VS THROWS KEYWORD ===\n");
        System.out.println("throw:");
        System.out.println("  - Used to explicitly throw an exception");
        System.out.println("  - Used inside method body");
        System.out.println("  - Can throw one exception at a time");
        System.out.println("  - Example: throw new RuntimeException();");
        System.out.println();
        System.out.println("throws:");
        System.out.println("  - Declares exceptions a method might throw");
        System.out.println("  - Used in method signature");
        System.out.println("  - Can declare multiple exceptions");
        System.out.println("  - Example: public void read() throws IOException, SQLException");
        System.out.println();
        System.out.println("throw: action (throwing)");
        System.out.println("throws: declaration (informing caller)");
    }
}
