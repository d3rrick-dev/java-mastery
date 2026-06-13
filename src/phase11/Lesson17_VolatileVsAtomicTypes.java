package phase11;

import java.util.concurrent.atomic.*;

/**
 * LESSON 17: VOLATILE VS ATOMIC TYPES
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. volatile keyword
 * 2. Atomic types
 * 3. When to use which
 * 4. Interview questions
 */

public class Lesson17_VolatileVsAtomicTypes {
    public static void main(String[] args) {
        System.out.println("""
            === VOLATILE VS ATOMIC TYPES ===
            
            1. VOLATILE KEYWORD
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Ensures visibility of changes across threads.
            
               WHY IT EXISTS:
               - Visibility without locking
               - Memory consistency
            
               SIMPLE EXAMPLE:
                   class Example {
                       private volatile boolean running = true;
                       private volatile int count = 0;
                   }
                   
                   // volatile ensures:
                   // 1. Visibility: Changes visible to all threads
                   // 2. Ordering: Prevents reordering
                   // 3. NOT atomic: read-modify-write still race
                   
                   // WRONG:
                   count++;  // NOT atomic!
                   
                   // RIGHT:
                   // Use for flags, not counters
            
               REAL-WORLD BACKEND EXAMPLE:
                   A shutdown flag:
                   private volatile boolean shutdown = false;
                   
                   public void run() {
                       while (!shutdown) {
                           // Process
                       }
                   }
                   
                   public void stop() {
                       shutdown = true;  // Visible immediately
                   }
            
               INTERVIEW QUESTION:
                   "What does volatile do?
                   How is it different from synchronized?"
            
               COMMON MISTAKES:
                   - Using for compound operations
                   - Not understanding visibility
            
            ─────────────────────────────────────────────────────────────────────
            
            2. ATOMIC TYPES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Provide atomic read-modify-write operations.
            
               WHY IT EXISTS:
               - Lock-free thread safety
               - High performance
            
               SIMPLE EXAMPLE:
                   AtomicInteger atomicInt = new AtomicInteger(0);
                   
                   // Atomic operations:
                   atomicInt.incrementAndGet();  // +1, return new
                   atomicInt.getAndAdd(5);       // +5, return old
                   atomicInt.compareAndSet(6, 10); // If 6, set to 10
                   
                   // Implementation:
                   // - Uses Compare-And-Swap (CAS)
                   // - Lock-free
                   // - CPU-level instruction
            
               REAL-WORLD BACKEND EXAMPLE:
                   A request counter:
                   private final AtomicInteger requestCount = new AtomicInteger(0);
                   
                   public void handleRequest() {
                       requestCount.incrementAndGet();
                       // Process request
                   }
            
               INTERVIEW QUESTION:
                   "How do atomic types work?
                   What is compare-and-swap (CAS)?"
            
               COMMON MISTAKES:
                   - ABA problem
                   - Not handling CAS failure
            
            ─────────────────────────────────────────────────────────────────────
            
            3. WHEN TO USE WHICH
               ─────────────────────────────────────────────────────────────────────
               USE VOLATILE WHEN:
                   - Simple flag/state variable
                   - Single writer, multiple readers
                   - No compound operations
                   - Example: boolean running, shutdown
            
               USE ATOMIC TYPES WHEN:
                   - Need atomic read-modify-write
                   - Multiple writers
                   - Counters, accumulators
                   - Example: AtomicInteger count
            
               SIMPLE EXAMPLE:
                   // volatile - for flags:
                   private volatile boolean initialized = false;
                   
                   // Atomic - for counters:
                   private final AtomicInteger counter = new AtomicInteger(0);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A service state:
                   private volatile ServiceState state = ServiceState.INITIAL;
                   private final AtomicInteger requestCount = new AtomicInteger(0);
                   
                   public void process() {
                       if (state == ServiceState.RUNNING) {
                           requestCount.incrementAndGet();
                       }
                   }
            
               INTERVIEW QUESTION:
                   "When to use volatile vs AtomicInteger?
                   What are the performance trade-offs?"
            
               COMMON MISTAKES:
                   - Using volatile for counters
                   - Not understanding atomicity
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Volatile and Atomic types are essential for:
            - Lock-free programming
            - High-performance concurrent code
            - Visibility guarantees
            - Thread-safe state management
            """);
    }
}
