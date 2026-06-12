package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Serializable and Externalizable?
 *
 * Difficulty: ADVANCED
 */

public class Lesson15_WhatIsTheDifferenceBetweenSerializableAndExternalizable {
    public static void main(String[] args) {
        System.out.println("=== SERIALIZABLE VS EXTERNALIZABLE ===\n");
        System.out.println("Serializable:");
        System.out.println("  - Marker interface (no methods to implement)");
        System.out.println("  - JVM handles serialization automatically");
        System.out.println("  - Uses default serialization mechanism");
        System.out.println("  - Less control over serialization process");
        System.out.println("  - Slower (uses reflection)");
        System.out.println();
        System.out.println("Externalizable:");
        System.out.println("  - Extends Serializable");
        System.out.println("  - Must implement writeExternal() and readExternal()");
        System.out.println("  - Full control over serialization process");
        System.out.println("  - Faster (no reflection)");
        System.out.println("  - More code to write");
        System.out.println();
        System.out.println("When to use:");
        System.out.println("  - Serializable: simple objects, quick implementation");
        System.out.println("  - Externalizable: performance-critical, custom format needed");
    }
}
