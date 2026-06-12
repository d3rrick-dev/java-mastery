package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does ConcurrentHashMap achieve thread safety?
 *
 * Difficulty: SENIOR
 */

public class Lesson04_HowDoesConcurrentHashMapAchieveThreadSafety {
    public static void main(String[] args) {
        System.out.println("=== CONCURRENTHASHMAP THREAD SAFETY ===\n");
        System.out.println("Java 7 Implementation (Segments):");
        System.out.println("  - Divided into 16 segments (default)");
        System.out.println("  - Each segment has its own lock (ReentrantLock)");
        System.out.println("  - Lock striping: different segments can be locked independently");
        System.out.println("  - Read operations: no locking");
        System.out.println("  - Write operations: lock only affected segment");
        System.out.println("  - Concurrent level: 16 (can be customized)");
        System.out.println();
        System.out.println("Java 8+ Implementation (CAS + synchronized):");
        System.out.println("  - Removed segments");
        System.out.println("  - Uses synchronized blocks on first node of bucket");
        System.out.println("  - Uses CAS (Compare-And-Swap) for lock-free reads");
        System.out.println("  - Table size is power of 2");
        System.out.println("  - Forwarding nodes for resizing");
        System.out.println("  - Tree bins for high collision buckets");
        System.out.println();
        System.out.println("Key Optimizations:");
        System.out.println("  - Volatile table reference for visibility");
        System.out.println("  - Unsynced reads (no lock overhead)");
        System.out.println("  - Lock only during write/update");
        System.out.println("  - Size estimation using counter cells (LongAdder)");
        System.out.println("  - No iterator modification exception (weakly consistent)");
        System.out.println();
        System.out.println("Senior-Level Insight:");
        System.out.println("  - Java 8: better scalability than Java 7");
        System.out.println("  - CAS reduces contention vs segment locks");
        System.out.println("  - Counter cells avoid contention on size()");
        System.out.println("  - Still not suitable for compound operations without external sync");
    }
}
