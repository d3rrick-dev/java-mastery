package phase4;

import java.util.HashMap;
import java.util.Map;

/**
 * LESSON 8: HashMap
 *
 * THEORY:
 * HashMap is a hash table-based implementation of the Map interface.
 * It stores key-value pairs and provides O(1) average time complexity for basic operations.
 *
 * INTERNAL WORKING:
 * - Array of buckets (Node<K,V>[] table)
 * - Each bucket contains a linked list (or tree if many collisions)
 * - Hash function determines bucket: hash(key) & (n-1)
 * - Load factor (default 0.75) triggers resizing
 * - When bucket size > 8, converts to balanced tree (Java 8+)
 *
 * TIME COMPLEXITY:
 * - put(K key, V value) - O(1) average, O(n) worst case
 * - get(Object key) - O(1) average, O(n) worst case
 * - remove(Object key) - O(1) average, O(n) worst case
 * - containsKey(Object key) - O(1) average
 * - containsValue(Object value) - O(n) - must search all buckets
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - put(K key, V value)     : Add/update key-value pair
 * - get(Object key)         : Get value by key
 * - remove(Object key)      : Remove by key
 * - containsKey(Object key) : Check if key exists
 * - containsValue(Object v) : Check if value exists
 * - keySet()                : Get all keys
 * - values()                : Get all values
 * - entrySet()              : Get all key-value pairs
 * - size()                  : Number of entries
 * - isEmpty()               : Check if empty
 * - clear()                 : Remove all entries
 */

public class Lesson08_HashMap {

    public static void main(String[] args) {
        System.out.println("=== LESSON 8: HashMap ===\n");

        // ============================================
        // 1. CREATING HASHMAP
        // ============================================
        System.out.println("--- 1. Creating HashMap ---");

        // Empty HashMap
        Map<String, Integer> map1 = new HashMap<>();
        System.out.println("Empty map: " + map1);

        // HashMap with initial capacity
        Map<String, Integer> map2 = new HashMap<>(16);
        System.out.println("Map with capacity 16: " + map2.size() + " entries");

        // HashMap from another map
        Map<String, Integer> map3 = new HashMap<>(Map.of("A", 1, "B", 2, "C", 3));
        System.out.println("Map from entries: " + map3);

        // ============================================
        // 2. PUTTING ENTRIES
        // ============================================
        System.out.println("\n--- 2. Putting Entries ---");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        System.out.println("After puts: " + scores);

        // put() returns previous value (or null if new key)
        Integer old = scores.put("Bob", 90);  // Update existing
        System.out.println("Updated Bob, old value: " + old);
        System.out.println("After update: " + scores);

        // putIfAbsent() - only put if key doesn't exist
        scores.putIfAbsent("Diana", 88);
        scores.putIfAbsent("Alice", 100);  // Won't update
        System.out.println("After putIfAbsent: " + scores);

        // ============================================
        // 3. GETTING VALUES
        // ============================================
        System.out.println("\n--- 3. Getting Values ---");

        System.out.println("Alice's score: " + scores.get("Alice"));
        System.out.println("Bob's score: " + scores.get("Bob"));
        System.out.println("Unknown's score: " + scores.get("Unknown"));  // Returns null

        // getOrDefault() - return default if key not found
        System.out.println("Unknown with default: " + scores.getOrDefault("Unknown", 0));

        // ============================================
        // 4. CHECKING CONTAINS
        // ============================================
        System.out.println("\n--- 4. Checking Contains ---");

        System.out.println("Contains key 'Alice': " + scores.containsKey("Alice"));
        System.out.println("Contains key 'Unknown': " + scores.containsKey("Unknown"));
        System.out.println("Contains value 95: " + scores.containsValue(95));
        System.out.println("Contains value 100: " + scores.containsValue(100));

        // ============================================
        // 5. REMOVING ENTRIES
        // ============================================
        System.out.println("\n--- 5. Removing Entries ---");

        System.out.println("Before remove: " + scores);
        scores.remove("Charlie");
        System.out.println("After remove Charlie: " + scores);

        // remove(key, value) - only remove if key maps to value
        scores.remove("Bob", 90);
        System.out.println("After remove Bob=90: " + scores);
        scores.remove("Bob", 87);  // Won't remove (value changed)
        System.out.println("After remove Bob=87 (no change): " + scores);

        // ============================================
        // 6. ITERATING
        // ============================================
        System.out.println("\n--- 6. Iterating ---");

        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        scores.put("Diana", 88);

        // Iterate over entries
        System.out.println("Iterating entries:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }

        // Iterate over keys
        System.out.println("Iterating keys:");
        for (String key : scores.keySet()) {
            System.out.println("  " + key);
        }

        // Iterate over values
        System.out.println("Iterating values:");
        for (Integer value : scores.values()) {
            System.out.println("  " + value);
        }

        // forEach (Java 8+)
        System.out.println("Using forEach:");
        scores.forEach((name, score) ->
                System.out.println("  " + name + " = " + score));

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Count frequency of characters
        String str = "hello world";
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : str.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        System.out.println("Character frequency in '" + str + "':");
        freq.forEach((c, count) ->
                System.out.println("  '" + c + "': " + count));

        // Challenge: Find first non-repeating character
        String str2 = "swiss";
        Map<Character, Integer> freq2 = new HashMap<>();
        for (char c : str2.toCharArray()) {
            freq2.put(c, freq2.getOrDefault(c, 0) + 1);
        }
        Character firstNonRepeat = null;
        for (char c : str2.toCharArray()) {
            if (freq2.get(c) == 1) {
                firstNonRepeat = c;
                break;
            }
        }
        System.out.println("First non-repeating in '" + str2 + "': " + firstNonRepeat);

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using mutable keys
        // HashMap uses hashCode() and equals() for keys
        // If key is mutable and changes after insertion, can't find it
        class MutableKey {
            int value;
            MutableKey(int value) { this.value = value; }
            @Override
            public int hashCode() { return value; }
            @Override
            public boolean equals(Object o) {
                return o instanceof MutableKey && ((MutableKey) o).value == value;
            }
        }

        MutableKey key = new MutableKey(1);
        Map<MutableKey, String> map = new HashMap<>();
        map.put(key, "value");
        System.out.println("Before mutation: " + map.get(key));
        key.value = 2;  // Mutate key
        System.out.println("After mutation: " + map.get(key));  // null!

        // Mistake 2: Using HashMap in multi-threaded environment
        // HashMap is not thread-safe
        // Use ConcurrentHashMap for thread-safety

        // Mistake 3: Not overriding hashCode() and equals()
        // Without proper hashCode/equals, HashMap won't work correctly
    }
}
