package phase6;

import java.util.*;

/**
 * LESSON 5: Interview Level Problems
 *
 * These are the hardest problems you'll see in interviews.
 * Focus: Complex algorithms, multiple data structures, optimization.
 */

public class Lesson05_InterviewLevel_Problems {

    public static void main(String[] args) {
        System.out.println("=== LESSON 5: Interview Level Problems ===\n");

        // ============================================
        // PROBLEM 1: Merge K Sorted Lists
        // ============================================
        System.out.println("--- Problem 1: Merge K Sorted Lists ---");
        problem1_mergeKSortedLists();

        // ============================================
        // PROBLEM 2: Trapping Rain Water
        // ============================================
        System.out.println("\n--- Problem 2: Trapping Rain Water ---");
        problem2_trap();

        // ============================================
        // PROBLEM 3: Median of Two Sorted Arrays
        // ============================================
        System.out.println("\n--- Problem 3: Median of Two Sorted Arrays ---");
        problem3_findMedianSortedArrays();

        // ============================================
        // PROBLEM 4: LRU Cache
        // ============================================
        System.out.println("\n--- Problem 4: LRU Cache ---");
        problem4_LRUCache();

        // ============================================
        // PROBLEM 5: Word Search
        // ============================================
        System.out.println("\n--- Problem 5: Word Search ---");
        problem5_exist();
    }

    // ============================================
    // PROBLEM 1: Merge K Sorted Lists
    // ============================================
    private static void problem1_mergeKSortedLists() {
        // Create sample linked lists
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));
        ListNode[] lists = {l1, l2, l3};

        // Thought Process:
        // - Merge k sorted linked lists into one sorted list
        // - Use min heap to always get smallest element
        // - Or merge pairs iteratively

        // Optimized: Min Heap
        System.out.println("Merged list: " + mergeKLists(lists));

        // Complexity:
        // Time: O(N log k) where N = total nodes, k = number of lists
        // Space: O(k) for heap
    }

    private static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        for (ListNode node : lists) {
            if (node != null) {
                heap.offer(node);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            current.next = node;
            current = current.next;
            if (node.next != null) {
                heap.offer(node.next);
            }
        }
        return dummy.next;
    }

    // ============================================
    // PROBLEM 2: Trapping Rain Water
    // ============================================
    private static void problem2_trap() {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};

        // Thought Process:
        // - For each position, water trapped = min(max_left, max_right) - height
        // - Precompute max_left and max_right arrays
        // - Or use two pointers

        // Optimized: Two pointers
        System.out.println("Trapped water: " + trap(height));

        // Complexity:
        // Time: O(n)
        // Space: O(1)
    }

    private static int trap(int[] height) {
        if (height.length == 0) return 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }
        return water;
    }

    // ============================================
    // PROBLEM 3: Median of Two Sorted Arrays
    // ============================================
    private static void problem3_findMedianSortedArrays() {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};

        // Thought Process:
        // - Find median of two sorted arrays
        // - Binary search on smaller array
        // - Partition both arrays such that left half <= right half

        // Optimized: Binary search
        System.out.println("Median: " + findMedianSortedArrays(nums1, nums2));

        // Complexity:
        // Time: O(log(min(m, n)))
        // Space: O(1)
    }

    private static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;
        while (left <= right) {
            int i = (left + right) / 2;
            int j = (m + n + 1) / 2 - i;
            if (i < m && nums2[j - 1] > nums1[i]) {
                left = i + 1;
            } else if (i > 0 && nums1[i - 1] > nums2[j]) {
                right = i - 1;
            } else {
                int maxLeft;
                if (i == 0) maxLeft = nums2[j - 1];
                else if (j == 0) maxLeft = nums1[i - 1];
                else maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);

                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight;
                if (i == m) minRight = nums2[j];
                else if (j == n) minRight = nums1[i];
                else minRight = Math.min(nums1[i], nums2[j]);

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    // ============================================
    // PROBLEM 4: LRU Cache
    // ============================================
    private static void problem4_LRUCache() {
        // Thought Process:
        // - LRU = Least Recently Used
        // - Need O(1) get and put
        // - Use HashMap + Doubly Linked List

        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println("Get 1: " + cache.get(1));  // 1
        cache.put(3, 3);  // Evicts key 2
        System.out.println("Get 2: " + cache.get(2));  // -1
        cache.put(4, 4);  // Evicts key 1
        System.out.println("Get 1: " + cache.get(1));  // -1
        System.out.println("Get 3: " + cache.get(3));  // 3
        System.out.println("Get 4: " + cache.get(4));  // 4

        // Complexity:
        // Time: O(1) for both get and put
        // Space: O(capacity)
    }

    static class LRUCache {
        private final int capacity;
        private final Map<Integer, Node> map;
        private final Node head, tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;
            Node node = map.get(key);
            remove(node);
            insert(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                remove(map.get(key));
            }
            Node node = new Node(key, value);
            insert(node);
            map.put(key, node);
            if (map.size() > capacity) {
                map.remove(tail.prev.key);
                remove(tail.prev);
            }
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void insert(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        static class Node {
            int key, value;
            Node prev, next;
            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }

    // ============================================
    // PROBLEM 5: Word Search
    // ============================================
    private static void problem5_exist() {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";

        // Thought Process:
        // - Search for word in 2D grid
        // - Can move up, down, left, right
        // - Can't reuse same cell
        // - Backtracking: try each direction

        // Optimized: Backtracking with DFS
        System.out.println("Word exists: " + exist(board, word));

        // Complexity:
        // Time: O(m * n * 4^L) where m,n = board size, L = word length
        // Space: O(L) for recursion stack
    }

    private static boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, i, j, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, String word, int i, int j,
                               int index, boolean[][] visited) {
        if (index == word.length()) return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;
        if (visited[i][j] || board[i][j] != word.charAt(index)) return false;

        visited[i][j] = true;
        boolean found = dfs(board, word, i + 1, j, index + 1, visited) ||
                dfs(board, word, i - 1, j, index + 1, visited) ||
                dfs(board, word, i, j + 1, index + 1, visited) ||
                dfs(board, word, i, j - 1, index + 1, visited);
        visited[i][j] = false;
        return found;
    }

    // ListNode class
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode curr = this;
            while (curr != null) {
                sb.append(curr.val);
                if (curr.next != null) sb.append(" -> ");
                curr = curr.next;
            }
            return sb.toString();
        }
    }
}
