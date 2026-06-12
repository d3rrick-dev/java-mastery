package phase5;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LESSON 4: Stream with Queue
 *
 * THEORY:
 * Queues are used with Streams for FIFO processing patterns.
 * Streams can process Queue elements and convert between Queue and other collections.
 *
 * COMMON OPERATIONS:
 * - Processing queue elements
 * - BFS with queue and streams
 * - Level-order traversal
 */

public class Lesson04_StreamWithQueue {

    public static void main(String[] args) {
        System.out.println("=== LESSON 4: Stream with Queue ===\n");

        // ============================================
        // 1. QUEUE TO LIST
        // ============================================
        System.out.println("--- 1. Queue to List ---");

        Queue<String> queue = new LinkedList<>();
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");
        System.out.println("Queue: " + queue);

        // Convert to list
        java.util.List<String> list = queue.stream().toList();
        System.out.println("As list: " + list);

        // ============================================
        // 2. PROCESSING QUEUE ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Processing Queue Elements ---");

        Queue<Integer> numbers = new LinkedList<>();
        numbers.offer(10);
        numbers.offer(20);
        numbers.offer(30);
        numbers.offer(40);
        numbers.offer(50);

        // Process all elements
        int sum = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Sum: " + sum);

        // Filter even numbers
        java.util.List<Integer> evens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        System.out.println("Even numbers: " + evens);

        // ============================================
        // 3. BFS WITH QUEUE AND STREAMS
        // ============================================
        System.out.println("\n--- 3. BFS with Queue and Streams ---");

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

        // ============================================
        // 4. LEVEL ORDER TRAVERSAL
        // ============================================
        System.out.println("\n--- 4. Level Order Traversal ---");

        java.util.Map<Integer, java.util.List<Integer>> levelOrder = new java.util.HashMap<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            java.util.List<Integer> currentLevel = new java.util.ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                currentLevel.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            levelOrder.put(level++, currentLevel);
        }

        System.out.println("Level order:");
        levelOrder.forEach((lvl, nodes) ->
                System.out.println("  Level " + lvl + ": " + nodes));

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Find minimum depth of binary tree
        System.out.println("Minimum depth: " + minDepth(root));

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Modifying queue while streaming
        Queue<String> q2 = new LinkedList<>(java.util.List.of("A", "B", "C"));
        try {
            q2.stream()
                    .forEach(s -> {
                        if (s.equals("B")) {
                            q2.remove(s); // ConcurrentModificationException!
                        }
                    });
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName());
        }

        // Mistake 2: Using stream when queue operations are more appropriate
        // For BFS, use queue directly, not streams
    }

    // BFS traversal
    private static void bfsTraversal(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println("  " + node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    // Minimum depth of binary tree
    private static int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            depth++;
        }
        return depth;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
