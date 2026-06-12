package phase10;

import java.util.*;

/**
 * LESSON 1: HOW HASHMAP WORKS INTERNALLY
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * HashMap stores key-value pairs in an array of "buckets".
 * Each bucket is a linked list (or tree when large).
 * Uses hashCode() to find the right bucket.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - O(1) average lookup/insert/delete
 * - Flexible key types
 * - Widely used in Java
 */

public class Lesson01_HowHashMapWorksInternally {

    public static void main(String[] args) {
        System.out.println("=== HOW HASHMAP WORKS INTERNALLY ===\n");

        // ============================================================
        // EXAMPLE 1: Basic HashMap usage
        // ============================================================
        System.out.println("--- Example 1: Basic Usage ---\n");

        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);

        System.out.println("apple -> " + map.get("apple"));
        System.out.println("banana -> " + map.get("banana"));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Internal structure visualization
        // ============================================================
        System.out.println("--- Example 2: Internal Structure ---\n");

        System.out.println("HashMap Internal Structure:");
        System.out.println();
        System.out.println("  +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |10 |11 |12 |13 |14 |15 |");
        System.out.println("  +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("  |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |");
        System.out.println("  +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("       |       |       |");
        System.out.println("       v       v       v");
        System.out.println("     [A:1]  [B:2]  [C:3]");
        System.out.println("       |");
        System.out.println("       v");
        System.out.println("     [A:1] -> [A:4]  (collision chain)");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: hashCode and bucket index
        // ============================================================
        System.out.println("--- Example 3: hashCode to Bucket ---\n");

        String key = "hello";
        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash) % 16;  // Default capacity = 16

        System.out.println("Key: " + key);
        System.out.println("hashCode: " + hash);
        System.out.println("Bucket index: " + bucketIndex);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Performance demonstration
        // ============================================================
        System.out.println("--- Example 4: Performance ---\n");

        Map<Integer, String> largeMap = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            largeMap.put(i, "value-" + i);
        }

        long start = System.nanoTime();
        String val = largeMap.get(50000);
        long end = System.nanoTime();

        System.out.println("Lookup time: " + (end - start) + " ns");
        System.out.println("Value: " + val);
        System.out.println();
    }

    // ============================================================
    // INTERNAL MECHANICS
    // ============================================================
    /*
     * HashMap Internal Structure (Java 8+):
     *
     * 1. Node<K,V>[] table - array of buckets
     * 2. Each Node contains:
     *    - int hash
     *    - K key
     *    - V value
     *    - Node<K,V> next (for collision chain)
     *
     * 3. When capacity > 12 and load factor > 0.75:
     *    - Chains with length > 8 become TreeNodes (red-black tree)
     *    - Chains with length < 6 become LinkedList again
     *
     * 4. Default capacity: 16
     * 5. Default load factor: 0.75
     * 6. Threshold = capacity * load factor
     */
}
