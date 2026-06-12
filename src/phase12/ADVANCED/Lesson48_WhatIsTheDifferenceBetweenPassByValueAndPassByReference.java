package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Is Java pass by value or pass by reference?
 *
 * Difficulty: ADVANCED
 */

public class Lesson48_WhatIsTheDifferenceBetweenPassByValueAndPassByReference {
    public static void main(String[] args) {
        System.out.println("=== PASS BY VALUE VS PASS BY REFERENCE ===\n");
        System.out.println("Java is ALWAYS pass by value:");
        System.out.println("  - Primitives: value of variable is copied");
        System.out.println("  - Objects: value of reference is copied (not object itself)");
        System.out.println();
        System.out.println("For Primitives:");
        System.out.println("  - int x = 10;");
        System.out.println("  - change(x); // x is copied, original unchanged");
        System.out.println();
        System.out.println("For Objects:");
        System.out.println("  - Person p = new Person();");
        System.out.println("  - change(p); // reference is copied, object CAN be modified");
        System.out.println("  - BUT reassigning p inside method doesn't affect original");
        System.out.println();
        System.out.println("Common Misconception:");
        System.out.println("  - 'Java passes objects by reference' - FALSE");
        System.out.println("  - Java passes object REFERENCES by value");
        System.out.println("  - The reference itself is copied, not the object");
        System.out.println();
        System.out.println("Interview Follow-up:");
        System.out.println("  - Can you swap two objects in Java?");
        System.out.println("  - Answer: No, because references are passed by value");
    }
}
