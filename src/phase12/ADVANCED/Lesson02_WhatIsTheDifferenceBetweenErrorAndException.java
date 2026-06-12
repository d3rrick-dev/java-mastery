package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Error and Exception?
 *
 * Difficulty: ADVANCED
 */

public class Lesson02_WhatIsTheDifferenceBetweenErrorAndException {
    public static void main(String[] args) {
        System.out.println("=== ERROR VS EXCEPTION ===\n");
        System.out.println("Error:");
        System.out.println("  - Serious problems that applications should NOT catch");
        System.out.println("  - Extend java.lang.Error");
        System.out.println("  - Indicate JVM/resource problems");
        System.out.println("  - Examples: OutOfMemoryError, StackOverflowError, VirtualMachineError");
        System.out.println();
        System.out.println("Exception:");
        System.out.println("  - Conditions that applications might want to catch");
        System.out.println("  - Extend java.lang.Exception");
        System.out.println("  - Indicate recoverable conditions");
        System.out.println("  - Examples: IOException, SQLException, NullPointerException");
        System.out.println();
        System.out.println("Both extend Throwable, but Errors are generally unrecoverable");
    }
}
