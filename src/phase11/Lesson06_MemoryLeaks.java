package phase11;

/**
 * LESSON 6: MEMORY LEAKS
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Static collection leaks
 * 2. Unclosed resources
 * 3. Inner class leaks
 * 4. ThreadLocal leaks
 * 5. Interview questions
 */

public class Lesson06_MemoryLeaks {
    public static void main(String[] args) {
        System.out.println("""
            === MEMORY LEAKS ===
            
            1. STATIC COLLECTION LEAKS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Static collections grow indefinitely if not managed.
            
               WHY IT EXISTS:
               - Unintentional object retention
               - Cache implementations
               - Registry patterns
            
               SIMPLE EXAMPLE:
                   // Bad:
                   static Map<Long, Object> cache = new HashMap<>();
                   // Never cleared, grows with every request
                   
                   // Good:
                   Map<Long, Object> cache = new WeakHashMap<>();
                   // Entries removed when key no longer referenced
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user session cache:
                   - Static map storing sessions
                   - Sessions never removed
                   - Memory leak over time
                   - Solution: Use Redis with TTL
            
               INTERVIEW QUESTION:
                   "How would you prevent a static cache from causing
                   a memory leak in a long-running application?"
            
               COMMON MISTAKES:
                   - Not limiting cache size
                   - Not using eviction policies
            
            ─────────────────────────────────────────────────────────────────────
            
            2. UNCLOSED RESOURCES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Resources like connections, streams, files must be closed.
            
               WHY IT EXISTS:
               - Native memory not managed by GC
               - File handles, sockets, DB connections
            
               SIMPLE EXAMPLE:
                   // Bad:
                   Connection conn = dataSource.getConnection();
                   // conn never closed - resource leak
                   
                   // Good:
                   try (Connection conn = dataSource.getConnection()) {
                       // Auto-closed
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A database connection pool:
                   - Connections not returned to pool
                   - Pool exhaustion
                   - Application hangs
                   - Solution: Always use try-with-resources
            
               INTERVIEW QUESTION:
                   "What happens if you don't close a database connection?
                   How does try-with-resources help?"
            
               COMMON MISTAKES:
                   - Not using try-with-resources
                   - Missing finally blocks
            
            ─────────────────────────────────────────────────────────────────────
            
            3. INNER CLASS LEAKS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Non-static inner classes hold implicit reference to outer class.
            
               WHY IT EXISTS:
               - Access to outer class members
               - Anonymous classes in long-running contexts
            
               SIMPLE EXAMPLE:
                   // Bad:
                   class Outer {
                       class Inner {  // Implicitly references Outer
                           void process() { ... }
                       }
                   }
                   
                   // Good:
                   class Outer {
                       static class Inner {  // No outer reference
                           void process() { ... }
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring @Component with anonymous Runnable:
                   - Runnable holds reference to component
                   - Thread pool keeps Runnable alive
                   - Component never GC'd
                   - Solution: Use static nested class
            
               INTERVIEW QUESTION:
                   "Why can non-static inner classes cause memory leaks?
                   How to fix it?"
            
               COMMON MISTAKES:
                   - Not understanding implicit references
                   - Using anonymous classes in long-lived contexts
            
            ─────────────────────────────────────────────────────────────────────
            
            4. THREADLOCAL LEAKS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               ThreadLocal values persist for thread lifetime.
            
               WHY IT EXISTS:
               - Thread isolation
               - Context propagation
            
               SIMPLE EXAMPLE:
                   ThreadLocal<String> context = new ThreadLocal<>();
                   context.set("value");
                   // In thread pools, thread lives forever
                   // Value never cleaned up
                   context.remove();  // Always clean up
            
               REAL-WORLD BACKEND EXAMPLE:
                   A web application with request context:
                   - ThreadLocal stores user ID
                   - Thread pool reuses threads
                   - Old user IDs accumulate
                   - Solution: Clean up in filter
            
               INTERVIEW QUESTION:
                   "How to prevent ThreadLocal memory leaks in
                   a thread pool environment?"
            
               COMMON MISTAKES:
                   - Not calling remove()
                   - Forgetting cleanup in finally block
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Memory leaks are critical for:
            - Long-running applications
            - High-throughput services
            - Resource management
            - JVM tuning
            """);
    }
}
