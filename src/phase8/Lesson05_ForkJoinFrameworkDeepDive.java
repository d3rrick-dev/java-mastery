package phase8;

import java.util.concurrent.*;
import java.util.function.*;

/**
 * LESSON 5: FORK JOIN FRAMEWORK DEEP DIVE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * ForkJoin Framework is designed for divide-and-conquer parallelism.
 * Tasks fork (split) into subtasks, execute in parallel, then join
 * (combine) results. Uses work-stealing for load balancing.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Traditional thread pools have single queue (bottleneck)
 * - ForkJoinPool has queue per thread (work stealing)
 * - Better for recursive, divide-and-conquer tasks
 * - Automatic load balancing
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Merge sort:
 * - Split array in half (fork)
 * - Sort each half recursively
 * - Merge results (join)
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Large file processing:
 * - Split file into chunks
 * - Process each chunk in parallel
 * - Combine results
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is ForkJoinPool?"
 * Answer: Thread pool for divide-and-conquer, uses work stealing
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using for non-recursive tasks
 * - Blocking in compute()
 * - Creating too many small tasks
 * - Not using invokeAll()
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Work stealing reduces idle time
 * - Task granularity matters
 * - Best for CPU-bound recursive tasks
 * - Common pool used by parallelStream()
 */

public class Lesson05_ForkJoinFrameworkDeepDive {

    public static void main(String[] args) throws Exception {
        System.out.println("=== FORK JOIN FRAMEWORK DEEP DIVE ===\n");

        // ============================================================
        // EXAMPLE 1: RecursiveTask for sum
        // ============================================================
        System.out.println("--- Example 1: Recursive Sum ---\n");

        int[] data = new int[1000];
        for (int i = 0; i < data.length; i++) {
            data[i] = i + 1;
        }

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(data, 0, data.length);
        long sum = pool.invoke(task);

        System.out.println("Sum: " + sum);
        System.out.println("Expected: " + (1000L * 1001 / 2));
        System.out.println();

        pool.shutdown();

        // ============================================================
        // EXAMPLE 2: RecursiveAction for processing
        // ============================================================
        System.out.println("--- Example 2: Recursive Action ---\n");

        String[] words = new String[100];
        for (int i = 0; i < words.length; i++) {
            words[i] = "word" + i;
        }

        ForkJoinPool processPool = new ForkJoinPool();
        ProcessAction action = new ProcessAction(words, 0, words.length);
        processPool.invoke(action);
        System.out.println();

        processPool.shutdown();

        // ============================================================
        // EXAMPLE 3: Work stealing visualization
        // ============================================================
        System.out.println("--- Example 3: Work Stealing ---\n");

        System.out.println("Work Stealing:");
        System.out.println("  - Each thread has its own deque");
        System.out.println("  - Threads steal from others' deques");
        System.out.println("  - LIFO for own tasks, FIFO for stolen");
        System.out.println("  - Reduces contention");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: invokeAll for multiple tasks
        // ============================================================
        System.out.println("--- Example 4: invokeAll ---\n");

        ForkJoinPool invokePool = new ForkJoinPool();

        java.util.List<SumTask> tasks = new java.util.ArrayList<>();
        int chunkSize = 100;
        for (int i = 0; i < data.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, data.length);
            tasks.add(new SumTask(data, i, end));
        }

//        java.util.List<Future<Long>> results = invokePool.invokeAll(tasks);

//        long total = 0;
//        for (Future<Long> f : results) {
//            total += f.get();
//        }
//        System.out.println("invokeAll total: " + total);

        invokePool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Custom ForkJoinTask
        // ============================================================
        System.out.println("--- Example 5: Custom Task ---\n");

        ForkJoinPool customPool = new ForkJoinPool();

        FibonacciTask fibTask = new FibonacciTask(20);
        long fibResult = customPool.invoke(fibTask);
        System.out.println("Fibonacci(20) = " + fibResult);

        customPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: When to use ForkJoin
        // ============================================================
        System.out.println("--- Example 6: When to Use ---\n");

        System.out.println("Use ForkJoin when:");
        System.out.println("  - Tasks can be divided recursively");
        System.out.println("  - CPU-bound computations");
        System.out.println("  - Large data processing");
        System.out.println();
        System.out.println("Don't use when:");
        System.out.println("  - Tasks are I/O-bound");
        System.out.println("  - Tasks can't be divided");
        System.out.println("  - Tasks are small");
    }

    // ============================================================
    // RECURSIVE TASKS
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

    static class ProcessAction extends RecursiveAction {
        private static final int THRESHOLD = 10;
        private final String[] array;
        private final int start;
        private final int end;

        public ProcessAction(String[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int length = end - start;

            if (length <= THRESHOLD) {
                for (int i = start; i < end; i++) {
                    System.out.println("Processing: " + array[i]);
                }
            } else {
                int mid = start + length / 2;
                ProcessAction left = new ProcessAction(array, start, mid);
                ProcessAction right = new ProcessAction(array, mid, end);
                left.fork();
                right.compute();
                left.join();
            }
        }
    }

    static class FibonacciTask extends RecursiveTask<Long> {
        private final int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        protected Long compute() {
            if (n <= 1) {
                return (long) n;
            }

            FibonacciTask f1 = new FibonacciTask(n - 1);
            FibonacciTask f2 = new FibonacciTask(n - 2);

            f1.fork();
            long result2 = f2.compute();
            long result1 = f1.join();

            return result1 + result2;
        }
    }
}
