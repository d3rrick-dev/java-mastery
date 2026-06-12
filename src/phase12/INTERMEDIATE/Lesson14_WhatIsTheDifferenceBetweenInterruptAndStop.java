package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between interrupt() and stop()?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson14_WhatIsTheDifferenceBetweenInterruptAndStop {
    public static void main(String[] args) {
        System.out.println("=== INTERRUPT() VS STOP() ===\n");
        System.out.println("interrupt():");
        System.out.println("  - Sets interrupt flag on thread");
        System.out.println("  - Cooperative: thread checks and responds");
        System.out.println("  - Safe: allows cleanup");
        System.out.println("  - Throws InterruptedException in blocking methods");
        System.out.println();
        System.out.println("stop():");
        System.out.println("  - Deprecated (unsafe)");
        System.out.println("  - Terminates thread immediately");
        System.out.println("  - Releases all locks (can cause corruption)");
        System.out.println("  - No cleanup possible");
        System.out.println();
        System.out.println("NEVER use stop(). Use interrupt() for thread termination.");
    }
}
