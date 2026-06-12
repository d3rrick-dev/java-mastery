package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between process and thread?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of concurrency basics
 * - Shows knowledge of OS concepts
 * - Reveals awareness of Java threading model
 */

public class Lesson25_WhatIsTheDifferenceBetweenProcessAndThread {

    public static void main(String[] args) {
        System.out.println("=== PROCESS VS THREAD ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between process and thread?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("PROCESS:");
        System.out.println("  - Independent execution unit");
        System.out.println("  - Has its own memory space (heap, stack)");
        System.out.println("  - Heavyweight (more resources)");
        System.out.println("  - Inter-process communication is slow");
        System.out.println("  - Isolated from other processes");
        System.out.println();

        System.out.println("THREAD:");
        System.out.println("  - Lightweight execution unit within process");
        System.out.println("  - Shares memory with other threads in same process");
        System.out.println("  - Lightweight (less resources)");
        System.out.println("  - Inter-thread communication is fast");
        System.out.println("  - Can directly access process data");
        System.out.println();

        System.out.println("KEY DIFFERENCES:");
        System.out.println("  1. Memory: Process has own, Threads share");
        System.out.println("  2. Weight: Process heavy, Thread lightweight");
        System.out.println("  3. Communication: Process slow (IPC), Thread fast (shared memory)");
        System.out.println("  4. Isolation: Process isolated, Threads not");
        System.out.println("  5. Creation: Process slow, Thread fast");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        System.out.println("Process example (running two Java programs):");
        System.out.println("  java Program1  // Process 1");
        System.out.println("  java Program2  // Process 2");
        System.out.println("  // Each has own memory, cannot share directly");
        System.out.println();

        System.out.println("Thread example (within one Java program):");
        System.out.println("  public class MyApp {");
        System.out.println("    public static void main(String[] args) {");
        System.out.println("      Thread t1 = new Thread(() -> { /* task 1 */ });");
        System.out.println("      Thread t2 = new Thread(() -> { /* task 2 */ });");
        System.out.println("      t1.start(); t2.start();  // Same process, shared memory");
        System.out.println("    }");
        System.out.println("  }");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Thinking threads are always faster:");
        System.out.println("   - Threads have overhead (context switching)");
        System.out.println("   - Not always faster than sequential");
        System.out.println();

        System.out.println("2. Not knowing Java is multi-threaded:");
        System.out.println("   - JVM has multiple threads (GC, finalizer, etc.)");
        System.out.println("   - main() runs in main thread");
        System.out.println();

        System.out.println("3. Confusing process and thread in interviews:");
        System.out.println("   - Be clear about memory model differences");
        System.out.println("   - Mention context switching");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is context switching?");
        System.out.println("2. What is a multi-threaded process?");
        System.out.println("3. How do threads communicate?");
        System.out.println("4. What is thread safety?");
        System.out.println("5. What is the difference between concurrency and parallelism?");
    }
}
