package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between process and thread?
 *
 * Difficulty: BEGINNER
 */

public class Lesson44_WhatIsTheDifferenceBetweenProcessAndThread {
    public static void main(String[] args) {
        System.out.println("=== PROCESS VS THREAD ===\n");
        System.out.println("Process:");
        System.out.println("  - Independent execution unit");
        System.out.println("  - Own memory space (heap, stack)");
        System.out.println("  - Heavyweight (context switch expensive)");
        System.out.println("  - Inter-process communication is complex");
        System.out.println();
        System.out.println("Thread:");
        System.out.println("  - Lightweight execution unit within a process");
        System.out.println("  - Shares process memory space");
        System.out.println("  - Lightweight (context switch cheaper)");
        System.out.println("  - Thread communication is easier (shared memory)");
        System.out.println();
        System.out.println("A process can have multiple threads");
    }
}
