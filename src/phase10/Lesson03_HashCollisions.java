package phase10;

import java.util.*;

/**
 * LESSON 3: HASH COLLISIONS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Hash collision happens when two different keys produce
 * the same bucket index. Like two students getting the same locker.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - hashCode() has limited range (int = 2^32 values)
 * - Infinite possible keys, finite buckets
 * - Collision handling is necessary
 */

public class Lesson03_HashCollisions {

    public static void main(String[] args) {
        System.out.println("=== HASH COLLISIONS ===\n");

        // ============================================================
        // EXAMPLE 1: Demonstrating collisions
        // ============================================================
        System.out.println("--- Example 1: Collision Demo ---\n");

        int capacity = 4;  // Small capacity to force collisions

        String[] keys = {"Aa", "BB", "Ccc", "DDDD", "Ee", "Ff"};
        System.out.println("With capacity = " + capacity + ":");
        System.out.println();

        for (String key : keys) {
            int hash = key.hashCode();
            int index = Math.abs(hash) % capacity;
            System.out.printf("Key: %-6s hashCode: %-8d bucket: %d%n", key, hash, index);
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Collision handling in HashMap
        // ============================================================
        System.out.println("--- Example 2: Collision Handling ---\n");

        Map<String, Integer> map = new HashMap<>();
        map.put("Aa", 1);
        map.put("BB", 2);  // Same bucket as "Aa" with capacity 4

        System.out.println("Map: " + map);
        System.out.println("Both entries stored in same bucket (linked list)");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Poor hashCode implementation
        // ============================================================
        System.out.println("--- Example 3: Bad hashCode ---\n");

        Map<BadKey, String> badMap = new HashMap<>();
        badMap.put(new BadKey("key1"), "value1");
        badMap.put(new BadKey("key2"), "value2");
        badMap.put(new BadKey("key3"), "value3");

        System.out.println("All keys in same bucket (bad hashCode)");
        System.out.println("Map size: " + badMap.size());
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Good hashCode implementation
        // ============================================================
        System.out.println("--- Example 4: Good hashCode ---\n");

        Map<GoodKey, String> goodMap = new HashMap<>();
        goodMap.put(new GoodKey("key1"), "value1");
        goodMap.put(new GoodKey("key2"), "value2");
        goodMap.put(new GoodKey("key3"), "value3");

        System.out.println("Keys distributed across buckets (good hashCode)");
        System.out.println("Map size: " + goodMap.size());
        System.out.println();
    }

    // ============================================================
    // HASH COLLISION EXAMPLES
    // ============================================================

    // Bad: Always returns same hash
    static class BadKey {
        private final String value;

        public BadKey(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BadKey)) return false;
            return value.equals(((BadKey) o).value);
        }

        @Override
        public int hashCode() {
            return 42;  // Always same! All keys collide!
        }

        @Override
        public String toString() {
            return value;
        }
    }

    // Good: Proper distribution
    static class GoodKey {
        private final String value;

        public GoodKey(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GoodKey)) return false;
            return value.equals(((GoodKey) o).value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();  // Good distribution
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
