package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between submit() and execute() in ExecutorService?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson11_WhatIsTheDifferenceBetweenSubmitAndExecuteInExecutorService {
    public static void main(String[] args) {
        System.out.println("=== SUBMIT() VS EXECUTE() ===\n");
        System.out.println("execute(Runnable):");
        System.out.println("  - From Executor interface");
        System.out.println("  - Returns void");
        System.out.println("  - Cannot get result or handle exceptions");
        System.out.println("  - Exceptions go to uncaught exception handler");
        System.out.println();
        System.out.println("submit(Runnable/Callable):");
        System.out.println("  - From ExecutorService interface");
        System.out.println("  - Returns Future<?> or Future<T>");
        System.out.println("  - Can check completion with isDone()");
        System.out.println("  - Can get result with get()");
        System.out.println("  - Exceptions captured in Future");
        System.out.println();
        System.out.println("Use execute() for fire-and-forget");
        System.out.println("Use submit() when you need result tracking");
    }
}
