package phase7;

import java.util.concurrent.*;

/**
 * LESSON 10: FORKJOINPOOL
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * ForkJoinPool is a special thread pool designed for tasks that can be
 * broken into smaller subtasks (divide-and-conquer). It uses "work stealing"
 * where idle threads steal work from busy threads.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Traditional thread pools have a single queue
 * - ForkJoinPool has a queue per thread (work stealing)
 * - Better for recursive, divide-and-conquer tasks
 * - More efficient CPU utilization
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Merge sort:
 * - Split array in half (fork)
 * - Sort each half recursively
 * - Merge results (join)
 * - Each half can be processed by different threads
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Large file processing:
 * - Split file into chunks
 * - Process each chunk in parallel
 * - Combine results
 * - Work stealing balances load automatically
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is ForkJoinPool?"
 * Answer: Thread pool for divide-and-conquer tasks,
 *         uses work stealing for better load balancing
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using ForkJoinPool for non-recursive tasks
 * - Not using RecursiveTask/RecursiveAction
 * - Blocking in compute() method
 * - Creating too many small tasks (overhead)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Work stealing reduces idle time
 * - Task granularity matters (too fine = overhead)
 * - Best for CPU-bound recursive tasks
 * - Common pool used by parallelStream()
 *
 * ============================================================
 * FORK/JOIN WORK STEALING
 * ============================================================
 *
 *   Thread 1 Queue: [Task A1] [Task A2] [Task A3]
 *   Thread 2 Queue: [Task B1] [Task B2]
 *   Thread 3 Queue: [Task C1] [Task C2] [Task C3]
 *
 *   If Thread 2 finishes early:
 *   Thread 2 steals from Thread 1 or 3
 *
 *   +--------+     +--------+     +--------+
 *   | Thread |     | Thread |     | Thread |
 *   |   1    |     |   2    |     |   3    |
 *   +---+----+     +---+----+     +---+----+
 *       |              |              |
 *       | steal <------+              |
 *       v              |              |
 *   +---+----+         |              |
 *   | Task A2|         |              |
 *   +--------+         |              |
 *                       |              |
 *                       v              |
 *                   +---+----+         |
 *                   | Task C3|         |
 *                   +--------+         |
 */

public class Lesson10_ForkJoinPool {

    public static void main(String[] args) throws Exception {
        System.out.println("=== FORKJOINPOOL ===\n");

        // ============================================================
        // EXAMPLE 1: Basic ForkJoinPool
        // ============================================================
        System.out.println("--- Example 1: Basic ForkJoinPool ---\n");

        ForkJoinPool pool = new ForkJoinPool();

        SimpleTask task = new SimpleTask(100);
        Integer result = pool.invoke(task);

        System.out.println("Result: " + result + "\n");

        pool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: RecursiveTask (returns value)
        // ============================================================
        System.out.println("--- Example 2: RecursiveTask ---\n");

        ForkJoinPool sumPool = new ForkJoinPool();

        int[] numbers = new int[100];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        SumTask sumTask = new SumTask(numbers, 0, numbers.length);
        Long sum = sumPool.invoke(sumTask);

        System.out.println("Sum of 1-100: " + sum);
        System.out.println("Expected: " + (100L * 101 / 2) + "\n");

        sumPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 3: RecursiveAction (no return value)
        // ============================================================
        System.out.println("--- Example 3: RecursiveAction ---\n");

        ForkJoinPool printPool = new ForkJoinPool();

        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
        PrintTask printTask = new PrintTask(nums, 0, nums.length);

        printPool.invoke(printTask);
        System.out.println();

        printPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Parallel array processing
        // ============================================================
        System.out.println("--- Example 4: Parallel Array Processing ---\n");

        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i + 1;
        }

        ForkJoinPool processPool = new ForkJoinPool();

        ProcessTask processTask = new ProcessTask(largeArray, 0, largeArray.length);
        int max = processPool.invoke(processTask);

        System.out.println("Max value: " + max);
        System.out.println("Expected: " + largeArray.length + "\n");

        processPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Common ForkJoinPool (parallelStream uses this)
        // ============================================================
        System.out.println("--- Example 5: Common ForkJoinPool ---\n");

        // parallelStream uses common ForkJoinPool
        long count = java.util.Arrays.stream(new int[1000])
            .parallel()
            .filter(i -> i % 2 == 0)
            .count();

        System.out.println("Even numbers count: " + count + "\n");

        // ============================================================
        // EXAMPLE 6: Custom ForkJoinPool
        // ============================================================
        System.out.println("--- Example 6: Custom ForkJoinPool ---\n");

        ForkJoinPool customPool = new ForkJoinPool(4);  // 4 threads

        int[] data = new int[100];
        for (int i = 0; i < data.length; i++) {
            data[i] = i + 1;
        }

        SumTask customSumTask = new SumTask(data, 0, data.length);
        Long customSum = customPool.invoke(customSumTask);

        System.out.println("Custom pool sum: " + customSum + "\n");

        customPool.shutdown();
        System.out.println();

        // ============================================================
        // EXAMPLE 7: When to use ForkJoinPool
        // ============================================================
        System.out.println("--- Example 7: When to Use ForkJoinPool ---\n");

        System.out.println("Use ForkJoinPool when:");
        System.out.println("  - Tasks can be divided into subtasks (divide-and-conquer)");
        System.out.println("  - Tasks are CPU-bound");
        System.out.println("  - Recursive algorithms (merge sort, quick sort)");
        System.out.println("  - Large data processing");
        System.out.println();
        System.out.println("Don't use when:");
        System.out.println("  - Tasks are I/O-bound (use regular thread pool)");
        System.out.println("  - Tasks can't be divided");
        System.out.println("  - Tasks are small (overhead > benefit)");
    }

    // ============================================================
    // RECURSIVE TASK EXAMPLES
    // ============================================================

    /**
     * Simple RecursiveTask that sums numbers.
     */
    static class SimpleTask extends RecursiveTask<Integer> {
        private final int n;

        public SimpleTask(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }

            // Fork: create subtask
            SimpleTask subTask = new SimpleTask(n - 1);
            subTask.fork();

            // Compute current
            int current = n;

            // Join: wait for subtask
            int subResult = subTask.join();

            return current + subResult;
        }
    }

    /**
     * RecursiveTask for summing array.
     */
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 10;
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
                // Small enough, compute directly
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            }

            // Split in half
            int mid = start + length / 2;

            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);

            // Fork left task
            left.fork();

            // Compute right task directly
            long rightResult = right.compute();

            // Join left task
            long leftResult = left.join();

            return leftResult + rightResult;
        }
    }

    /**
     * RecursiveAction for printing array.
     */
    static class PrintTask extends RecursiveAction {
        private static final int THRESHOLD = 5;
        private final int[] array;
        private final int start;
        private final int end;

        public PrintTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int length = end - start;

            if (length <= THRESHOLD) {
                // Print directly
                for (int i = start; i < end; i++) {
                    System.out.print(array[i] + " ");
                }
            } else {
                // Split
                int mid = start + length / 2;
                PrintTask left = new PrintTask(array, start, mid);
                PrintTask right = new PrintTask(array, mid, end);

                // Fork both
                left.fork();
                right.compute();
                left.join();
            }
        }
    }

    /**
     * RecursiveTask for finding max value.
     */
    static class ProcessTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 20;
        private final int[] array;
        private final int start;
        private final int end;

        public ProcessTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int length = end - start;

            if (length <= THRESHOLD) {
                int max = Integer.MIN_VALUE;
                for (int i = start; i < end; i++) {
                    if (array[i] > max) {
                        max = array[i];
                    }
                }
                return max;
            }

            int mid = start + length / 2;

            ProcessTask left = new ProcessTask(array, start, mid);
            ProcessTask right = new ProcessTask(array, mid, end);

            left.fork();
            int rightMax = right.compute();
            int leftMax = left.join();

            return Math.max(leftMax, rightMax);
        }
    }
}
