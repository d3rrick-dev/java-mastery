package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle Class Data Sharing (CDS)?
 *
 * Difficulty: SENIOR
 */

public class Lesson24_HowDoesTheJVMHandleClassDataSharing {
    public static void main(String[] args) {
        System.out.println("=== CLASS DATA SHARING (CDS) ===\n");
        System.out.println("What is CDS?");
        System.out.println("  - Feature to share class metadata across JVM instances");
        System.out.println("  - Reduces startup time and memory footprint");
        System.out.println("  - Introduced in Java 5, enhanced in later versions");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - First JVM: loads classes, creates shared archive");
        System.out.println("  - Subsequent JVMs: load from shared archive (faster)");
        System.out.println("  - Archive contains: class metadata, bytecode, constants");
        System.out.println("  - Memory-mapped file for fast access");
        System.out.println();
        System.out.println("Types of CDS:");
        System.out.println("  - AppCDS: Application Class Data Sharing");
        System.out.println("  - Reduces startup time for large applications");
        System.out.println("  - Dynamic CDS: archive created at runtime (Java 10+)");
        System.out.println("  - Archive heap objects (Java 12+)");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Significant startup improvement for microservices");
        System.out.println("  - Reduces memory usage in container environments");
        System.out.println("  - Use -Xshare:on to enable");
        System.out.println("  - Use -XX:+UseAppCDS for application classes");
    }
}
