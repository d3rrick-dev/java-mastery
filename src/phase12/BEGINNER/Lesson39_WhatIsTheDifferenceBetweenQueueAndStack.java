package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between Queue and Stack?
 *
 * Difficulty: BEGINNER
 */

public class Lesson39_WhatIsTheDifferenceBetweenQueueAndStack {
    public static void main(String[] args) {
        System.out.println("=== QUEUE VS STACK ===\n");
        System.out.println("Queue (FIFO - First In First Out):");
        System.out.println("  - Elements added at rear, removed from front");
        System.out.println("  - Like a line at a store");
        System.out.println("  - Operations: offer(), poll(), peek()");
        System.out.println();
        System.out.println("Stack (LIFO - Last In First Out):");
        System.out.println("  - Elements added/removed from same end (top)");
        System.out.println("  - Like a stack of plates");
        System.out.println("  - Operations: push(), pop(), peek()");
        System.out.println();
        System.out.println("Java Stack class is legacy, use Deque for stack operations");
    }
}
