package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between notify() and notifyAll()?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson04_WhatIsTheDifferenceBetweenNotifyAndNotifyAll {
    public static void main(String[] args) {
        System.out.println("=== NOTIFY() VS NOTIFYALL() ===\n");
        System.out.println("notify():");
        System.out.println("  - Wakes up ONE waiting thread (arbitrary)");
        System.out.println("  - Used when all waiting threads are identical");
        System.out.println("  - Risk: May wake wrong thread, causing deadlock");
        System.out.println();
        System.out.println("notifyAll():");
        System.out.println("  - Wakes up ALL waiting threads");
        System.out.println("  - Each thread competes for the lock");
        System.out.println("  - Safer: All threads get chance to run");
        System.out.println("  - Preferred in most cases");
        System.out.println();
        System.out.println("Use notify() only when you know exactly which thread to wake");
        System.out.println("Use notifyAll() as default (safer)");
    }
}
