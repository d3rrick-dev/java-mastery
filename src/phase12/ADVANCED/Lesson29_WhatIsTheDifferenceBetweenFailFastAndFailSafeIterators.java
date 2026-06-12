package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between fail-fast and fail-safe iterators?
 *
 * Difficulty: ADVANCED
 */

public class Lesson29_WhatIsTheDifferenceBetweenFailFastAndFailSafeIterators {
    public static void main(String[] args) {
        System.out.println("=== FAIL-FAST VS FAIL-SAFE ITERATORS ===\n");
        System.out.println("Fail-Fast Iterator:");
        System.out.println("  - Throws ConcurrentModificationException on modification");
        System.out.println("  - Works on original collection (not copy)");
        System.out.println("  - Uses modCount to detect changes");
        System.out.println("  - Examples: ArrayList, HashMap, HashSet iterators");
        System.out.println("  - Fast but not thread-safe");
        System.out.println();
        System.out.println("Fail-Safe Iterator:");
        System.out.println("  - Does NOT throw exception on modification");
        System.out.println("  - Works on copy of collection or uses locking");
        System.out.println("  - May show stale data (from copy)");
        System.out.println("  - Examples: ConcurrentHashMap, CopyOnWriteArrayList");
        System.out.println("  - Thread-safe but may have overhead");
        System.out.println();
        System.out.println("How Fail-Fast Works:");
        System.out.println("  - Each collection has modCount field");
        System.out.println("  - Iterator captures expectedModCount at creation");
        System.out.println("  - On next(), checks if modCount == expectedModCount");
        System.out.println("  - If different, throws ConcurrentModificationException");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Fail-fast: detects concurrent modification, throws exception");
        System.out.println("  - Fail-safe: tolerates concurrent modification, may show stale data");
    }
}
