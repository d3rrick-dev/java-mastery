package phase11;

/**
 * LESSON 3: METASPACE
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Metaspace vs PermGen
 * 2. What's stored in Metaspace
 * 3. Metaspace growth
 * 4. Monitoring Metaspace
 * 5. Common issues
 */

public class Lesson03_Metaspace {
    public static void main(String[] args) {
        System.out.println("""
            === METASPACE ===
            
            1. METASPACE VS PERMGEN
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Metaspace replaced PermGen in Java 8. It stores class metadata
               in native memory instead of heap.
            
               WHY IT EXISTS:
               - Unlimited class metadata (auto-grows)
               - Better memory management
               - Strings interned in heap (not PermGen)
            
               PERMGEN (Java 7 and earlier):
                   - Part of JVM heap
                   - Fixed size (default 64MB)
                   - OutOfMemoryError: PermGen space
            
               METASPACE (Java 8+):
                   - Uses native memory
                   - Auto-grows (limited by OS)
                   - Can set max: -XX:MaxMetaspaceSize
            
               INTERNAL MECHANICS:
                   - Class metadata allocated in native memory
                   - GC cleans up when classes unloaded
                   - Compressed class pointers for 64-bit
            
               SIMPLE EXAMPLE:
                   // JVM flags:
                   -XX:MetaspaceSize=64m     // Initial size
                   -XX:MaxMetaspaceSize=256m   // Max size
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring Boot app:
                   - Many @Component classes
                   - Dynamic proxies (CGLIB)
                   - Monitor metaspace usage
            
               INTERVIEW QUESTION:
                   "What replaced PermGen? Why was it changed?
                   How do you limit metaspace size?"
            
               COMMON MISTAKES:
                   - Not setting MaxMetaspaceSize
                   - Classloader leaks
            
            ─────────────────────────────────────────────────────────────────────
            
            2. WHAT'S STORED IN METASPACE
               ─────────────────────────────────────────────────────────────────────
               METASPACE CONTAINS:
                   - Class metadata (name, modifiers, parent)
                   - Method information (name, signature)
                   - Field information (name, type)
                   - Constant pool
                   - Static variables
                   - Method bytecode
            
               NOT IN METASPACE:
                   - Instance data (on heap)
                   - Local variables (on stack)
                   - Interned strings (in heap, Java 8+)
            
               SIMPLE EXAMPLE:
                   public class Example {
                       private static int x;  // Metaspace
                       private int y;         // Heap
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservice:
                   - Each @Entity: Class metadata
                   - Each @Repository: Class metadata
                   - Many classes: Large metaspace
            
               INTERVIEW QUESTION:
                   "What is stored in metaspace?
                   What about static variables?"
            
               COMMON MISTAKES:
                   - Confusing with heap
                   - Not understanding class metadata
            
            ─────────────────────────────────────────────────────────────────────
            
            3. METASPACE GROWTH
               ─────────────────────────────────────────────────────────────────────
               WHEN METASPACE GROWS:
                   - New classes loaded
                   - New methods/fields defined
                   - Class loaders create classes
            
               COMMON CAUSES OF OOM:
                   - Classloader leaks (web apps)
                   - Dynamic proxy generation
                   - Too many classes loaded
                   - Reflection creating classes
            
               SIMPLE EXAMPLE:
                   // Classloader leak:
                   ClassLoader cl = new URLClassLoader(urls);
                   Class<?> cls = cl.loadClass("com.Example");
                   // cl never closed: leak!
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Tomcat application:
                   - Each webapp: Separate classloader
                   - Redeploy: Old classloader not GC'd
                   - Monitor: jstat -gc
            
               INTERVIEW QUESTION:
                   "What causes metaspace OOM?
                   How to prevent classloader leaks?"
            
               COMMON MISTAKES:
                   - Not closing classloaders
                   - Not monitoring metaspace
            
            ─────────────────────────────────────────────────────────────────────
            
            4. MONITORING METASPACE
               ─────────────────────────────────────────────────────────────────────
               JVM FLAGS:
                   -XX:MetaspaceSize=64m     // Initial
                   -XX:MaxMetaspaceSize=256m   // Maximum
                   -XX:+PrintFlagsFinal       // Show all
            
               MONITORING TOOLS:
                   - jstat -gc <pid>
                   - jmap -clstats <pid>
                   - VisualVM, JConsole
                   - GC logs: -Xlog:gc*
            
               SIMPLE EXAMPLE:
                   // Check metaspace usage:
                   jstat -gc 12345
                   // MC: Metaspace capacity
                   // MU: Metaspace used
            
               REAL-WORLD BACKEND EXAMPLE:
                   Production monitoring:
                   - Alert on metaspace growth
                   - GC log analysis
                   - Heap dump analysis
            
               INTERVIEW QUESTION:
                   "How to monitor metaspace?
                   What tools show metaspace usage?"
            
               COMMON MISTAKES:
                   - Not setting limits
                   - Ignoring metaspace metrics
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Metaspace is important for:
            - Class metadata storage
            - Memory leak prevention
            - Performance monitoring
            - JVM tuning
            """);
    }
}
