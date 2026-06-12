package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between Serializable and transient?
 *
 * Difficulty: ADVANCED
 */

public class Lesson38_WhatIsTheDifferenceBetweenSerializableAndTransient {
    public static void main(String[] args) {
        System.out.println("=== SERIALIZABLE VS TRANSIENT ===\n");
        System.out.println("Serializable:");
        System.out.println("  - Marker interface (no methods)");
        System.out.println("  - Applied to class level");
        System.out.println("  - Enables object serialization");
        System.out.println("  - All non-transient fields are serialized");
        System.out.println("  - Example: public class User implements Serializable");
        System.out.println();
        System.out.println("transient:");
        System.out.println("  - Keyword applied to fields");
        System.out.println("  - Prevents field from being serialized");
        System.out.println("  - Field gets default value on deserialization");
        System.out.println("  - Used for sensitive data (passwords) or derived data");
        System.out.println("  - Example: transient String password;");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Scope: class vs field");
        System.out.println("  - Purpose: enable serialization vs exclude from serialization");
        System.out.println("  - Level: class-level vs field-level");
        System.out.println();
        System.out.println("Interview Follow-up:");
        System.out.println("  - What happens to static fields during serialization?");
        System.out.println("  - Answer: Static fields are NOT serialized (belong to class, not instance)");
    }
}
