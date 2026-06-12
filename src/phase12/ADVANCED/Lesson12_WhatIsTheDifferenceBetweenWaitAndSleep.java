package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between wait() and sleep()?
 *
 * Difficulty: ADVANCED
 */

public class Lesson12_WhatIsTheDifferenceBetweenWaitAndSleep {
    public static void main(String[] args) {
        System.out.println("=== WAIT() VS SLEEP() ===\n");
        System.out.println("wait():");
        System.out.println("  - Method of Object class");
        System.out.println("  - Releases the lock on the object");
        System.out.println("  - Must be called from synchronized block/method");
        System.out.println("  - Used for inter-thread communication");
        System.out.println("  - Thread waits until notified or timeout");
        System.out.println();
        System.out.println("sleep():");
        System.out.println("  - Static method of Thread class");
        System.out.println("  - Does NOT release the lock");
        System.out.println("  - Can be called from anywhere");
        System.out.println("  - Used to pause execution for specified time");
        System.out.println("  - Thread wakes up after timeout automatically");
        System.out.println();
        System.out.println("Key Difference:");
        System.out.println("  - wait(): releases lock, used for coordination");
        System.out.println("  - sleep(): keeps lock, used for timing");
        System.out.println();
        System.out.println("Interview Follow-up:");
        System.out.println("  - What happens if you call wait() outside synchronized block?");
        System.out.println("  - Answer: IllegalMonitorStateException");
    }
}
