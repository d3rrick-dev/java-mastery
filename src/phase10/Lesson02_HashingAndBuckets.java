package phase10;

import java.util.*;

/**
 * LESSON 2: HASHING AND BUCKETS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Hashing converts a key into a number (hash code).
 * That number determines which "bucket" (array index) the entry goes into.
 * Like assigning lockers by student ID number.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Fast lookup without searching entire collection
 * - Distributes entries across array
 * - Enables O(1) average operations
 */

public class Lesson02_HashingAndBuckets {

    public static void main(String[] args) {
        System.out.println("=== HASHING AND BUCKETS ===\n");

        // ============================================================
        // EXAMPLE 1: hashCode() method
        // ============================================================
        System.out.println("--- Example 1: hashCode() ---\n");

        String s1 = "hello";
        String s2 = "hello";
        String s3 = "world";

        System.out.println("s1.hashCode(): " + s1.hashCode());
        System.out.println("s2.hashCode(): " + s2.hashCode());
        System.out.println("s3.hashCode(): " + s3.hashCode());
        System.out.println("s1 == s2: " + (s1 == s2));  // Same object
        System.out.println("s1.equals(s2): " + s1.equals(s2));  // Same content
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Bucket index calculation
        // ============================================================
        System.out.println("--- Example 2: Bucket Index ---\n");

        int capacity = 16;  // Default HashMap capacity

        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};
        for (String key : keys) {
            int hash = key.hashCode();
            int index = Math.abs(hash) % capacity;
            System.out.printf("Key: %-12s hashCode: %-10d bucket: %d%n", key, hash, index);
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Custom hashCode
        // ============================================================
        System.out.println("--- Example 3: Custom hashCode ---\n");

        Person p1 = new Person("Alice", 30);
        Person p2 = new Person("Alice", 30);
        Person p3 = new Person("Bob", 25);

        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode());
        System.out.println("p3.hashCode(): " + p3.hashCode());
        System.out.println("p1.equals(p2): " + p1.equals(p2));
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Hash distribution
        // ============================================================
        System.out.println("--- Example 4: Hash Distribution ---\n");

        Map<String, Integer> distribution = new HashMap<>();
        String[] words = {"the", "quick", "brown", "fox", "jumps", "over", "lazy", "dog"};

        for (String word : words) {
            int bucket = Math.abs(word.hashCode()) % 16;
            distribution.put(word, bucket);
        }

        distribution.forEach((word, bucket) ->
            System.out.println(word + " -> bucket " + bucket)
        );
        System.out.println();
    }

    // ============================================================
    // HASHING EXAMPLES
    // ============================================================

    static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person p = (Person) o;
            return age == p.age && name.equals(p.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
}
