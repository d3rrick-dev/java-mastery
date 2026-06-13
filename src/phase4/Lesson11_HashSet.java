package phase4;

import java.util.HashSet;
import java.util.Set;

/**
 * LESSON 11: HashSet
 *
 * THEORY:
 * HashSet is a hash table-based implementation of the Set interface.
 * It stores unique elements (no duplicates) and provides O(1) average time complexity.
 *
 * INTERNAL WORKING:
 * - Backed by HashMap (uses HashMap internally)
 * - Each element is stored as a key in HashMap with a constant value (PRESENT)
 * - Uses hashCode() and equals() to determine uniqueness
 * - No guarantee on iteration order
 *
 * TIME COMPLEXITY:
 * - add(E e) - O(1) average
 * - remove(Object o) - O(1) average
 * - contains(Object o) - O(1) average
 * - size() - O(1)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - add(E e)           : Add element (returns false if duplicate)
 * - remove(Object o)   : Remove element
 * - contains(Object o) : Check if exists
 * - size()             : Number of elements
 * - isEmpty()          : Check if empty
 * - clear()            : Remove all elements
 * - iterator()         : Get iterator
 */

public class Lesson11_HashSet {

    public static void main(String[] args) {
        System.out.println("=== LESSON 11: HashSet ===\n");

        // ============================================
        // 1. CREATING HASHSET
        // ============================================
        System.out.println("--- 1. Creating HashSet ---");

        Set<String> set1 = new HashSet<>();
        System.out.println("Empty set: " + set1);

        Set<String> set2 = new HashSet<>(16);  // Initial capacity
        System.out.println("Set with capacity 16: " + set2.size() + " elements");

        Set<String> set3 = new HashSet<>(java.util.List.of("A", "B", "C"));
        System.out.println("Set from collection: " + set3);

        // ============================================
        // 2. ADDING ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Adding Elements ---");

        Set<String> fruits = new HashSet<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");  // Duplicate - ignored
        fruits.add("Banana"); // Duplicate - ignored
        System.out.println("Fruits: " + fruits);
        System.out.println("Size: " + fruits.size());  // Only 3 unique

        // add() returns false if element already exists
        boolean added = fruits.add("Apple");
        System.out.println("Added 'Apple' again: " + added);  // false

        // ============================================
        // 3. CHECKING CONTAINS
        // ============================================
        System.out.println("\n--- 3. Checking Contains ---");

        System.out.println("Contains 'Apple': " + fruits.contains("Apple"));
        System.out.println("Contains 'Grape': " + fruits.contains("Grape"));

        // ============================================
        // 4. REMOVING ELEMENTS
        // ============================================
        System.out.println("\n--- 4. Removing Elements ---");

        System.out.println("Before remove: " + fruits);
        fruits.remove("Banana");
        System.out.println("After remove 'Banana': " + fruits);

        // ============================================
        // 5. SET OPERATIONS
        // ============================================
        System.out.println("\n--- 5. Set Operations ---");

        Set<Integer> setA = new HashSet<>(java.util.List.of(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(java.util.List.of(4, 5, 6, 7, 8));

        System.out.println("Set A: " + setA);
        System.out.println("Set B: " + setB);

        // Union
        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("Union (A ∪ B): " + union);

        // Intersection
        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("Intersection (A ∩ B): " + intersection);

        // Difference
        Set<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("Difference (A - B): " + difference);

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Find intersection of two arrays
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {4, 5, 6, 7, 8};
        System.out.println("Array 1: " + java.util.Arrays.toString(arr1));
        System.out.println("Array 2: " + java.util.Arrays.toString(arr2));

        Set<Integer> intersection2 = new HashSet<>();
        Set<Integer> set4 = new HashSet<>();
        for (int num : arr1) {
            set4.add(num);
        }
        for (int num : arr2) {
            if (set1.contains(num)) {
                intersection2.add(num);
            }
        }
        System.out.println("Intersection: " + intersection2);

        // Challenge: Remove duplicates from array
        int[] withDups = {1, 2, 2, 3, 3, 3, 4, 4, 5};
        System.out.println("\nWith duplicates: " + java.util.Arrays.toString(withDups));
        Set<Integer> unique = new HashSet<>();
        for (int num : withDups) {
            unique.add(num);
        }
        System.out.println("Unique: " + unique);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Using HashSet with mutable objects
        // HashSet uses hashCode() and equals()
        // If object is mutable and changes, can't find it
        class Mutable {
            int value;
            Mutable(int value) { this.value = value; }
            @Override
            public int hashCode() { return value; }
            @Override
            public boolean equals(Object o) {
                return o instanceof Mutable && ((Mutable) o).value == value;
            }
            @Override
            public String toString() { return String.valueOf(value); }
        }

        Set<Mutable> set = new HashSet<>();
        Mutable m = new Mutable(1);
        set.add(m);
        System.out.println("Before mutation: " + set.contains(m));
        m.value = 2;  // Mutate
        System.out.println("After mutation: " + set.contains(m));  // false!

        // Mistake 2: Expecting order
        Set<String> unordered = new HashSet<>(java.util.List.of("C", "A", "B", "D"));
        System.out.println("HashSet order: " + unordered);  // No guaranteed order
        // Use LinkedHashSet if you need insertion order
    }
}
