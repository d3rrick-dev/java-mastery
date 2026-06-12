package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between instance variable and local variable?
 *
 * Difficulty: BEGINNER
 */

public class Lesson49_WhatIsTheDifferenceBetweenInstanceVariableAndLocalVariable {
    public static void main(String[] args) {
        System.out.println("=== INSTANCE VARIABLE VS LOCAL VARIABLE ===\n");
        System.out.println("Instance Variable (Field):");
        System.out.println("  - Declared in class, outside methods");
        System.out.println("  - Belongs to object instance");
        System.out.println("  - Has default values (0, null, false)");
        System.out.println("  - Can have access modifiers");
        System.out.println("  - Lives as long as object lives");
        System.out.println();
        System.out.println("Local Variable:");
        System.out.println("  - Declared inside method/block");
        System.out.println("  - Belongs to method/block scope");
        System.out.println("  - NO default values (must initialize)");
        System.out.println("  - No access modifiers");
        System.out.println("  - Lives only during method execution");
    }
}
