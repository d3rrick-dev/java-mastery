package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between yield() and join()?
 *
 * Difficulty: ADVANCED
 */

public class Lesson13_WhatIsTheDifferenceBetweenYieldAndJoin {
    public static void main(String[] args) {
        System.out.println("=== YIELD() VS JOIN() ===\n");
        System.out.println("yield():");
        System.out.println("  - Static method of Thread class");
        System.out.println("  - Hints to scheduler that current thread is willing to yield");
        System.out.println("  - Thread remains in RUNNABLE state");
        System.out.println("  - Scheduler may or may not honor the hint");
        System.out.println("  - No guarantee of when thread will resume");
        System.out.println();
        System.out.println("join():");
        System.out.println("  - Instance method of Thread class");
        System.out.println("  - Waits for thread to die (terminate)");
        System.out.println("  - Current thread blocks until target thread completes");
        System.out.println("  - Used to ensure thread completion");
        System.out.println("  - Can specify timeout: join(milliseconds)");
        System.out.println();
        System.out.println("Key Difference:");
        System.out.println("  - yield(): cooperative, thread gives up CPU voluntarily");
        System.out.println("  - join(): blocking, thread waits for another to finish");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - yield() is rarely used in practice");
        System.out.println("  - join() is commonly used for thread coordination");
    }
}
