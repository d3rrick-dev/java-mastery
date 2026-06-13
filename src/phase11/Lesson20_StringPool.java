package phase11;

/**
 * LESSON 20: STRING POOL
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. String interning
 * 2. intern() method
 * 3. String pool location
 * 4. When strings are interned
 * 5. Interview questions
 */

public class Lesson20_StringPool {
    public static void main(String[] args) {
        System.out.println("""
            === STRING POOL ===
            
            1. STRING INTERNING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               String literals are automatically stored in a shared pool.
            
               WHY IT EXISTS:
               - Memory savings
               - Fast comparison
            
               SIMPLE EXAMPLE:
                   String s1 = "hello";      // Goes to pool
                   String s2 = "hello";      // Reuses from pool
                   String s3 = new String("hello");  // New object, NOT from pool
                   
                   s1 == s2;  // true (same pool object)
                   s1 == s3;  // false (different objects)
                   s1.equals(s3);  // true (same content)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A configuration loader:
                   // All "true" strings share same reference
                   if (config.get("enabled").intern() == "true") {
                       // Fast reference comparison
                   }
            
               INTERVIEW QUESTION:
                   "What is the String pool?
                   How does string interning work?"
            
               COMMON MISTAKES:
                   - Using == for non-literal strings
                   - Not understanding pool
            
            ─────────────────────────────────────────────────────────────────────
            
            2. INTERN() METHOD
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Add a string to the pool or return existing reference.
            
               WHY IT EXISTS:
               - Manual pool management
               - Memory optimization
            
               SIMPLE EXAMPLE:
                   String s4 = new String("world");
                   String s5 = s4.intern();  // Add to pool if not present
                   String s6 = "world";      // From pool
                   
                   s4 == s5;  // false (s4 is new object)
                   s5 == s6;  // true (both from pool)
                   
                   // Returns pool reference
                   // If already in pool, returns existing
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user input cache:
                   String normalized = userInput.intern();
                   // All identical inputs share same reference
            
               INTERVIEW QUESTION:
                   "What does intern() do?
                   When should you use it?"
            
               COMMON MISTAKES:
                   - Excessive interning
                   - Not understanding memory impact
            
            ─────────────────────────────────────────────────────────────────────
            
            3. STRING POOL LOCATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               String pool location changed in Java 7.
            
               WHY IT EXISTS:
               - Memory management
               - GC efficiency
            
               SIMPLE EXAMPLE:
                   // Java 6 and earlier:
                   // - String pool in PermGen (fixed size)
                   // - Could cause OutOfMemoryError: PermGen space
                   
                   // Java 7+:
                   // - String pool in Heap
                   // - Same garbage collection as other objects
                   // - Can grow dynamically
            
               REAL-WORLD BACKEND EXAMPLE:
                   A high-throughput service:
                   // Java 7+ handles string pool better
                   // No PermGen OOM issues
            
               INTERVIEW QUESTION:
                   "Where is the String pool located?
                   How did it change in Java 7?"
            
               COMMON MISTAKES:
                   - Not knowing about PermGen
                   - Assuming pool is always in heap
            
            ─────────────────────────────────────────────────────────────────────
            
            4. WHEN STRINGS ARE INTERNED
               ─────────────────────────────────────────────────────────────────────
               AUTOMATICALLY INTERNED:
                   - String literals: "hello"
                   - String constants in code
            
               MANUALLY INTERNED:
                   - new String("hello").intern()
                   - String created at runtime
            
               NOT INTERNED:
                   - new String("hello") without intern()
                   - StringBuilder.toString()
            
               SIMPLE EXAMPLE:
                   // Compile-time constant:
                   String s1 = "constant";  // Interned
                   
                   // Runtime string:
                   String s2 = new String("runtime");  // Not interned
                   String s3 = s2.intern();  // Now interned
            
               REAL-WORLD BACKEND EXAMPLE:
                   A JSON parser:
                   // Property names can be interned:
                   String key = jsonKey.intern();
                   // All identical keys share reference
            
               INTERVIEW QUESTION:
                   "Which strings are automatically interned?
                   How to check if a string is in the pool?"
            
               COMMON MISTAKES:
                   - Assuming all strings are interned
                   - Not understanding compile-time vs runtime
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            String pool is essential for:
            - Memory optimization
            - Performance
            - String comparison
            - Understanding JVM internals
            """);
    }
}
