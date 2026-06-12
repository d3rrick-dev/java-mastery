package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between strong, weak, soft, and phantom references?
 *
 * Difficulty: ADVANCED
 */

public class Lesson35_WhatIsTheDifferenceBetweenStrongReferenceAndWeakReference {
    public static void main(String[] args) {
        System.out.println("=== REFERENCE TYPES IN JAVA ===\n");
        System.out.println("Strong Reference:");
        System.out.println("  - Normal object reference (Object obj = new Object())");
        System.out.println("  - Prevents GC from collecting object");
        System.out.println("  - Object collected only when reference becomes null");
        System.out.println("  - Most common reference type");
        System.out.println();
        System.out.println("Weak Reference:");
        System.out.println("  - Created with WeakReference<T>");
        System.out.println("  - Does NOT prevent GC from collecting object");
        System.out.println("  - Object collected in next GC cycle if only weak refs exist");
        System.out.println("  - Used for canonicalizing mappings, memory-sensitive caches");
        System.out.println("  - Example: WeakReference<Object> ref = new WeakReference<>(obj)");
        System.out.println();
        System.out.println("Soft Reference:");
        System.out.println("  - Created with SoftReference<T>");
        System.out.println("  - Similar to weak but cleared only when memory is low");
        System.out.println("  - Useful for memory-sensitive caches");
        System.out.println("  - Cleared before OutOfMemoryError");
        System.out.println();
        System.out.println("Phantom Reference:");
        System.out.println("  - Created with PhantomReference<T>");
        System.out.println("  - Enqueued after finalization, before GC");
        System.out.println("  - Cannot get object reference (get() returns null)");
        System.out.println("  - Used for cleanup scheduling");
        System.out.println("  - Requires ReferenceQueue");
    }
}
