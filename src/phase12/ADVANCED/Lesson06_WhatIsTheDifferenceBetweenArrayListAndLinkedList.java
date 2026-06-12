package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between ArrayList and LinkedList?
 *
 * Difficulty: ADVANCED
 */

public class Lesson06_WhatIsTheDifferenceBetweenArrayListAndLinkedList {
    public static void main(String[] args) {
        System.out.println("=== ARRAYLIST VS LINKEDLIST ===\n");
        System.out.println("ArrayList:");
        System.out.println("  - Backed by dynamic array");
        System.out.println("  - Random access: O(1)");
        System.out.println("  - Insertion/deletion in middle: O(n)");
        System.out.println("  - Better for read-heavy operations");
        System.out.println("  - Less memory overhead");
        System.out.println();
        System.out.println("LinkedList:");
        System.out.println("  - Doubly-linked list");
        System.out.println("  - Random access: O(n)");
        System.out.println("  - Insertion/deletion at ends: O(1)");
        System.out.println("  - Better for write-heavy operations");
        System.out.println("  - More memory overhead (node objects)");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Always ask: What operations will be most frequent?");
        System.out.println("  - ArrayList is default choice for most scenarios");
    }
}
