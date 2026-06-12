package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between checked and unchecked exceptions?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of exception handling
 * - Shows knowledge of Java error types
 * - Reveals best practices awareness
 */

public class Lesson08_WhatIsDifferenceBetweenCheckedAndUncheckedExceptions {

    public static void main(String[] args) {
        System.out.println("=== CHECKED VS UNCHECKED EXCEPTIONS ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between checked and unchecked exceptions?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("CHECKED EXCEPTIONS:");
        System.out.println("  - Extend Exception (but NOT RuntimeException)");
        System.out.println("  - Checked at COMPILE TIME");
        System.out.println("  - Must be caught or declared (throws)");
        System.out.println("  - Represent recoverable conditions");
        System.out.println("  - Examples: IOException, SQLException, ClassNotFoundException");
        System.out.println();

        System.out.println("UNCHECKED EXCEPTIONS:");
        System.out.println("  - Extend RuntimeException");
        System.out.println("  - Checked at RUNTIME");
        System.out.println("  - NOT required to catch or declare");
        System.out.println("  - Represent programming errors");
        System.out.println("  - Examples: NullPointerException, IllegalArgumentException, IndexOutOfBoundsException");
        System.out.println();

        System.out.println("ERROR (not exception):");
        System.out.println("  - Extend Error");
        System.out.println("  - Serious problems (OutOfMemoryError, StackOverflowError)");
        System.out.println("  - Applications should NOT catch");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("Checked exception (must handle):");
        System.out.println("  public void readFile() throws IOException {");
        System.out.println("    FileInputStream fis = new FileInputStream(\"file.txt\");");
        System.out.println("  }");
        System.out.println();
        System.out.println("  // Must either catch or declare:");
        System.out.println("  try { readFile(); }");
        System.out.println("  catch (IOException e) { /* handle */ }");
        System.out.println();

        System.out.println("Unchecked exception (optional handling):");
        System.out.println("  public void divide(int a, int b) {");
        System.out.println("    int result = a / b;  // ArithmeticException if b=0");
        System.out.println("  }");
        System.out.println();
        System.out.println("  // No compile error if not caught:");
        System.out.println("  divide(10, 0);  // Throws at runtime");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Catching Exception or Throwable:");
        System.out.println("   - Catches everything including RuntimeExceptions");
        System.out.println("   - Hides programming errors");
        System.out.println();

        System.out.println("2. Empty catch blocks:");
        System.out.println("   - Swallows exceptions silently");
        System.out.println("   - Always log or handle properly");
        System.out.println();

        System.out.println("3. Using checked exceptions for programming errors:");
        System.out.println("   - IllegalArgumentException should be unchecked");
        System.out.println("   - Use checked for external conditions (IO, DB)");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. When should you create a custom checked exception?");
        System.out.println("2. What is exception chaining?");
        System.out.println("3. What is the difference between throw and throws?");
        System.out.println("4. What happens when an exception is thrown?");
        System.out.println("5. What is try-with-resources?");
    }
}
