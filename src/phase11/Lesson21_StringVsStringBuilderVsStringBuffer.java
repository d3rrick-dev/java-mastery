package phase11;

/**
 * LESSON 21: STRING VS STRINGBUILDER VS STRINGBUFFER
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. String immutability
 * 2. StringBuilder
 * 3. StringBuffer
 * 4. Performance comparison
 * 5. Interview questions
 */

public class Lesson21_StringVsStringBuilderVsStringBuffer {
    public static void main(String[] args) {
        System.out.println("""
            === STRING VS STRINGBUILDER VS STRINGBUFFER ===
            
            1. STRING IMMUABILITY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               String objects cannot be changed after creation.
            
               WHY IT EXISTS:
               - Thread safety
               - Security
               - Hash code caching
            
               SIMPLE EXAMPLE:
                   String s = "hello";
                   s = s.concat(" world");  // Creates NEW string
                   
                   // Original "hello" still exists
                   // s now points to "hello world"
                   // String is immutable
            
               REAL-WORLD BACKEND EXAMPLE:
                   A configuration key:
                   // Safe to share across threads
                   // Can be used as HashMap key
                   // Hash code cached
                   private static final String CONFIG_KEY = "app.config";
            
               INTERVIEW QUESTION:
                   "Why is String immutable?
                   What are the benefits?"
            
               COMMON MISTAKES:
                   - Not understanding immutability
                   - String concatenation in loops
            
            ─────────────────────────────────────────────────────────────────────
            
            2. STRINGBUILDER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Mutable sequence of characters, not thread-safe.
            
               WHY IT EXISTS:
               - Efficient string building
               - Single-threaded performance
            
               SIMPLE EXAMPLE:
                   StringBuilder sb = new StringBuilder("hello");
                   sb.append(" world");  // Modifies SAME object
                   sb.append(123);
                   sb.reverse();
                   
                   // Features:
                   // - Mutable
                   // - NOT thread-safe
                   // - Faster than StringBuffer
            
               REAL-WORLD BACKEND EXAMPLE:
                   A JSON builder:
                   StringBuilder json = new StringBuilder();
                   json.append("{\"name\":\"").append(user.getName()).append("\"}");
            
               INTERVIEW QUESTION:
                   "What is StringBuilder?
                   When should you use it?"
            
               COMMON MISTAKES:
                   - Not using in loops
                   - Sharing between threads
            
            ─────────────────────────────────────────────────────────────────────
            
            3. STRINGBUFFER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Mutable sequence of characters, thread-safe.
            
               WHY IT EXISTS:
               - Multi-threaded string building
               - Legacy compatibility
            
               SIMPLE EXAMPLE:
                   StringBuffer sbf = new StringBuffer("hello");
                   sbf.append(" world");  // Thread-safe modification
                   
                   // Features:
                   // - Mutable
                   // - Thread-safe (synchronized)
                   // - Slower than StringBuilder
            
               REAL-WORLD BACKEND EXAMPLE:
                   A shared log buffer (rare):
                   private final StringBuffer logBuffer = new StringBuffer();
                   
                   public void log(String message) {
                       logBuffer.append(message).append("\n");
                   }
            
               INTERVIEW QUESTION:
                   "What is the difference between StringBuilder and StringBuffer?
                   When would you use StringBuffer?"
            
               COMMON MISTAKES:
                   - Using StringBuffer unnecessarily
                   - Not understanding synchronization
            
            ─────────────────────────────────────────────────────────────────────
            
            4. PERFORMANCE COMPARISON
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               StringBuilder is fastest, String is slowest for building.
            
               WHY IT EXISTS:
               - Performance optimization
               - Memory efficiency
            
               SIMPLE EXAMPLE:
                   // String concatenation in loop (SLOW):
                   String result = "";
                   for (int i = 0; i < 10000; i++) {
                       result += i;  // Creates new StringBuilder each time!
                   }
                   
                   // StringBuilder (FAST):
                   StringBuilder sb = new StringBuilder();
                   for (int i = 0; i < 10000; i++) {
                       sb.append(i);  // Modifies same object
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A CSV generator:
                   // WRONG:
                   String csv = "";
                   for (Record r : records) {
                       csv += r.toCsv() + "\n";
                   }
                   
                   // RIGHT:
                   StringBuilder csv = new StringBuilder();
                   for (Record r : records) {
                       csv.append(r.toCsv()).append("\n");
                   }
            
               INTERVIEW QUESTION:
                   "Why is StringBuilder faster than String concatenation?
                   What happens with += in a loop?"
            
               COMMON MISTAKES:
                   - String concatenation in loops
                   - Not understanding compiler optimization
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            String types are essential for:
            - Performance optimization
            - Memory management
            - Thread safety
            - Efficient string handling
            """);
    }
}
