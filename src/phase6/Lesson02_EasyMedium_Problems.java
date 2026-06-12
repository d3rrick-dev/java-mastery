package phase6;

import java.util.*;

/**
 * LESSON 2: Easy-Medium Interview Problems
 *
 * These problems combine multiple concepts.
 * Focus: Two-pointer, sliding window basics, hash map usage.
 */

public class Lesson02_EasyMedium_Problems {

    public static void main(String[] args) {
        System.out.println("=== LESSON 2: Easy-Medium Interview Problems ===\n");

        // ============================================
        // PROBLEM 1: Two Sum
        // ============================================
        System.out.println("--- Problem 1: Two Sum ---");
        problem1_twoSum();

        // ============================================
        // PROBLEM 2: Move Zeroes
        // ============================================
        System.out.println("\n--- Problem 2: Move Zeroes ---");
        problem2_moveZeroes();

        // ============================================
        // PROBLEM 3: Remove Duplicates from Sorted Array
        // ============================================
        System.out.println("\n--- Problem 3: Remove Duplicates ---");
        problem3_removeDuplicates();

        // ============================================
        // PROBLEM 4: Valid Anagram
        // ============================================
        System.out.println("\n--- Problem 4: Valid Anagram ---");
        problem4_validAnagram();

        // ============================================
        // PROBLEM 5: First Unique Character
        // ============================================
        System.out.println("\n--- Problem 5: First Unique Character ---");
        problem5_firstUniqueChar();
    }

    // ============================================
    // PROBLEM 1: Two Sum
    // ============================================
    private static void problem1_twoSum() {
        int[] arr = {2, 7, 11, 15};
        int target = 9;

        // Thought Process:
        // - Need to find two numbers that sum to target
        // - Brute force: check all pairs O(n²)
        // - Optimized: Use HashMap to store complement O(n)

        // Brute Force: Nested loops
        int[] resultBrute = twoSumBrute(arr, target);
        System.out.println("Two sum (brute): " + Arrays.toString(resultBrute));

        // Optimized: HashMap
        int[] resultOpt = twoSumOptimized(arr, target);
        System.out.println("Two sum (optimized): " + Arrays.toString(resultOpt));

        // Complexity:
        // Brute: Time O(n²), Space O(1)
        // Optimized: Time O(n), Space O(n)
    }

    private static int[] twoSumBrute(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private static int[] twoSumOptimized(int[] arr, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int complement = target - arr[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(arr[i], i);
        }
        return new int[]{-1, -1};
    }

    // ============================================
    // PROBLEM 2: Move Zeroes
    // ============================================
    private static void problem2_moveZeroes() {
        int[] arr = {0, 1, 0, 3, 12};

        // Thought Process:
        // - Move all zeroes to end while maintaining order
        // - Use two pointers: one for current, one for non-zero position

        // Brute Force: Create new array
        int[] resultBrute = moveZeroesBrute(arr);
        System.out.println("Move zeroes (brute): " + Arrays.toString(resultBrute));

        // Optimized: In-place with two pointers
        int[] resultOpt = arr.clone();
        moveZeroesOptimized(resultOpt);
        System.out.println("Move zeroes (optimized): " + Arrays.toString(resultOpt));

        // Complexity:
        // Brute: Time O(n), Space O(n)
        // Optimized: Time O(n), Space O(1)
    }

    private static int[] moveZeroesBrute(int[] arr) {
        int[] result = new int[arr.length];
        int index = 0;
        for (int num : arr) {
            if (num != 0) {
                result[index++] = num;
            }
        }
        return result;
    }

    private static void moveZeroesOptimized(int[] arr) {
        int nonZeroIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                int temp = arr[nonZeroIndex];
                arr[nonZeroIndex] = arr[i];
                arr[i] = temp;
                nonZeroIndex++;
            }
        }
    }

    // ============================================
    // PROBLEM 3: Remove Duplicates from Sorted Array
    // ============================================
    private static void problem3_removeDuplicates() {
        int[] arr = {1, 1, 2, 2, 3, 4, 4, 5};

        // Thought Process:
        // - Array is sorted, duplicates are adjacent
        // - Use two pointers: one for current, one for unique position

        // Brute Force: Use Set
        int[] resultBrute = removeDuplicatesBrute(arr);
        System.out.println("Remove duplicates (brute): " + Arrays.toString(resultBrute));

        // Optimized: Two pointers
        int[] resultOpt = arr.clone();
        int newLength = removeDuplicatesOptimized(resultOpt);
        System.out.println("Remove duplicates (optimized): " +
                Arrays.toString(Arrays.copyOf(resultOpt, newLength)));

        // Complexity:
        // Brute: Time O(n), Space O(n)
        // Optimized: Time O(n), Space O(1)
    }

    private static int[] removeDuplicatesBrute(int[] arr) {
        return Arrays.stream(arr).distinct().toArray();
    }

    private static int removeDuplicatesOptimized(int[] arr) {
        if (arr.length == 0) return 0;
        int uniqueIndex = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[uniqueIndex - 1]) {
                arr[uniqueIndex] = arr[i];
                uniqueIndex++;
            }
        }
        return uniqueIndex;
    }

    // ============================================
    // PROBLEM 4: Valid Anagram
    // ============================================
    private static void problem4_validAnagram() {
        String s1 = "listen";
        String s2 = "silent";
        String s3 = "hello";

        // Thought Process:
        // - Two strings are anagrams if they have same characters with same counts
        // - Sort both strings and compare
        // - Or use frequency map

        // Brute Force: Sort and compare
        System.out.println("Valid anagram (brute): " + isAnagramBrute(s1, s2));
        System.out.println("Valid anagram (brute): " + isAnagramBrute(s1, s3));

        // Optimized: Frequency map
        System.out.println("Valid anagram (optimized): " + isAnagramOptimized(s1, s2));
        System.out.println("Valid anagram (optimized): " + isAnagramOptimized(s1, s3));

        // Complexity:
        // Brute: Time O(n log n), Space O(n)
        // Optimized: Time O(n), Space O(1) - assuming fixed alphabet
    }

    private static boolean isAnagramBrute(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

    private static boolean isAnagramOptimized(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int[] freq = new int[26];
        for (char c : s1.toCharArray()) {
            freq[c - 'a']++;
        }
        for (char c : s2.toCharArray()) {
            freq[c - 'a']--;
        }
        for (int count : freq) {
            if (count != 0) return false;
        }
        return true;
    }

    // ============================================
    // PROBLEM 5: First Unique Character
    // ============================================
    private static void problem5_firstUniqueChar() {
        String str = "leetcode";

        // Thought Process:
        // - Count frequency of each character
        // - Find first character with count 1

        // Brute Force: Nested loops
        System.out.println("First unique (brute): " + firstUniqueBrute(str));

        // Optimized: Frequency map
        System.out.println("First unique (optimized): " + firstUniqueOptimized(str));

        // Complexity:
        // Brute: Time O(n²), Space O(1)
        // Optimized: Time O(n), Space O(1)
    }

    private static int firstUniqueBrute(String str) {
        for (int i = 0; i < str.length(); i++) {
            boolean unique = true;
            for (int j = 0; j < str.length(); j++) {
                if (i != j && str.charAt(i) == str.charAt(j)) {
                    unique = false;
                    break;
                }
            }
            if (unique) return i;
        }
        return -1;
    }

    private static int firstUniqueOptimized(String str) {
        int[] freq = new int[26];
        for (char c : str.toCharArray()) {
            freq[c - 'a']++;
        }
        for (int i = 0; i < str.length(); i++) {
            if (freq[str.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
