package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does the Java Memory Model work?
 *
 * Difficulty: SENIOR
 */

public class Lesson05_HowDoesTheJavaMemoryModelWork {
    public static void main(String[] args) {
        System.out.println("=== JAVA MEMORY MODEL (JMM) ===\n");
        System.out.println("What is JMM?");
        System.out.println("  - Specification of how JVM works with memory");
        System.out.println("  - Defines how threads interact through memory");
        System.out.println("  - Ensures consistent behavior across platforms");
        System.out.println("  - Part of JLS (Java Language Specification)");
        System.out.println();
        System.out.println("Main Memory vs Working Memory:");
        System.out.println("  - Main Memory: shared heap, stores all variables");
        System.out.println("  - Working Memory: each thread has private copy");
        System.out.println("  - Threads cannot directly access main memory");
        System.out.println("  - Must copy variables to working memory");
        System.out.println();
        System.out.println("Happens-Before Relationship:");
        System.out.println("  - Defines ordering of operations");
        System.out.println("  - If A happens-before B, then B sees A's effects");
        System.out.println("  - Program order: within same thread");
        System.out.println("  - Monitor lock: unlock happens-before lock");
        System.out.println("  - Volatile: write happens-before read");
        System.out.println("  - Thread start: start happens-before first action");
        System.out.println("  - Thread join: last action happens-before join returns");
        System.out.println();
        System.out.println("Senior-Level Topics:");
        System.out.println("  - Memory barriers/fences");
        System.out.println("  - Instruction reordering");
        System.out.println("  - Visibility vs atomicity");
        System.out.println("  - Safe publication");
        System.out.println("  - Double-checked locking problem");
    }
}
