package phase10;

import java.util.*;

/**
 * LESSON 4: HASHMAP RESIZING
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * When HashMap gets too full, it doubles its size and
 * redistributes all entries. Like expanding a restaurant
 * and moving all tables to new locations.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Maintains O(1) performance
 * - Keeps load factor under control
 * - Reduces collision chains
 */

public class Lesson04_HashMapResizing {

    public static void main(String[] args) {
        System.out.println("=== HASHMAP RESIZING ===\n");

        // ============================================================
        // EXAMPLE 1: Triggering resize
        // ============================================================
        System.out.println("--- Example 1: Triggering Resize ---\n");

        Map<String, Integer> map = new HashMap<>(4, 0.75f);
        // Capacity 4, load factor 0.75
        // Threshold = 4 * 0.75 = 3
        // Resize when size > 3

        System.out.println("Initial capacity: 4");
        System.out.println("Load factor: 0.75");
        System.out.println("Threshold: " + (int)(4 * 0.75));
        System.out.println();

        for (int i = 1; i <= 5; i++) {
            map.put("key" + i, i);
            System.out.println("Added key" + i + ", size: " + map.size());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Resize visualization
        // ============================================================
        System.out.println("--- Example 2: Resize Visualization ---\n");

        System.out.println("Before resize (capacity=4):");
        System.out.println("  +---+---+---+---+");
        System.out.println("  | A | B | C | D |");
        System.out.println("  +---+---+---+---+");
        System.out.println("      |   |   |");
        System.out.println("      v   v   v");
        System.out.println("    [A:1][B:2][C:3]");
        System.out.println();
        System.out.println("After resize (capacity=8):");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        System.out.println("      |       |       |       |");
        System.out.println("      v       v       v       v");
        System.out.println("    [A:1]   [B:2]   [C:3]   [D:4]");
        System.out.println("    (entries redistributed)");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Performance impact
        // ============================================================
        System.out.println("--- Example 3: Performance Impact ---\n");

        int initialCapacity = 16;
        Map<Integer, String> perfMap = new HashMap<>(initialCapacity);

        long start = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            perfMap.put(i, "value" + i);
        }
        long end = System.nanoTime();

        System.out.println("Inserted 100,000 entries");
        System.out.println("Time: " + (end - start) / 1_000_000 + " ms");
        System.out.println("Final capacity: " + getCapacity(perfMap));
        System.out.println();
    }

    // ============================================================
    // RESIZING MECHANICS
    // ============================================================
    /*
     * Resize Process:
     *
     * 1. New table created with 2x capacity
     * 2. Each entry rehashed:
     *    - If (hash & oldCapacity) == 0: stays at same index
     *    - Else: moves to index + oldCapacity
     * 3. Old table replaced with new table
     *
     * Why power of 2?
     * - (n - 1) & hash is faster than hash % n
     * - Better distribution
     */

    // Reflection to get HashMap capacity (for demo)
    static int getCapacity(Map<?, ?> map) {
        try {
            java.lang.reflect.Field tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(map);
            return table == null ? 0 : table.length;
        } catch (Exception e) {
            return -1;
        }
    }
}
