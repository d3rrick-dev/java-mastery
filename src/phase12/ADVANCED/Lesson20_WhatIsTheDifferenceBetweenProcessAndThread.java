package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between process and thread?
 *
 * Difficulty: ADVANCED
 */

public class Lesson20_WhatIsTheDifferenceBetweenProcessAndThread {
    public static void main(String[] args) {
        System.out.println("=== PROCESS VS THREAD ===\n");
        System.out.println("Process:");
        System.out.println("  - Independent execution unit");
        System.out.println("  - Has its own memory space (heap, stack)");
        System.out.println("  - Heavyweight (context switch expensive)");
        System.out.println("  - Inter-process communication is complex");
        System.out.println("  - Isolated from other processes");
        System.out.println();
        System.out.println("Thread:");
        System.out.println("  - Lightweight execution unit within a process");
        System.out.println("  - Shares process memory space with other threads");
        System.out.println("  - Lightweight (context switch cheaper)");
        System.out.println("  - Inter-thread communication is easy (shared memory)");
        System.out.println("  - Can directly access process data");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Memory: Process has own memory, Thread shares memory");
        System.out.println("  - Creation: Process creation is slow, Thread creation is fast");
        System.out.println("  - Communication: IPC vs shared variables");
        System.out.println("  - Isolation: Process is isolated, Thread is not");
        System.out.println("  - Overhead: Process has high overhead, Thread has low overhead");
    }
}
