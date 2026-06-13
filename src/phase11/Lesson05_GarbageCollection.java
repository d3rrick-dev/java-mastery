package phase11;

/**
 * LESSON 5: GARBAGE COLLECTION
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. GC eligibility
 * 2. GC roots
 * 3. GC types
 * 4. GC algorithms
 * 5. Interview questions
 */

public class Lesson05_GarbageCollection {
    public static void main(String[] args) {
        System.out.println("""
            === GARBAGE COLLECTION ===
            
            1. GC ELIGIBILITY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               An object becomes eligible for GC when it's no longer reachable
               from GC roots.
            
               WHY IT EXISTS:
               - Automatic memory management
               - Prevents memory leaks
               - No manual free() like in C/C++
            
               ELIGIBILITY CONDITIONS:
                   - No references point to it
                   - All references are null
                   - Reference goes out of scope
            
               SIMPLE EXAMPLE:
                   String s1 = new String("hello");
                   s1 = null;  // Now eligible for GC
                   
                   // Or:
                   {
                       String s2 = "world";
                   }  // s2 out of scope, eligible for GC
            
               REAL-WORLD BACKEND EXAMPLE:
                   A request handler:
                   - Request object: Eligible after response
                   - Cache entries: Eligible when evicted
                   - Connection pool: Not eligible (static refs)
            
               INTERVIEW QUESTION:
                   "How does GC determine what to collect?
                   What makes an object eligible for GC?"
            
               COMMON MISTAKES:
                   - Memory leaks (unintentional references)
                   - Not nulling out collections
            
            ─────────────────────────────────────────────────────────────────────
            
            2. GC ROOTS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               GC starts from "roots" and marks all reachable objects.
            
               WHY IT EXISTS:
               - Efficient marking algorithm
               - Avoid scanning entire heap
            
               GC ROOTS:
                   - Local variables on stack
                   - Static variables in method area
                   - JNI references
                   - Thread references
                   - System classes
            
               SIMPLE EXAMPLE:
                   public class Example {
                           static Object staticRef;
                           
                           public void method() {
                               Object localRef = new Object();
                               staticRef = localRef;
                               // localRef out of scope, but object NOT eligible
                               // because staticRef still references it
                           }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A singleton service:
                   - Static reference keeps it alive
                   - Not eligible for GC
                   - Memory leak if not managed
            
               INTERVIEW QUESTION:
                   "What are GC roots?
                   How does GC traverse the object graph?"
            
               COMMON MISTAKES:
                   - Not understanding root references
                   - Static memory leaks
            
            ─────────────────────────────────────────────────────────────────────
            
            3. GC TYPES
               ─────────────────────────────────────────────────────────────────────
               MINOR GC (Young Generation):
                   - Collects Eden and Survivor spaces
                   - Fast, frequent
                   - Objects that survive move to Old Gen
            
               MAJOR GC (Old Generation):
                   - Collects Old Gen
                   - Slower, less frequent
                   - Often triggers Full GC
            
               FULL GC:
                   - Collects entire heap
                   - Stop-the-world (application pauses)
                   - Should be minimized
            
               SIMPLE EXAMPLE:
                   // Young Gen:
                   // Eden -> Survivor -> Old Gen (after N GCs)
                   
                   // Monitor with:
                   // -XX:+PrintGC
                   // -Xlog:gc*
            
               REAL-WORLD BACKEND EXAMPLE:
                   A high-throughput service:
                   - Frequent minor GC
                   - Rare major GC
                   - Monitor pause times
            
               INTERVIEW QUESTION:
                   "What is the difference between minor and major GC?
                   What is the generational hypothesis?"
            
               COMMON MISTAKES:
                   - Not understanding generations
                   - Not tuning GC
            
            ─────────────────────────────────────────────────────────────────────
            
            4. GC ALGORITHMS
               ─────────────────────────────────────────────────────────────────────
               SERIAL GC:
                   - Single-threaded
                   - For small applications
                   - Simple, low overhead
            
               PARALLEL GC:
                   - Multi-threaded young gen
                   - Throughput focused
                   - Default in Java 8
            
               G1 GC:
                   - Default in Java 9+
                   - Region-based
                   - Predictable pause times
            
               ZGC:
                   - Low latency (sub-millisecond pauses)
                   - Concurrent
                   - Java 11+
            
               SIMPLE EXAMPLE:
                   // JVM flags:
                   -XX:+UseSerialGC    // Serial
                   -XX:+UseParallelGC   // Parallel
                   -XX:+UseG1GC         // G1
                   -XX:+UseZGC          // ZGC
            
               REAL-WORLD BACKEND EXAMPLE:
                   A latency-sensitive service:
                   - Use G1 or ZGC
                   - Set pause time goal: -XX:MaxGCPauseMillis=100
                   - Monitor GC logs
            
               INTERVIEW QUESTION:
                   "What GC would you use for a low-latency app?
                   How to tune GC pause times?"
            
               COMMON MISTAKES:
                   - Not choosing right GC
                   - Not monitoring GC
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Garbage Collection is essential for:
            - Memory management
            - Performance optimization
            - Debugging memory leaks
            - JVM tuning
            """);
    }
}
