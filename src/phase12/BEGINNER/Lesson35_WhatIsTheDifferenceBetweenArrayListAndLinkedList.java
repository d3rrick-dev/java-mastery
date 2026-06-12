package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between ArrayList and LinkedList?
 *
 * Difficulty: BEGINNER
 */

public class Lesson35_WhatIsTheDifferenceBetweenArrayListAndLinkedList {
    public static void main(String[] args) {
        System.out.println("=== ARRAYLIST VS LINKEDLIST ===\n");
        System.out.println("ArrayList:");
        System.out.println("  - Backed by dynamic array");
        System.out.println("  - Random access: O(1)");
        System.out.println("  - Insert/delete middle: O(n)");
        System.out.println("  - Better for read-heavy workloads");
        System.out.println();
        System.out.println("LinkedList:");
        System.out.println("  - Doubly-linked list");
        System.out.println("  - Random access: O(n)");
        System.out.println("  - Insert/delete: O(1) at ends, O(n) with traversal");
        System.out.println("  - Better for insert/delete-heavy workloads");
        System.out.println();
        System.out.println("ArrayList is preferred in most cases due to cache locality");
    }
}
