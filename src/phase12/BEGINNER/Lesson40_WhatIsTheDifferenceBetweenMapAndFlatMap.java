package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between map() and flatMap()?
 *
 * Difficulty: BEGINNER
 */

public class Lesson40_WhatIsTheDifferenceBetweenMapAndFlatMap {
    public static void main(String[] args) {
        System.out.println("=== MAP() VS FLATMAP() ===\n");
        System.out.println("map():");
        System.out.println("  - Transforms each element to ONE result");
        System.out.println("  - 1-to-1 mapping");
        System.out.println("  - Stream<T> -> Stream<R>");
        System.out.println();
        System.out.println("flatMap():");
        System.out.println("  - Transforms each element to a STREAM, then flattens");
        System.out.println("  - 1-to-many mapping");
        System.out.println("  - Stream<T> -> Stream<R> (flattened)");
        System.out.println();
        System.out.println("Example: List<List<String>> -> flatMap gives List<String>");
    }
}
