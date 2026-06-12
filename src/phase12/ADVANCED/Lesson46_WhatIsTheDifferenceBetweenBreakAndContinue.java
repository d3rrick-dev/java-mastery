package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between break and continue?
 *
 * Difficulty: ADVANCED
 */

public class Lesson46_WhatIsTheDifferenceBetweenBreakAndContinue {
    public static void main(String[] args) {
        System.out.println("=== BREAK VS CONTINUE ===\n");
        System.out.println("break:");
        System.out.println("  - Terminates the loop or switch statement");
        System.out.println("  - Control exits the innermost loop/switch");
        System.out.println("  - No further iterations execute");
        System.out.println("  - Example: for(int i=0; i<10; i++) { if(i==5) break; }");
        System.out.println();
        System.out.println("continue:");
        System.out.println("  - Skips current iteration, continues with next");
        System.out.println("  - Control goes to loop condition check");
        System.out.println("  - Remaining iterations still execute");
        System.out.println("  - Example: for(int i=0; i<10; i++) { if(i==5) continue; }");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - break: exits loop completely");
        System.out.println("  - continue: skips current iteration only");
        System.out.println("  - break: no more iterations");
        System.out.println("  - continue: next iteration still happens");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - break: 'stop everything'");
        System.out.println("  - continue: 'skip this one, keep going'");
    }
}
