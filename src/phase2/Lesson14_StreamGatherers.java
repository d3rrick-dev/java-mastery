package phase2;

/**
 * JAVA STREAMS LESSON: Stream Gatherers (Java 24 Preview)
 *
 * Phase 2: Java Streams Fundamentals
 *
 * This lesson covers:
 * 1. What are Stream Gatherers
 * 2. Why Stream Gatherers exist
 * 3. Internal mechanics
 * 4. Simple examples
 * 5. Real-world backend examples
 * 6. Interview questions
 * 7. Common mistakes
 * 8. Performance implications
 * 9. Alternatives
 */

public class Lesson14_StreamGatherers {
    public static void main(String[] args) {
        System.out.println("""
            === STREAM GATHERERS (JAVA 24 PREVIEW) ===
            
            1. WHAT ARE STREAM GATHERERS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Stream Gatherers are a new intermediate operation in Java 24 that
               provide a more flexible and composable way to process stream elements
               compared to traditional collectors.
            
               WHY IT EXISTS:
               - More flexible than Collectors for complex stream processing
               - Composable: gatherers can be chained together
               - Incremental: processes elements one at a time
               - State management: can maintain state during processing
               - Better performance for certain use cases
            
               INTERNAL MECHANICS:
                   - Gatherer interface: integrator, combiner, finisher
                   - integrator: processes each element
                   - combiner: merges state from parallel streams
                   - finisher: produces final result
                   - StreamGathererOp is the internal stream operation
            
               SIMPLE EXAMPLE:
                   // Built-in gatherers
                   var result = Stream.of(1, 2, 3, 4, 5)
                       .gather(Gatherers.fold(() -> 0, (acc, n) -> acc + n))
                       .findFirst()
                       .orElse(0); // 15
                   
                   // Custom gatherer
                   public class RunningAverageGatherer implements Gatherer<Integer, double[], Double> {
                       @Override
                       public void integrate(int element, double[] state, 
                                           Downstream<? super Double> downstream) {
                           state[0] += element;
                           state[1]++;
                           downstream.push(state[0] / state[1]);
                       }
                       
                       @Override
                       public double[] finish(double[] state, 
                                             Downstream<? super Double> downstream) {
                           return state;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A real-time analytics service:
                   - Process incoming events in batches
                   - Calculate running statistics
                   - Emit intermediate results for monitoring
                   - Handle backpressure gracefully
            
               INTERVIEW QUESTION:
                   "How do Stream Gatherers differ from Collectors?
                   What are the performance benefits of gatherers?"
            
               COMMON MISTAKES:
                   - Not understanding the state management
                   - Forgetting to handle the combiner for parallel streams
                   - Not calling downstream.push() for intermediate results
            
               PERFORMANCE & SCALABILITY:
                   - Better memory efficiency for incremental processing
                   - Can process infinite streams
                   - Parallel processing with proper combiner
            
               ALTERNATIVES:
                   - Custom collectors
                   - forEach with side effects
                   - Custom stream operations
            
            ─────────────────────────────────────────────────────────────────────
            
            2. BUILT-IN GATHERERS
               ─────────────────────────────────────────────────────────────────────
               FOLD:
                   // Like reduce but with gatherer API
                   Stream.of(1, 2, 3, 4, 5)
                       .gather(Gatherers.fold(() -> 0, Integer::sum))
                       .forEach(System.out::println);
            
               MAP:
                   // Transform elements
                   Stream.of("a", "b", "c")
                       .gather(Gatherers.map(String::toUpperCase))
                       .forEach(System.out::println);
            
               FILTER:
                   // Filter elements
                   Stream.of(1, 2, 3, 4, 5)
                       .gather(Gatherers.filter(n -> n % 2 == 0))
                       .forEach(System.out::println);
            
               FLATMAP:
                   // Flatten nested structures
                   Stream.of(List.of(1, 2), List.of(3, 4))
                       .gather(Gatherers.flatMap(List::stream))
                       .forEach(System.out::println);
            
               LIMIT:
                   // Limit elements
                   Stream.iterate(1, n -> n + 1)
                       .gather(Gatherers.limit(5))
                       .forEach(System.out::println);
            
               DISTINCT:
                   // Remove duplicates
                   Stream.of(1, 2, 1, 3, 2)
                       .gather(Gatherers.distinct())
                       .forEach(System.out::println);
            
               INTERVIEW QUESTION:
                   "How do gatherers compose? Can you chain multiple gatherers?"
            
               COMMON MISTAKES:
                   - Not understanding the difference from standard operations
                   - Using gatherers when standard operations suffice
            
            ─────────────────────────────────────────────────────────────────────
            
            3. WINDOW GATHERERS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Window gatherers create sliding or fixed windows over stream elements,
               useful for time-series analysis and batch processing.
            
               FIXED WINDOW:
                   // Fixed-size windows
                   Stream.of(1, 2, 3, 4, 5, 6)
                       .gather(Gatherers.windowFixed(3))
                       .forEach(window -> {
                           System.out.println("Window: " + window);
                       });
                   // Output: [1, 2, 3], [4, 5, 6]
            
               SLIDING WINDOW:
                   // Sliding windows with overlap
                   Stream.of(1, 2, 3, 4, 5)
                       .gather(Gatherers.windowSliding(3, 1))
                       .forEach(window -> {
                           System.out.println("Window: " + window);
                       });
                   // Output: [1, 2, 3], [2, 3, 4], [3, 4, 5]
            
               REAL-WORLD BACKEND EXAMPLE:
                   A monitoring system:
                   - Process metrics in 5-minute windows
                   - Calculate averages per window
                   - Alert on threshold breaches
                   - Handle late-arriving data
            
               INTERVIEW QUESTION:
                   "How would you implement a sliding window average?
                   What are the memory implications of window gatherers?"
            
               COMMON MISTAKES:
                   - Not handling window overflow
                   - Memory leaks with large windows
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CUSTOM GATHERER IMPLEMENTATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Custom gatherers allow implementing complex stream processing logic
               that isn't available in built-in gatherers.
            
               SIMPLE EXAMPLE - RUNNING AVERAGE:
                   public class RunningAverage implements Gatherer<Integer, double[], Double> {
                       @Override
                       public void integrate(Integer element, double[] state,
                                           Downstream<? super Double> downstream) {
                           state[0] += element;
                           state[1]++;
                           downstream.push(state[0] / state[1]);
                       }
                       
                       @Override
                       public double[] combine(double[] state1, double[] state2) {
                           state1[0] += state2[0];
                           state1[1] += state2[1];
                           return state1;
                       }
                       
                       @Override
                       public double[] finish(double[] state,
                                             Downstream<? super Double> downstream) {
                           return state;
                       }
                   }
                   
                   // Usage
                   Stream.of(1, 2, 3, 4, 5)
                       .gather(new RunningAverage())
                       .forEach(avg -> System.out.println("Running avg: " + avg));
            
               CUSTOM GATHERER - BATCH PROCESSING:
                   public class BatchGatherer<T> implements Gatherer<T, List<T>, List<T>> {
                       private final int batchSize;
                       
                       public BatchGatherer(int batchSize) {
                           this.batchSize = batchSize;
                       }
                       
                       @Override
                       public void integrate(T element, List<T> state,
                                           Downstream<? super List<T>> downstream) {
                           state.add(element);
                           if (state.size() >= batchSize) {
                               downstream.push(new ArrayList<>(state));
                               state.clear();
                           }
                       }
                       
                       @Override
                       public List<T> finish(List<T> state,
                                             Downstream<? super List<T>> downstream) {
                           if (!state.isEmpty()) {
                               downstream.push(state);
                           }
                           return state;
                       }
                   }
            
               INTERVIEW QUESTION:
                   "How do you implement a gatherer that maintains state?
                   What is the role of the combiner in parallel streams?"
            
               COMMON MISTAKES:
                   - Not thread-safe state management
                   - Forgetting to implement combiner
                   - Not handling finish correctly
            
               PERFORMANCE & SCALABILITY:
                   - State should be minimal
                   - Consider memory usage for large streams
                   - Parallel streams need proper combiner
            
            ─────────────────────────────────────────────────────────────────────
            
            5. GATHERERS VS COLLECTORS
               ─────────────────────────────────────────────────────────────────────
               COMPARISON:
                   - Collectors: terminal operations, produce final result
                   - Gatherers: intermediate operations, can produce multiple results
                   - Collectors: no state during processing
                   - Gatherers: maintain state, can emit intermediate results
                   - Collectors: not composable
                   - Gatherers: highly composable
            
               SIMPLE EXAMPLE:
                   // Collector - produces single result
                   List<String> result = stream.collect(Collectors.toList());
                   
                   // Gatherer - can produce multiple results
                   Stream<List<String>> result = stream.gather(Gatherers.windowFixed(100));
            
               REAL-WORLD BACKEND EXAMPLE:
                   A data pipeline:
                   - Use gatherers for incremental processing
                   - Use collectors for final aggregation
                   - Combine both for complex workflows
            
               INTERVIEW QUESTION:
                   "When would you choose gatherers over collectors?
                   What are the trade-offs?"
            
               COMMON MISTAKES:
                   - Using gatherers when collectors are simpler
                   - Not understanding the intermediate vs terminal distinction
            
               PERFORMANCE & SCALABILITY:
                   - Gatherers can be more memory-efficient
                   - Collectors are optimized for final results
                   - Choose based on use case
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Stream Gatherers provide:
            - More flexible stream processing
            - Composable intermediate operations
            - State management capabilities
            - Better performance for incremental processing
            - Window and batch processing support
            """);
    }
}