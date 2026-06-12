package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between while and do-while?
 *
 * Difficulty: BEGINNER
 */

public class Lesson27_WhatIsTheDifferenceBetweenWhileAndDoWhile {
    public static void main(String[] args) {
        System.out.println("=== WHILE VS DO-WHILE ===\n");
        System.out.println("while: Entry-controlled, condition checked before loop body");
        System.out.println("do-while: Exit-controlled, condition checked after loop body");
        System.out.println("do-while always executes at least once");
        System.out.println();
        System.out.println("while (condition) { }");
        System.out.println("do { } while (condition);  // Note semicolon!");
    }
}
