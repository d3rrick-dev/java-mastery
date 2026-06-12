package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between Serializable and Externalizable?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson15_WhatIsTheDifferenceBetweenSerializableAndExternalizable {
    public static void main(String[] args) {
        System.out.println("=== SERIALIZABLE VS EXTERNALIZABLE ===\n");
        System.out.println("Serializable:");
        System.out.println("  - Marker interface (no methods)");
        System.out.println("  - JVM handles serialization automatically");
        System.out.println("  - Uses reflection");
        System.out.println("  - Slower, less control");
        System.out.println("  - Default serialization");
        System.out.println();
        System.out.println("Externalizable:");
        System.out.println("  - Extends Serializable");
        System.out.println("  - Has writeExternal() and readExternal() methods");
        System.out.println("  - Programmer controls serialization");
        System.out.println("  - Faster, more control");
        System.out.println("  - Must implement both methods");
        System.out.println();
        System.out.println("Use Serializable for simple cases");
        System.out.println("Use Externalizable for performance/control");
    }
}
