package phase11;

/**
 * LESSON 2: HEAP VS STACK
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Stack memory
 * 2. Heap memory
 * 3. Memory allocation
 * 4. Stack overflow
 * 5. Memory leak patterns
 */

public class Lesson02_HeapVsStack {
    public static void main(String[] args) {
        System.out.println("""
            === HEAP VS STACK ===
            
            1. STACK MEMORY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Stack is thread-private memory for method frames and local variables.
               It's LIFO (Last In, First Out) - like a stack of plates.
            
               WHY IT EXISTS:
               - Fast allocation/deallocation
               - Thread-safe (each thread has its own)
               - Method call management
            
               INTERNAL MECHANICS:
                   - Each method call creates a frame
                   - Frame contains: local vars, return address, operands
                   - Frame destroyed when method returns
                   - Size: ~1MB per thread by default
            
               SIMPLE EXAMPLE:
                   public void methodA() {
                       int x = 10;           // Stack: primitive
                       String s = "hello";   // Stack: reference
                       methodB();            // New frame on stack
                   }                         // Frame destroyed
                   
                   // Stack grows/shrinks with method calls
            
               REAL-WORLD BACKEND EXAMPLE:
                   A REST endpoint:
                   - Request parameters: Stack
                   - Method variables: Stack
                   - Request object: Heap
                   - Deep call stacks: Stack overflow risk
            
               INTERVIEW QUESTION:
                   "What is stored on stack? What about heap?
                   What error occurs when stack is full?"
            
               COMMON MISTAKES:
                   - Infinite recursion
                   - Not understanding thread-private nature
            
            ─────────────────────────────────────────────────────────────────────
            
            2. HEAP MEMORY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Heap is shared memory for object storage. It's managed by garbage
               collection.
            
               WHY IT EXISTS:
               - Dynamic object allocation
               - Shared between threads
               - Automatic memory management
            
               INTERNAL MECHANICS:
                   - Young Generation: New objects
                   - Old Generation: Long-lived objects
                   - GC algorithms: Serial, Parallel, G1, ZGC
            
               SIMPLE EXAMPLE:
                   public void method() {
                       String s = new String("hello");  // Heap
                       int[] arr = new int[1000];       // Heap
                       // Reference on stack, object on heap
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user service:
                   - User objects: Heap
                   - Cache entries: Heap
                   - Large datasets: Heap
                   - Memory leaks: Unreferenced objects
            
               INTERVIEW QUESTION:
                   "How does GC work? What are generations?
                   What error occurs when heap is full?"
            
               COMMON MISTAKES:
                   - Memory leaks
                   - Not understanding GC roots
            
            ─────────────────────────────────────────────────────────────────────
            
            3. MEMORY ALLOCATION VISUALIZATION
               ─────────────────────────────────────────────────────────────────────
               STACK LAYOUT:
                   +-----------------+
                   | methodB() frame  |
                   |  localVar: 5     |
                   +-----------------+
                   | methodA() frame  |
                   |  x: 10           |
                   |  s: 0x100        |----> Heap
                   +-----------------+
                   | main() frame     |
                   +-----------------+
            
               HEAP LAYOUT:
                   +-----------------+
                   | 0x100: "hello"  |
                   +-----------------+
                   | 0x200: int[1000] |
                   +-----------------+
            
               SIMPLE EXAMPLE:
                   int x = 42;              // Stack
                   String s = "hello";      // Stack ref, Heap string
                   Person p = new Person();   // Stack ref, Heap object
            
               REAL-WORLD BACKEND EXAMPLE:
                   A request handler:
                   - Request params: Stack
                   - Service objects: Heap
                   - Response: Heap
            
               INTERVIEW QUESTION:
                   "Where is a primitive stored?
                   Where is an object reference stored?"
            
               COMMON MISTAKES:
                   - Confusing reference and object
            
            ─────────────────────────────────────────────────────────────────────
            
            4. STACK OVERFLOW
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Stack overflow occurs when stack memory is exhausted.
            
               CAUSES:
                   - Infinite recursion
                   - Very deep call stacks
                   - Large local variables
            
               SIMPLE EXAMPLE:
                   void recurse() {
                       recurse();  // StackOverflowError
                   }
                   
                   // Or:
                   void deepCall(int n) {
                       if (n > 0) deepCall(n - 1);  // Deep stack
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A JSON parser:
                   - Deeply nested JSON: Stack overflow
                   - Use iterative parsing
                   - Increase stack size: -Xss2m
            
               INTERVIEW QUESTION:
                   "How to prevent stack overflow?
                   What JVM flag controls stack size?"
            
               COMMON MISTAKES:
                   - Not handling deep recursion
                   - Not understanding stack limits
            
            ─────────────────────────────────────────────────────────────────────
            
            5. HEAP OUT OF MEMORY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Heap OOM occurs when heap memory is exhausted.
            
               CAUSES:
                   - Memory leaks
                   - Large object allocation
                   - Insufficient heap size
            
               SIMPLE EXAMPLE:
                   List<String> list = new ArrayList<>();
                   while (true) {
                       list.add("data");  // OutOfMemoryError
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A cache service:
                   - Unbounded cache: OOM
                   - Use LRU eviction
                   - Monitor heap usage
            
               INTERVIEW QUESTION:
                   "How to diagnose heap OOM?
                   What tools can you use?"
            
               COMMON MISTAKES:
                   - Not setting max heap
                   - Memory leaks in caches
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Understanding memory model helps with:
            - Performance optimization
            - Memory leak prevention
            - Debugging OOM issues
            - Choosing appropriate data structures
            """);
    }
}
