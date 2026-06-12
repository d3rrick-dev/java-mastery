package phase11;

/**
 * LESSON 1: JVM ARCHITECTURE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * JVM (Java Virtual Machine) is the runtime environment
 * that executes Java bytecode. It's like a translator
 * that converts Java code to machine code your computer understands.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Platform independence (Write Once, Run Anywhere)
 * - Memory management (automatic garbage collection)
 * - Security (sandbox execution)
 * - Performance optimization (JIT compilation)
 */

public class Lesson01_JVMArchitecture {

    public static void main(String[] args) {
        System.out.println("=== JVM ARCHITECTURE ===\n");

        // ============================================================
        // EXAMPLE 1: JVM Architecture Overview
        // ============================================================
        System.out.println("--- Example 1: JVM Architecture ---\n");

        System.out.println("JVM Architecture Components:");
        System.out.println();
        System.out.println("  +------------------+");
        System.out.println("  |   Class Loader   |");
        System.out.println("  +------------------+");
        System.out.println("           |");
        System.out.println("           v");
        System.out.println("  +------------------+");
        System.out.println("  |  Runtime Data    |");
        System.out.println("  |  Areas           |");
        System.out.println("  |  (Heap, Stack,   |");
        System.out.println("  |   Method Area)   |");
        System.out.println("  +------------------+");
        System.out.println("           |");
        System.out.println("           v");
        System.out.println("  +------------------+");
        System.out.println("  | Execution Engine |");
        System.out.println("  | (Interpreter,    |");
        System.out.println("  |  JIT Compiler)   |");
        System.out.println("  +------------------+");
        System.out.println("           |");
        System.out.println("           v");
        System.out.println("  +------------------+");
        System.out.println("  |   Native Method  |");
        System.out.println("  |   Interface      |");
        System.out.println("  +------------------+");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Class Loader Subsystem
        // ============================================================
        System.out.println("--- Example 2: Class Loader Subsystem ---\n");

        System.out.println("Class Loader Hierarchy:");
        System.out.println("  1. Bootstrap ClassLoader (loads core Java classes)");
        System.out.println("  2. Extension ClassLoader (loads extensions)");
        System.out.println("  3. System/Application ClassLoader (loads app classes)");
        System.out.println();
        System.out.println("Loading Process:");
        System.out.println("  Loading -> Verification -> Preparation -> Resolution -> Initialization");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Runtime Data Areas
        // ============================================================
        System.out.println("--- Example 3: Runtime Data Areas ---\n");

        System.out.println("Method Area (Metaspace in Java 8+):");
        System.out.println("  - Class metadata, static variables, method code");
        System.out.println();
        System.out.println("Heap:");
        System.out.println("  - Object storage, shared among threads");
        System.out.println("  - Garbage collected");
        System.out.println();
        System.out.println("Stack:");
        System.out.println("  - Method frames, local variables");
        System.out.println("  - Thread-private");
        System.out.println();
        System.out.println("PC Register:");
        System.out.println("  - Points to current instruction");
        System.out.println();
        System.out.println("Native Method Stack:");
        System.out.println("  - For native method execution");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Execution Engine
        // ============================================================
        System.out.println("--- Example 4: Execution Engine ---\n");

        System.out.println("Interpreter:");
        System.out.println("  - Executes bytecode line by line");
        System.out.println("  - Slower but starts immediately");
        System.out.println();
        System.out.println("JIT Compiler:");
        System.out.println("  - Compiles hot code to native machine code");
        System.out.println("  - Faster execution after warmup");
        System.out.println();
        System.out.println("JIT Compilation Levels:");
        System.out.println("  C1 (Client): Fast compilation, moderate optimization");
        System.out.println("  C2 (Server): Slow compilation, aggressive optimization");
        System.out.println("  GraalVM: Advanced optimizations");
        System.out.println();
    }

    // ============================================================
    // JVM ARCHITECTURE DETAILS
    // ============================================================
    /*
     * JVM Architecture:
     *
     * 1. Class Loader Subsystem
     *    - Bootstrap ClassLoader (JRE/lib/rt.jar)
     *    - Extension ClassLoader (JRE/lib/ext)
     *    - System ClassLoader (CLASSPATH)
     *
     * 2. Runtime Data Areas
     *    - Method Area (Metaspace): Class info, static vars
     *    - Heap: Objects, GC managed
     *    - Java Stack: Method frames, thread-private
     *    - PC Register: Current instruction pointer
     *    - Native Method Stack: Native method calls
     *
     * 3. Execution Engine
     *    - Interpreter: Execute bytecode
     *    - JIT Compiler: Compile to native code
     *    - GC: Memory management
     *
     * 4. Native Method Interface (JNI)
     *    - Call C/C++ libraries
     */
}
