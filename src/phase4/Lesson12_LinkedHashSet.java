package phase4;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * LESSON 12: LinkedHashSet
 *
 * THEORY:
 * LinkedHashSet is a hash table and linked list implementation of the Set interface.
 * It maintains insertion order unlike HashSet.
 *
 * INTERNAL WORKING:
 * - Backed by LinkedHashMap (uses LinkedHashMap internally)
 * - Hash table for O(1) operations
 * - Doubly-linked list to maintain insertion order
 * - Each element is stored as a key in LinkedHashMap with constant value
 *
 * TIME COMPLEXITY:
 * - add(E e) - O(1)
 * - remove(Object o) - O(1)
 * - contains(Object o) - O(1)
 * - size() - O(1)
 *
 * SPACE COMPLEXITY: O(n) - extra space for linked list pointers
 *
 * WHEN TO USE:
 * - Need uniqueness (like HashSet)
 * - Need insertion order preserved
 * - Need predictable iteration order
 */

public class Lesson12_LinkedHashSet {

    public static void main(String[] args) {
        System.out.println("=== LESSON 12: LinkedHashSet ===\n");

        // ============================================
        // 1. CREATING LINKEDHASHSET
        // ============================================
        System.out.println("--- 1. Creating LinkedHashSet ---");

        Set<String> set1 = new LinkedHashSet<>();
        System.out.println("Empty set: " + set1);

        Set<String> set2 = new LinkedHashSet<>(16);  // Initial capacity
        System.out.println("Set with capacity 16: " + set2.size() + " elements");

        // ============================================
        // 2. ADDING ELEMENTS (PRESERVES ORDER)
        // ============================================
        System.out.println("\n--- 2. Adding Elements (Preserves Order) ---");

        Set<String> fruits = new LinkedHashSet<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");  // Duplicate - ignored
        fruits.add("Banana"); // Duplicate - ignored
        System.out.println("Fruits: " + fruits);
        System.out.println("Size: " + fruits.size());  // Only 3 unique

        // Compare with HashSet
        Set<String> hashSet = new java.util.HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        System.out.println("HashSet (no order): " + hashSet);
        System.out.println("LinkedHashSet (insertion order): " + fruits);

        // ============================================
        // 3. BASIC OPERATIONS
        // ============================================
        System.out.println("\n--- 3. Basic Operations ---");

        System.out.println("Contains 'Apple': " + fruits.contains("Apple"));
        System.out.println("Contains 'Grape': " + fruits.contains("Grape"));
        System.out.println("Size: " + fruits.size());
        System.out.println("Is empty: " + fruits.isEmpty());

        fruits.remove("Banana");
        System.out.println("After remove 'Banana': " + fruits);

        // ============================================
        // 4. ITERATION (PREDICTABLE ORDER)
        // ============================================
        System.out.println("\n--- 4. Iteration (Predictable Order) ---");

        Set<Integer> numbers = new LinkedHashSet<>();
        numbers.add(30);
        numbers.add(10);
        numbers.add(20);
        numbers.add(5);
        numbers.add(15);

        System.out.println("Iteration order:");
        for (Integer num : numbers) {
            System.out.println("  " + num);
        }

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Remove duplicates while preserving order
        String[] items = {"A", "B", "A", "C", "B", "D", "C", "E"};
        System.out.println("Original: " + java.util.Arrays.toString(items));

        Set<String> unique = new LinkedHashSet<>();
        for (String item : items) {
            unique.add(item);
        }
        System.out.println("Unique (preserving order): " + unique);

        // Challenge: Find first unique element
        String[] stream = {"a", "b", "a", "c", "b", "d"};
        System.out.println("\nStream: " + java.util.Arrays.toString(stream));

        Set<String> seen = new LinkedHashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();
        for (String s : stream) {
            if (!seen.add(s)) {
                duplicates.add(s);
            }
        }
        System.out.println("Duplicates: " + duplicates);

        // First non-duplicate
        for (String s : stream) {
            if (!duplicates.contains(s)) {
                System.out.println("First non-duplicate: " + s);
                break;
            }
        }

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using LinkedHashSet when HashSet is sufficient
        // If you don't need order, use HashSet (less memory overhead)

        // Mistake 2: Expecting sorted order
        // LinkedHashSet preserves INSERTION order, not sorted order
        // Use TreeSet for sorted order

        Set<Integer> numbers2 = new LinkedHashSet<>();
        numbers2.add(30);
        numbers2.add(10);
        numbers2.add(20);
        System.out.println("LinkedHashSet: " + numbers2);  // [30, 10, 20]
        System.out.println("Not sorted! Use TreeSet for sorting");

        // Mistake 3: Using mutable elements
        // Same issue as HashSet - uses hashCode() and equals()
    }
}
