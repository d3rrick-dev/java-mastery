package phase4;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * LESSON 7: PriorityQueue (Heap)
 *
 * THEORY:
 * PriorityQueue is a queue where elements are ordered by priority (natural order or custom comparator).
 * Internally implemented as a binary heap (array-based complete binary tree).
 *
 * INTERNAL WORKING:
 * - Binary heap: Complete binary tree stored in array
 * - Min-heap by default (smallest element at root)
 * - Can be max-heap with custom comparator
 * - Not thread-safe
 * - Does not allow null elements
 * - Does not maintain insertion order
 *
 * TIME COMPLEXITY:
 * - offer(E e) - O(log n)
 * - poll() - O(log n)
 * - peek() - O(1)
 * - remove(Object) - O(n)
 * - contains(Object) - O(n)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - offer(E e)     : Add element
 * - poll()         : Remove and return highest priority
 * - peek()         : View highest priority
 * - remove(Object) : Remove specific element
 * - contains(Object): Check if exists
 * - size()         : Number of elements
 * - isEmpty()      : Check if empty
 */

public class Lesson07_PriorityQueue {

    public static void main(String[] args) {
        System.out.println("=== LESSON 7: PriorityQueue (Heap) ===\n");

        // ============================================
        // 1. CREATING PRIORITYQUEUE
        // ============================================
        System.out.println("--- 1. Creating PriorityQueue ---");

        // Min-heap (default - smallest first)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        System.out.println("Min-heap: " + minHeap);

        // Max-heap (custom comparator)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        System.out.println("Max-heap: " + maxHeap);

        // ============================================
        // 2. ADDING ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Adding Elements ---");

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(30);
        pq.offer(10);
        pq.offer(20);
        pq.offer(5);
        pq.offer(15);
        System.out.println("After offers: " + pq);
        System.out.println("Internal order (heap): " + pq);
        System.out.println("Peek (highest priority): " + pq.peek());

        // ============================================
        // 3. REMOVING ELEMENTS
        // ============================================
        System.out.println("\n--- 3. Removing Elements ---");

        System.out.println("Poll: " + pq.poll());      // Remove smallest
        System.out.println("After poll: " + pq);
        System.out.println("Poll: " + pq.poll());
        System.out.println("After poll: " + pq);

        // ============================================
        // 4. MAX-HEAP EXAMPLE
        // ============================================
        System.out.println("\n--- 4. Max-Heap Example ---");

        PriorityQueue<Integer> maxPq = new PriorityQueue<>((a, b) -> b - a);
        maxPq.offer(30);
        maxPq.offer(10);
        maxPq.offer(20);
        maxPq.offer(5);
        maxPq.offer(15);
        System.out.println("Max-heap: " + maxPq);
        System.out.println("Peek (largest): " + maxPq.peek());
        System.out.println("Poll: " + maxPq.poll());
        System.out.println("After poll: " + maxPq);

        // ============================================
        // 5. PRIORITYQUEUE WITH CUSTOM OBJECTS
        // ============================================
        System.out.println("\n--- 5. PriorityQueue with Custom Objects ---");

        // Task class with priority
        record Task(String name, int priority) implements Comparable<Task> {
            @Override
            public int compareTo(Task other) {
                return Integer.compare(this.priority, other.priority);
            }

            @Override
            public String toString() {
                return name + "(p=" + priority + ")";
            }
        }

        PriorityQueue<Task> taskQueue = new PriorityQueue<>();
        taskQueue.offer(new Task("Low priority", 3));
        taskQueue.offer(new Task("High priority", 1));
        taskQueue.offer(new Task("Medium priority", 2));
        taskQueue.offer(new Task("Urgent", 0));

        System.out.println("Tasks by priority:");
        while (!taskQueue.isEmpty()) {
            System.out.println("  " + taskQueue.poll());
        }

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Find K largest elements
        int[] arr = {3, 1, 5, 12, 2, 11};
        int k = 3;
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("Top " + k + " largest elements:");

        PriorityQueue<Integer> topK = new PriorityQueue<>();
        for (int num : arr) {
            topK.offer(num);
            if (topK.size() > k) {
                topK.poll();  // Remove smallest, keep k largest
            }
        }

        // Print in descending order
        while (!topK.isEmpty()) {
            System.out.println("  " + topK.poll());
        }

        // Challenge: Merge K sorted lists
        System.out.println("\nMerging sorted lists:");
        java.util.List<java.util.List<Integer>> lists = List.of(
                List.of(1, 4, 7),
                List.of(2, 5, 8),
                List.of(3, 6, 9)
        );
        java.util.List<Integer> merged = mergeKSortedLists(lists);
        System.out.println("Merged: " + merged);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Expecting natural ordering for custom objects
        // PriorityQueue<Task> wrong = new PriorityQueue<>(); // ERROR: Task doesn't implement Comparable
        // Correct:
        PriorityQueue<Task> correct = new PriorityQueue<>(); // Task implements Comparable
        correct.offer(new Task("A", 1));
        System.out.println("Correct: " + correct.peek());

        // Mistake 2: Using null elements
        PriorityQueue<String> pq2 = new PriorityQueue<>();
        // pq2.offer(null); // Throws NullPointerException!

        // Mistake 3: Iterating doesn't give sorted order
        PriorityQueue<Integer> pq3 = new PriorityQueue<>(List.of(3, 1, 2));
        System.out.println("Iteration order: " + pq3); // Not sorted!
        // To get sorted order, poll all elements
    }

    // Merge K sorted lists using PriorityQueue
    private static java.util.List<Integer> mergeKSortedLists(java.util.List<java.util.List<Integer>> lists) {
        PriorityQueue<java.util.Map.Entry<Integer, java.util.Map.Entry<Integer, Integer>>> pq =
                new PriorityQueue<>((a, b) -> a.getKey() - b.getKey());

        // Add first element of each list
        for (int i = 0; i < lists.size(); i++) {
            if (!lists.get(i).isEmpty()) {
                pq.offer(new java.util.AbstractMap.SimpleEntry<>(
                        lists.get(i).get(0),
                        new java.util.AbstractMap.SimpleEntry<>(i, 0)
                ));
            }
        }

        java.util.List<Integer> result = new java.util.ArrayList<>();
        while (!pq.isEmpty()) {
            java.util.Map.Entry<Integer, java.util.Map.Entry<Integer, Integer>> entry = pq.poll();
            result.add(entry.getKey());

            int listIndex = entry.getValue().getKey();
            int elementIndex = entry.getValue().getValue() + 1;

            if (elementIndex < lists.get(listIndex).size()) {
                pq.offer(new java.util.AbstractMap.SimpleEntry<>(
                        lists.get(listIndex).get(elementIndex),
                        new java.util.AbstractMap.SimpleEntry<>(listIndex, elementIndex)
                ));
            }
        }

        return result;
    }
}
