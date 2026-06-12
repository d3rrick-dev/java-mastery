package phase10;

import java.util.*;

/**
 * LESSON 7: HASHSET INTERNALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * HashSet is a Set backed by a HashMap.
 * It stores only keys (values are a dummy object).
 * Like a guest list - names only, no extra info.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - No duplicates allowed
 * - O(1) add/remove/contains
 * - Simple set operations
 */

public class Lesson07_HashSetInternals {

    public static void main(String[] args) {
        System.out.println("=== HASHSET INTERNALS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic HashSet
        // ============================================================
        System.out.println("--- Example 1: Basic HashSet ---\n");

        Set<String> set = new HashSet<>();
        set.add("apple");
        set.add("banana");
        set.add("cherry");
        set.add("apple");  // Duplicate - ignored

        System.out.println("Set: " + set);
        System.out.println("Size: " + set.size());
        System.out.println("Contains 'banana': " + set.contains("banana"));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: HashSet vs HashMap
        // ============================================================
        System.out.println("--- Example 2: HashSet vs HashMap ---\n");

        System.out.println("HashSet internally uses HashMap:");
        System.out.println("  - Key = element you add");
        System.out.println("  - Value = PRESENT (dummy object)");
        System.out.println();
        System.out.println("HashSet operations:");
        System.out.println("  add(e) -> map.put(e, PRESENT)");
        System.out.println("  remove(e) -> map.remove(e)");
        System.out.println("  contains(e) -> map.containsKey(e)");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Duplicate prevention
        // ============================================================
        System.out.println("--- Example 3: Duplicate Prevention ---\n");

        Set<Integer> numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);  // Duplicate
        numbers.add(3);  // Duplicate

        System.out.println("Added: 1, 2, 3, 2, 3");
        System.out.println("Set: " + numbers);
        System.out.println("Duplicates automatically removed");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Performance
        // ============================================================
        System.out.println("--- Example 4: Performance ---\n");

        Set<Integer> perfSet = new HashSet<>();
        for (int i = 0; i < 100000; i++) {
            perfSet.add(i);
        }

        long start = System.nanoTime();
        boolean contains = perfSet.contains(50000);
        long end = System.nanoTime();

        System.out.println("Contains 50000: " + contains);
        System.out.println("Lookup time: " + (end - start) + " ns");
        System.out.println();
    }

    // ============================================================
    // INTERNAL STRUCTURE
    // ============================================================
    /*
     * HashSet Internal Structure:
     *
     * - Backed by HashMap<E, Object>
     * - private static final Object PRESENT = new Object();
     *
     * add(E e):
     *   return map.put(e, PRESENT) == null;
     *
     * remove(E e):
     *   return map.remove(e) == PRESENT;
     *
     * contains(E e):
     *   return map.containsKey(e);
     *
     * Time Complexity:
     * - add: O(1) average
     * - remove: O(1) average
     * - contains: O(1) average
     */
}
