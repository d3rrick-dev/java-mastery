package phase6;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LESSON 3: Medium Interview Problems
 *
 * These problems require more complex logic.
 * Focus: Sliding window, two pointers, hash maps with complex logic.
 */

public class Lesson03_Medium_Problems {

    public static void main(String[] args) {
        System.out.println("=== LESSON 3: Medium Interview Problems ===\n");

        // ============================================
        // PROBLEM 1: Longest Substring Without Repeating Characters
        // ============================================
        System.out.println("--- Problem 1: Longest Substring Without Repeating ---");
        problem1_longestSubstring();

        // ============================================
        // PROBLEM 2: Group Anagrams
        // ============================================
        System.out.println("\n--- Problem 2: Group Anagrams ---");
        problem2_groupAnagrams();

        // ============================================
        // PROBLEM 3: Product of Array Except Self
        // ============================================
        System.out.println("\n--- Problem 3: Product of Array Except Self ---");
        problem3_productExceptSelf();

        // ============================================
        // PROBLEM 4: Longest Palindromic Substring
        // ============================================
        System.out.println("\n--- Problem 4: Longest Palindromic Substring ---");
        problem4_longestPalindrome();

        // ============================================
        // PROBLEM 5: Container With Most Water
        // ============================================
        System.out.println("\n--- Problem 5: Container With Most Water ---");
        problem5_maxArea();
    }

    // ============================================
    // PROBLEM 1: Longest Substring Without Repeating Characters
    // ============================================
    private static void problem1_longestSubstring() {
        String str = "abcabcbb";

        // Thought Process:
        // - Sliding window with hash set
        // - Expand right pointer, shrink left when duplicate found
        // - Track maximum window size

        // Brute Force: Check all substrings
        System.out.println("Longest substring (brute): " + longestSubstringBrute(str));

        // Optimized: Sliding window
        System.out.println("Longest substring (optimized): " + longestSubstringOptimized(str));

        // Complexity:
        // Brute: Time O(n³), Space O(n)
        // Optimized: Time O(n), Space O(min(n, m)) where m is charset size
    }

    private static int longestSubstringBrute(String s) {
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();
            for (int j = i; j < s.length(); j++) {
                if (!set.add(s.charAt(j))) {
                    break;
                }
                max = Math.max(max, j - i + 1);
            }
        }
        return max;
    }

    private static int longestSubstringOptimized(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0, max = 0;
        for (int right = 0; right < s.length(); right++) {
            while (!set.add(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    // ============================================
    // PROBLEM 2: Group Anagrams
    // ============================================
    private static void problem2_groupAnagrams() {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

        // Thought Process:
        // - Anagrams have same sorted characters
        // - Group by sorted string as key

        // Brute Force: Compare each pair
        System.out.println("Group anagrams (brute): " + groupAnagramsBrute(strs));

        // Optimized: HashMap with sorted key
        System.out.println("Group anagrams (optimized): " + groupAnagramsOptimized(strs));

        // Stream approach
        System.out.println("Group anagrams (stream): " + groupAnagramsStream(strs));

        // Complexity:
        // Brute: Time O(n² * k log k), Space O(n * k)
        // Optimized: Time O(n * k log k), Space O(n * k)
        // where n is number of strings, k is max string length
    }

    private static List<List<String>> groupAnagramsBrute(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        boolean[] visited = new boolean[strs.length];
        for (int i = 0; i < strs.length; i++) {
            if (visited[i]) continue;
            List<String> group = new ArrayList<>();
            group.add(strs[i]);
            visited[i] = true;
            for (int j = i + 1; j < strs.length; j++) {
                if (!visited[j] && isAnagram(strs[i], strs[j])) {
                    group.add(strs[j]);
                    visited[j] = true;
                }
            }
            result.add(group);
        }
        return result;
    }

    private static boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

    private static List<List<String>> groupAnagramsOptimized(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    private static List<List<String>> groupAnagramsStream(String[] strs) {
        return Arrays.stream(strs)
                .collect(Collectors.groupingBy(s -> {
                    char[] chars = s.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars);
                }))
                .values().stream()
                .toList();
    }

    // ============================================
    // PROBLEM 3: Product of Array Except Self
    // ============================================
    private static void problem3_productExceptSelf() {
        int[] arr = {1, 2, 3, 4};

        // Thought Process:
        // - For each element, product of all others
        // - Can't use division
        // - Use prefix and suffix products

        // Brute Force: Nested loops
        System.out.println("Product except self (brute): " +
                Arrays.toString(productExceptSelfBrute(arr)));

        // Optimized: Prefix + Suffix
        System.out.println("Product except self (optimized): " +
                Arrays.toString(productExceptSelfOptimized(arr)));

        // Complexity:
        // Brute: Time O(n²), Space O(1)
        // Optimized: Time O(n), Space O(n) (or O(1) if output doesn't count)
    }

    private static int[] productExceptSelfBrute(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int product = 1;
            for (int j = 0; j < arr.length; j++) {
                if (i != j) {
                    product *= arr[j];
                }
            }
            result[i] = product;
        }
        return result;
    }

    private static int[] productExceptSelfOptimized(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];

        // Left products
        int leftProduct = 1;
        for (int i = 0; i < n; i++) {
            result[i] = leftProduct;
            leftProduct *= arr[i];
        }

        // Right products
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= arr[i];
        }

        return result;
    }

    // ============================================
    // PROBLEM 4: Longest Palindromic Substring
    // ============================================
    private static void problem4_longestPalindrome() {
        String str = "babad";

        // Thought Process:
        // - Expand around each center
        // - Two types of centers: single char and between chars

        // Brute Force: Check all substrings
        System.out.println("Longest palindrome (brute): " + longestPalindromeBrute(str));

        // Optimized: Expand around center
        System.out.println("Longest palindrome (optimized): " + longestPalindromeOptimized(str));

        // Complexity:
        // Brute: Time O(n³), Space O(1)
        // Optimized: Time O(n²), Space O(1)
    }

    private static String longestPalindromeBrute(String s) {
        String longest = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                String sub = s.substring(i, j + 1);
                if (isPalindrome(sub) && sub.length() > longest.length()) {
                    longest = sub;
                }
            }
        }
        return longest;
    }

    private static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static String longestPalindromeOptimized(String s) {
        if (s.length() < 2) return s;
        int start = 0, maxLen = 1;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);      // Odd length
            int len2 = expandAroundCenter(s, i, i + 1);  // Even length
            int len = Math.max(len1, len2);
            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;
            }
        }
        return s.substring(start, start + maxLen);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    // ============================================
    // PROBLEM 5: Container With Most Water
    // ============================================
    private static void problem5_maxArea() {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};

        // Thought Process:
        // - Two pointers at both ends
        // - Move the pointer with smaller height
        // - Track maximum area

        // Brute Force: Check all pairs
        System.out.println("Max area (brute): " + maxAreaBrute(heights));

        // Optimized: Two pointers
        System.out.println("Max area (optimized): " + maxAreaOptimized(heights));

        // Complexity:
        // Brute: Time O(n²), Space O(1)
        // Optimized: Time O(n), Space O(1)
    }

    private static int maxAreaBrute(int[] heights) {
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                int area = Math.min(heights[i], heights[j]) * (j - i);
                max = Math.max(max, area);
            }
        }
        return max;
    }

    private static int maxAreaOptimized(int[] heights) {
        int left = 0, right = heights.length - 1;
        int max = 0;
        while (left < right) {
            int area = Math.min(heights[left], heights[right]) * (right - left);
            max = Math.max(max, area);
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }
}
