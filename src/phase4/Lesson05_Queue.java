package phase4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LESSON 5: Queue
 *
 * THEORY:
 * Queue is a FIFO (First In, First Out) data structure.
 * Elements are added at the tail and removed from the head.
 *
 * IMPLEMENTATIONS:
 * 1. LinkedList (implements Queue)
 * 2. PriorityQueue (heap-based, orders by priority)
 * 3. ArrayDeque (can be used as queue)
 *
 * INTERNAL WORKING:
 * - LinkedList: Doubly-linked list
 * - PriorityQueue: Binary heap (array-based)
 * - ArrayDeque: Resizable circular array
 *
 * TIME COMPLEXITY (LinkedList):
 * - offer(E e) - O(1)
 * - poll() - O(1)
 * - peek() - O(1)
 * - add(E e) - O(1)
 * - remove() - O(1)
 *
 * TIME COMPLEXITY (PriorityQueue):
 * - offer(E e) - O(log n)
 * - poll() - O(log n)
 * - peek() - O(1)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - offer(E e)     : Add to tail (returns false if full)
 * - add(E e)       : Add to tail (throws exception if full)
 * - poll()         : Remove and return head (null if empty)
 * - remove()       : Remove and return head (exception if empty)
 * - peek()         : View head (null if empty)
 * - element()      : View head (exception if empty)
 * - size()         : Number of elements
 * - isEmpty()      : Check if empty
 */

public class Lesson05_Queue {

    public static void main(String[] args) {
        System.out.println("=== LESSON 5: Queue ===\n");

        // ============================================
        // 1. CREATING QUEUE
        // ============================================
        System.out.println("--- 1. Creating Queue ---");

        // Using LinkedList
        Queue<String> queue1 = new LinkedList<>();
        System.out.println("Empty queue: " + queue1);

        // ============================================
        // 2. ENQUEUE (ADD)
        // ============================================
        System.out.println("\n--- 2. Enqueue (Add) ---");

        Queue<String> queue = new LinkedList<>();
        queue.offer("First");    // Add to tail - O(1)
        queue.offer("Second");
        queue.offer("Third");
        System.out.println("After offers: " + queue);
        System.out.println("Head: " + queue.peek());

        // add() vs offer()
        // add() throws IllegalStateException if capacity restricted
        // offer() returns false if capacity restricted
        // For LinkedList (unbounded), both work the same

        // ============================================
        // 3. DEQUEUE (REMOVE)
        // ============================================
        System.out.println("\n--- 3. Dequeue (Remove) ---");

        System.out.println("Poll: " + queue.poll());      // Remove head - O(1)
        System.out.println("After poll: " + queue);
        System.out.println("Poll: " + queue.poll());
        System.out.println("After poll: " + queue);

        // poll() vs remove()
        // poll() returns null if empty
        // remove() throws NoSuchElementException if empty
        Queue<String> emptyQueue = new LinkedList<>();
        String polled = emptyQueue.poll();  // Returns null
        System.out.println("Poll from empty: " + polled);

        // ============================================
        // 4. PEEK (VIEW HEAD)
        // ============================================
        System.out.println("\n--- 4. Peek (View Head) ---");

        queue.offer("A");
        queue.offer("B");
        System.out.println("Peek: " + queue.peek());    // View head without removing
        System.out.println("After peek: " + queue);     // Unchanged

        // peek() vs element()
        // peek() returns null if empty
        // element() throws NoSuchElementException if empty

        // ============================================
        // 5. QUEUE OPERATIONS
        // ============================================
        System.out.println("\n--- 5. Queue Operations ---");

        Queue<Integer> numbers = new LinkedList<>();
        numbers.offer(10);
        numbers.offer(20);
        numbers.offer(30);
        System.out.println("Queue: " + numbers);
        System.out.println("Size: " + numbers.size());
        System.out.println("Is empty: " + numbers.isEmpty());
        System.out.println("Contains 20: " + numbers.contains(20));

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Implement BFS (Breadth-First Search)
        // Simple tree traversal using queue
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4),
                        new TreeNode(5)),
                new TreeNode(3,
                        new TreeNode(6),
                        new TreeNode(7))
        );

        System.out.println("BFS traversal:");
        bfsTraversal(root);

        // Challenge: Generate binary numbers
        System.out.println("\nBinary numbers (1-10):");
        generateBinaryNumbers(10);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Using add() when you should use offer()
        // add() throws exception on capacity limit
        // offer() returns false (safer)

        // Mistake 2: Using remove() when you should use poll()
        // remove() throws exception on empty queue
        // poll() returns null (safer)

        // Mistake 3: Confusing Queue with Stack
        // Queue: FIFO (First In, First Out)
        // Stack: LIFO (Last In, First Out)
        // Use poll() for queue, pop() for stack
    }

    // BFS traversal using queue
    private static void bfsTraversal(TreeNode root) {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println("  " + node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    // Generate binary numbers using queue
    private static void generateBinaryNumbers(int n) {
        Queue<String> queue = new LinkedList<>();
        queue.offer("1");

        for (int i = 0; i < n; i++) {
            String current = queue.poll();
            System.out.println("  " + current);

            queue.offer(current + "0");
            queue.offer(current + "1");
        }
    }

    // Simple tree node
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
