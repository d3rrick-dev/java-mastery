package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between public, private, protected, and default?
 *
 * Difficulty: BEGINNER
 */

public class Lesson30_WhatIsTheDifferenceBetweenPublicPrivateProtectedAndDefault {
    public static void main(String[] args) {
        System.out.println("=== ACCESS MODIFIERS ===\n");
        System.out.println("public: Accessible from anywhere");
        System.out.println("private: Accessible only within the same class");
        System.out.println("protected: Accessible within package + subclasses");
        System.out.println("default (no modifier): Accessible only within same package");
        System.out.println();
        System.out.println("Class: public or default only");
        System.out.println("Method/Field: All four");
    }
}
