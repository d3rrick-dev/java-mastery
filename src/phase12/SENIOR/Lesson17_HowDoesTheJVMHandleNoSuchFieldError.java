package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle NoSuchFieldError?
 *
 * Difficulty: SENIOR
 */

public class Lesson17_HowDoesTheJVMHandleNoSuchFieldError {
    public static void main(String[] args) {
        System.out.println("=== NOSUCHFIELDERROR HANDLING ===\n");
        System.out.println("What is NoSuchFieldError?");
        System.out.println("  - Thrown when application tries to access non-existent field");
        System.out.println("  - Field existed at compile time but not at runtime");
        System.out.println("  - Extends IncompatibleClassChangeError");
        System.out.println();
        System.out.println("Common Causes:");
        System.out.println("  - Field was removed from class");
        System.out.println("  - Field name changed");
        System.out.println("  - Binary incompatibility");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  - Compile: class A { public int x; }");
        System.out.println("  - Runtime: class A { public int y; } // x renamed to y");
        System.out.println("  - Code: a.x // throws NoSuchFieldError");
        System.out.println();
        System.out.println("Prevention:");
        System.out.println("  - Don't remove public fields");
        System.out.println("  - Deprecate before removal");
        System.out.println("  - Use getters/setters instead of public fields");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Common in library updates");
        System.out.println("  - Can affect serialization (serialVersionUID)");
        System.out.println("  - Use interfaces to define contracts");
    }
}
