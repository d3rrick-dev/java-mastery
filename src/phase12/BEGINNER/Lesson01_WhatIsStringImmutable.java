package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Why is String immutable in Java?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of String fundamentals
 * - Shows knowledge of memory management
 * - Reveals design decision understanding
 */

public class Lesson01_WhatIsStringImmutable {

    public static void main(String[] args) {
        System.out.println("=== WHY IS STRING IMMUTABLE? ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: Why is String immutable in Java?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("String is immutable because:\n");

        System.out.println("1. SECURITY:");
        System.out.println("   - Strings used for file paths, network connections");
        System.out.println("   - Immutable prevents malicious modification");
        System.out.println("   - Class loading uses strings - immutable ensures correct class loaded");
        System.out.println();

        System.out.println("2. CACHING:");
        System.out.println("   - String pool reuses strings");
        System.out.println("   - If mutable, one reference could change all");
        System.out.println("   - Example: s1 = \"hello\"; s2 = \"hello\"; s1 changes -> s2 also changes!");
        System.out.println();

        System.out.println("3. HASHCODE CACHING:");
        System.out.println("   - String hashCode() cached at creation");
        System.out.println("   - Immutable ensures hashCode never changes");
        System.out.println("   - HashMap keys work correctly");
        System.out.println();

        System.out.println("4. THREAD SAFETY:");
        System.out.println("   - Immutable objects are thread-safe");
        System.out.println("   - No synchronization needed");
        System.out.println("   - Safe to share between threads");
        System.out.println();

        System.out.println("5. CLASS LOADING:");
        System.out.println("   - Class names are strings");
        System.out.println("   - Immutable ensures correct class loaded");
        System.out.println("   - Prevents class loading attacks");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        String s1 = "hello";
        String s2 = "hello";

        System.out.println("s1 == s2: " + (s1 == s2));  // true - same pool object
        System.out.println("If String were mutable:");
        System.out.println("  s1 = \"hello\"; s2 = \"hello\";");
        System.out.println("  s1.toUpperCase();  // Would change s2 too!");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Saying 'for performance' only:");
        System.out.println("   - Performance is a benefit, not the primary reason");
        System.out.println("   - Security and correctness are primary");
        System.out.println();

        System.out.println("2. Not mentioning String pool:");
        System.out.println("   - Pool relies on immutability");
        System.out.println("   - Mutable strings would break pool");
        System.out.println();

        System.out.println("3. Confusing with StringBuffer/StringBuilder:");
        System.out.println("   - Those are mutable for specific use cases");
        System.out.println("   - String is immutable by design");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. How does String pool work?");
        System.out.println("2. What happens when you do s1.concat(s2)?");
        System.out.println("3. Where is String pool located (Java 7 vs earlier)?");
        System.out.println("4. How many objects are created: new String(\"abc\")?");
        System.out.println("5. Difference between == and equals() for Strings?");
        System.out.println();

        // ============================================================
        // REAL-WORLD USAGE
        // ============================================================
        System.out.println("--- Real-World Usage ---\n");

        System.out.println("1. Database credentials in connection strings");
        System.out.println("2. File paths in security-sensitive code");
        System.out.println("3. HashMap keys (immutable hashCode)");
        System.out.println("4. Network addresses and ports");
        System.out.println("5. Configuration properties");
    }
}
