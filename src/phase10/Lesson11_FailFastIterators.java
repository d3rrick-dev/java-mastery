package phase10;

import java.util.*;

/**
 * LESSON 11: FAIL-FAST ITERATORS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Fail-fast iterators throw ConcurrentModificationException
 * if the collection is modified while iterating (except through iterator).
 * Like a security guard who stops you if you change the rules mid-game.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Prevents undefined behavior
 * - Detects concurrent modifications
 * - Fail-fast, not fail-safe
 */

public class Lesson11_FailFastIterators {

    public static void main(String[] args) {
        System.out.println("=== FAIL-FAST ITERATORS ===\n");

        // ============================================================
        // EXAMPLE 1: ConcurrentModificationException
        // ============================================================
        System.out.println("--- Example 1: Concurrent Modification ---\n");

        List<String> list = new ArrayList<>(List.of("A", "B", "C", "D"));

        System.out.println("Iterating and modifying:");
        try {
            for (String s : list) {
                System.out.println("  " + s);
                if (s.equals("B")) {
                    list.remove(s);  // Throws ConcurrentModificationException!
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("  ERROR: " + e.getClass().getSimpleName());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Safe removal via iterator
        // ============================================================
        System.out.println("--- Example 2: Safe Removal ---\n");

        list = new ArrayList<>(List.of("A", "B", "C", "D"));
        System.out.println("Before: " + list);

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s.equals("B")) {
                it.remove();  // Safe!
            }
        }
        System.out.println("After: " + list);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: How fail-fast works
        // ============================================================
        System.out.println("--- Example 3: How It Works ---\n");

        System.out.println("ArrayList iterator uses modCount:");
        System.out.println("  - modCount: incremented on structural changes");
        System.out.println("  - expectedModCount: captured at iterator creation");
        System.out.println("  - checkForComodification(): compares both");
        System.out.println("  - Throws ConcurrentModificationException if different");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Fail-safe collections
        // ============================================================
        System.out.println("--- Example 4: Fail-Safe Collections ---\n");

        Map<String, String> concurrentMap = new java.util.concurrent.ConcurrentHashMap<>();
        concurrentMap.put("A", "1");
        concurrentMap.put("B", "2");
        concurrentMap.put("C", "3");

        System.out.println("ConcurrentHashMap (fail-safe):");
        for (Map.Entry<String, String> entry : concurrentMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
            concurrentMap.put("D", "4");  // No exception!
        }
        System.out.println("Final map: " + concurrentMap.keySet());
        System.out.println();
    }

    // ============================================================
    // FAIL-FAST MECHANICS
    // ============================================================
    /*
     * Fail-Fast Mechanism:
     *
     * 1. Collection has modCount field
     * 2. Iterator captures expectedModCount
     * 3. On each next()/hasNext(), check:
     *    if (modCount != expectedModCount)
     *        throw new ConcurrentModificationException();
     *
     * 4. Iterator.remove() updates expectedModCount
     *
     * Fail-Safe (CopyOnWriteArrayList):
     * - Iterates over snapshot copy
     * - No ConcurrentModificationException
     * - Higher memory usage
     */
}
