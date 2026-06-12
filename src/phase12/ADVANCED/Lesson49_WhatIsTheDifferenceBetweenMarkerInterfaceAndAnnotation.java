package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between marker interface and annotation?
 *
 * Difficulty: ADVANCED
 */

public class Lesson49_WhatIsTheDifferenceBetweenMarkerInterfaceAndAnnotation {
    public static void main(String[] args) {
        System.out.println("=== MARKER INTERFACE VS ANNOTATION ===\n");
        System.out.println("Marker Interface:");
        System.out.println("  - Interface with no methods/fields");
        System.out.println("  - Example: Serializable, Cloneable, Remote");
        System.out.println("  - Used to mark class for special behavior");
        System.out.println("  - Checked at runtime using instanceof");
        System.out.println("  - Cannot have attributes/values");
        System.out.println("  - Introduced in Java 1.0");
        System.out.println();
        System.out.println("Annotation:");
        System.out.println("  - Metadata added to code");
        System.out.println("  - Can have attributes/elements");
        System.out.println("  - Example: @Override, @Deprecated, @SuppressWarnings");
        System.out.println("  - Can be checked at compile-time or runtime via reflection");
        System.out.println("  - More flexible and powerful");
        System.out.println("  - Introduced in Java 5");
        System.out.println();
        System.out.println("Key Differences:");
        System.out.println("  - Definition: interface vs metadata");
        System.out.println("  - Methods: none vs can have attributes");
        System.out.println("  - Checking: instanceof vs reflection");
        System.out.println("  - Flexibility: limited vs highly flexible");
        System.out.println("  - Modern: legacy vs preferred approach");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Marker interfaces are legacy");
        System.out.println("  - Annotations are the modern replacement");
        System.out.println("  - Example: @FunctionalInterface replaces marker for functional interfaces");
    }
}
