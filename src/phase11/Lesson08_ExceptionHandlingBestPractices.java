package phase11;

import java.sql.SQLException;

/**
 * LESSON 8: EXCEPTION HANDLING BEST PRACTICES
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Checked vs Unchecked exceptions
 * 2. try-with-resources
 * 3. Custom exceptions
 * 4. Exception chaining
 * 5. Interview questions
 */

public class Lesson08_ExceptionHandlingBestPractices {
    public static void main(String[] args) {
        System.out.println("""
            === EXCEPTION HANDLING BEST PRACTICES ===
            
            1. CHECKED VS UNCHECKED EXCEPTIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Two types of exceptions with different handling requirements.
            
               WHY IT EXISTS:
               - Compile-time error handling
               - API contract clarity
            
               SIMPLE EXAMPLE:
                   // Checked: Must be caught or declared
                   public void readFile() throws IOException {
                       FileInputStream fis = new FileInputStream("file.txt");
                   }
                   
                   // Unchecked: No compile requirement
                   public void divide(int a, int b) {
                       return a / b;  // May throw ArithmeticException
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A service method:
                   - Checked: Database connection failures
                   - Unchecked: Invalid input validation
                   - Design: Use checked for recoverable, unchecked for bugs
            
               INTERVIEW QUESTION:
                   "When should you use checked vs unchecked exceptions?
                   What are the trade-offs?"
            
               COMMON MISTAKES:
                   - Using checked for programming errors
                   - Not declaring throws
            
            ─────────────────────────────────────────────────────────────────────
            
            2. TRY-WITH-RESOURCES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Automatic resource management for AutoCloseable resources.
            
               WHY IT EXISTS:
               - Prevent resource leaks
               - Cleaner code
            
               SIMPLE EXAMPLE:
                   // Bad (manual close):
                   FileInputStream fis = new FileInputStream("file.txt");
                   try {
                       // use fis
                   } finally {
                       fis.close();  // May throw exception
                   }
                   
                   // Good (try-with-resources):
                   try (FileInputStream fis = new FileInputStream("file.txt")) {
                       // use fis - auto-closed
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A database query:
                   try (Connection conn = dataSource.getConnection();
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery()) {
                       // Process results
                   }  // All resources auto-closed
            
               INTERVIEW QUESTION:
                   "How does try-with-resources work internally?
                   What happens if close() throws an exception?"
            
               COMMON MISTAKES:
                   - Not using try-with-resources
                   - Not handling suppressed exceptions
            
            ─────────────────────────────────────────────────────────────────────
            
            3. CUSTOM EXCEPTIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Domain-specific exceptions for better error handling.
            
               WHY IT EXISTS:
               - Clear error semantics
               - API documentation
            
               SIMPLE EXAMPLE:
                   class InvalidAgeException extends Exception {
                       public InvalidAgeException(String message) {
                           super(message);
                       }
                   }
                   
                   void validateAge(int age) throws InvalidAgeException {
                       if (age < 0 || age > 150) {
                           throw new InvalidAgeException("Invalid age: " + age);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment service:
                   - PaymentValidationException
                   - InsufficientFundsException
                   - FraudDetectedException
                   - Clear error handling for each case
            
               INTERVIEW QUESTION:
                   "When should you create custom exceptions?
                   Checked or unchecked for business exceptions?"
            
               COMMON MISTAKES:
                   - Too many custom exceptions
                   - Not following naming conventions
            
            ─────────────────────────────────────────────────────────────────────
            
            4. EXCEPTION CHAINING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Wrapping exceptions to preserve stack trace.
            
               WHY IT EXISTS:
               - Root cause preservation
               - Abstraction layer separation
            
               SIMPLE EXAMPLE:
                   try {
                       // Database operation
                       throw new SQLException("Connection timeout");
                   } catch (SQLException e) {
                       throw new OrderProcessingException(
                           "Failed to process order", e
                       );
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A service layer:
                   - Catches repository exceptions
                   - Wraps in service exceptions
                   - Preserves root cause for debugging
            
               INTERVIEW QUESTION:
                   "What is exception chaining?
                   How to implement it properly?"
            
               COMMON MISTAKES:
                   - Not preserving cause
                   - Breaking stack trace
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Exception handling is critical for:
            - Robust applications
            - Debugging production issues
            - API design
            - Error recovery
            """);
    }
}
