package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between aggregation and composition?
 *
 * Difficulty: ADVANCED
 */

public class Lesson40_WhatIsTheDifferenceBetweenAggregationAndComposition {
    public static void main(String[] args) {
        System.out.println("=== AGGREGATION VS COMPOSITION ===\n");
        System.out.println("Aggregation (Weak Association):");
        System.out.println("  - HAS-A relationship");
        System.out.println("  - Child can exist independently of parent");
        System.out.println("  - Parent holds reference to child");
        System.out.println("  - Example: Department has Employees (employees exist without department)");
        System.out.println("  - Represented by: class Department { List<Employee> employees; }");
        System.out.println();
        System.out.println("Composition (Strong Association):");
        System.out.println("  - PART-OF relationship");
        System.out.println("  - Child CANNOT exist without parent");
        System.out.println("  - Parent creates and destroys child");
        System.out.println("  - Example: House has Rooms (rooms don't exist without house)");
        System.out.println("  - Represented by: class House { List<Room> rooms; }");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Dependency: independent vs dependent");
        System.out.println("  - Lifecycle: separate vs shared");
        System.out.println("  - Ownership: shared vs exclusive");
        System.out.println("  - UML: hollow diamond vs filled diamond");
    }
}
