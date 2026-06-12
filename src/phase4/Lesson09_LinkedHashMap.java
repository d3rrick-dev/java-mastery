package phase4;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LESSON 9: LinkedHashMap
 *
 * THEORY:
 * LinkedHashMap is a hash table and linked list implementation of the Map interface.
 * It maintains insertion order (or access order) unlike HashMap.
 *
 * INTERNAL WORKING:
 * - Hash table for fast lookup (like HashMap)
 * - Doubly-linked list to maintain order
 * - Each entry has: before, after pointers
 * - Two order modes: insertion-order (default) or access-order
 *
 * TIME COMPLEXITY:
 * - put(K key, V value) - O(1)
 * - get(Object key) - O(1)
 * - remove(Object key) - O(1)
 * - containsKey(Object key) - O(1)
 *
 * SPACE COMPLEXITY: O(n) - extra space for linked list pointers
 *
 * WHEN TO USE:
 * - Need insertion order preserved
 * - Need predictable iteration order
 * - LRU cache implementation (access-order mode)
 */

public class Lesson09_LinkedHashMap {

    public static void main(String[] args) {
        System.out.println("=== LESSON 9: LinkedHashMap ===\n");

        // ============================================
        // 1. CREATING LINKEDHASHMAP
        // ============================================
        System.out.println("--- 1. Creating LinkedHashMap ---");

        // Insertion order (default)
        Map<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("C", 3);
        map1.put("A", 1);
        map1.put("B", 2);
        System.out.println("Insertion order: " + map1);

        // ============================================
        // 2. INSERTION ORDER vs HASHMAP
        // ============================================
        System.out.println("\n--- 2. Insertion Order vs HashMap ---");

        Map<String, Integer> hashMap = new java.util.HashMap<>();
        hashMap.put("C", 3);
        hashMap.put("A", 1);
        hashMap.put("B", 2);
        System.out.println("HashMap (no order): " + hashMap);

        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("C", 3);
        linkedHashMap.put("A", 1);
        linkedHashMap.put("B", 2);
        System.out.println("LinkedHashMap (insertion order): " + linkedHashMap);

        // ============================================
        // 3. ACCESS ORDER MODE (LRU)
        // ============================================
        System.out.println("\n--- 3. Access Order Mode (LRU) ---");

        // accessOrder = true: reorder on access (for LRU cache)
        Map<String, Integer> lruCache = new LinkedHashMap<>(16, 0.75f, true);
        lruCache.put("A", 1);
        lruCache.put("B", 2);
        lruCache.put("C", 3);
        System.out.println("Initial: " + lruCache);

        // Access "A" - moves to end
        lruCache.get("A");
        System.out.println("After accessing A: " + lruCache);

        // Access "C" - moves to end
        lruCache.get("C");
        System.out.println("After accessing C: " + lruCache);

        // ============================================
        // 4. LRU CACHE IMPLEMENTATION
        // ============================================
        System.out.println("\n--- 4. LRU Cache Implementation ---");

        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("1", "One");
        cache.put("2", "Two");
        cache.put("3", "Three");
        System.out.println("Cache: " + cache);

        cache.put("4", "Four");  // Evicts "1" (least recently used)
        System.out.println("After adding 4: " + cache);

        cache.get("2");  // Access "2"
        cache.put("5", "Five");  // Evicts "3" (not "2")
        System.out.println("After accessing 2 and adding 5: " + cache);

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Remove duplicates while preserving order
        String[] items = {"A", "B", "A", "C", "B", "D", "C"};
        System.out.println("Original: " + java.util.Arrays.toString(items));

        // Using LinkedHashSet
        java.util.Set<String> unique = new java.util.LinkedHashSet<>(java.util.List.of(items));
        System.out.println("Unique (preserving order): " + unique);

        // Manual with LinkedHashMap
        Map<String, Boolean> seen = new LinkedHashMap<>();
        for (String item : items) {
            seen.putIfAbsent(item, true);
        }
        System.out.println("Unique (LinkedHashMap): " + seen.keySet());

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using LinkedHashMap when HashMap is sufficient
        // If you don't need order, use HashMap (faster, less memory)

        // Mistake 2: Forgetting that access-order affects iteration
        // In access-order mode, iteration order changes on get()
        Map<String, String> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
        accessOrder.put("A", "1");
        accessOrder.put("B", "2");
        accessOrder.put("C", "3");
        System.out.println("Before access: " + accessOrder.keySet());
        accessOrder.get("A");
        System.out.println("After accessing A: " + accessOrder.keySet());

        // Mistake 3: Not removing eldest entry in LRU
        // LinkedHashMap doesn't automatically remove old entries
        // Need to override removeEldestEntry()
    }

    // Simple LRU Cache using LinkedHashMap
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        LRUCache(int capacity) {
            super(16, 0.75f, true);  // access-order = true
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }
}
