package phase11;

/**
 * LESSON 3: METASPACE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Metaspace stores class metadata (replaced PermGen in Java 8).
 * Like a library catalog - stores info about books, not the books themselves.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Stores class definitions, methods, fields
 * - Native memory (not JVM heap)
 * - Dynamically resizable
 */

public class Lesson03_Metaspace {

    public static void main(String[] args) {
        System.out.println("=== METASPACE ===\n");

        // ============================================================
        // EXAMPLE 1: Metaspace vs PermGen
        // ============================================================
        System.out.println("--- Example 1: Metaspace vs PermGen ---\n");

        System.out.println("PermGen (Java 7 and earlier):");
        System.out.println("  - Part of JVM heap");
        System.out.println("  - Fixed size (default 64MB)");
        System.out.println("  - Causes OutOfMemoryError: PermGen space");
        System.out.println();
        System.out.println("Metaspace (Java 8+):");
        System.out.println("  - Uses native memory");
        System.out.println("  - Auto-grows (limited by OS memory)");
        System.out.println("  - Can set max with -XX:MaxMetaspaceSize");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: What's stored in Metaspace
        // ============================================================
        System.out.println("--- Example 2: What's Stored ---\n");

        System.out.println("Metaspace contains:");
        System.out.println("  - Class metadata (name, modifiers, parent, interfaces)");
        System.out.println("  - Method information (name, return type, parameters)");
        System.out.println("  - Field information (name, type, modifiers)");
        System.out.println("  - Constant pool");
        System.out.println("  - Static variables (moved from PermGen)");
        System.out.println("  - Method bytecode");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Metaspace growth
        // ============================================================
        System.out.println("--- Example 3: Metaspace Growth ---\n");

        System.out.println("Metaspace grows when:");
        System.out.println("  - New classes are loaded");
        System.out.println("  - New methods/fields are defined");
        System.out.println("  - Class loaders create new classes");
        System.out.println();
        System.out.println("Common causes of Metaspace OOM:");
        System.out.println("  - Class loader leaks (web apps)");
        System.out.println("  - Dynamic proxy generation (CGLIB)");
        System.out.println("  - Too many classes loaded");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Monitoring Metaspace
        // ============================================================
        System.out.println("--- Example 4: Monitoring ---\n");

        System.out.println("JVM flags for Metaspace:");
        System.out.println("  -XX:MetaspaceSize=64m    (initial size)");
        System.out.println("  -XX:MaxMetaspaceSize=256m (max size)");
        System.out.println("  -XX:+PrintFlagsFinal     (show all flags)");
        System.out.println();
        System.out.println("Monitoring tools:");
        System.out.println("  - jstat -gc <pid>");
        System.out.println("  - jmap -clstats <pid>");
        System.out.println("  - VisualVM, JConsole");
        System.out.println();
    }

    // ============================================================
    // METASPACE DETAILS
    // ============================================================
    /*
     * Metaspace (Java 8+):
     *
     * - Replaced PermGen (Java 7 and earlier)
     * - Uses native memory (not JVM heap)
     * - Auto-grows by default
     * - Garbage collected when classes are unloaded
     *
     * Key differences from PermGen:
     * 1. No fixed size (auto-grows)
     * 2. Native memory (not heap)
     * 3. Strings interned in heap (not PermGen)
     * 4. Class metadata only
     *
     * Common issues:
     * - Metaspace OOM: Too many classes loaded
     * - Class loader leaks in app servers
     * - CGLIB proxy generation
     */
}
