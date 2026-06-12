package phase12.INTERMEDIATE;

/**
 * INTERVIEW QUESTION: Difference between serialization and deserialization?
 *
 * Difficulty: INTERMEDIATE
 */

public class Lesson16_WhatIsTheDifferenceBetweenSerializationAndDeserialization {
    public static void main(String[] args) {
        System.out.println("=== SERIALIZATION VS DESERIALIZATION ===\n");
        System.out.println("Serialization:");
        System.out.println("  - Converting object to byte stream");
        System.out.println("  - Object -> bytes");
        System.out.println("  - Uses ObjectOutputStream");
        System.out.println("  - For storage/transmission");
        System.out.println();
        System.out.println("Deserialization:");
        System.out.println("  - Converting byte stream back to object");
        System.out.println("  - Bytes -> object");
        System.out.println("  - Uses ObjectInputStream");
        System.out.println("  - For retrieval/reconstruction");
        System.out.println();
        System.out.println("Requirements: Class must implement Serializable");
        System.out.println("transient fields are not serialized");
    }
}
