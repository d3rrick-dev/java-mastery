package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Stack and Heap memory?
 *
 * Difficulty: ADVANCED
 */

public class Lesson32_WhatIsTheDifferenceBetweenStackAndHeapMemory {
    public static void main(String[] args) {
        System.out.println("=== STACK VS HEAP MEMORY ===\n");
        System.out.println("Stack Memory:");
        System.out.println("  - Stores primitive values and method call frames");
        System.out.println("  - Each thread has its own stack");
        System.out.println("  - LIFO (Last In First Out) structure");
        System.out.println("  - Fast access (direct pointer)");
        System.out.println("  - Limited size (can cause StackOverflowError)");
        System.out.println("  - Automatically allocated/deallocated");
        System.out.println();
        System.out.println("Heap Memory:");
        System.out.println("  - Stores all objects and arrays");
        System.out.println("  - Shared among all threads");
        System.out.println("  - Larger size than stack");
        System.out.println("  - Slower access (indirect reference)");
        System.out.println("  - Garbage collected (not automatic)");
        System.out.println("  - Can cause OutOfMemoryError");
        System.out.println();
        System.out.println("Memory Layout:");
        System.out.println("  - Stack: local variables, method parameters, return addresses");
        System.out.println("  - Heap: objects, instance variables, arrays");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - int x = 10; // x stored in stack");
        System.out.println("  - Object obj = new Object(); // reference in stack, object in heap");
    }
}
