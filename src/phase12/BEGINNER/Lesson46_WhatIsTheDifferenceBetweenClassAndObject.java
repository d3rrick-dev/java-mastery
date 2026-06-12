package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between class and object?
 *
 * Difficulty: BEGINNER
 */

public class Lesson46_WhatIsTheDifferenceBetweenClassAndObject {
    public static void main(String[] args) {
        System.out.println("=== CLASS VS OBJECT ===\n");
        System.out.println("Class:");
        System.out.println("  - Blueprint/template for creating objects");
        System.out.println("  - Defines properties (fields) and behaviors (methods)");
        System.out.println("  - Does not occupy memory until instantiated");
        System.out.println("  - Example: class Car { String color; void drive() {} }");
        System.out.println();
        System.out.println("Object:");
        System.out.println("  - Instance of a class");
        System.out.println("  - Occupies memory");
        System.out.println("  - Has actual values for properties");
        System.out.println("  - Example: Car myCar = new Car();");
    }
}
