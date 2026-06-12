package phase10;

import java.util.*;

/**
 * LESSON 9: ARRAYLIST INTERNALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * ArrayList is a resizable array backed by Object[].
 * Like a dynamic array that grows automatically.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Random access O(1)
 * - Auto-resizing
 * - Most commonly used List implementation
 */

public class Lesson09_ArrayListInternals {

    public static void main(String[] args) {
        System.out.println("=== ARRAYLIST INTERNALS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic ArrayList
        // ============================================================
        System.out.println("--- Example 1: Basic Usage ---\n");

        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        System.out.println("List: " + list);
        System.out.println("Get(1): " + list.get(1));
        System.out.println("Size: " + list.size());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Internal array growth
        // ============================================================
        System.out.println("--- Example 2: Array Growth ---\n");

        System.out.println("ArrayList Internal Array:");
        System.out.println("  - Default capacity: 10");
        System.out.println("  - Grows by 50% when full");
        System.out.println("  - newCapacity = oldCapacity + (oldCapacity >> 1)");
        System.out.println();
        System.out.println("Growth sequence:");
        System.out.println("  10 -> 15 -> 22 -> 33 -> 49 -> ...");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Performance comparison
        // ============================================================
        System.out.println("--- Example 3: Performance ---\n");

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // Add performance
        long start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            arrayList.add(i);
        }
        long arrayListTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            linkedList.add(i);
        }
        long linkedListTime = System.nanoTime() - start;

        System.out.println("Add 100,000 elements:");
        System.out.println("  ArrayList: " + arrayListTime / 1_000_000 + " ms");
        System.out.println("  LinkedList: " + linkedListTime / 1_000_000 + " ms");
        System.out.println();

        // Random access
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            arrayList.get(i * 10);
        }
        long arrayListGet = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            linkedList.get(i * 10);
        }
        long linkedListGet = System.nanoTime() - start;

        System.out.println("Random access 10,000 times:");
        System.out.println("  ArrayList: " + arrayListGet / 1_000_000 + " ms");
        System.out.println("  LinkedList: " + linkedListGet / 1_000_000 + " ms");
        System.out.println();
    }

    // ============================================================
    // INTERNAL STRUCTURE
    // ============================================================
    /*
     * ArrayList Internal Structure:
     *
     * - Object[] elementData
     * - int size
     *
     * Key methods:
     * - add(E e): elementData[size++] = e
     * - get(int i): return (E) elementData[i]
     * - remove(int i): System.arraycopy(...)
     *
     * Time Complexity:
     * - add (end): O(1) amortized
     * - add (middle): O(n)
     * - get: O(1)
     * - remove: O(n)
     *
     * Initial capacity: 10
     * Growth: 1.5x (old + old/2)
     */
}
