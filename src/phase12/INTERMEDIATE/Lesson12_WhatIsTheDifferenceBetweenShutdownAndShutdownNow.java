package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between shutdown() and shutdownNow()?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson12_WhatIsTheDifferenceBetweenShutdownAndShutdownNow {
    public static void main(String[] args) {
        System.out.println("=== SHUTDOWN() VS SHUTDOWNNOW() ===\n");
        System.out.println("shutdown():");
        System.out.println("  - Graceful shutdown");
        System.out.println("  - Waits for running tasks to complete");
        System.out.println("  - Rejects new tasks");
        System.out.println("  - Returns list of awaiting tasks");
        System.out.println();
        System.out.println("shutdownNow():");
        System.out.println("  - Immediate shutdown");
        System.out.println("  - Attempts to stop running tasks (interrupt)");
        System.out.println("  - Rejects new tasks");
        System.out.println("  - Returns list of tasks that never started");
        System.out.println();
        System.out.println("Use shutdown() for normal termination");
        System.out.println("Use shutdownNow() for emergency/cancellation");
    }
}
