package phase12.BEGINNER;

import java.util.*;

/**
 * INTERVIEW QUESTION: Difference between HashMap and Hashtable?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests knowledge of collection framework history
 * - Shows understanding of thread safety
 * - Reveals knowledge of null handling
 */

public class Lesson02_WhatIsDifferenceBetweenHashMapAndHashTable {

    public static void main(String[] args) {
        System.out.println("=== HASHMAP VS HASHTABLE ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between HashMap and Hashtable?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("1. THREAD SAFETY:");
        System.out.println("   - HashMap: NOT thread-safe");
        System.out.println("   - Hashtable: Thread-safe (all methods synchronized)");
        System.out.println();

        System.out.println("2. NULL KEYS/VALUES:");
        System.out.println("   - HashMap: Allows one null key, multiple null values");
        System.out.println("   - Hashtable: Does NOT allow null keys or values");
        System.out.println();

        System.out.println("3. LEGACY:");
        System.out.println("   - HashMap: Introduced in Java 1.2");
        System.out.println("   - Hashtable: Legacy class (Java 1.0)");
        System.out.println();

        System.out.println("4. PERFORMANCE:");
        System.out.println("   - HashMap: Faster (no synchronization overhead)");
        System.out.println("   - Hashtable: Slower (synchronized methods)");
        System.out.println();

        System.out.println("5. ITERATION:");
        System.out.println("   - HashMap: Fail-fast iterator");
        System.out.println("   - Hashtable: Fail-fast enumerator (legacy)");
        System.out.println();

        System.out.println("6. INHERITANCE:");
        System.out.println("   - HashMap: Extends AbstractMap");
        System.out.println("   - Hashtable: Extends Dictionary (abstract class)");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        // HashMap allows null
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put(null, 1);
        hashMap.put("key", null);
        System.out.println("HashMap with null: " + hashMap);

        // Hashtable throws exception
        try {
            Map<String, Integer> hashTable = new Hashtable<>();
            hashTable.put(null, 1);  // NullPointerException!
        } catch (NullPointerException e) {
            System.out.println("Hashtable rejects null: " + e.getClass().getSimpleName());
        }
        System.out.println();

        // ============================================================
        // MODERN ALTERNATIVE
        // ============================================================
        System.out.println("--- Modern Alternative ---\n");

        System.out.println("For thread-safe maps, use:");
        System.out.println("  - ConcurrentHashMap (preferred)");
        System.out.println("  - Collections.synchronizedMap(new HashMap<>())");
        System.out.println("  - Hashtable is considered legacy");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Using Hashtable in new code:");
        System.out.println("   - It's legacy, use ConcurrentHashMap instead");
        System.out.println();

        System.out.println("2. Thinking HashMap is thread-safe:");
        System.out.println("   - It's NOT thread-safe");
        System.out.println("   - Use ConcurrentHashMap for multi-threading");
        System.out.println();

        System.out.println("3. Confusing with ConcurrentHashMap:");
        System.out.println("   - ConcurrentHashMap is better (segment-level locking)");
        System.out.println("   - Hashtable locks entire map");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What is ConcurrentHashMap and how does it work?");
        System.out.println("2. When would you use synchronizedMap vs ConcurrentHashMap?");
        System.out.println("3. What is fail-fast vs fail-safe?");
        System.out.println("4. How does HashMap handle collisions?");
        System.out.println("5. What is the load factor in HashMap?");
    }
}
