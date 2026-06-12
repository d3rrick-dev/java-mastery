package phase6;

import java.util.*;
import java.util.stream.IntStream;

/**
 * LESSON 1: Easy Interview Problems
 *
 * These problems test basic Java and Stream knowledge.
 * Focus: Simple iteration, filtering, basic data structures.
 */

public class Lesson01_Easy_Problems {

    public static void main(String[] args) {
        System.out.println("=== LESSON 1: Easy Interview Problems ===\n");

        // ============================================
        // PROBLEM 1: Find Even Numbers
        // ============================================
        System.out.println("--- Problem 1: Find Even Numbers ---");
        problem1_findEvenNumbers();

        // ============================================
        // PROBLEM 2: Count Vowels in String
        // ============================================
        System.out.println("\n--- Problem 2: Count Vowels in String ---");
        problem2_countVowels();

        // ============================================
        // PROBLEM 3: Reverse a String
        // ============================================
        System.out.println("\n--- Problem 3: Reverse a String ---");
        problem3_reverseString();

        // ============================================
        // PROBLEM 4: Find Maximum in Array
        // ============================================
        System.out.println("\n--- Problem 4: Find Maximum in Array ---");
        problem4_findMax();

        // ============================================
        // PROBLEM 5: Check if Array is Sorted
        // ============================================
        System.out.println("\n--- Problem 5: Check if Array is Sorted ---");
        problem5_checkSorted();
    }

    // ============================================
    // PROBLEM 1: Find Even Numbers
    // ============================================
    private static void problem1_findEvenNumbers() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Thought Process:
        // - Iterate through array
        // - Check if each number is divisible by 2
        // - Collect even numbers

        // Brute Force: Traditional loop
        List<Integer> evensBrute = new ArrayList<>();
        for (int num : arr) {
            if (num % 2 == 0) {
                evensBrute.add(num);
            }
        }
        System.out.println("Even numbers (brute): " + evensBrute);

        // Optimized: Stream
        List<Integer> evensStream = Arrays.stream(arr)
                .filter(n -> n % 2 == 0)
                .boxed()
                .toList();
        System.out.println("Even numbers (stream): " + evensStream);

        // Complexity:
        // Time: O(n) - single pass
        // Space: O(k) where k is number of evens
    }

    // ============================================
    // PROBLEM 2: Count Vowels in String
    // ============================================
    private static void problem2_countVowels() {
        String str = "Hello World";

        // Thought Process:
        // - Convert string to lowercase
        // - Check each character if it's a vowel
        // - Count vowels

        // Brute Force: Traditional loop
        int countBrute = 0;
        String vowels = "aeiou";
        for (char c : str.toLowerCase().toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                countBrute++;
            }
        }
        System.out.println("Vowel count (brute): " + countBrute);

        // Optimized: Stream
        long countStream = str.toLowerCase().chars()
                .filter(c -> "aeiou".indexOf(c) != -1)
                .count();
        System.out.println("Vowel count (stream): " + countStream);

        // Complexity:
        // Time: O(n) where n is string length
        // Space: O(1)
    }

    // ============================================
    // PROBLEM 3: Reverse a String
    // ============================================
    private static void problem3_reverseString() {
        String str = "Hello";

        // Thought Process:
        // - Convert string to character array
        // - Reverse the array
        // - Convert back to string

        // Brute Force: Traditional loop
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = chars.length - 1; i >= 0; i--) {
            sb.append(chars[i]);
        }
        System.out.println("Reversed (brute): " + sb);

        // Optimized: StringBuilder reverse
        System.out.println("Reversed (StringBuilder): " + new StringBuilder(str).reverse());

        // Stream approach (less common but valid)
        String reversedStream = new StringBuilder(str)
                .reverse()
                .toString();
        System.out.println("Reversed (stream-like): " + reversedStream);

        // Complexity:
        // Time: O(n)
        // Space: O(n)
    }

    // ============================================
    // PROBLEM 4: Find Maximum in Array
    // ============================================
    private static void problem4_findMax() {
        int[] arr = {3, 1, 5, 2, 4};

        // Thought Process:
        // - Initialize max with first element
        // - Compare each element with max
        // - Update max if current is larger

        // Brute Force: Traditional loop
        int maxBrute = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxBrute) {
                maxBrute = arr[i];
            }
        }
        System.out.println("Maximum (brute): " + maxBrute);

        // Optimized: Stream
        OptionalInt maxStream = Arrays.stream(arr).max();
        System.out.println("Maximum (stream): " + maxStream.orElse(Integer.MIN_VALUE));

        // Complexity:
        // Time: O(n)
        // Space: O(1)
    }

    // ============================================
    // PROBLEM 5: Check if Array is Sorted
    // ============================================
    private static void problem5_checkSorted() {
        int[] sorted = {1, 2, 3, 4, 5};
        int[] unsorted = {1, 3, 2, 4, 5};

        // Thought Process:
        // - Compare each element with previous
        // - If any element is smaller than previous, not sorted

        // Brute Force: Traditional loop
        System.out.println("Is sorted (brute): " + isSortedBrute(sorted));
        System.out.println("Is sorted (brute): " + isSortedBrute(unsorted));

        // Stream: Check adjacent pairs
        System.out.println("Is sorted (stream): " + isSortedStream(sorted));
        System.out.println("Is sorted (stream): " + isSortedStream(unsorted));

        // Complexity:
        // Time: O(n)
        // Space: O(1)
    }

    // Helper method for isSortedBrute
    private static boolean isSortedBrute(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    // Helper method for isSortedStream
    private static boolean isSortedStream(int[] arr) {
        return IntStream.range(1, arr.length)
                .allMatch(i -> arr[i] >= arr[i - 1]);
    }
}
