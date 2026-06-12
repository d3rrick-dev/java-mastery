package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between yield() and join()?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson03_WhatIsTheDifferenceBetweenYieldAndJoin {
    public static void main(String[] args) {
        System.out.println("=== YIELD() VS JOIN() ===\n");
        System.out.println("yield():");
        System.out.println("  - Thread hints to scheduler it can pause");
        System.out.println("  - Thread remains in RUNNABLE state");
        System.out.println("  - Scheduler may or may not honor it");
        System.out.println("  - Used for cooperative multitasking");
        System.out.println();
        System.out.println("join():");
        System.out.println("  - Waits for another thread to die");
        System.out.println("  - Current thread blocks until target thread finishes");
        System.out.println("  - Used to ensure thread completion");
        System.out.println("  - Can specify timeout");
        System.out.println();
        System.out.println("yield() is a hint, join() is a guarantee (with timeout)");
    }
}
