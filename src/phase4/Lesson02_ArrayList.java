package phase4;

import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 2: ArrayList
 *
 * THEORY:
 * ArrayList is a resizable array implementation of the List interface.
 * It's the most commonly used List implementation in Java.
 *
 * INTERNAL WORKING:
 * - Backed by a dynamic array (Object[])
 * - Grows automatically when capacity is exceeded (usually 1.5x)
 * - Maintains insertion order
 * - Allows duplicate elements
 * - Allows null elements
 *
 * TIME COMPLEXITY:
 * - add(E e) - O(1) amortized (O(n) when resizing)
 * - add(int index, E e) - O(n) - need to shift elements
 * - get(int index) - O(1) - direct array access
 * - set(int index, E e) - O(1)
 * - remove(int index) - O(n) - need to shift elements
 * - remove(Object o) - O(n) - need to search then shift
 * - contains(Object o) - O(n) - need to search
 * - indexOf(Object o) - O(n)
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS:
 * - add(E e)           : Add to end
 * - add(int index, E e): Add at index
 * - get(int index)     : Get element at index
 * - set(int index, E e): Replace element at index
 * - remove(int index)  : Remove at index
 * - remove(Object o)   : Remove first occurrence
 * - contains(Object o) : Check if exists
 * - indexOf(Object o)  : Get index of first occurrence
 * - size()             : Number of elements
 * - clear()            : Remove all elements
 * - isEmpty()          : Check if empty
 * - toArray()          : Convert to array
 */

public class Lesson02_ArrayList {

    public static void main(String[] args) {
        System.out.println("=== LESSON 2: ArrayList ===\n");

        // ============================================
        // 1. CREATING ARRAYLIST
        // ============================================
        System.out.println("--- 1. Creating ArrayList ---");

        // Empty ArrayList
        List<String> list1 = new ArrayList<>();
        System.out.println("Empty list: " + list1);

        // ArrayList with initial capacity
        List<String> list2 = new ArrayList<>(20);
        System.out.println("List with capacity 20: " + list2.size() + " elements");

        // ArrayList from another collection
        List<String> list3 = new ArrayList<>(List.of("A", "B", "C"));
        System.out.println("List from collection: " + list3);

        // ============================================
        // 2. ADDING ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Adding Elements ---");

        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);      // Add to end - O(1)
        numbers.add(20);
        numbers.add(30);
        System.out.println("After add: " + numbers);

        numbers.add(1, 15);   // Add at index 1 - O(n)
        System.out.println("After add at index 1: " + numbers);

        numbers.add(0, 5);    // Add at beginning - O(n)
        System.out.println("After add at index 0: " + numbers);

        // ============================================
        // 3. ACCESSING ELEMENTS
        // ============================================
        System.out.println("\n--- 3. Accessing Elements ---");

        System.out.println("Element at 0: " + numbers.get(0));      // O(1)
        System.out.println("Element at 2: " + numbers.get(2));      // O(1)
        System.out.println("Element at end: " + numbers.get(numbers.size() - 1));  // O(1)

        // ============================================
        // 4. MODIFYING ELEMENTS
        // ============================================
        System.out.println("\n--- 4. Modifying Elements ---");

        System.out.println("Before set: " + numbers);
        numbers.set(2, 99);   // Replace element at index 2 - O(1)
        System.out.println("After set: " + numbers);

        // ============================================
        // 5. REMOVING ELEMENTS
        // ============================================
        System.out.println("\n--- 5. Removing Elements ---");

        System.out.println("Before remove: " + numbers);
        numbers.remove(0);    // Remove at index 0 - O(n)
        System.out.println("After remove index 0: " + numbers);

        numbers.remove(Integer.valueOf(99));  // Remove by value - O(n)
        System.out.println("After remove value 99: " + numbers);

        // ============================================
        // 6. SEARCHING
        // ============================================
        System.out.println("\n--- 6. Searching ---");

        List<String> fruits = new ArrayList<>(List.of("Apple", "Banana", "Cherry", "Date", "Elderberry"));
        System.out.println("Fruits: " + fruits);

        System.out.println("Contains 'Cherry': " + fruits.contains("Cherry"));  // O(n)
        System.out.println("Index of 'Date': " + fruits.indexOf("Date"));        // O(n)
        System.out.println("Index of 'Grape': " + fruits.indexOf("Grape"));      // O(n) - returns -1

        // ============================================
        // 7. ITERATION
        // ============================================
        System.out.println("\n--- 7. Iteration ---");

        // For loop
        System.out.println("For loop:");
        for (int i = 0; i < fruits.size(); i++) {
            System.out.println("  " + fruits.get(i));
        }

        // Enhanced for-loop
        System.out.println("Enhanced for-loop:");
        for (String fruit : fruits) {
            System.out.println("  " + fruit);
        }

        // Iterator
        System.out.println("Iterator:");
        java.util.Iterator<String> it = fruits.iterator();
        while (it.hasNext()) {
            System.out.println("  " + it.next());
        }

        // ListIterator (can go backward)
        System.out.println("ListIterator backward:");
        java.util.ListIterator<String> listIt = fruits.listIterator(fruits.size());
        while (listIt.hasPrevious()) {
            System.out.println("  " + listIt.previous());
        }

        // ============================================
        // 8. SUBLIST
        // ============================================
        System.out.println("\n--- 8. Sublist ---");

        List<String> sublist = fruits.subList(1, 4);  // From index 1 to 3 (exclusive)
        System.out.println("Sublist [1,4): " + sublist);

        // ============================================
        // 9. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 9. Coding Challenge ---");

        // Challenge: Remove all even numbers from list
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Original: " + nums);

        // Remove even numbers (iterate backward to avoid index issues)
        for (int i = nums.size() - 1; i >= 0; i--) {
            if (nums.get(i) % 2 == 0) {
                nums.remove(i);
            }
        }
        System.out.println("After removing evens: " + nums);

        // Better: Use removeIf (Java 8+)
        List<Integer> nums2 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        nums2.removeIf(n -> n % 2 == 0);
        System.out.println("Using removeIf: " + nums2);

        // ============================================
        // 10. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 10. Common Mistakes ---");

        // Mistake 1: ConcurrentModificationException
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        try {
            for (String s : list) {
                if (s.equals("B")) {
                    list.remove(s);  // Throws ConcurrentModificationException!
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName());
        }

        // Correct: Use iterator
        java.util.Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("B")) {
                iterator.remove();  // Safe
            }
        }

        // Or use removeIf
        list.removeIf(s -> s.equals("B"));

        // Mistake 2: Using raw types
        // List list = new ArrayList(); // Raw type - avoid
        List<String> typedList = new ArrayList<>(); // Generic - correct
    }
}
