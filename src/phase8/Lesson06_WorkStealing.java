package phase8;

import java.util.concurrent.*;

/**
 * LESSON 6: WORK STEALING
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Work stealing is a scheduling technique where idle threads
 * "steal" work from busy threads' queues. Like a worker finishing
 * their tasks and helping a colleague who's overloaded.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Traditional thread pools have single queue (contention)
 * - Work stealing has queue per thread (no contention)
 * - Better load balancing
 * - Reduces idle time
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Restaurant kitchen:
 * - Chef A has 10 orders, Chef B has 2 orders
 * - Chef B finishes, takes orders from Chef A's list
 * - Work is balanced automatically
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Data processing:
 * - Thread 1 has 1000 records, Thread 2 has 100 records
 * - Thread 2 finishes early, steals from Thread 1
 * - All cores utilized efficiently
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is work stealing?"
 * Answer: Idle threads steal tasks from busy threads,
 *         used in ForkJoinPool
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Assuming work stealing is always better
 * - Not understanding deque structure
 * - Creating tasks that are too small
 * - Blocking in compute() method
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - LIFO for own tasks (cache friendly)
 * - FIFO for stolen tasks (fair)
 * - Reduces synchronization overhead
 * - Best for irregular workloads
 */

public class Lesson06_WorkStealing {

    public static void main(String[] args) throws Exception {
        System.out.println("=== WORK STEALING ===\n");

        // ============================================================
        // EXAMPLE 1: ForkJoinPool with work stealing
        // ============================================================
        System.out.println("--- Example 1: Work Stealing in Action ---\n");

        ForkJoinPool pool = new ForkJoinPool();

        // Create tasks with different sizes
        java.util.List<RecursiveTask<Long>> tasks = new java.util.ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int size = (i < 4) ? 1000 : 100;  // Uneven work distribution
            tasks.add(new SumTask(new int[size], 0, size));
        }

        // Submit all tasks
        List<Future<Long>> futures = new java.util.ArrayList<>();
        for (RecursiveTask<Long> task : tasks) {
            futures.add(pool.submit(task));
        }

        long total = 0;
        for (Future<Long> f : futures) {
            total += f.get();
        }

        System.out.println("Total with work stealing: " + total);
        System.out.println("(Idle threads automatically steal work)\n");

        pool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Work stealing vs fixed queue
        // ============================================================
        System.out.println("--- Example 2: Work Stealing vs Fixed Queue ---\n");

        System.out.println("Fixed Queue (ThreadPoolExecutor):");
        System.out.println("  - Single shared queue");
        System.out.println("  - Contention on queue access");
        System.out.println("  - All threads compete for same tasks");
        System.out.println();
        System.out.println("Work Stealing (ForkJoinPool):");
        System.out.println("  - Queue per thread (deque)");
        System.out.println("  - No contention on own queue");
        System.out.println("  - Stealing only when idle");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Deque structure
        // ============================================================
        System.out.println("--- Example 3: Deque Structure ---\n");

        System.out.println("Each thread has a Double-Ended Queue (Deque):");
        System.out.println();
        System.out.println("  Thread 1 Deque:     Thread 2 Deque:     Thread 3 Deque:");
        System.out.println("  +---+---+---+       +---+---+           +---+");
        System.out.println("  | T4| T3| T2| T1 |   | T8| T7| T6|     | T5|");
        System.out.println("  +---+---+---+---+   +---+---+---+       +---+");
        System.out.println("       ^                 ^                    ^");
        System.out.println("       |                 |                    |");
        System.out.println("      Top               Top                  Top");
        System.out.println();
        System.out.println("  - Push/pop from top (LIFO for own tasks)");
        System.out.println("  - Steal from bottom (FIFO for stolen tasks)");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Work stealing for irregular tasks
        // ============================================================
        System.out.println("--- Example 4: Irregular Workloads ---\n");

        ForkJoinPool irregularPool = new ForkJoinPool();

        // Tasks with varying complexity
        List<RecursiveTask<Long>> irregularTasks = new java.util.ArrayList<>();
        irregularTasks.add(new SumTask(new int[10000], 0, 10000));  // Large
        irregularTasks.add(new SumTask(new int[100], 0, 100));      // Small
        irregularTasks.add(new SumTask(new int[5000], 0, 5000));    // Medium
        irregularTasks.add(new SumTask(new int[50], 0, 50));        // Tiny

        List<Future<Long>> irregularFutures = new java.util.ArrayList<>();
        for (RecursiveTask<Long> task : irregularTasks) {
            irregularFutures.add(irregularPool.submit(task));
        }

        for (Future<Long> f : irregularFutures) {
            f.get();
        }

        System.out.println("Irregular tasks completed with work stealing");
        System.out.println("(Small tasks finished quickly, helped with large ones)\n");

        irregularPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Common pool configuration
        // ============================================================
        System.out.println("--- Example 5: Common Pool ---\n");

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("Common pool parallelism: " + commonPool.getParallelism());
        System.out.println("Common pool pool size: " + commonPool.getPoolSize());
        System.out.println("Common pool active threads: " + commonPool.getActiveThreadCount());
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Custom work stealing pool
        // ============================================================
        System.out.println("--- Example 6: Custom Pool ---\n");

        ForkJoinPool customPool = new ForkJoinPool(
            4,  // parallelism
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null,
            true  // asyncMode = true for FIFO processing
        );

        System.out.println("Custom pool created with 4 threads");
        System.out.println("Async mode: " + customPool.getParallelism());
        System.out.println();

        customPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 7: When work stealing shines
        // ============================================================
        System.out.println("--- Example 7: When Work Stealing Shines ---\n");

        System.out.println("Work stealing is best for:");
        System.out.println("1. Recursive divide-and-conquer algorithms");
        System.out.println("2. Irregular workloads (varying task sizes)");
        System.out.println("3. CPU-bound computations");
        System.out.println("4. Tasks that spawn more tasks");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  - Merge sort, quick sort");
        System.out.println("  - Tree/graph traversals");
        System.out.println("  - Matrix multiplication");
        System.out.println("  - Large data processing");
    }

    // ============================================================
    // HELPER CLASS
    // ============================================================

    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 100;
        private final int[] array;
        private final int start;
        private final int end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;

            if (length <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            }

            int mid = start + length / 2;
            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);

            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();

            return leftResult + rightResult;
        }
    }
}
