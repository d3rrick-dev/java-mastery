package phase10;

import java.util.*;

/**
 * LESSON 13: equals() AND hashCode()
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * equals(): Checks if two objects are logically equal
 * hashCode(): Returns integer representing object's hash
 * Contract: equal objects must have same hashCode
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - equals(): Object comparison
 * - hashCode(): HashMap/HashSet bucket placement
 * - Contract ensures collections work correctly
 */

public class Lesson13_EqualsAndHashCode {

    public static void main(String[] args) {
        System.out.println("=== equals() AND hashCode() ===\n");

        // ============================================================
        // EXAMPLE 1: equals() contract
        // ============================================================
        System.out.println("--- Example 1: equals() Contract ---\n");

        String s1 = "hello";
        String s2 = new String("hello");
        String s3 = "world";

        System.out.println("s1.equals(s2): " + s1.equals(s2));  // true
        System.out.println("s1 == s2: " + (s1 == s2));  // false (different objects)
        System.out.println("s1.equals(s3): " + s1.equals(s3));  // false
        System.out.println();

        // ============================================================
        // EXAMPLE 2: hashCode() contract
        // ============================================================
        System.out.println("--- Example 2: hashCode() Contract ---\n");

        String a = "hello";
        String b = "hello";
        String c = "world";

        System.out.println("a.hashCode(): " + a.hashCode());
        System.out.println("b.hashCode(): " + b.hashCode());
        System.out.println("c.hashCode(): " + c.hashCode());
        System.out.println("a.equals(b) && a.hashCode() == b.hashCode(): " +
            (a.equals(b) && a.hashCode() == b.hashCode()));
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Breaking the contract
        // ============================================================
        System.out.println("--- Example 3: Breaking Contract ---\n");

        Set<BadEquals> badSet = new HashSet<>();
        badSet.add(new BadEquals("key", 1));
        badSet.add(new BadEquals("key", 1));  // Same content, different hash

        System.out.println("Added 2 objects with same content");
        System.out.println("Set size: " + badSet.size());  // 2! Should be 1
        System.out.println("Contract broken: equals() true but hashCode() different");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Correct implementation
        // ============================================================
        System.out.println("--- Example 4: Correct Implementation ---\n");

        Set<GoodEquals> goodSet = new HashSet<>();
        goodSet.add(new GoodEquals("key", 1));
        goodSet.add(new GoodEquals("key", 1));  // Same content, same hash

        System.out.println("Added 2 objects with same content");
        System.out.println("Set size: " + goodSet.size());  // 1! Correct
        System.out.println("Contract maintained: equals() true && hashCode() same");
        System.out.println();
    }

    // ============================================================
    // EQUALS AND HASHCODE EXAMPLES
    // ============================================================

    // Bad: equals() but no hashCode()
    static class BadEquals {
        private final String key;
        private final int value;

        public BadEquals(String key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BadEquals)) return false;
            BadEquals that = (BadEquals) o;
            return value == that.value && key.equals(that.key);
        }

        // No hashCode() override! Uses Object's identity hash
    }

    // Good: Both equals() and hashCode()
    static class GoodEquals {
        private final String key;
        private final int value;

        public GoodEquals(String key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GoodEquals)) return false;
            GoodEquals that = (GoodEquals) o;
            return value == that.value && Objects.equals(key, that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
