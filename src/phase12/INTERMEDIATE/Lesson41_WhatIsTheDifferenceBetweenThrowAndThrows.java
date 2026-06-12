package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: What is the difference between throw and throws?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson41_WhatIsTheDifferenceBetweenThrowAndThrows {
    public static void main(String[] args) {
        System.out.println("=== THROW vs THROWS ===\n");
        System.out.println("throw:");
        System.out.println("  - Used to explicitly throw an exception");
        System.out.println("  - Used inside method body");
        System.out.println("  - Throws one exception at a time");
        System.out.println("  - Example: throw new RuntimeException();");
        System.out.println();
        System.out.println("throws:");
        System.out.println("  - Used to declare exceptions a method might throw");
        System.out.println("  - Used in method signature");
        System.out.println("  - Can declare multiple exceptions");
        System.out.println("  - Example: public void read() throws IOException, SQLException");
        System.out.println();
        System.out.println("Key Difference:");
        System.out.println("  - throw: action (throwing exception)");
        System.out.println("  - throws: declaration (declaring possible exceptions)");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - throw is for throwing, throws is for declaring");
        System.out.println("  - Checked exceptions must be declared with throws or caught");
        System.out.println("  - Runtime exceptions don't need throws declaration");
    }
}
