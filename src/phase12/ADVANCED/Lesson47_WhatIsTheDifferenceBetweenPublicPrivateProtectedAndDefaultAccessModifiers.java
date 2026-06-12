package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between public, private, protected, and default access modifiers?
 *
 * Difficulty: ADVANCED
 */

public class Lesson47_WhatIsTheDifferenceBetweenPublicPrivateProtectedAndDefaultAccessModifiers {
    public static void main(String[] args) {
        System.out.println("=== ACCESS MODIFIERS IN JAVA ===\n");
        System.out.println("public:");
        System.out.println("  - Accessible everywhere");
        System.out.println("  - No restrictions");
        System.out.println("  - Example: public class, public method");
        System.out.println();
        System.out.println("private:");
        System.out.println("  - Accessible only within the same class");
        System.out.println("  - Most restrictive");
        System.out.println("  - Example: private int count;");
        System.out.println();
        System.out.println("protected:");
        System.out.println("  - Accessible within same package and subclasses");
        System.out.println("  - Used for inheritance");
        System.out.println("  - Example: protected void init();");
        System.out.println();
        System.out.println("default (package-private):");
        System.out.println("  - Accessible only within same package");
        System.out.println("  - No keyword needed");
        System.out.println("  - Example: void helper();");
        System.out.println();
        System.out.println("Access Matrix:");
        System.out.println("  Modifier    | Class | Package | Subclass | World");
        System.out.println("  public      |   Y   |    Y    |    Y     |   Y");
        System.out.println("  protected   |   Y   |    Y    |    Y     |   N");
        System.out.println("  default     |   Y   |    Y    |    N     |   N");
        System.out.println("  private     |   Y   |    N    |    N     |   N");
    }
}
