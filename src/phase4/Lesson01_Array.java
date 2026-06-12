package phase4;

import java.util.Arrays;

/**
 * LESSON 1: Array
 *
 * THEORY:
 * An array is a fixed-size, contiguous block of memory that stores elements of the same type.
 * It's the most basic data structure in Java.
 *
 * CHARACTERISTICS:
 * - Fixed size (cannot grow/shrink after creation)
 * - Contiguous memory allocation
 * - Direct index access (O(1))
 * - Stores primitives or object references
 * - Zero-based indexing
 *
 * TIME COMPLEXITY:
 * - Access: O(1)
 * - Search: O(n)
 * - Insert: O(n) - need to shift elements
 * - Delete: O(n) - need to shift elements
 *
 * SPACE COMPLEXITY: O(n)
 *
 * COMMON METHODS (built-in):
 * - length: property, not method
 * - No add(), remove(), etc. (use ArrayList for those)
 */

public class Lesson01_Array {

    public static void main(String[] args) {
        System.out.println("=== LESSON 1: Array ===\n");

        // ============================================
        // 1. CREATING ARRAYS
        // ============================================
        System.out.println("--- 1. Creating Arrays ---");

        // Declaration and initialization
        int[] numbers = new int[5];  // Fixed size 5, default values 0
        numbers[0] = 10;
        numbers[1] = 20;
        numbers[2] = 30;
        numbers[3] = 40;
        numbers[4] = 50;

        System.out.println("Numbers: " + Arrays.toString(numbers));
        System.out.println("Length: " + numbers.length);

        // Array with initial values
        int[] primes = {2, 3, 5, 7, 11, 13};
        System.out.println("Primes: " + Arrays.toString(primes));
        
        // Array of objects
        String[] names = new String[3];
        names[0] = "Alice";
        names[1] = "Bob";
        names[2] = "Charlie";
        System.out.println("Names: " + Arrays.toString(names));

        // ============================================
        // 2. ACCESSING ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Accessing Elements ---");

        // Direct index access - O(1)
        System.out.println("First number: " + numbers[0]);
        System.out.println("Last number: " + numbers[numbers.length - 1]);

        // Iterating
        System.out.println("Iterating forward:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("  [" + i + "] = " + numbers[i]);
        }

        System.out.println("Iterating backward:");
        for (int i = numbers.length - 1; i >= 0; i--) {
            System.out.println("  [" + i + "] = " + numbers[i]);
        }

        // Enhanced for-loop
        System.out.println("Enhanced for-loop:");
        for (int num : numbers) {
            System.out.println("  " + num);
        }

        // ============================================
        // 3. SEARCHING
        // ============================================
        System.out.println("\n--- 3. Searching ---");

        // Linear search - O(n)
        int target = 30;
        int index = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                index = i;
                break;
            }
        }
        System.out.println("Found " + target + " at index: " + index);

        // Using built-in methods
        index = Arrays.binarySearch(primes, 7);  // Only works on sorted arrays
        System.out.println("Binary search for 7: " + index);

        // ============================================
        // 4. INSERTION (INEFFICIENT)
        // ============================================
        System.out.println("\n--- 4. Insertion ---");

        // Arrays are fixed size! To "insert", we need a new array
        int[] small = {1, 2, 3, 4, 5};
        System.out.println("Original: " + Arrays.toString(small));

        // Insert at index 2
        int[] newArray = new int[small.length + 1];
        int insertIndex = 2;
        int insertValue = 99;

        for (int i = 0; i < newArray.length; i++) {
            if (i < insertIndex) {
                newArray[i] = small[i];
            } else if (i == insertIndex) {
                newArray[i] = insertValue;
            } else {
                newArray[i] = small[i - 1];
            }
        }
        System.out.println("After insert: " + Arrays.toString(newArray));

        // ============================================
        // 5. DELETION (INEFFICIENT)
        // ============================================
        System.out.println("\n--- 5. Deletion ---");

        // Delete at index 2
        int[] toDelete = {1, 2, 3, 4, 5};
        int deleteIndex = 2;
        int[] afterDelete = new int[toDelete.length - 1];

        for (int i = 0; i < afterDelete.length; i++) {
            if (i < deleteIndex) {
                afterDelete[i] = toDelete[i];
            } else {
                afterDelete[i] = toDelete[i + 1];
            }
        }
        System.out.println("After delete: " + Arrays.toString(afterDelete));

        // ============================================
        // 6. MULTI-DIMENSIONAL ARRAYS
        // ============================================
        System.out.println("\n--- 6. Multi-dimensional Arrays ---");

        // 2D array (matrix)
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Reverse an array
        int[] original = {1, 2, 3, 4, 5};
        System.out.println("Original: " + Arrays.toString(original));

        // Reverse in-place
        for (int i = 0; i < original.length / 2; i++) {
            int temp = original[i];
            original[i] = original[original.length - 1 - i];
            original[original.length - 1 - i] = temp;
        }
        System.out.println("Reversed: " + Arrays.toString(original));

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: ArrayIndexOutOfBoundsException
        // int[] arr = new int[5];
        // arr[5] = 10; // ERROR: index 5 is out of bounds (0-4)

        // Mistake 2: Confusing length with size
        int[] arr = new int[5];
        // arr.length(); // ERROR: length is a property, not a method
        System.out.println("Length: " + arr.length); // Correct

        // Mistake 3: Using arrays when ArrayList is more appropriate
        // If you need dynamic sizing, use ArrayList instead
    }
}
