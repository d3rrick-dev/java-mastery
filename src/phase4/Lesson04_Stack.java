package phase4;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * LESSON 4: Stack
 *
 * THEORY:
 * Stack is a LIFO (Last In, First Out) data structure.
 * The last element added is the first one removed.
 *
 * IMPLEMENTATIONS:
 * 1. java.util.Stack (legacy, extends Vector - synchronized)
 * 2. Deque (ArrayDeque) - preferred, faster, not synchronized
 *
 * INTERNAL WORKING:
 * - Array-based (Stack) or doubly-linked (ArrayDeque)
 * - Top pointer tracks the top element
 *
 * TIME COMPLEXITY (ArrayDeque):
 * - push(E e) - O(1)
 * - pop() - O(1)
 * - peek() - O(1)
 * - search(Object o) - O(n)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - push(E e)     : Add to top
 * - pop()         : Remove and return top
 * - peek()        : View top without removing
 * - empty()       : Check if empty
 * - search(Object): Get 1-based position from top
 */

public class Lesson04_Stack {

    public static void main(String[] args) {
        System.out.println("=== LESSON 4: Stack ===\n");

        // ============================================
        // 1. CREATING STACK
        // ============================================
        System.out.println("--- 1. Creating Stack ---");

        // Using ArrayDeque (preferred)
        Deque<String> stack = new ArrayDeque<>();
        System.out.println("Empty stack: " + stack);

        // ============================================
        // 2. PUSH OPERATIONS
        // ============================================
        System.out.println("\n--- 2. Push Operations ---");

        stack.push("First");    // Add to top
        stack.push("Second");
        stack.push("Third");
        System.out.println("After pushes: " + stack);
        System.out.println("Top element: " + stack.peek());

        // ============================================
        // 3. POP OPERATIONS
        // ============================================
        System.out.println("\n--- 3. Pop Operations ---");

        System.out.println("Pop: " + stack.pop());      // Remove top
        System.out.println("After pop: " + stack);
        System.out.println("Pop: " + stack.pop());
        System.out.println("After pop: " + stack);

        // ============================================
        // 4. PEEK OPERATION
        // ============================================
        System.out.println("\n--- 4. Peek Operation ---");

        stack.push("A");
        stack.push("B");
        System.out.println("Peek: " + stack.peek());    // View top without removing
        System.out.println("After peek: " + stack);     // Unchanged

        // ============================================
        // 5. CHECK IF EMPTY
        // ============================================
        System.out.println("\n--- 5. Check Empty ---");

        System.out.println("Is empty: " + stack.isEmpty());
        stack.pop();
        stack.pop();
        System.out.println("After popping all, is empty: " + stack.isEmpty());

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Check if parentheses are balanced
        String expr1 = "((()))";
        String expr2 = "(()";
        String expr3 = "())(";

        System.out.println(expr1 + " balanced: " + isBalanced(expr1));
        System.out.println(expr2 + " balanced: " + isBalanced(expr2));
        System.out.println(expr3 + " balanced: " + isBalanced(expr3));

        // Challenge: Reverse a string using stack
        String original = "Hello World";
        String reversed = reverseString(original);
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Using legacy Stack class
        Stack<String> legacyStack = new Stack<>();
        legacyStack.push("A");
        System.out.println("Legacy stack: " + legacyStack);
        // Stack extends Vector (synchronized, slower)
        // Use ArrayDeque instead

        // Mistake 2: EmptyStackException
        Deque<String> emptyStack = new ArrayDeque<>();
        try {
            emptyStack.pop();  // Throws NoSuchElementException!
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName());
        }

        // Better: Check before popping
        if (!emptyStack.isEmpty()) {
            emptyStack.pop();
        }

        // Mistake 3: Confusing push() and add()
        // push() adds to front (top of stack)
        // add() adds to end (bottom of deque when used as stack)
        Deque<String> s = new ArrayDeque<>();
        s.push("A");
        s.add("B");
        System.out.println("After push A, add B: " + s);
        System.out.println("Pop: " + s.pop());  // Returns "A" (top)
    }

    // Check if parentheses are balanced
    private static boolean isBalanced(String expr) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : expr.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    // Reverse string using stack
    private static String reverseString(String str) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : str.toCharArray()) {
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }
}
