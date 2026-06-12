package phase11;

import java.lang.ref.*;
import java.util.*;

/**
 * LESSON 7: STRONG, WEAK, SOFT, AND PHANTOM REFERENCES
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Java has 4 reference types with different GC behaviors:
 * - Strong: Normal references, never collected
 * - Weak: Collected when no strong refs
 * - Soft: Collected when memory low
 * - Phantom: Collected after finalization
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Fine-grained control over object lifecycle
 * - Caching strategies
 * - Resource cleanup
 */

public class Lesson07_StrongWeakSoftPhantomReferences {

    public static void main(String[] args) {
        System.out.println("=== REFERENCE TYPES ===\n");

        // ============================================================
        // EXAMPLE 1: Strong Reference
        // ============================================================
        System.out.println("--- Example 1: Strong Reference ---\n");

        String strong = new String("strong");
        System.out.println("Strong reference: " + strong);
        System.out.println("GC will NOT collect while 'strong' variable exists");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Weak Reference
        // ============================================================
        System.out.println("--- Example 2: Weak Reference ---\n");

        WeakReference<String> weak = new WeakReference<>(new String("weak"));
        System.out.println("Weak reference created: " + weak.get());

        // Suggest GC
        System.gc();
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        System.out.println("After GC: " + weak.get());  // Likely null
        System.out.println("Weak references collected when no strong refs");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Soft Reference
        // ============================================================
        System.out.println("--- Example 3: Soft Reference ---\n");

        SoftReference<String> soft = new SoftReference<>(new String("soft"));
        System.out.println("Soft reference created: " + soft.get());
        System.out.println("Soft references collected when memory is low");
        System.out.println("Good for: memory-sensitive caches");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Phantom Reference
        // ============================================================
        System.out.println("--- Example 4: Phantom Reference ---\n");

        ReferenceQueue<String> queue = new ReferenceQueue<>();
        PhantomReference<String> phantom = new PhantomReference<>(
            new String("phantom"), queue
        );

        System.out.println("Phantom reference created");
        System.out.println("phantom.get() always returns null");
        System.out.println("Added to ReferenceQueue after finalization");
        System.out.println("Used for: cleanup after object death");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: ReferenceQueue usage
        // ============================================================
        System.out.println("--- Example 5: ReferenceQueue ---\n");

        ReferenceQueue<Object> refQueue = new ReferenceQueue<>();
        Map<String, WeakReference<Object>> cache = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            String key = "key" + i;
            WeakReference<Object> ref = new WeakReference<>(
                new Object(), refQueue
            );
            cache.put(key, ref);
        }

        System.out.println("Created 5 weak references in cache");
        System.out.println("When GC runs, cleared refs go to queue");
        System.out.println("Process queue to clean up cache entries");
        System.out.println();
    }

    // ============================================================
    // REFERENCE TYPES DETAILS
    // ============================================================
    /*
     * Reference Types:
     *
     * 1. Strong Reference (default)
     *    - Object obj = new Object();
     *    - Never collected by GC
     *    - Prevents GC
     *
     * 2. Weak Reference
     *    - WeakReference<T> ref = new WeakReference<>(obj);
     *    - Collected when no strong refs
     *    - Use: WeakHashMap, canonicalizing mappings
     *
     * 3. Soft Reference
     *    - SoftReference<T> ref = new SoftReference<>(obj);
     *    - Collected when memory low (before OOM)
     *    - Use: Memory-sensitive caches
     *
     * 4. Phantom Reference
     *    - PhantomReference<T> ref = new PhantomReference<>(obj, queue);
     *    - get() always returns null
     *    - Enqueued after finalization
     *    - Use: Cleanup without preventing GC
     *
     * ReferenceQueue:
     * - Receives references when object is collected
     * - Used to clean up associated resources
     */
}
