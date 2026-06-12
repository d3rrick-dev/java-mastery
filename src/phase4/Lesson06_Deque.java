package phase4;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LESSON 6: Deque (Double Ended Queue)
 *
 * THEORY:
 * Deque is a double-ended queue that supports insertion and removal at both ends.
 * It can be used as both Queue (FIFO) and Stack (LIFO).
 *
 * IMPLEMENTATIONS:
 * - ArrayDeque (preferred - fast, no capacity restrictions)
 * - LinkedList (also implements Deque)
 *
 * INTERNAL WORKING:
 * - ArrayDeque: Resizable circular array
 * - Two pointers: head and tail
 * - Grows dynamically when needed
 *
 * TIME COMPLEXITY (ArrayDeque):
 * - addFirst(E e) - O(1) amortized
 * - addLast(E e) - O(1) amortized
 * - removeFirst() - O(1)
 * - removeLast() - O(1)
 * - getFirst() - O(1)
 * - getLast() - O(1)
 * - offerFirst(E e) - O(1) amortized
 * - offerLast(E e) - O(1) amortized
 * - pollFirst() - O(1)
 * - pollLast() - O(1)
 * - peekFirst() - O(1)
 * - peekLast() - O(1)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - addFirst(E e)    : Add to front
 * - addLast(E e)     : Add to end
 * - offerFirst(E e)  : Add to front (returns false if full)
 * - offerLast(E e)   : Add to end (returns false if full)
 * - removeFirst()    : Remove and return front
 * - removeLast()     : Remove and return end
 * - pollFirst()      : Remove front (null if empty)
 * - pollLast()       : Remove end (null if empty)
 * - getFirst()       : View front
 * - getLast()        : View end
 * - peekFirst()      : View front (null if empty)
 * - peekLast()       : View end (null if empty)
 * - push(E e)        : Add to front (same as addFirst)
 * - pop()            : Remove front (same as removeFirst)
 */

public class Lesson06_Deque {

    public static void main(String[] args) {
        System.out.println("=== LESSON 6: Deque ===\n");

        // ============================================
        // 1. CREATING DEQUE
        // ============================================
        System.out.println("--- 1. Creating Deque ---");

        Deque<String> deque = new ArrayDeque<>();
        System.out.println("Empty deque: " + deque);

        // ============================================
        // 2. ADDING TO BOTH ENDS
        // ============================================
        System.out.println("\n--- 2. Adding to Both Ends ---");

        deque.addFirst("First");     // Add to front
        deque.addLast("Last");       // Add to end
        deque.offerFirst("Front");   // Add to front (returns false if full)
        deque.offerLast("Back");     // Add to end (returns false if full)
        System.out.println("After adds: " + deque);

        // ============================================
        // 3. REMOVING FROM BOTH ENDS
        // ============================================
        System.out.println("\n--- 3. Removing from Both Ends ---");

        System.out.println("Remove first: " + deque.removeFirst());
        System.out.println("After removeFirst: " + deque);
        System.out.println("Remove last: " + deque.removeLast());
        System.out.println("After removeLast: " + deque);

        // pollFirst() vs removeFirst()
        // pollFirst() returns null if empty
        // removeFirst() throws NoSuchElementException if empty

        // ============================================
        // 4. VIEWING BOTH ENDS
        // ============================================
        System.out.println("\n--- 4. Viewing Both Ends ---");

        deque.addFirst("A");
        deque.addLast("B");
        System.out.println("First: " + deque.getFirst());
        System.out.println("Last: " + deque.getLast());
        System.out.println("Peek first: " + deque.peekFirst());
        System.out.println("Peek last: " + deque.peekLast());
        System.out.println("Deque unchanged: " + deque);

        // ============================================
        // 5. USING AS STACK
        // ============================================
        System.out.println("\n--- 5. Using as Stack ---");

        Deque<String> stack = new ArrayDeque<>();
        stack.push("First");    // Same as addFirst
        stack.push("Second");
        stack.push("Third");
        System.out.println("Stack: " + stack);
        System.out.println("Pop: " + stack.pop());      // Same as removeFirst
        System.out.println("After pop: " + stack);
        System.out.println("Peek: " + stack.peek());    // Same as peekFirst

        // ============================================
        // 6. USING AS QUEUE
        // ============================================
        System.out.println("\n--- 6. Using as Queue ---");

        Deque<String> queue = new ArrayDeque<>();
        queue.offer("First");   // Same as offerLast
        queue.offer("Second");
        queue.offer("Third");
        System.out.println("Queue: " + queue);
        System.out.println("Poll: " + queue.poll());    // Same as pollFirst
        System.out.println("After poll: " + queue);
        System.out.println("Peek: " + queue.peek());    // Same as peekFirst

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Check if string is palindrome
        String palindrome = "racecar";
        String notPalindrome = "hello";

        System.out.println(palindrome + " is palindrome: " + isPalindrome(palindrome));
        System.out.println(notPalindrome + " is palindrome: " + isPalindrome(notPalindrome));

        // Challenge: Sliding window maximum
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println("\nSliding window maximum (k=" + k + "):");
        int[] maxInWindow = maxSlidingWindow(nums, k);
        for (int max : maxInWindow) {
            System.out.println("  " + max);
        }

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using null elements
        // ArrayDeque doesn't allow null elements
        Deque<String> deque2 = new ArrayDeque<>();
        // deque2.addFirst(null); // Throws NullPointerException!

        // Mistake 2: Confusing add/offer and remove/poll
        // add/remove throw exceptions on failure
        // offer/poll return false/null on failure
        // Use offer/poll for safer code

        // Mistake 3: Using Deque when you only need Queue or Stack
        // If you only need FIFO, use Queue interface
        // If you only need LIFO, use Deque with push/pop
    }

    // Check if string is palindrome using deque
    private static boolean isPalindrome(String str) {
        Deque<Character> deque = new ArrayDeque<>();
        for (char c : str.toCharArray()) {
            deque.addLast(c);
        }

        while (deque.size() > 1) {
            if (!deque.removeFirst().equals(deque.removeLast())) {
                return false;
            }
        }
        return true;
    }

    // Sliding window maximum using deque
    private static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // Store indices

        for (int i = 0; i < n; i++) {
            // Remove indices out of current window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Add to result when window is formed
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
