package phase4;

import java.util.Set;
import java.util.TreeSet;

/**
 * LESSON 13: TreeSet
 *
 * THEORY:
 * TreeSet is a Red-Black tree implementation of the NavigableSet interface.
 * It stores unique elements sorted by natural order or custom comparator.
 *
 * INTERNAL WORKING:
 * - Backed by TreeMap (uses TreeMap internally)
 * - Red-Black tree: Self-balancing BST
 * - Each element is stored as a key in TreeMap with constant value
 * - Guarantees O(log n) for basic operations
 * - No null elements allowed
 *
 * TIME COMPLEXITY:
 * - add(E e) - O(log n)
 * - remove(Object o) - O(log n)
 * - contains(Object o) - O(log n)
 * - first() - O(log n)
 * - last() - O(log n)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * WHEN TO USE:
 * - Need unique elements (like HashSet)
 * - Need sorted order
 * - Need range operations (subSet, headSet, tailSet)
 */

public class Lesson13_TreeSet {

    public static void main(String[] args) {
        System.out.println("=== LESSON 13: TreeSet ===\n");

        // ============================================
        // 1. CREATING TREESET
        // ============================================
        System.out.println("--- 1. Creating TreeSet ---");

        // Natural order
        Set<String> set1 = new TreeSet<>();
        set1.add("Charlie");
        set1.add("Alice");
        set1.add("Bob");
        System.out.println("Natural order: " + set1);

        // Custom comparator (reverse order)
        Set<String> set2 = new TreeSet<>((a, b) -> b.compareTo(a));
        set2.add("Charlie");
        set2.add("Alice");
        set2.add("Bob");
        System.out.println("Reverse order: " + set2);

        // ============================================
        // 2. BASIC OPERATIONS
        // ============================================
        System.out.println("\n--- 2. Basic Operations ---");

        Set<Integer> numbers = new TreeSet<>();
        numbers.add(30);
        numbers.add(10);
        numbers.add(20);
        numbers.add(5);
        numbers.add(15);
        System.out.println("TreeSet: " + numbers);

        System.out.println("Contains 20: " + numbers.contains(20));
        System.out.println("Contains 100: " + numbers.contains(100));
        System.out.println("Size: " + numbers.size());

        numbers.remove(10);
        System.out.println("After remove 10: " + numbers);

        // ============================================
        // 3. NAVIGATION METHODS
        // ============================================
        System.out.println("\n--- 3. Navigation Methods ---");

        Set<Integer> nums = new TreeSet<>();
        nums.add(10);
        nums.add(20);
        nums.add(30);
        nums.add(40);
        nums.add(50);
        System.out.println("TreeSet: " + nums);

        TreeSet<Integer> treeSet = new TreeSet<>(nums);
        System.out.println("First: " + treeSet.first());
        System.out.println("Last: " + treeSet.last());
        System.out.println("Lower than 30: " + treeSet.lower(30));
        System.out.println("Higher than 30: " + treeSet.higher(30));
        System.out.println("Floor of 30: " + treeSet.floor(30));
        System.out.println("Ceiling of 30: " + treeSet.ceiling(30));

        // ============================================
        // 4. SUBSETS
        // ============================================
        System.out.println("\n--- 4. Subsets ---");

        Set<Integer> subSet = treeSet.subSet(20, 40);  // [20, 40)
        System.out.println("SubSet [20, 40): " + subSet);

        Set<Integer> headSet = treeSet.headSet(30);  // < 30
        System.out.println("HeadSet < 30: " + headSet);

        Set<Integer> tailSet = treeSet.tailSet(30);  // >= 30
        System.out.println("TailSet >= 30: " + tailSet);

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Find range of values
        Set<Integer> scores = new TreeSet<>();
        scores.add(10);
        scores.add(20);
        scores.add(30);
        scores.add(40);
        scores.add(50);
        scores.add(60);
        scores.add(70);
        System.out.println("Scores: " + scores);

        // Get scores in range [25, 55)
        Set<Integer> inRange = ((TreeSet<Integer>) scores).subSet(25, 55);
        System.out.println("Scores in range [25, 55): " + inRange);

        // Challenge: Sort and remove duplicates
        int[] arr = {5, 3, 1, 4, 2, 3, 5, 1, 4};
        System.out.println("\nOriginal: " + java.util.Arrays.toString(arr));

        Set<Integer> sortedUnique = new TreeSet<>();
        for (int num : arr) {
            sortedUnique.add(num);
        }
        System.out.println("Sorted unique: " + sortedUnique);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using null elements
        Set<String> treeSet2 = new TreeSet<>();
        // treeSet2.add(null); // Throws NullPointerException!

        // Mistake 2: Using TreeSet when HashSet is sufficient
        // TreeSet: O(log n), sorted order
        // HashSet: O(1), no order
        // Use TreeSet only when you need sorted order

        // Mistake 3: Using mutable elements
        // TreeSet uses compareTo() or comparator
        // If element is mutable and comparison changes, set breaks
        class MutableInt implements Comparable<MutableInt> {
            int value;
            MutableInt(int value) { this.value = value; }
            @Override
            public int compareTo(MutableInt other) {
                return Integer.compare(this.value, other.value);
            }
            @Override
            public String toString() { return String.valueOf(value); }
        }

        Set<MutableInt> set = new TreeSet<>();
        MutableInt m = new MutableInt(1);
        set.add(m);
        System.out.println("Before mutation: " + set.contains(m));
        m.value = 2;  // Mutate
        System.out.println("After mutation: " + set.contains(m));  // May not find!
    }
}
