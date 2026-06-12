package phase10;

import java.util.*;

/**
 * LESSON 5: LINKEDHASHMAP INTERNALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * LinkedHashMap extends HashMap with a doubly-linked list
 * that maintains insertion order (or access order).
 * Like a notebook with numbered pages - you know the order.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Maintains insertion order
 * - Predictable iteration order
 * - Can implement LRU cache with accessOrder=true
 */

public class Lesson05_LinkedHashMapInternals {

    public static void main(String[] args) {
        System.out.println("=== LINKEDHASHMAP INTERNALS ===\n");

        // ============================================================
        // EXAMPLE 1: Insertion order
        // ============================================================
        System.out.println("--- Example 1: Insertion Order ---\n");

        Map<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("third", 3);
        linkedMap.put("first", 1);
        linkedMap.put("second", 2);

        System.out.println("LinkedHashMap (insertion order):");
        linkedMap.forEach((k, v) -> System.out.println("  " + k + " -> " + v));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: vs HashMap (no order)
        // ============================================================
        System.out.println("--- Example 2: vs HashMap ---\n");

        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("third", 3);
        hashMap.put("first", 1);
        hashMap.put("second", 2);

        System.out.println("HashMap (no guaranteed order):");
        hashMap.forEach((k, v) -> System.out.println("  " + k + " -> " + v));
        System.out.println();

        // ============================================================
        // EXAMPLE 3: LRU Cache with accessOrder
        // ============================================================
        System.out.println("--- Example 3: LRU Cache ---\n");

        LinkedHashMap<String, String> lruCache = new LinkedHashMap<>(4, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 3;  // Keep max 3 entries
            }
        };

        lruCache.put("A", "value-A");
        lruCache.put("B", "value-B");
        lruCache.put("C", "value-C");
        System.out.println("After adding A, B, C: " + lruCache.keySet());

        lruCache.get("A");  // Access A - moves to end
        System.out.println("After accessing A: " + lruCache.keySet());

        lruCache.put("D", "value-D");  // B is eldest, removed
        System.out.println("After adding D: " + lruCache.keySet());
        System.out.println();
    }

    // ============================================================
    // INTERNAL STRUCTURE
    // ============================================================
    /*
     * LinkedHashMap adds to HashMap:
     *
     * - Entry<K,V> extends HashMap.Node<K,V>
     *   + Entry<K,V> before
     *   + Entry<K,V> after
     *
     * - head, tail pointers
     *
     * - accessOrder flag:
     *   false = insertion order (default)
     *   true = access order (for LRU)
     *
     * - afterNodeAccess() - moves entry to end on get()
     * - afterNodeInsertion() - removes eldest if needed
     */
}
