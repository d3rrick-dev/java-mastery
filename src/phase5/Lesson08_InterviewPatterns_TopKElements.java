package phase5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LESSON 8: Interview Pattern - Top K Elements
 *
 * THEORY:
 * Finding top K elements is a very common interview pattern.
 * Can be solved using PriorityQueue (Heap) or sorting.
 *
 * APPROACHES:
 * 1. Sort entire array: O(n log n)
 * 2. Use PriorityQueue: O(n log k) - better for large n, small k
 * 3. Use QuickSelect: O(n) average - best theoretical
 */

public class Lesson08_InterviewPatterns_TopKElements {

    public static void main(String[] args) {
        System.out.println("=== LESSON 8: Top K Elements ===\n");

        int[] arr = {3, 1, 5, 12, 2, 11, 7, 4, 6, 9};
        int k = 3;
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("K: " + k);

        // ============================================
        // 1. TOP K LARGEST (SORTING)
        // ============================================
        System.out.println("\n--- 1. Top K Largest (Sorting) ---");

        // Traditional: Sort and take last k
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int[] topKSorted = Arrays.copyOfRange(sorted, sorted.length - k, sorted.length);
        System.out.println("Top " + k + " largest (sorting): " + Arrays.toString(topKSorted));

        // Stream: sort descending, limit k
        int[] topKStream = Arrays.stream(arr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(k)
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println("Top " + k + " largest (stream): " + Arrays.toString(topKStream));

        // ============================================
        // 2. TOP K LARGEST (PRIORITY QUEUE - MIN HEAP)
        // ============================================
        System.out.println("\n--- 2. Top K Largest (Min Heap) ---");

        // Traditional: Min heap of size k
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for (int num : arr) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }
        int[] topKHeap = minHeap.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(topKHeap);  // Sort for display
        System.out.println("Top " + k + " largest (min heap): " + Arrays.toString(topKHeap));

        // ============================================
        // 3. TOP K SMALLEST
        // ============================================
        System.out.println("\n--- 3. Top K Smallest ---");

        // Stream: sort ascending, limit k
        int[] topKSmallest = Arrays.stream(arr)
                .boxed()
                .sorted()
                .limit(k)
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println("Top " + k + " smallest (stream): " + Arrays.toString(topKSmallest));

        // Traditional: Max heap of size k
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Collections.reverseOrder());
        for (int num : arr) {
            if (maxHeap.size() < k) {
                maxHeap.offer(num);
            } else if (num < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(num);
            }
        }
        int[] topKSmallestHeap = maxHeap.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(topKSmallestHeap);
        System.out.println("Top " + k + " smallest (max heap): " + Arrays.toString(topKSmallestHeap));

        // ============================================
        // 4. TOP K FREQUENT ELEMENTS
        // ============================================
        System.out.println("\n--- 4. Top K Frequent Elements ---");

        int[] freqArr = {1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4, 5};
        int kFreq = 2;
        System.out.println("Array: " + Arrays.toString(freqArr));
        System.out.println("K: " + kFreq);

        // Stream: frequency map, sort by count, take top k
        List<Integer> topKFreq = Arrays.stream(freqArr)
                .boxed()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(kFreq)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("Top " + kFreq + " frequent: " + topKFreq);

        // ============================================
        // 5. CODING CHALLENGE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Find Kth largest element
        int kth = 4;
        int kthLargest = Arrays.stream(arr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .skip(kth - 1)
                .findFirst()
                .orElse(null);
        System.out.println(kth + "th largest element: " + kthLargest);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Sorting when k is small (inefficient)
        // Better: Use PriorityQueue for O(n log k)

        // Mistake 2: Using max heap for top k largest (inefficient)
        // Better: Use min heap for top k largest

        // Mistake 3: Not handling k > array length
        if (k > arr.length) {
            System.out.println("K cannot be larger than array length!");
        }
    }

    // Helper for Arrays.toString
    private static String toString(int[] arr) {
        return Arrays.toString(arr);
    }
}
