package phase5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LESSON 7: Interview Pattern - Frequency Counting
 *
 * THEORY:
 * Counting element frequencies is one of the most common interview patterns.
 * Used for: finding most frequent, least frequent, elements with specific frequency.
 *
 * DATA STRUCTURES:
 * - HashMap: General frequency counting
 * - TreeMap: Sorted frequency counting
 */

public class Lesson07_InterviewPatterns_FrequencyCounting {

    public static void main(String[] args) {
        System.out.println("=== LESSON 7: Frequency Counting ===\n");

        int[] arr = {1, 2, 3, 2, 4, 1, 5, 3, 2, 4, 4, 4};
        System.out.println("Array: " + Arrays.toString(arr));

        // ============================================
        // 1. COUNT FREQUENCIES
        // ============================================
        System.out.println("\n--- 1. Count Frequencies ---");

        // Traditional: HashMap
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : arr) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        System.out.println("Frequency map (traditional): " + freqMap);

        // Stream: groupingBy + counting
        Map<Integer, Long> freqStream = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()));
        System.out.println("Frequency map (stream): " + freqStream);

        // ============================================
        // 2. FIND MOST FREQUENT ELEMENT
        // ============================================
        System.out.println("\n--- 2. Find Most Frequent Element ---");

        // Traditional: iterate map
        int mostFreq = 0;
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFreq = entry.getKey();
            }
        }
        System.out.println("Most frequent (traditional): " + mostFreq + " (count: " + maxCount + ")");

        // Stream: maxBy comparator
        Optional<Integer> mostFreqStream = freqStream.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
        System.out.println("Most frequent (stream): " + mostFreqStream.orElse(null));

        // ============================================
        // 3. FIND LEAST FREQUENT ELEMENT
        // ============================================
        System.out.println("\n--- 3. Find Least Frequent Element ---");

        // Stream: minBy comparator
        Optional<Integer> leastFreqStream = freqStream.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
        System.out.println("Least frequent (stream): " + leastFreqStream.orElse(null));

        // ============================================
        // 4. FIND ELEMENTS WITH SPECIFIC FREQUENCY
        // ============================================
        System.out.println("\n--- 4. Find Elements with Frequency = 2 ---");

        // Stream: filter by count
        List<Integer> freq2 = freqStream.entrySet().stream()
                .filter(entry -> entry.getValue() == 2)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("Elements with frequency 2: " + freq2);

        // ============================================
        // 5. SORT BY FREQUENCY
        // ============================================
        System.out.println("\n--- 5. Sort by Frequency (Descending) ---");

        // Stream: sort by value descending
        List<Integer> sortedByFreq = freqStream.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("Sorted by frequency: " + sortedByFreq);

        // ============================================
        // 6. CODING CHALLENGE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Find top 3 most frequent elements
        List<Integer> top3 = freqStream.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("Top 3 most frequent: " + top3);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Using ArrayList.contains() for frequency check (O(n²))
        // Better: Use HashMap (O(n))

        // Mistake 2: Forgetting to handle null values
        // HashMap allows null keys, but be careful

        // Mistake 3: Using TreeMap when order doesn't matter (slower)
    }

    // Helper for Arrays.toString
    private static String toString(int[] arr) {
        return Arrays.toString(arr);
    }
}
