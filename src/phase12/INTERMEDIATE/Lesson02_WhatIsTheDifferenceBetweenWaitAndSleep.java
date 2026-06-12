package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between wait() and sleep()?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson02_WhatIsTheDifferenceBetweenWaitAndSleep {
    public static void main(String[] args) {
        System.out.println("=== WAIT() VS SLEEP() ===\n");
        System.out.println("wait():");
        System.out.println("  - Releases the lock on the object");
        System.out.println("  - Must be called from synchronized block/method");
        System.out.println("  - Object method (not Thread method)");
        System.out.println("  - Used for inter-thread communication");
        System.out.println("  - Wakes up when notified or timeout");
        System.out.println();
        System.out.println("sleep():");
        System.out.println("  - Does NOT release the lock");
        System.out.println("  - Can be called from anywhere");
        System.out.println("  - Thread static method");
        System.out.println("  - Used to pause execution for specified time");
        System.out.println("  - Wakes up after timeout or interrupt");
    }
}
