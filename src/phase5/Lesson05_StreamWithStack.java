package phase5;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LESSON 5: Stream with Stack
 *
 * THEORY:
 * Stacks are used with Streams for LIFO processing patterns.
 * Streams can process Stack elements and convert between Stack and other collections.
 *
 * COMMON OPERATIONS:
 * - Processing stack elements
 * - DFS with stack and streams
 * - Reversing sequences
 */

public class Lesson05_StreamWithStack {

    public static void main(String[] args) {
        System.out.println("=== LESSON 5: Stream with Stack ===\n");

        // ============================================
        // 1. STACK TO LIST
        // ============================================
        System.out.println("--- 1. Stack to List ---");

        Deque<String> stack = new ArrayDeque<>();
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        System.out.println("Stack: " + stack);

        // Convert to list (reverses order)
        java.util.List<String> list = stack.stream().toList();
        System.out.println("As list: " + list);

        // ============================================
        // 2. PROCESSING STACK ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Processing Stack Elements ---");

        Deque<Integer> numberStack = new ArrayDeque<>();
        numberStack.push(10);
        numberStack.push(20);
        numberStack.push(30);
        numberStack.push(40);
        numberStack.push(50);

        // Process all elements
        int sum = numberStack.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Sum: " + sum);

        // Filter even numbers
        java.util.List<Integer> evens = numberStack.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        System.out.println("Even numbers: " + evens);

        // ============================================
        // 3. DFS WITH STACK AND STREAMS
        // ============================================
        System.out.println("\n--- 3. DFS with Stack and Streams ---");

        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4),
                        new TreeNode(5)),
                new TreeNode(3,
                        new TreeNode(6),
                        new TreeNode(7))
        );

        System.out.println("DFS traversal (pre-order):");
        dfsTraversal(root);

        // ============================================
        // 4. REVERSING WITH STACK
        // ============================================
        System.out.println("\n--- 4. Reversing with Stack ---");

        String original = "Hello World";
        Deque<Character> charStack = new ArrayDeque<>();
        for (char c : original.toCharArray()) {
            charStack.push(c);
        }

        StringBuilder reversed = new StringBuilder();
        while (!charStack.isEmpty()) {
            reversed.append(charStack.pop());
        }
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Check if string is palindrome using stack
        String palindrome = "racecar";
        String notPalindrome = "hello";
        System.out.println(palindrome + " is palindrome: " + isPalindrome(palindrome));
        System.out.println(notPalindrome + " is palindrome: " + isPalindrome(notPalindrome));

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Confusing push/pop order
        Deque<String> s = new ArrayDeque<>();
        s.push("A");
        s.push("B");
        s.push("C");
        System.out.println("Pop order: " + s.pop() + ", " + s.pop() + ", " + s.pop());
        // Returns C, B, A (LIFO)

        // Mistake 2: Using stream when stack operations are more appropriate
        // For DFS, use stack directly, not streams
    }

    // DFS traversal (pre-order)
    private static void dfsTraversal(TreeNode root) {
        if (root == null) return;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.println("  " + node.val);
            // Push right first, then left (so left is processed first)
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
    }

    // Check palindrome using stack
    private static boolean isPalindrome(String str) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : str.toCharArray()) {
            stack.push(c);
        }
        for (char c : str.toCharArray()) {
            if (c != stack.pop()) {
                return false;
            }
        }
        return true;
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
