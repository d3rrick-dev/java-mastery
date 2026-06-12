package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between shallow copy and deep copy?
 *
 * Difficulty: ADVANCED
 */

public class Lesson14_WhatIsTheDifferenceBetweenShallowCopyAndDeepCopy {
    public static void main(String[] args) {
        System.out.println("=== SHALLOW COPY VS DEEP COPY ===\n");
        System.out.println("Shallow Copy:");
        System.out.println("  - Copies object references, not actual objects");
        System.out.println("  - New object points to same nested objects");
        System.out.println("  - Changes to nested objects affect both copies");
        System.out.println("  - Faster and uses less memory");
        System.out.println("  - Default behavior of Object.clone()");
        System.out.println();
        System.out.println("Deep Copy:");
        System.out.println("  - Copies object AND all nested objects");
        System.out.println("  - New object has independent copies of everything");
        System.out.println("  - Changes to nested objects don't affect original");
        System.out.println("  - Slower and uses more memory");
        System.out.println("  - Must be implemented manually or via serialization");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - Original: Person { name: 'John', address: { city: 'NYC' } }");
        System.out.println("  - Shallow copy: New Person points to SAME address object");
        System.out.println("  - Deep copy: New Person has NEW address object");
    }
}
