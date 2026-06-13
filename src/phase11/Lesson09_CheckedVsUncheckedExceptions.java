package phase11;

/**
 * LESSON 9: CHECKED VS UNCHECKED EXCEPTIONS
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Checked exceptions
 * 2. Unchecked exceptions
 * 3. When to use each
 * 4. Interview questions
 */

public class Lesson09_CheckedVsUncheckedExceptions {
    public static void main(String[] args) {
        System.out.println("""
            === CHECKED VS UNCHECKED EXCEPTIONS ===
            
            1. CHECKED EXCEPTIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Compiler forces handling of these exceptions.
            
               WHY IT EXISTS:
               - Force handling of recoverable errors
               - API contract clarity
            
               SIMPLE EXAMPLE:
                   // Must catch or declare:
                   public void readFile() throws IOException {
                       FileInputStream fis = new FileInputStream("file.txt");
                   }
                   
                   // OR catch:
                   try {
                       readFile();
                   } catch (IOException e) {
                       // handle
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A file upload service:
                   - IOException for file operations
                   - Caller must handle disk full, permissions
                   - Clear error handling contract
            
               INTERVIEW QUESTION:
                   "Why are checked exceptions controversial?
                   What are the pros and cons?"
            
               COMMON MISTAKES:
                   - Not declaring throws
                   - Empty catch blocks
            
            ─────────────────────────────────────────────────────────────────────
            
            2. UNCHECKED EXCEPTIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Compiler doesn't require handling of these exceptions.
            
               WHY IT EXISTS:
               - Programming errors
               - Not recoverable
            
               SIMPLE EXAMPLE:
                   public void divide(int a, int b) {
                       int result = a / b;  // ArithmeticException if b=0
                   }
                   
                   // No compile error if not caught
                   divide(10, 0);  // Throws at runtime
            
               REAL-WORLD BACKEND EXAMPLE:
                   A validation method:
                   - IllegalArgumentException for null input
                   - Programming error, caller can't recover
                   - Let it propagate to top level
            
               INTERVIEW QUESTION:
                   "Should you catch RuntimeException?
                   When is it appropriate?"
            
               COMMON MISTAKES:
                   - Catching all RuntimeExceptions
                   - Not logging errors
            
            ─────────────────────────────────────────────────────────────────────
            
            3. WHEN TO USE WHICH
               ─────────────────────────────────────────────────────────────────────
               USE CHECKED WHEN:
                   - Error is recoverable
                   - Caller should handle it
                   - External condition (IO, network, DB)
                   - Expected in normal flow
            
               USE UNCHECKED WHEN:
                   - Programming error
                   - Not recoverable
                   - Caller can't do anything about it
                   - Violates contract (null, illegal arg)
            
               SIMPLE EXAMPLE:
                   // Checked: Database connection failure
                   public User findUser(Long id) throws SQLException {
                       return userRepository.findById(id);
                   }
                   
                   // Unchecked: Invalid ID
                   public User findUser(Long id) {
                       if (id == null) {
                           throw new IllegalArgumentException("ID cannot be null");
                       }
                       return userRepository.findById(id);
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment service:
                   - Checked: PaymentGatewayTimeoutException
                   - Unchecked: InvalidAmountException
                   - Clear separation of concerns
            
               INTERVIEW QUESTION:
                   "Design a custom exception hierarchy for a banking application.
                   Which would be checked vs unchecked?"
            
               COMMON MISTAKES:
                   - Using checked for all exceptions
                   - Not following conventions
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Exception types are essential for:
            - API design
            - Error handling strategy
            - Code maintainability
            - Debugging
            """);
    }
}
