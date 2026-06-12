package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Iterator and ListIterator?
 *
 * Difficulty: ADVANCED
 */

public class Lesson30_WhatIsTheDifferenceBetweenIteratorAndListIterator {
    public static void main(String[] args) {
        System.out.println("=== ITERATOR VS LISTITERATOR ===\n");
        System.out.println("Iterator:");
        System.out.println("  - Interface in java.util package");
        System.out.println("  - Works with all Collection types (List, Set, Queue)");
        System.out.println("  - Can only traverse in forward direction");
        System.out.println("  - Methods: hasNext(), next(), remove()");
        System.out.println("  - Cannot modify or add elements during iteration");
        System.out.println();
        System.out.println("ListIterator:");
        System.out.println("  - Interface in java.util package");
        System.out.println("  - Works only with List types (ArrayList, LinkedList)");
        System.out.println("  - Can traverse in both directions (forward and backward)");
        System.out.println("  - Methods: hasNext(), next(), hasPrevious(), previous()");
        System.out.println("  - Can modify and add elements during iteration");
        System.out.println("  - Can get index: nextIndex(), previousIndex()");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Direction: forward only vs bidirectional");
        System.out.println("  - Scope: all collections vs List only");
        System.out.println("  - Modification: read-only vs can modify/add");
        System.out.println("  - Index access: no vs yes");
    }
}
