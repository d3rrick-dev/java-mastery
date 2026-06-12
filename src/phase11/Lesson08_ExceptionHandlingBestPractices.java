package phase11;

import java.sql.SQLException;

/**
 * LESSON 8: EXCEPTION HANDLING BEST PRACTICES
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Exception handling is managing errors gracefully.
 * Like having a backup plan when things go wrong.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Separate error handling from business logic
 * - Graceful degradation
 * - Resource cleanup
 */

public class Lesson08_ExceptionHandlingBestPractices {

    public static void main(String[] args) {
        System.out.println("=== EXCEPTION HANDLING BEST PRACTICES ===\n");

        // ============================================================
        // EXAMPLE 1: Checked vs Unchecked
        // ============================================================
        System.out.println("--- Example 1: Checked vs Unchecked ---\n");

        System.out.println("Checked Exceptions (compile-time):");
        System.out.println("  - IOException, SQLException, ClassNotFoundException");
        System.out.println("  - Must be caught or declared");
        System.out.println();
        System.out.println("Unchecked Exceptions (runtime):");
        System.out.println("  - NullPointerException, IllegalArgumentException");
        System.out.println("  - Not required to catch");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: try-with-resources
        // ============================================================
        System.out.println("--- Example 2: Try-With-Resources ---\n");

        System.out.println("Bad (manual close):");
        System.out.println("  FileInputStream fis = new FileInputStream(\"file.txt\");");
        System.out.println("  try {");
        System.out.println("    // use fis");
        System.out.println("  } finally {");
        System.out.println("    fis.close();  // May throw exception");
        System.out.println("  }");
        System.out.println();
        System.out.println("Good (try-with-resources):");
        System.out.println("  try (FileInputStream fis = new FileInputStream(\"file.txt\")) {");
        System.out.println("    // use fis - auto-closed");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Custom exceptions
        // ============================================================
        System.out.println("--- Example 3: Custom Exceptions ---\n");

        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Exception chaining
        // ============================================================
        System.out.println("--- Example 4: Exception Chaining ---\n");

        try {
            processOrder("order-123");
        } catch (OrderProcessingException e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
        }
        System.out.println();
    }

    // ============================================================
    // EXCEPTION HANDLING EXAMPLES
    // ============================================================

    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String message) {
            super(message);
        }
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Invalid age: " + age);
        }
        System.out.println("Valid age: " + age);
    }

    static class OrderProcessingException extends Exception {
        public OrderProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static void processOrder(String orderId) throws OrderProcessingException {
        try {
            // Simulate database error
            throw new SQLException("Connection timeout");
        } catch (SQLException e) {
            throw new OrderProcessingException(
                "Failed to process order: " + orderId, e
            );
        }
    }
}
