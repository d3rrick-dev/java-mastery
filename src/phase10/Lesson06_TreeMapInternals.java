package phase10;

import java.util.*;

/**
 * LESSON 6: TREEMAP INTERNALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * TreeMap stores entries sorted by key using a Red-Black Tree.
 * Like a filing cabinet organized alphabetically.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Sorted key-value pairs
 * - Range queries (subMap, headMap, tailMap)
 * - O(log n) operations guaranteed
 */

public class Lesson06_TreeMapInternals {

    public static void main(String[] args) {
        System.out.println("=== TREEMAP INTERNALS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic TreeMap
        // ============================================================
        System.out.println("--- Example 1: Basic TreeMap ---\n");

        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("cherry", 3);
        treeMap.put("apple", 1);
        treeMap.put("banana", 2);
        treeMap.put("date", 4);

        System.out.println("TreeMap (sorted by key):");
        treeMap.forEach((k, v) -> System.out.println("  " + k + " -> " + v));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Range queries
        // ============================================================
        System.out.println("--- Example 2: Range Queries ---\n");

        TreeMap<Integer, String> scores = new TreeMap<>();
        scores.put(95, "Alice");
        scores.put(87, "Bob");
        scores.put(92, "Charlie");
        scores.put(78, "Diana");
        scores.put(88, "Eve");

        System.out.println("All scores: " + scores);
        System.out.println("Scores >= 90: " + scores.tailMap(90));
        System.out.println("Scores < 90: " + scores.headMap(90));
        System.out.println("Scores 85-95: " + scores.subMap(85, 95));
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Custom comparator
        // ============================================================
        System.out.println("--- Example 3: Custom Comparator ---\n");

        TreeMap<String, Integer> lengthMap = new TreeMap<>(
            Comparator.comparingInt(String::length)
        );
        lengthMap.put("hi", 2);
        lengthMap.put("hello", 5);
        lengthMap.put("a", 1);
        lengthMap.put("world", 5);

        System.out.println("Sorted by length: " + lengthMap.keySet());
        System.out.println();
    }

    // ============================================================
    // INTERNAL STRUCTURE
    // ============================================================
    /*
     * TreeMap Internal Structure:
     *
     * - Red-Black Tree (self-balancing BST)
     * - Entry<K,V> implements Map.Entry<K,V>
     *   + K key
     *   + V value
     *   + Entry<K,V> left
     *   + Entry<K,V> right
     *   + Entry<K,V> parent
     *   + boolean color (RED or BLACK)
     *
     * Operations: O(log n)
     * - put, get, remove
     * - firstKey, lastKey
     * - subMap, headMap, tailMap
     *
     * Red-Black Tree Properties:
     * 1. Every node is red or black
     * 2. Root is black
     * 3. Red nodes have black children
     * 4. Every path from root to leaf has same black count
     */
}
