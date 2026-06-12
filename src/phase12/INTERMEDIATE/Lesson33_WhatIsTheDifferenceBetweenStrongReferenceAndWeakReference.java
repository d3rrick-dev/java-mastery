package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between strong and weak references?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson33_WhatIsTheDifferenceBetweenStrongReferenceAndWeakReference {
    public static void main(String[] args) {
        System.out.println("=== STRONG VS WEAK REFERENCE ===\n");
        System.out.println("Strong Reference:");
        System.out.println("  - Normal object reference (Object obj = new Object())");
        System.out.println("  - Prevents GC from collecting object");
        System.out.println("  - Object stays in memory as long as reference exists");
        System.out.println();
        System.out.println("Weak Reference:");
        System.out.println("  - java.lang.ref.WeakReference");
        System.out.println("  - Does NOT prevent GC from collecting object");
        System.out.println("  - Object can be collected if only weak references exist");
        System.out.println("  - Used for caches, canonicalizing mappings");
        System.out.println();
        System.out.println("WeakReference ref = new WeakReference<>(obj);");
    }
}
