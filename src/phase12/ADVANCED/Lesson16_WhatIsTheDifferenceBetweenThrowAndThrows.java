package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between throw and throws?
 *
 * Difficulty: ADVANCED
 */

public class Lesson16_WhatIsTheDifferenceBetweenThrowAndThrows {
    public static void main(String[] args) {
        System.out.println("=== THROW VS THROWS ===\n");
        System.out.println("throw:");
        System.out.println("  - Keyword used to explicitly throw an exception");
        System.out.println("  - Used inside method body");
        System.out.println("  - Can throw only one exception at a time");
        System.out.println("  - Example: throw new IllegalArgumentException();");
        System.out.println();
        System.out.println("throws:");
        System.out.println("  - Keyword used in method signature");
        System.out.println("  - Declares exceptions that method might throw");
        System.out.println("  - Can declare multiple exceptions separated by comma");
        System.out.println("  - Example: public void read() throws IOException, SQLException");
        System.out.println();
        System.out.println("Key Difference:");
        System.out.println("  - throw: actual throwing of exception (action)");
        System.out.println("  - throws: declaration of possible exceptions (declaration)");
        System.out.println();
        System.out.println("Interview Follow-up:");
        System.out.println("  - Can we throw checked exceptions without declaring them?");
        System.out.println("  - Answer: No, must declare with throws or catch");
    }
}
