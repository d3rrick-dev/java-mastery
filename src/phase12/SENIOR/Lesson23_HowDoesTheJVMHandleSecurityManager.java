package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the JVM handle SecurityManager?
 *
 * Difficulty: SENIOR
 */

public class Lesson23_HowDoesTheJVMHandleSecurityManager {
    public static void main(String[] args) {
        System.out.println("=== SECURITYMANAGER IN JVM ===\n");
        System.out.println("What is SecurityManager?");
        System.out.println("  - Class that defines security policy for JVM");
        System.out.println("  - Checks permissions before sensitive operations");
        System.out.println("  - Deprecated in Java 17, removed in Java 18+");
        System.out.println();
        System.out.println("How it Works:");
        System.out.println("  - checkPermission(Permission perm) called before operations");
        System.out.println("  - AccessController checks permission against policy");
        System.out.println("  - Policy file defines allowed/denied permissions");
        System.out.println("  - If denied: SecurityException thrown");
        System.out.println();
        System.out.println("Common Operations Checked:");
        System.out.println("  - File access (read, write, delete)");
        System.out.println("  - Network access (socket, URL)");
        System.out.println("  - Reflection access");
        System.out.println("  - Runtime access (exec, loadLibrary)");
        System.out.println("  - Property access (System.getProperty)");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Used in applets, sandboxed environments");
        System.out.println("  - Replaced by Java Platform Module System (JPMS)");
        System.out.println("  - Strong encapsulation via modules");
        System.out.println("  - Still relevant for legacy systems");
    }
}
