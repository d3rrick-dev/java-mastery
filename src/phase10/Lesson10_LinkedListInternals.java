package phase10;

import java.util.*;

/**
 * LESSON 10: LINKEDLIST INTERNALS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * LinkedList is a doubly-linked list implementation of List and Deque.
 * Each element points to next and previous.
 * Like a chain where each link knows its neighbors.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - O(1) insertion/removal at ends
 * - Implements Queue and Deque
 * - No capacity restrictions
 */

public class Lesson10_LinkedListInternals {

    public static void main(String[] args) {
        System.out.println("=== LINKEDLIST INTERNALS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic LinkedList
        // ============================================================
        System.out.println("--- Example 1: Basic Usage ---\n");

        List<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        System.out.println("List: " + list);
        System.out.println("Get(1): " + list.get(1));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: As Queue
        // ============================================================
        System.out.println("--- Example 2: As Queue ---\n");

        Queue<String> queue = new LinkedList<>();
        queue.offer("A");
        queue.offer("B");
        queue.offer("C");

        System.out.println("Queue: " + queue);
        System.out.println("Poll: " + queue.poll());
        System.out.println("Peek: " + queue.peek());
        System.out.println("After operations: " + queue);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: As Deque (double-ended)
        // ============================================================
        System.out.println("--- Example 3: As Deque ---\n");

        Deque<String> deque = new LinkedList<>();
        deque.addFirst("first");
        deque.addLast("last");
        deque.offerFirst("newFirst");
        deque.offerLast("newLast");

        System.out.println("Deque: " + deque);
        System.out.println("Remove first: " + deque.removeFirst());
        System.out.println("Remove last: " + deque.removeLast());
        System.out.println("Final: " + deque);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Internal structure
        // ============================================================
        System.out.println("--- Example 4: Internal Structure ---\n");

        System.out.println("LinkedList Node structure:");
        System.out.println();
        System.out.println("  +------+     +------+     +------+");
        System.out.println("  | prev |<--->| item |<--->| next |");
        System.out.println("  +------+     +------+     +------+");
        System.out.println("       ^           |           ^");
        System.out.println("       |           v           |");
        System.out.println("  +------+     +------+     +------+");
        System.out.println("  | prev |     | item |     | next |");
        System.out.println("  +------+     +------+     +------+");
        System.out.println();
    }

    // ============================================================
    // INTERNAL STRUCTURE
    // ============================================================
    /*
     * LinkedList Internal Structure:
     *
     * - Node<E>:
     *   + E item
     *   + Node<E> next
     *   + Node<E> prev
     *
     * - Node<E> first
     * - Node<E> last
     * - int size
     *
     * Time Complexity:
     * - add (first/last): O(1)
     * - add (middle): O(n) - need to traverse
     * - get: O(n) - need to traverse
     * - remove (first/last): O(1)
     * - remove (middle): O(n)
     *
     * Implements: List, Deque, Queue, Cloneable, Serializable
     */
}
