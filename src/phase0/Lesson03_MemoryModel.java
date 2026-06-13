package phase0;

/**
 * JAVA FUNDAMENTALS LESSON: Java Memory Model
 *
 * Phase 0: Java Fundamentals & Data Types
 *
 * This lesson covers:
 * 1. Stack vs Heap memory
 * 2. How variables are stored
 * 3. Reference types vs value types
 * 4. Real-world examples
 * 5. Interview questions
 */

public class Lesson03_MemoryModel {
    public static void main(String[] args) {
        System.out.println("""
            === JAVA MEMORY MODEL ===
            
            1. STACK VS HEAP MEMORY
               ─────────────────────────────────────────────────────────────────────
               STACK MEMORY:
                   - Stores method frames and local variables
                   - Fast access (LIFO structure)
                   - Thread-safe (each thread has its own stack)
                   - Fixed size (throws StackOverflowError if full)
                   - Stores: primitives, object references, method call info
            
               HEAP MEMORY:
                   - Stores objects and arrays
                   - Dynamic size (throws OutOfMemoryError if full)
                   - Shared between threads
                   - Garbage collected
                   - Stores: object instances, array elements
            
               MEMORY LAYOUT:
                   Stack:
                   +--------+
                   | local1 | <- int x = 5;
                   +--------+
                   | local2 | <- String ref -> [points to heap]
                   +--------+
                   | method | <- return address, parameters
                   +--------+
                   
                   Heap:
                   +-----------------+
                   | String object   |
                   | value: "hello"  |
                   +-----------------+
                   | Integer object  |
                   | value: 42       |
                   +-----------------+
            
               WHY IT MATTERS:
                   - Stack: fast, limited, thread-safe
                   - Heap: flexible, shared, GC overhead
                   - Understanding helps with performance
            
               SIMPLE EXAMPLE:
                   public void method() {
                       int x = 5;              // Stack: primitive value
                       String s = "hello";     // Stack: reference, Heap: object
                       int[] arr = {1,2,3};  // Stack: reference, Heap: array
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A web request handler:
                   - Request parameters: stack (primitives)
                   - User object: heap
                   - Response list: heap
                   - Deep call stacks: stack overflow risk
            
               INTERVIEW QUESTION:
                   "Where are local variables stored? What about objects?
                   What error occurs when stack is full?"
            
               COMMON MISTAKES:
                   - Creating unnecessary objects
                   - Not understanding memory implications
            
            ─────────────────────────────────────────────────────────────────────
            
            2. VALUE TYPES VS REFERENCE TYPES
               ─────────────────────────────────────────────────────────────────────
               VALUE TYPES (Primitives):
                   - Store actual value
                   - Copied by value
                   - No null (except boolean in some contexts)
                   - Default: 0, 0.0, false
            
               REFERENCE TYPES (Objects):
                   - Store reference (memory address)
                   - Copied by reference
                   - Can be null
                   - Default: null
            
               SIMPLE EXAMPLE:
                   // Value type
                   int a = 5;
                   int b = a;
                   a = 10;
                   System.out.println(b); // 5 (copied)
                   
                   // Reference type
                   int[] arr1 = {1, 2, 3};
                   int[] arr2 = arr1;
                   arr1[0] = 10;
                   System.out.println(arr2[0]); // 10 (same object)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user service:
                   - int age: value type (independent copies)
                   - User user: reference type (shared state)
                   - Modifying shared user affects all references
            
               INTERVIEW QUESTION:
                   "What is the difference between == and equals for wrappers?
                   What happens when you assign an array to another variable?"
            
               COMMON MISTAKES:
                   - Using == for object comparison
                   - Not understanding shared references
            
            ─────────────────────────────────────────────────────────────────────
            
            3. OBJECT HEADERS AND OVERHEAD
               ─────────────────────────────────────────────────────────────────────
               OBJECT HEADER:
                   - Mark word: 8-16 bytes (GC info, locking)
                   - Class pointer: 4-8 bytes (type info)
                   - Padding: to align to 8-byte boundary
            
               MEMORY OVERHEAD:
                   Object size = header + fields + padding
                   
                   Example: Integer object
                   - Header: ~16 bytes
                   - int value: 4 bytes
                   - Padding: 4 bytes
                   - Total: ~24 bytes (vs 4 bytes for int)
            
               SIMPLE EXAMPLE:
                   // Memory efficient
                   int[] values = new int[1000]; // 4000 bytes
                   
                   // Memory inefficient
                   Integer[] values = new Integer[1000]; // ~24000 bytes
            
               REAL-WORLD BACKEND EXAMPLE:
                   A caching service:
                   - Use int[] for numeric cache keys
                   - Use Integer for nullable values
                   - Consider memory when caching large datasets
            
               INTERVIEW QUESTION:
                   "How much memory does an Integer object use?
                   What is the object header for?"
            
               COMMON MISTAKES:
                   - Not considering object overhead
                   - Using wrappers unnecessarily
            
            ─────────────────────────────────────────────────────────────────────
            
            4. STRING INTERN POOL
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
                   String literals are stored in a special pool to save memory.
            
               WHY IT EXISTS:
                   - Memory efficiency for duplicate strings
                   - String immutability enables safe sharing
            
               INTERNAL MECHANICS:
                   - String literals: automatically interned
                   - new String(): creates new object (not interned)
                   - String.intern(): manually intern
            
               SIMPLE EXAMPLE:
                   String s1 = "hello";
                   String s2 = "hello";
                   System.out.println(s1 == s2); // true (same reference)
                   
                   String s3 = new String("hello");
                   System.out.println(s1 == s3); // false (different object)
                   
                   String s4 = s3.intern();
                   System.out.println(s1 == s4); // true (interned)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user management system:
                   - Role names: "ADMIN", "USER" (interned)
                   - User input: may not be interned
                   - Use intern() for deduplication
            
               INTERVIEW QUESTION:
                   "What is the String pool? How does intern() work?
                   What is the difference between String s = 'a' and new String('a')?"
            
               COMMON MISTAKES:
                   - Using == for string comparison
                   - Not understanding intern behavior
            
            ─────────────────────────────────────────────────────────────────────
            
            5. ARRAYS IN MEMORY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
                   Arrays are objects stored in heap with length field.
            
               MEMORY LAYOUT:
                   int[] arr:
                   +-----------------+
                   | Object Header   |
                   | length: 3       |
                   +-----------------+
                   | [0]: 1          |
                   | [1]: 2          |
                   | [2]: 3          |
                   +-----------------+
            
               SIMPLE EXAMPLE:
                   int[] arr = {1, 2, 3};
                   // Stack: arr (reference)
                   // Heap: array object with 3 int values
            
               REAL-WORLD BACKEND EXAMPLE:
                   A batch processing service:
                   - int[] for numeric IDs
                   - String[] for string data
                   - Large arrays: consider memory impact
            
               INTERVIEW QUESTION:
                   "Where are arrays stored? What is the length field for?
                   How much memory for int[1000]?"
            
               COMMON MISTAKES:
                   - Not considering array overhead
                   - ArrayIndexOutOfBoundsException
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Understanding memory model helps with:
            - Performance optimization
            - Memory leak prevention
            - Choosing appropriate types
            - Debugging memory issues
            """);
    }
}