package phase10;

import java.util.*;

/**
 * LESSON 8: PRIORITYQUEUE INTERNALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * PriorityQueue is a queue where elements are ordered by priority.
 * Uses a binary heap internally.
 * Like a hospital ER - patients treated by severity, not arrival time.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Always get highest/lowest priority element
 * - O(log n) insertion and removal
 * - Natural ordering or custom comparator
 */

public class Lesson08_PriorityQueueInternals {

    public static void main(String[] args) {
        System.out.println("=== PRIORITYQUEUE INTERNALS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic PriorityQueue (min-heap)
        // ============================================================
        System.out.println("--- Example 1: Min-Heap ---\n");

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(5);
        minHeap.add(3);
        minHeap.add(8);
        minHeap.add(1);
        minHeap.add(4);

        System.out.println("Min-Heap elements (poll order):");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println("\n");

        // ============================================================
        // EXAMPLE 2: Max-Heap
        // ============================================================
        System.out.println("--- Example 2: Max-Heap ---\n");

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(5);
        maxHeap.add(3);
        maxHeap.add(8);
        maxHeap.add(1);
        maxHeap.add(4);

        System.out.println("Max-Heap elements (poll order):");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");

        // ============================================================
        // EXAMPLE 3: Custom comparator
        // ============================================================
        System.out.println("--- Example 3: Custom Comparator ---\n");

        record Task(String name, int priority) {}

        PriorityQueue<Task> taskQueue = new PriorityQueue<>(
            Comparator.comparingInt(t -> t.priority())
        );
        taskQueue.add(new Task("Low priority", 3));
        taskQueue.add(new Task("High priority", 1));
        taskQueue.add(new Task("Medium priority", 2));

        System.out.println("Tasks by priority:");
        while (!taskQueue.isEmpty()) {
            Task t = taskQueue.poll();
            System.out.println("  " + t.name() + " (priority: " + t.priority() + ")");
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Heap visualization
        // ============================================================
        System.out.println("--- Example 4: Heap Visualization ---\n");

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(10);
        heap.add(20);
        heap.add(15);
        heap.add(30);
        heap.add(40);

        System.out.println("Heap array representation:");
        System.out.println("Index:  0   1   2   3   4");
        System.out.println("Value: " + heap.toArray());
        System.out.println();
        System.out.println("Tree structure:");
        System.out.println("       10");
        System.out.println("      /  \\");
        System.out.println("    20    15");
        System.out.println("   /  \\");
        System.out.println("  30   40");
        System.out.println();
    }

    // ============================================================
    // INTERNAL STRUCTURE
    // ============================================================
    /*
     * PriorityQueue Internal Structure:
     *
     * - Backed by array (Object[] queue)
     * - Binary heap (complete binary tree)
     * - Default: min-heap (natural order)
     *
     * Array representation:
     * - Parent at i: children at 2i+1, 2i+2
     * - Child at i: parent at (i-1)/2
     *
     * Operations:
     * - offer/add: O(log n) - bubble up
     * - poll/remove: O(log n) - bubble down
     * - peek: O(1)
     *
     * NOT thread-safe (use PriorityBlockingQueue)
     */
}
