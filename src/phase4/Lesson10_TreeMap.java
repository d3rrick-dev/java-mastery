package phase4;

import java.util.Map;
import java.util.TreeMap;

/**
 * LESSON 10: TreeMap
 *
 * THEORY:
 * TreeMap is a Red-Black tree (self-balancing BST) implementation of the NavigableMap interface.
 * It stores key-value pairs sorted by natural key order or custom comparator.
 *
 * INTERNAL WORKING:
 * - Red-Black tree: Balanced binary search tree
 * - Each node is either red or black
 * - Guarantees O(log n) for basic operations
 * - Keys must be comparable (or provide comparator)
 * - No null keys allowed (null values allowed)
 *
 * TIME COMPLEXITY:
 * - put(K key, V value) - O(log n)
 * - get(Object key) - O(log n)
 * - remove(Object key) - O(log n)
 * - containsKey(Object key) - O(log n)
 * - firstKey() - O(log n)
 * - lastKey() - O(log n)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - put(K key, V value)     : Add/update entry
 * - get(Object key)         : Get value by key
 * - remove(Object key)      : Remove by key
 * - firstKey()              : Get smallest key
 * - lastKey()               : Get largest key
 * - firstEntry()            : Get first entry
 * - lastEntry()             : Get last entry
 * - lowerKey(K key)         : Greatest key < given key
 * - higherKey(K key)        : Least key > given key
 * - floorKey(K key)         : Greatest key <= given key
 * - ceilingKey(K key)       : Least key >= given key
 * - headMap(K toKey)        : View of portion < toKey
 * - tailMap(K fromKey)      : View of portion >= fromKey
 * - subMap(K from, K to)    : View of portion [from, to)
 */

public class Lesson10_TreeMap {

    public static void main(String[] args) {
        System.out.println("=== LESSON 10: TreeMap ===\n");

        // ============================================
        // 1. CREATING TREEMAP
        // ============================================
        System.out.println("--- 1. Creating TreeMap ---");

        // Natural order (keys must implement Comparable)
        Map<String, Integer> map1 = new TreeMap<>();
        map1.put("Charlie", 3);
        map1.put("Alice", 1);
        map1.put("Bob", 2);
        System.out.println("Natural order: " + map1);

        // Custom comparator
        Map<String, Integer> map2 = new TreeMap<>((a, b) -> b.compareTo(a));  // Reverse order
        map2.put("Charlie", 3);
        map2.put("Alice", 1);
        map2.put("Bob", 2);
        System.out.println("Reverse order: " + map2);

        // ============================================
        // 2. BASIC OPERATIONS
        // ============================================
        System.out.println("\n--- 2. Basic Operations ---");

        Map<Integer, String> numbers = new TreeMap<>();
        numbers.put(30, "Thirty");
        numbers.put(10, "Ten");
        numbers.put(20, "Twenty");
        numbers.put(5, "Five");
        System.out.println("TreeMap: " + numbers);

        System.out.println("Get 20: " + numbers.get(20));
        System.out.println("Contains key 10: " + numbers.containsKey(10));
        System.out.println("Contains value 'Five': " + numbers.containsValue("Five"));

        numbers.remove(10);
        System.out.println("After remove 10: " + numbers);

        // ============================================
        // 3. NAVIGATION METHODS
        // ============================================
        System.out.println("\n--- 3. Navigation Methods ---");

        Map<Integer, String> nums = new TreeMap<>();
        nums.put(10, "Ten");
        nums.put(20, "Twenty");
        nums.put(30, "Thirty");
        nums.put(40, "Forty");
        nums.put(50, "Fifty");
        System.out.println("TreeMap: " + nums);

        System.out.println("First key: " + ((TreeMap<Integer, String>) nums).firstKey());
        System.out.println("Last key: " + ((TreeMap<Integer, String>) nums).lastKey());
        System.out.println("Lower than 30: " + ((TreeMap<Integer, String>) nums).lowerKey(30));
        System.out.println("Higher than 30: " + ((TreeMap<Integer, String>) nums).higherKey(30));
        System.out.println("Floor of 30: " + ((TreeMap<Integer, String>) nums).floorKey(30));
        System.out.println("Ceiling of 30: " + ((TreeMap<Integer, String>) nums).ceilingKey(30));

        // ============================================
        // 4. SUBMAPS
        // ============================================
        System.out.println("\n--- 4. Submaps ---");

        Map<Integer, String> subMap = ((TreeMap<Integer, String>) nums).subMap(20, 40);  // [20, 40)
        System.out.println("SubMap [20, 40): " + subMap);

        Map<Integer, String> headMap = ((TreeMap<Integer, String>) nums).headMap(30);  // < 30
        System.out.println("HeadMap < 30: " + headMap);

        Map<Integer, String> tailMap = ((TreeMap<Integer, String>) nums).tailMap(30);  // >= 30
        System.out.println("TailMap >= 30: " + tailMap);

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Find range of keys
        Map<Integer, String> scores = new TreeMap<>();
        scores.put(10, "Alice");
        scores.put(20, "Bob");
        scores.put(30, "Charlie");
        scores.put(40, "Diana");
        scores.put(50, "Eve");
        System.out.println("Scores: " + scores);

        // Get all entries in range [25, 45)
        Map<Integer, String> inRange = ((TreeMap<Integer, String>) scores).subMap(25, 45);
        System.out.println("Scores in range [25, 45): " + inRange);

        // Challenge: Sort map by values
        Map<String, Integer> wordCount = new TreeMap<>();
        wordCount.put("apple", 5);
        wordCount.put("banana", 3);
        wordCount.put("cherry", 8);
        wordCount.put("date", 2);
        System.out.println("\nWord count: " + wordCount);

        // Sort by value (descending)
        Map<String, Integer> sortedByValue = new java.util.LinkedHashMap<>();
        wordCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> sortedByValue.put(entry.getKey(), entry.getValue()));
        System.out.println("Sorted by value: " + sortedByValue);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using null keys
        Map<String, String> treeMap = new TreeMap<>();
        // treeMap.put(null, "value"); // Throws NullPointerException!

        // Mistake 2: Using TreeMap when HashMap is sufficient
        // TreeMap: O(log n) operations, sorted order
        // HashMap: O(1) operations, no order
        // Use TreeMap only when you need sorted order

        // Mistake 3: Forgetting that TreeMap is not thread-safe
        // Use ConcurrentSkipListMap for thread-safe sorted map
    }
}
