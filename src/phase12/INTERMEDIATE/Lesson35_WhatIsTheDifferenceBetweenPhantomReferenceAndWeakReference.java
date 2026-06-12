package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between phantom and weak reference?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson35_WhatIsTheDifferenceBetweenPhantomReferenceAndWeakReference {
    public static void main(String[] args) {
        System.out.println("=== PHANTOM VS WEAK REFERENCE ===\n");
        System.out.println("Phantom Reference:");
        System.out.println("  - java.lang.ref.PhantomReference");
        System.out.println("  - Enqueued after finalization, before GC");
        System.out.println("  - Cannot get referent (get() returns null)");
        System.out.println("  - Used for cleanup scheduling");
        System.out.println("  - Requires ReferenceQueue");
        System.out.println();
        System.out.println("Weak Reference:");
        System.out.println("  - java.lang.ref.WeakReference");
        System.out.println("  - Can get referent (get() returns object or null)");
        System.out.println("  - Collected eagerly by GC");
        System.out.println("  - Used for caches, mappings");
        System.out.println();
        System.out.println("Phantom: for post-finalization cleanup");
        System.out.println("Weak: for memory-sensitive caching");
    }
}
