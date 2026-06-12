package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between soft and weak reference?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson34_WhatIsTheDifferenceBetweenSoftAndWeakReference {
    public static void main(String[] args) {
        System.out.println("=== SOFT VS WEAK REFERENCE ===\n");
        System.out.println("Soft Reference:");
        System.out.println("  - java.lang.ref.SoftReference");
        System.out.println("  - GC collects only when memory is low");
        System.out.println("  - Survives minor GCs");
        System.out.println("  - Used for memory-sensitive caches");
        System.out.println();
        System.out.println("Weak Reference:");
        System.out.println("  - java.lang.ref.WeakReference");
        System.out.println("  - GC collects eagerly (next GC cycle)");
        System.out.println("  - Does NOT prevent collection");
        System.out.println("  - Used for canonicalizing mappings");
        System.out.println();
        System.out.println("Soft: cleared only if memory needed");
        System.out.println("Weak: cleared eagerly");
    }
}
