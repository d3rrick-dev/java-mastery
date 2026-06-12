package phase5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LESSON 6: Interview Pattern - Duplicate Detection
 *
 * THEORY:
 * Finding duplicates is a common interview problem.
 * Can be solved using HashSet, HashMap, or Streams.
 *
 * PATTERNS:
 * 1. Find if array has duplicates
 * 2. Find all duplicate elements
 * 3. Find first duplicate
 */

public class Lesson06_InterviewPatterns_DuplicateDetection {

    public static void main(String[] args) {
        System.out.println("=== LESSON 6: Duplicate Detection ===\n");

        int[] arr = {1, 2, 3, 4, 2, 5, 3, 6, 1};
        System.out.println("Array: " + Arrays.toString(arr));

        // ============================================
        // 1. CHECK IF DUPLICATES EXIST
        // ============================================
        System.out.println("\n--- 1. Check If Duplicates Exist ---");

        // Traditional: HashSet
        Set<Integer> seen = new HashSet<>();
        boolean hasDup = false;
        for (int num : arr) {
            if (!seen.add(num)) {  // add() returns false if already exists
                hasDup = true;
                break;
            }
        }
        System.out.println("Has duplicates (traditional): " + hasDup);

        // Stream: distinct() + count
        boolean hasDupStream = arr.length != Arrays.stream(arr).distinct().count();
        System.out.println("Has duplicates (stream): " + hasDupStream);

        // ============================================
        // 2. FIND ALL DUPLICATES
        // ============================================
        System.out.println("\n--- 2. Find All Duplicates ---");

        // Traditional: HashMap
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : arr) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        List<Integer> duplicates = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add(entry.getKey());
            }
        }
        System.out.println("Duplicates (traditional): " + duplicates);

        // Stream: groupingBy + filter
        List<Integer> dupStream = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("Duplicates (stream): " + dupStream);

        // ============================================
        // 3. FIND FIRST DUPLICATE
        // ============================================
        System.out.println("\n--- 3. Find First Duplicate ---");

        // Traditional: HashSet
        Set<Integer> seen2 = new HashSet<>();
        Integer firstDup = null;
        for (int num : arr) {
            if (!seen2.add(num)) {
                firstDup = num;
                break;
            }
        }
        System.out.println("First duplicate (traditional): " + firstDup);

        // Stream: findFirst on duplicates
        Set<Integer> seen3 = new HashSet<>();
        Optional<Integer> firstDupStream = Arrays.stream(arr)
                .boxed()
                .filter(n -> !seen3.add(n))
                .findFirst();
        System.out.println("First duplicate (stream): " + firstDupStream.orElse(null));

        // ============================================
        // 4. FIND DUPLICATE COUNT
        // ============================================
        System.out.println("\n--- 4. Find Duplicate Count ---");

        // Stream: count duplicates
        long dupCount = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .values().stream()
                .filter(count -> count > 1)
                .count();
        System.out.println("Number of duplicate values: " + dupCount);

        // ============================================
        // 5. CODING CHALLENGE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Find all duplicates with their counts
        Map<Integer, Long> dupWithCount = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()));
        System.out.println("Duplicates with counts:");
        dupWithCount.forEach((num, count) -> {
            if (count > 1) {
                System.out.println("  " + num + " appears " + count + " times");
            }
        });

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using contains() on ArrayList (O(n))
        // Better: Use HashSet (O(1))

        // Mistake 2: Not handling null values
        // HashSet allows one null, but be careful

        // Mistake 3: Using stream when simple loop is clearer
        // For simple duplicate check, traditional loop is often clearer
    }

    // Helper for Arrays.toString
    private static String toString(int[] arr) {
        return Arrays.toString(arr);
    }
}
