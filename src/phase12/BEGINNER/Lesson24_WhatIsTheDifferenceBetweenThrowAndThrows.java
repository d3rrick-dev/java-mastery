package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between throw and throws?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of exception handling
 * - Shows knowledge of method signatures
 * - Reveals awareness of exception propagation
 */

public class Lesson24_WhatIsTheDifferenceBetweenThrowAndThrows {

    public static void main(String[] args) {
        System.out.println("=== THROW VS THROWS ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between throw and throws?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("THROW:");
        System.out.println("  - Keyword to actually throw an exception");
        System.out.println("  - Used inside method body");
        System.out.println("  - Creates new exception object");
        System.out.println("  - Syntax: throw new Exception();");
        System.out.println();

        System.out.println("THROWS:");
        System.out.println("  - Keyword in method signature");
        System.out.println("  - Declares exceptions method may throw");
        System.out.println("  - Does NOT throw exception itself");
        System.out.println("  - Syntax: public void method() throws Exception");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. throw: inside method, throws: in signature");
        System.out.println("  2. throw: creates exception, throws: declares");
        System.out.println("  3. throw: one exception at a time, throws: multiple");
        System.out.println("  4. throw: for unchecked, throws: for checked");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("Using throw:");
        System.out.println("  public void validate(int age) {");
        System.out.println("    if (age < 0) {");
        System.out.println("      throw new IllegalArgumentException(\"Invalid age\");");
        System.out.println("    }");
        System.out.println("  }");
        System.out.println();

        System.out.println("Using throws:");
        System.out.println("  public void readFile() throws IOException {");
        System.out.println("    FileInputStream fis = new FileInputStream(\"file.txt\");");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using throw for checked exceptions without throws:");
        System.out.println("   - Compile error if not declared");
        System.out.println("   - Must add throws clause");
        System.out.println();

        System.out.println("2. Catching and rethrowing unnecessarily:");
        System.out.println("   - Only catch if you can handle it");
        System.out.println("   - Otherwise let it propagate");
        System.out.println();

        System.out.println("3. Throwing Exception instead of specific:");
        System.out.println("   - Use specific exception types");
        System.out.println("   - Better error handling");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. Can you throw multiple exceptions?");
        System.out.println("2. What is exception propagation?");
        System.out.println("3. What happens when an exception is thrown?");
        System.out.println("4. Can you throw Error?");
        System.out.println("5. What is the difference between throw new and throw existing?");
    }
}
