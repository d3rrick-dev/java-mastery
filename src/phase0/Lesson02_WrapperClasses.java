package phase0;

/**
 * JAVA FUNDAMENTALS LESSON: Wrapper Classes
 *
 * Phase 0: Java Fundamentals & Data Types
 *
 * This lesson covers:
 * 1. Wrapper classes overview
 * 2. Autoboxing and unboxing
 * 3. Common pitfalls
 * 4. Real-world examples
 * 5. Interview questions
 */

public class Lesson02_WrapperClasses {
    public static void main(String[] args) {
        System.out.println("""
            === WRAPPER CLASSES ===
            
            1. WRAPPER CLASSES OVERVIEW
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Wrapper classes provide object representations of primitive types,
               enabling primitives to be used where objects are required.
            
               THE 8 WRAPPER CLASSES:
                   Primitive | Wrapper Class
                   ----------|--------------
                   byte      | Byte
                   short     | Short
                   int       | Integer
                   long      | Long
                   float     | Float
                   double    | Double
                   char      | Character
                   boolean   | Boolean
            
               WHY THEY EXIST:
               - Collections require objects (no primitives)
               - Generics work with objects only
               - Nullability (primitives can't be null)
               - Utility methods (parseInt, valueOf, etc.)
            
               INTERNAL MECHANICS:
                   - Each wrapper has a 'value' field holding the primitive
                   - Object header overhead (~12-16 bytes)
                   - Cached instances for common values (-128 to 127 for Integer)
            
               SIMPLE EXAMPLE:
                   // Before Java 5 (manual wrapping)
                   Integer i1 = Integer.valueOf(42);
                   int i2 = i1.intValue();
                   
                   // After Java 5 (autoboxing)
                   Integer i3 = 42;  // autoboxing
                   int i4 = i3;      // unboxing
            
               REAL-WORLD BACKEND EXAMPLE:
                   A JSON API response:
                   - JSON numbers are objects, not primitives
                   - Map<String, Integer> for JSON object
                   - Optional<Integer> for nullable values
                   - Database NULL values map to null wrappers
            
               INTERVIEW QUESTION:
                   "What is the difference between Integer and int?
                   What is the memory overhead of wrapper classes?"
            
               COMMON MISTAKES:
                   - Using wrappers when primitives suffice
                   - Not understanding caching behavior
            
               PERFORMANCE & SCALABILITY:
                   - 16-20x memory overhead vs primitives
                   - Cache helps for small values
                   - Avoid in performance-critical code
            
            ─────────────────────────────────────────────────────────────────────
            
            2. AUTOBOXING AND UNBOXING
               ─────────────────────────────────────────────────────────────────────
               AUTOBOXING:
                   - Automatic conversion from primitive to wrapper
                   - Compiler inserts valueOf() calls
                   
                   List<Integer> list = new ArrayList<>();
                   list.add(42);  // autoboxed to Integer.valueOf(42)
            
               UNBOXING:
                   - Automatic conversion from wrapper to primitive
                   - Compiler inserts xxxValue() calls
                   
                   Integer i = 42;
                   int x = i;  // unboxed to i.intValue()
            
               COMPILATION EQUIVALENCE:
                   // This code:
                   Integer i = 42;
                   int x = i;
                   
                   // Compiles to:
                   Integer i = Integer.valueOf(42);
                   int x = i.intValue();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A REST API endpoint:
                   - @RequestParam Integer count (autoboxing)
                   - List<Long> ids (autoboxing in stream)
                   - JSON deserialization to wrappers
            
               INTERVIEW QUESTION:
                   "What happens during autoboxing? Is it expensive?
                   How does the Integer cache work?"
            
               COMMON MISTAKES:
                   - int x = null; // Compilation error
                   - Integer y = null; int z = y; // NullPointerException!
            
            ─────────────────────────────────────────────────────────────────────
            
            3. INTEGER CACHE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Integer objects for values -128 to 127 are cached and reused.
            
               WHY IT EXISTS:
               - Performance optimization
               - Memory efficiency for small values
               - Common in typical applications
            
               INTERNAL MECHANICS:
                   - Integer.valueOf() checks cache first
                   - Cache is an array of Integer objects
                   - Values outside cache create new objects
            
               SIMPLE EXAMPLE:
                   Integer a = 127;
                   Integer b = 127;
                   System.out.println(a == b);  // true (cached)
                   
                   Integer c = 128;
                   Integer d = 128;
                   System.out.println(c == d);  // false (not cached)
                   
                   System.out.println(c.equals(d)); // true
            
               CACHE CONFIGURATION:
                   -XX:AutoBoxCacheMax=256  // Extend cache to 256
            
               REAL-WORLD BACKEND EXAMPLE:
                   A caching service:
                   - Small status codes benefit from cache
                   - Large IDs create new objects
                   - Consider cache when comparing wrappers
            
               INTERVIEW QUESTION:
                   "What is the output of: Integer a = 128; Integer b = 128; a == b?
                   How can you extend the Integer cache?"
            
               COMMON MISTAKES:
                   - Using == for wrapper comparison
                   - Assuming all wrappers are cached
            
            ─────────────────────────────────────────────────────────────────────
            
            4. NULL POINTER EXCEPTION PITFALLS
               ─────────────────────────────────────────────────────────────────────
               PROBLEM:
                   Unboxing a null wrapper throws NullPointerException.
            
               SIMPLE EXAMPLE:
                   Integer nullable = null;
                   int value = nullable;  // NullPointerException!
                   
                   // In streams:
                   List<Integer> list = Arrays.asList(1, null, 3);
                   int sum = list.stream()
                       .mapToInt(Integer::intValue)  // NPE on null!
                       .sum();
            
               SOLUTIONS:
                   // Filter nulls
                   int sum = list.stream()
                       .filter(Objects::nonNull)
                       .mapToInt(Integer::intValue)
                       .sum();
                   
                   // Use Optional
                   int value = Optional.ofNullable(nullable)
                       .orElse(0);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A database query result:
                   - Nullable integer column
                   - Map to Integer (not int)
                   - Handle null in business logic
            
               INTERVIEW QUESTION:
                   "How do you handle null wrapper values?
                   What is the result of: Integer x = null; int y = x * 2?"
            
               COMMON MISTAKES:
                   - Not checking for null before unboxing
                   - Using == for wrapper comparison
            
            ─────────────────────────────────────────────────────────────────────
            
            5. UTILITY METHODS
               ─────────────────────────────────────────────────────────────────────
               PARSING:
                   int x = Integer.parseInt("123");
                   long y = Long.parseLong("456");
                   double z = Double.parseDouble("78.9");
            
               VALUEOF:
                   Integer a = Integer.valueOf("123");
                   Integer b = Integer.valueOf(123);
            
               STRING CONVERSION:
                   String s1 = String.valueOf(123);
                   String s2 = Integer.toString(123);
                   String s3 = 123 + "";  // Avoid! Creates StringBuilder
            
               REAL-WORLD BACKEND EXAMPLE:
                   A configuration service:
                   - Parse config values from strings
                   - Handle NumberFormatException
                   - Validate input ranges
            
               INTERVIEW QUESTION:
                   "What is the difference between parseInt and valueOf?
                   What exception is thrown for invalid input?"
            
               COMMON MISTAKES:
                   - Not handling NumberFormatException
                   - Using + "" for string conversion
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Wrapper classes are essential for:
            - Using primitives in collections
            - Nullable values
            - Utility methods
            - But avoid in performance-critical code
            """);
    }
}