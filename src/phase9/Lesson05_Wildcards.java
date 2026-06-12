package phase9;

import java.util.*;

/**
 * LESSON 5: WILDCARDS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Wildcards (?) represent unknown types in generics.
 * They allow you to write flexible code that works with
 * unknown or related types.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Make generic code more flexible
 * - Work with unknown types
 * - Enable covariance and contravariance
 * - PECS principle (Producer Extends, Consumer Super)
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Copying lists:
 * - copy(List<Object> dest, List<? extends Object> src)
 * - Works with List<String>, List<Integer>, etc.
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Service layer:
 * - List<? extends User> - read users
 * - List<? super User> - write users
 */

public class Lesson05_Wildcards {

    public static void main(String[] args) {
        System.out.println("=== WILDCARDS ===\n");

        // ============================================================
        // EXAMPLE 1: Unbounded wildcard
        // ============================================================
        System.out.println("--- Example 1: Unbounded Wildcard ---\n");

        List<String> strings = List.of("a", "b", "c");
        List<Integer> integers = List.of(1, 2, 3);

        printList(strings);
        printList(integers);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Upper bounded wildcard (? extends T)
        // ============================================================
        System.out.println("--- Example 2: Upper Bounded (? extends T) ---\n");

        List<Integer> ints = List.of(1, 2, 3);
        List<Double> doubles = List.of(1.1, 2.2, 3.3);

        double sum1 = sum(ints);
        double sum2 = sum(doubles);

        System.out.println("Sum of ints: " + sum1);
        System.out.println("Sum of doubles: " + sum2);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Lower bounded wildcard (? super T)
        // ============================================================
        System.out.println("--- Example 3: Lower Bounded (? super T) ---\n");

        List<Object> objects = new ArrayList<>();
        addNumbers(objects);
        System.out.println("Objects: " + objects);

        List<Number> numbers = new ArrayList<>();
        addNumbers(numbers);
        System.out.println("Numbers: " + numbers);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: PECS principle
        // ============================================================
        System.out.println("--- Example 4: PECS Principle ---\n");

        System.out.println("PECS: Producer Extends, Consumer Super");
        System.out.println();
        System.out.println("? extends T (Producer):");
        System.out.println("  - You can READ T from the source");
        System.out.println("  - You cannot WRITE to it");
        System.out.println("  - Example: List<? extends Number>");
        System.out.println();
        System.out.println("? super T (Consumer):");
        System.out.println("  - You can WRITE T to the destination");
        System.out.println("  - You can only read Object from it");
        System.out.println("  - Example: List<? super Integer>");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Wildcard capture
        // ============================================================
        System.out.println("--- Example 5: Wildcard Capture ---\n");

        List<?> unknownList = List.of("a", "b", "c");
        System.out.println("Unknown list: " + unknownList);
        System.out.println("(Cannot add to ? wildcard)");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Common patterns
        // ============================================================
        System.out.println("--- Example 6: Common Patterns ---\n");

        System.out.println("1. Read-only: List<? extends T>");
        System.out.println("2. Write-only: List<? super T>");
        System.out.println("3. Unknown: List<?>");
        System.out.println("4. Exact type: List<T>");
        System.out.println();

        // ============================================================
        // EXAMPLE 7: When to use which
        // ============================================================
        System.out.println("--- Example 7: When to Use ---\n");

        System.out.println("Use ? extends when:");
        System.out.println("  - Reading values from collection");
        System.out.println("  - Need covariance");
        System.out.println();
        System.out.println("Use ? super when:");
        System.out.println("  - Writing values to collection");
        System.out.println("  - Need contravariance");
        System.out.println();
        System.out.println("Use ? when:");
        System.out.println("  - Type doesn't matter");
        System.out.println("  - Just need to iterate");
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    static void printList(List<?> list) {
        for (Object elem : list) {
            System.out.println("  " + elem);
        }
    }

    static double sum(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }

    static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }
}
