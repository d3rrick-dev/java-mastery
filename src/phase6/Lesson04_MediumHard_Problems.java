package phase6;

import java.util.*;

/**
 * LESSON 4: Medium-Hard Interview Problems
 *
 * These problems combine multiple advanced techniques.
 * Focus: Dynamic programming, backtracking, complex data structures.
 */

public class Lesson04_MediumHard_Problems {

    public static void main(String[] args) {
        System.out.println("=== LESSON 4: Medium-Hard Interview Problems ===\n");

        // ============================================
        // PROBLEM 1: Longest Increasing Subsequence
        // ============================================
        System.out.println("--- Problem 1: Longest Increasing Subsequence ---");
        problem1_LIS();

        // ============================================
        // PROBLEM 2: Coin Change
        // ============================================
        System.out.println("\n--- Problem 2: Coin Change ---");
        problem2_coinChange();

        // ============================================
        // PROBLEM 3: Word Break
        // ============================================
        System.out.println("\n--- Problem 3: Word Break ---");
        problem3_wordBreak();

        // ============================================
        // PROBLEM 4: Combination Sum
        // ============================================
        System.out.println("\n--- Problem 4: Combination Sum ---");
        problem4_combinationSum();

        // ============================================
        // PROBLEM 5: Permutations
        // ============================================
        System.out.println("\n--- Problem 5: Permutations ---");
        problem5_permutations();
    }

    // ============================================
    // PROBLEM 1: Longest Increasing Subsequence (LIS)
    // ============================================
    private static void problem1_LIS() {
        int[] arr = {10, 9, 2, 5, 3, 7, 101, 18};

        // Thought Process:
        // - Find longest subsequence where elements are in increasing order
        // - DP approach: dp[i] = length of LIS ending at i
        // - For each element, check all previous elements

        // Brute Force: Check all subsequences (2^n)
        // Not practical for large arrays

        // Optimized: DP with binary search (Patience Sorting)
        System.out.println("LIS length (DP): " + lengthOfLIS(arr));
        System.out.println("LIS (DP): " + getLIS(arr));

        // Complexity:
        // DP: Time O(n²), Space O(n)
        // Binary Search: Time O(n log n), Space O(n)
    }

    private static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    private static List<Integer> getLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);
        int maxIdx = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > dp[maxIdx]) {
                maxIdx = i;
            }
        }

        // Reconstruct LIS
        List<Integer> lis = new ArrayList<>();
        int idx = maxIdx;
        while (idx != -1) {
            lis.add(nums[idx]);
            idx = prev[idx];
        }
        Collections.reverse(lis);
        return lis;
    }

    // ============================================
    // PROBLEM 2: Coin Change
    // ============================================
    private static void problem2_coinChange() {
        int[] coins = {1, 2, 5};
        int amount = 11;

        // Thought Process:
        // - Find minimum number of coins to make amount
        // - DP: dp[i] = min coins to make amount i
        // - dp[i] = min(dp[i - coin] + 1) for all coins

        // Brute Force: Try all combinations (exponential)
        // Not practical

        // Optimized: DP
        System.out.println("Min coins (DP): " + coinChange(coins, amount));

        // Complexity:
        // Time: O(amount * coins.length)
        // Space: O(amount)
    }

    private static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ============================================
    // PROBLEM 3: Word Break
    // ============================================
    private static void problem3_wordBreak() {
        String s = "leetcode";
        List<String> wordDict = List.of("leet", "code");

        // Thought Process:
        // - Can we segment s into words from dictionary?
        // - DP: dp[i] = can segment s[0..i-1]
        // - dp[i] = true if dp[j] && s[j..i-1] in dict

        // Optimized: DP
        System.out.println("Can break (DP): " + wordBreak(s, wordDict));

        // Complexity:
        // Time: O(n² * m) where n = s.length, m = max word length
        // Space: O(n)
    }

    private static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    // ============================================
    // PROBLEM 4: Combination Sum
    // ============================================
    private static void problem4_combinationSum() {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;

        // Thought Process:
        // - Find all combinations that sum to target
        // - Each number can be used unlimited times
        // - Backtracking: try each number, recurse

        // Optimized: Backtracking
        System.out.println("Combination sum: " + combinationSum(candidates, target));

        // Complexity:
        // Time: O(2^t) where t is target (worst case)
        // Space: O(t) for recursion stack
    }

    private static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] candidates, int target, int start,
                                  List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            current.add(candidates[i]);
            backtrack(candidates, target - candidates[i], i, current, result);
            current.remove(current.size() - 1);
        }
    }

    // ============================================
    // PROBLEM 5: Permutations
    // ============================================
    private static void problem5_permutations() {
        int[] nums = {1, 2, 3};

        // Thought Process:
        // - Generate all permutations of array
        // - Backtracking: swap elements, recurse, swap back

        // Optimized: Backtracking with swapping
        System.out.println("Permutations: " + permute(nums));

        // Complexity:
        // Time: O(n * n!) - n! permutations, each of length n
        // Space: O(n!) for result, O(n) for recursion
    }

    private static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermute(nums, 0, result);
        return result;
    }

    private static void backtrackPermute(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> perm = new ArrayList<>();
            for (int num : nums) {
                perm.add(num);
            }
            result.add(perm);
            return;
        }
        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            backtrackPermute(nums, start + 1, result);
            swap(nums, start, i);  // backtrack
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
