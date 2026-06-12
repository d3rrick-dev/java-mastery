package phase4;

import java.util.LinkedList;
import java.util.List;

/**
 * LESSON 3: LinkedList
 *
 * THEORY:
 * LinkedList is a doubly-linked list implementation of List and Deque interfaces.
 * Each element (node) contains data and references to previous and next nodes.
 *
 * INTERNAL WORKING:
 * - Each node has: data, next pointer, previous pointer
 * - First node's previous = null, last node's next = null
 * - Maintains insertion order
 * - Allows duplicate elements
 * - Allows null elements
 *
 * TIME COMPLEXITY:
 * - add(E e) - O(1) - add to end
 * - add(int index, E e) - O(n) - need to traverse to index
 * - get(int index) - O(n) - need to traverse from head/tail
 * - set(int index, E e) - O(n)
 * - remove(int index) - O(n) - need to traverse, but removal is O(1) once found
 * - remove(Object o) - O(n)
 * - contains(Object o) - O(n)
 * - addFirst(E e) - O(1)
 * - addLast(E e) - O(1)
 * - getFirst() - O(1)
 * - getLast() - O(1)
 * - removeFirst() - O(1)
 * - removeLast() - O(1)
 *
 * SPACE COMPLEXITY: O(n) - extra space for pointers
 *
 * WHEN TO USE:
 * - Frequent insertions/deletions at beginning or end
 * - Implementing queue/deque
 * - When you need both List and Deque operations
 *
 * WHEN NOT TO USE:
 * - Random access (get by index) - use ArrayList
 * - Memory-constrained environments (extra pointer overhead)
 */

public class Lesson03_LinkedList {

    public static void main(String[] args) {
        System.out.println("=== LESSON 3: LinkedList ===\n");

        // ============================================
        // 1. CREATING LINKEDLIST
        // ============================================
        System.out.println("--- 1. Creating LinkedList ---");

        // Empty LinkedList
        LinkedList<String> list1 = new LinkedList<>();
        System.out.println("Empty list: " + list1);

        // LinkedList from collection
        LinkedList<String> list2 = new LinkedList<>(List.of("A", "B", "C"));
        System.out.println("List from collection: " + list2);

        // ============================================
        // 2. ADDING ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Adding Elements ---");

        LinkedList<Integer> numbers = new LinkedList<>();
        numbers.add(10);           // Add to end - O(1)
        numbers.add(20);
        numbers.add(30);
        System.out.println("After add: " + numbers);

        numbers.addFirst(5);       // Add to beginning - O(1)
        System.out.println("After addFirst: " + numbers);

        numbers.addLast(40);       // Add to end - O(1)
        System.out.println("After addLast: " + numbers);

        numbers.add(2, 99);        // Add at index 2 - O(n)
        System.out.println("After add at index 2: " + numbers);

        // ============================================
        // 3. ACCESSING ELEMENTS
        // ============================================
        System.out.println("\n--- 3. Accessing Elements ---");

        System.out.println("First: " + numbers.getFirst());      // O(1)
        System.out.println("Last: " + numbers.getLast());        // O(1)
        System.out.println("At index 2: " + numbers.get(2));     // O(n)

        // peek() - get first without removing
        System.out.println("Peek first: " + numbers.peekFirst());
        System.out.println("Peek last: " + numbers.peekLast());

        // ============================================
        // 4. REMOVING ELEMENTS
        // ============================================
        System.out.println("\n--- 4. Removing Elements ---");

        System.out.println("Before remove: " + numbers);
        numbers.removeFirst();     // Remove first - O(1)
        System.out.println("After removeFirst: " + numbers);

        numbers.removeLast();      // Remove last - O(1)
        System.out.println("After removeLast: " + numbers);

        numbers.remove(1);         // Remove at index - O(n)
        System.out.println("After remove index 1: " + numbers);

        numbers.remove(Integer.valueOf(99));  // Remove by value - O(n)
        System.out.println("After remove value 99: " + numbers);

        // poll() - remove and return first, returns null if empty
        Integer polled = numbers.pollFirst();
        System.out.println("Polled first: " + polled + ", list: " + numbers);

        // ============================================
        // 5. USING AS QUEUE (FIFO)
        // ============================================
        System.out.println("\n--- 5. Using as Queue (FIFO) ---");

        LinkedList<String> queue = new LinkedList<>();
        queue.offer("First");      // Add to end
        queue.offer("Second");
        queue.offer("Third");
        System.out.println("Queue: " + queue);

        System.out.println("Poll: " + queue.poll());      // Remove from front
        System.out.println("Peek: " + queue.peek());      // View front
        System.out.println("Queue after operations: " + queue);

        // ============================================
        // 6. USING AS STACK (LIFO)
        // ============================================
        System.out.println("\n--- 6. Using as Stack (LIFO) ---");

        LinkedList<String> stack = new LinkedList<>();
        stack.push("First");       // Add to top
        stack.push("Second");
        stack.push("Third");
        System.out.println("Stack: " + stack);

        System.out.println("Pop: " + stack.pop());         // Remove from top
        System.out.println("Peek: " + stack.peek());       // View top
        System.out.println("Stack after operations: " + stack);

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Implement a browser history (back/forward)
        BrowserHistory history = new BrowserHistory();
        history.visit("google.com");
        history.visit("github.com");
        history.visit("stackoverflow.com");
        System.out.println("Current: " + history.current());
        System.out.println("Back: " + history.back());
        System.out.println("Forward: " + history.forward());
        System.out.println("Current: " + history.current());

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Using LinkedList when ArrayList is better
        // LinkedList is slower for random access
        // Use ArrayList unless you need frequent insertions/deletions at ends

        // Mistake 2: Confusing add() and offer() for queues
        // add() throws exception if capacity restricted
        // offer() returns false if capacity restricted
        // For LinkedList (unbounded), both work the same

        // Mistake 3: Using remove() without checking if empty
        LinkedList<String> empty = new LinkedList<>();
        // empty.removeFirst(); // Throws NoSuchElementException!
        // Better:
        String first = empty.pollFirst();  // Returns null if empty
        System.out.println("Poll from empty: " + first);
    }

    // Simple browser history implementation
    static class BrowserHistory {
        private LinkedList<String> history = new LinkedList<>();
        private int currentIndex = -1;

        public void visit(String url) {
            // Remove forward history
            while (history.size() > currentIndex + 1) {
                history.removeLast();
            }
            history.addLast(url);
            currentIndex++;
        }

        public String back() {
            if (currentIndex > 0) {
                currentIndex--;
                return history.get(currentIndex);
            }
            return history.getFirst();
        }

        public String forward() {
            if (currentIndex < history.size() - 1) {
                currentIndex++;
                return history.get(currentIndex);
            }
            return history.getLast();
        }

        public String current() {
            return history.get(currentIndex);
        }
    }
}
