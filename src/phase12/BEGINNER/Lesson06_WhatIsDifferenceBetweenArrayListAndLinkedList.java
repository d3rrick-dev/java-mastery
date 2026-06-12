package phase12.BEGINNER;

import java.util.*;

/**
 * INTERVIEW QUESTION: Difference between ArrayList and LinkedList?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests knowledge of List implementations
 * - Shows understanding of data structures
 * - Reveals performance awareness
 */

public class Lesson06_WhatIsDifferenceBetweenArrayListAndLinkedList {

    public static void main(String[] args) {
        System.out.println("=== ARRAYLIST VS LINKEDLIST ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between ArrayList and LinkedList?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("1. INTERNAL STRUCTURE:");
        System.out.println("   - ArrayList: Dynamic array (Object[])");
        System.out.println("   - LinkedList: Doubly-linked list");
        System.out.println();

        System.out.println("2. RANDOM ACCESS:");
        System.out.println("   - ArrayList: O(1) - direct index access");
        System.out.println("   - LinkedList: O(n) - must traverse");
        System.out.println();

        System.out.println("3. INSERTION/DELETION:");
        System.out.println("   - ArrayList: O(n) - shift elements");
        System.out.println("   - LinkedList: O(1) at ends, O(n) in middle");
        System.out.println();

        System.out.println("4. MEMORY:");
        System.out.println("   - ArrayList: Less memory (contiguous array)");
        System.out.println("   - LinkedList: More memory (node objects + pointers)");
        System.out.println();

        System.out.println("5. USE CASES:");
        System.out.println("   - ArrayList: Frequent reads, random access");
        System.out.println("   - LinkedList: Frequent inserts/deletes at ends");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // Add elements
        for (int i = 0; i < 1000; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        // Random access
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arrayList.get(i);
        }
        long arrayListTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            linkedList.get(i);
        }
        long linkedListTime = System.nanoTime() - start;

        System.out.println("Random access (1000 gets):");
        System.out.println("  ArrayList: " + arrayListTime / 1_000_000 + " ms");
        System.out.println("  LinkedList: " + linkedListTime / 1_000_000 + " ms");
        System.out.println("  ArrayList is much faster for random access!");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using LinkedList for random access:");
        System.out.println("   - Very slow O(n) per access");
        System.out.println("   - Use ArrayList instead");
        System.out.println();

        System.out.println("2. Using ArrayList for frequent inserts:");
        System.out.println("   - O(n) for middle inserts");
        System.out.println("   - Consider LinkedList for queue operations");
        System.out.println();

        System.out.println("3. Not knowing LinkedList implements Deque:");
        System.out.println("   - Can use as queue/stack");
        System.out.println("   - addFirst(), removeLast(), etc.");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. When would you choose LinkedList over ArrayList?");
        System.out.println("2. What is the initial capacity of ArrayList?");
        System.out.println("3. How does ArrayList grow?");
        System.out.println("4. What is the difference between add() and offer() in LinkedList?");
        System.out.println("5. Is LinkedList thread-safe?");
    }
}
