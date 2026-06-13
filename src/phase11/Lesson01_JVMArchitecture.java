package phase11;

/**
 * LESSON 1: JVM ARCHITECTURE
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. JVM Architecture overview
 * 2. Class Loader Subsystem
 * 3. Runtime Data Areas
 * 4. Execution Engine
 * 5. Native Method Interface
 */

public class Lesson01_JVMArchitecture {
    public static void main(String[] args) {
        System.out.println("""
            === JVM ARCHITECTURE ===
            
            1. JVM ARCHITECTURE OVERVIEW
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               JVM (Java Virtual Machine) is the runtime environment that executes
               Java bytecode. It provides platform independence through the
               "Write Once, Run Anywhere" principle.
            
               WHY IT EXISTS:
               - Platform independence
               - Memory management (automatic garbage collection)
               - Security (sandbox execution)
               - Performance optimization (JIT compilation)
            
               INTERNAL MECHANICS:
                   - Class Loader: Loads .class files
                   - Runtime Data Areas: Memory management
                   - Execution Engine: Interprets/compiles bytecode
                   - JNI: Native method interface
            
               SIMPLE EXAMPLE:
                   // When you run: java MyApp
                   // 1. Bootstrap ClassLoader loads core Java classes
                   // 2. System ClassLoader loads MyApp.class
                   // 3. JVM allocates heap/stack memory
                   // 4. Execution Engine runs the bytecode
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring Boot application:
                   - Bootstrap loads java.lang, java.util
                   - System loads application classes
                   - Heap stores Spring context, beans
                   - JIT optimizes hot request handling code
            
               INTERVIEW QUESTION:
                   "What are the main components of JVM?
                   How does JVM achieve platform independence?"
            
               COMMON MISTAKES:
                   - Confusing JVM with JDK/JRE
                   - Not understanding classloader hierarchy
            
            ─────────────────────────────────────────────────────────────────────
            
            2. CLASS LOADER SUBSYSTEM
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               ClassLoaders load Java classes into memory. They follow a parent-first
               delegation model.
            
               WHY IT EXISTS:
               - Security: Core classes can't be overridden
               - Memory efficiency: Share common classes
               - Modularity: Separate class loading
            
               CLASS LOADER TYPES:
                   Bootstrap ClassLoader:
                       - Loads core Java classes (rt.jar)
                       - Native implementation
                       - No parent
                   Extension ClassLoader:
                       - Loads JRE extensions (lib/ext)
                       - Parent: Bootstrap
                   System ClassLoader:
                       - Loads application classes
                       - Parent: Extension
            
               SIMPLE EXAMPLE:
                   // Class loading process:
                   // 1. Loading: Find and load .class file
                   // 2. Verification: Ensure bytecode is valid
                   // 3. Preparation: Allocate static fields
                   // 4. Resolution: Link to other classes
                   // 5. Initialization: Run static blocks
            
               REAL-WORLD BACKEND EXAMPLE:
                   A web application server:
                   - Each webapp has its own ClassLoader
                   - Allows different versions of same library
                   - Classloader leaks cause memory issues
            
               INTERVIEW QUESTION:
                   "What is the parent-first delegation model?
                   How do you create a custom ClassLoader?"
            
               COMMON MISTAKES:
                   - Not understanding delegation
                   - Classloader memory leaks
            
            ─────────────────────────────────────────────────────────────────────
            
            3. RUNTIME DATA AREAS
               ─────────────────────────────────────────────────────────────────────
               METHOD AREA (Metaspace):
                   - Class metadata, static variables
                   - Native memory (not heap)
                   - Grows automatically
            
               HEAP:
                   - Object storage
                   - Shared among threads
                   - Garbage collected
                   - Young/Old generation
            
               STACK:
                   - Method frames
                   - Thread-private
                   - Primitives and references
            
               PC REGISTER:
                   - Points to current instruction
                   - Thread-private
            
               NATIVE METHOD STACK:
                   - Native method calls
                   - Thread-private
            
               SIMPLE EXAMPLE:
                   public class Example {
                       private static int staticVar;  // Metaspace
                       private int instanceVar;       // Heap
                       
                       public void method() {
                           int localVar = 42;       // Stack
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A REST API endpoint:
                   - Request object: Heap
                   - Method parameters: Stack
                   - Class metadata: Metaspace
            
               INTERVIEW QUESTION:
                   "What is stored in each memory area?
                   What error occurs when stack is full?"
            
               COMMON MISTAKES:
                   - Confusing heap and metaspace
                   - Not understanding thread-private memory
            
            ─────────────────────────────────────────────────────────────────────
            
            4. EXECUTION ENGINE
               ─────────────────────────────────────────────────────────────────────
               INTERPRETER:
                   - Executes bytecode line by line
                   - Slower but starts immediately
            
               JIT COMPILER:
                   - Compiles hot code to native machine code
                   - C1 (Client): Fast compile, moderate optimization
                   - C2 (Server): Slow compile, aggressive optimization
            
               GARBAGE COLLECTOR:
                   - Reclaims unused memory
                   - Runs concurrently
            
               SIMPLE EXAMPLE:
                   // JIT optimization:
                   for (int i = 0; i < 1000000; i++) {
                           // Interpreted at first
                           // JIT compiled after ~10K iterations
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A high-throughput service:
                   - JIT optimizes request processing loops
                   - Profile-guided optimization
                   - Consider warmup in performance tests
            
               INTERVIEW QUESTION:
                   "What is JIT compilation?
                   How does the interpreter differ from JIT?"
            
               COMMON MISTAKES:
                   - Not understanding warmup period
                   - Confusing C1 and C2 compilers
            
            ─────────────────────────────────────────────────────────────────────
            
            5. NATIVE METHOD INTERFACE (JNI)
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               JNI allows Java to call native code (C/C++).
            
               WHY IT EXISTS:
               - Performance-critical code
               - OS-specific functionality
               - Legacy system integration
            
               SIMPLE EXAMPLE:
                   // System.loadLibrary("native");
                   // native method: private native void process();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A database driver:
                   - JDBC uses JNI for native DB access
                   - Performance optimization
            
               INTERVIEW QUESTION:
                   "What is JNI? When would you use it?
                   What are the drawbacks?"
            
               COMMON MISTAKES:
                   - Not considering portability
                   - Memory management in native code
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            JVM Architecture is essential for:
            - Understanding Java execution
            - Performance optimization
            - Memory management
            - Debugging class loading issues
            """);
    }
}
