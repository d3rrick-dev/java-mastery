package phase11;

import java.util.*;

/**
 * LESSON 13: STREAMS INTERNALS
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Stream pipeline
 * 2. Lazy evaluation
 * 3. Stream types
 * 4. Short-circuiting
 * 5. Interview questions
 */

public class Lesson13_StreamsInternals {
    public static void main(String[] args) {
        System.out.println("""
            === STREAMS INTERNALS ===
            
            1. STREAM PIPELINE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Streams are pipelines that process data through stages.
            
               WHY IT EXISTS:
               - Declarative data processing
               - Lazy evaluation
               - Functional style
            
               SIMPLE EXAMPLE:
                   // Pipeline: source -> intermediate -> terminal
                   long count = numbers.stream()
                       .filter(n -> n % 2 == 0)    // Intermediate
                       .map(n -> n * n)              // Intermediate
                       .count();                      // Terminal
                   
                   // Each element flows through all stages
                   // Before next element enters
            
               REAL-WORLD BACKEND EXAMPLE:
                   A data processing pipeline:
                   orders.stream()
                       .filter(Order::isCompleted)
                       .map(Order::getTotal)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
            
               INTERVIEW QUESTION:
                   "How does a stream pipeline work internally?
                   What is the difference between intermediate and terminal ops?"
            
               COMMON MISTAKES:
                   - Multiple terminal operations
                   - Not understanding laziness
            
            ─────────────────────────────────────────────────────────────────────
            
            2. LAZY EVALUATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Intermediate operations don't execute until terminal is called.
            
               WHY IT EXISTS:
               - Performance optimization
               - Short-circuiting
               - Memory efficiency
            
               SIMPLE EXAMPLE:
                   // This does NOT execute filter/map yet:
                   Stream<Integer> stream = numbers.stream()
                       .filter(n -> {
                           System.out.println("Filter: " + n);
                           return n > 0;
                       })
                       .map(n -> {
                           System.out.println("Map: " + n);
                           return n * 2;
                       });
                   
                   // Only now it executes:
                   stream.count();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A large dataset query:
                   users.stream()
                       .filter(this::isActive)
                       .map(this::toDTO)
                       .limit(100)  // Short-circuits
                       .toList();
            
               INTERVIEW QUESTION:
                   "What is lazy evaluation in streams?
                   How does it improve performance?"
            
               COMMON MISTAKES:
                   - Expecting immediate execution
                   - Side effects in intermediate ops
            
            ─────────────────────────────────────────────────────────────────────
            
            3. STREAM TYPES
               ─────────────────────────────────────────────────────────────────────
               SEQUENTIAL STREAM:
                   - Single thread
                   - numbers.stream()
                   - Predictable order
            
               PARALLEL STREAM:
                   - Multiple threads (ForkJoinPool)
                   - numbers.parallelStream()
                   - Not always faster (overhead)
            
               INFINITE STREAM:
                   - Stream.generate(), Stream.iterate()
                   - Must use limit() to terminate
            
               SIMPLE EXAMPLE:
                   // Sequential:
                   List<String> result1 = items.stream()
                       .map(this::process)
                       .toList();
                   
                   // Parallel:
                   List<String> result2 = items.parallelStream()
                       .map(this::process)
                       .toList();
                   
                   // Infinite:
                   Stream<Integer> infinite = Stream.iterate(1, n -> n + 1);
                   List<Integer> first10 = infinite.limit(10).toList();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A CPU-intensive batch job:
                   records.parallelStream()
                       .map(this::expensiveCalculation)
                       .collect(Collectors.toList());
            
               INTERVIEW QUESTION:
                   "When should you use parallel streams?
                   What are the performance considerations?"
            
               COMMON MISTAKES:
                   - Using parallel for small datasets
                   - Not thread-safe operations
            
            ─────────────────────────────────────────────────────────────────────
            
            4. SHORT-CIRCUITING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Terminal operations that stop processing early.
            
               WHY IT EXISTS:
               - Performance optimization
               - Early termination
            
               SIMPLE EXAMPLE:
                   // findFirst - stops after first match:
                   Optional<Integer> first = nums.stream()
                       .filter(n -> n > 2)
                       .findFirst();
                   
                   // anyMatch - stops after first match:
                   boolean hasEven = nums.stream()
                       .anyMatch(n -> n % 2 == 0);
                   
                   // limit - stops after n elements:
                   List<Integer> first5 = nums.stream()
                       .limit(5)
                       .toList();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A search operation:
                   users.stream()
                       .filter(u -> u.getEmail().equals(target))
                       .findFirst()  // Stop at first match
                       .orElseThrow(() -> new UserNotFoundException());
            
               INTERVIEW QUESTION:
                   "What is short-circuiting in streams?
                   Give examples of short-circuiting operations."
            
               COMMON MISTAKES:
                   - Not using short-circuiting
                   - Processing entire dataset unnecessarily
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Stream internals are essential for:
            - Performance optimization
            - Understanding lazy evaluation
            - Debugging stream issues
            - Choosing between sequential/parallel
            """);
    }
}
