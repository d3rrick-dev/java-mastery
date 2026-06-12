package phase11;

/**
 * LESSON 9: CHECKED VS UNCHECKED EXCEPTIONS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Checked: Compiler forces you to handle (IOException)
 * Unchecked: Compiler doesn't care (NullPointerException)
 * Like seatbelt reminder (checked) vs speed limit sign (unchecked).
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Checked: Force handling of recoverable errors
 * - Unchecked: Programming errors, not recoverable
 */

public class Lesson09_CheckedVsUncheckedExceptions {

    public static void main(String[] args) {
        System.out.println("=== CHECKED VS UNCHECKED EXCEPTIONS ===\n");

        // ============================================================
        // EXAMPLE 1: Checked exception
        // ============================================================
        System.out.println("--- Example 1: Checked Exception ---\n");

        System.out.println("Checked exceptions extend Exception (not RuntimeException)");
        System.out.println("Compiler requires handling:");
        System.out.println();
        System.out.println("  // Must catch or declare:");
        System.out.println("  public void readFile() throws IOException {");
        System.out.println("    FileInputStream fis = new FileInputStream(\"file.txt\");");
        System.out.println("  }");
        System.out.println();
        System.out.println("  // OR catch:");
        System.out.println("  try {");
        System.out.println("    readFile();");
        System.out.println("  } catch (IOException e) {");
        System.out.println("    // handle");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Unchecked exception
        // ============================================================
        System.out.println("--- Example 2: Unchecked Exception ---\n");

        System.out.println("Unchecked exceptions extend RuntimeException");
        System.out.println("Compiler does NOT require handling:");
        System.out.println();
        System.out.println("  public void divide(int a, int b) {");
        System.out.println("    int result = a / b;  // ArithmeticException if b=0");
        System.out.println("  }");
        System.out.println();
        System.out.println("  // No compile error if not caught");
        System.out.println("  divide(10, 0);  // Throws at runtime");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Common checked exceptions
        // ============================================================
        System.out.println("--- Example 3: Common Checked Exceptions ---\n");

        System.out.println("Checked:");
        System.out.println("  - IOException (file/network errors)");
        System.out.println("  - SQLException (database errors)");
        System.out.println("  - ClassNotFoundException (class loading)");
        System.out.println("  - InterruptedException (thread interruption)");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Common unchecked exceptions
        // ============================================================
        System.out.println("--- Example 4: Common Unchecked Exceptions ---\n");

        System.out.println("Unchecked (RuntimeException):");
        System.out.println("  - NullPointerException (null reference)");
        System.out.println("  - IllegalArgumentException (invalid argument)");
        System.out.println("  - IndexOutOfBoundsException (invalid index)");
        System.out.println("  - IllegalStateException (invalid state)");
        System.out.println("  - ArithmeticException (math error)");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: When to use which
        // ============================================================
        System.out.println("--- Example 5: When to Use Which ---\n");

        System.out.println("Use CHECKED when:");
        System.out.println("  - Error is recoverable");
        System.out.println("  - Caller should handle it");
        System.out.println("  - External condition (IO, network, DB)");
        System.out.println();
        System.out.println("Use UNCHECKED when:");
        System.out.println("  - Programming error");
        System.out.println("  - Not recoverable");
        System.out.println("  - Caller can't do anything about it");
        System.out.println("  - Violates contract (null, illegal arg)");
        System.out.println();
    }

    // ============================================================
    // CHECKED VS UNCHECKED DETAILS
    // ============================================================
    /*
     * Exception Hierarchy:
     *
     * Throwable
     *   |-- Exception (checked)
     *   |     |-- IOException
     *   |     |-- SQLException
     *   |     |-- ClassNotFoundException
     *   |     |-- CustomException
     *   |
     *   |-- RuntimeException (unchecked)
     *         |-- NullPointerException
     *         |-- IllegalArgumentException
     *         |-- IndexOutOfBoundsException
     *         |-- IllegalStateException
     *
     * Best Practices:
     * - Use checked for recoverable, expected errors
     * - Use unchecked for programming errors
     * - Don't catch Exception or Throwable
     * - Don't swallow exceptions (empty catch)
     * - Always log exceptions
     */
}
