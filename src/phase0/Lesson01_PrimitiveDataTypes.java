package phase0;

/**
 * JAVA FUNDAMENTALS LESSON: Primitive Data Types
 *
 * Phase 0: Java Fundamentals & Data Types
 *
 * This lesson covers:
 * 1. Primitive data types overview
 * 2. Size and memory usage
 * 3. Min/max values
 * 4. Memory representation
 * 5. Real-world examples
 * 6. Interview questions
 * 7. Common mistakes
 */

public class Lesson01_PrimitiveDataTypes {
    public static void main(String[] args) {
        System.out.println("""
            === PRIMITIVE DATA TYPES ===
            
            1. PRIMITIVE DATA TYPES OVERVIEW
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Java has 8 primitive data types that are the building blocks of all
               data manipulation. They are not objects and are stored directly in
               memory (stack for local variables).
            
               WHY THEY EXIST:
               - Performance: No object overhead
               - Memory efficiency: Fixed size
               - Direct value storage: No references
               - CPU optimization: Native machine types
            
               THE 8 PRIMITIVES:
                   Type      | Size (bits) | Size (bytes) | Min Value           | Max Value
                   ---------|-------------|--------------|---------------------|---------------------
                   byte     | 8           | 1            | -128                | 127
                   short    | 16          | 2            | -32,768             | 32,767
                   int      | 32          | 4            | -2,147,483,648      | 2,147,483,647
                   long     | 64          | 8            | -9,223,372,036,854,775,808 | 9,223,372,036,854,775,807
                   float    | 32          | 4            | 1.4e-45             | 3.4028235e38
                   double   | 64          | 8            | 4.9e-324            | 1.7976931348623157e308
                   char     | 16          | 2            | 0                   | 65,535 (unsigned)
                   boolean  | 8 (JVM dep) | 1 (approx)   | true / false        | true / false
            
               INTERNAL MECHANICS:
                   - Stored directly in stack memory (for local variables)
                   - No object header overhead
                   - Direct CPU register mapping
                   - Two's complement for integers
                   - IEEE 754 for floating point
            
               SIMPLE EXAMPLE:
                   public class DataTypeExample {
                       public static void main(String[] args) {
                           byte b = 127;        // 1 byte
                           short s = 32767;     // 2 bytes
                           int i = 2147483647; // 4 bytes
                           long l = 9223372036854775807L; // 8 bytes
                           float f = 3.14f;     // 4 bytes
                           double d = 3.14159;  // 8 bytes
                           char c = 'A';        // 2 bytes (Unicode)
                           boolean bool = true; // 1 byte (JVM dependent)
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user management system:
                   - byte: HTTP status codes (-128 to 127)
                   - short: Age (0-150 fits in short)
                   - int: User ID (most common for IDs)
                   - long: Timestamp in milliseconds
                   - double: Financial calculations (precision)
                   - char: Single character fields
                   - boolean: Active status, email verified
            
               INTERVIEW QUESTION:
                   "What is the size of an int in Java? What happens on overflow?
                   How many primitive types are there?"
            
               COMMON MISTAKES:
                   - int x = 2147483648; // Compilation error! Too large
                   - long l = 1234567890123; // Missing L suffix
                   - float f = 3.14; // Should be 3.14f
            
               PERFORMANCE & SCALABILITY:
                   - Use smallest type that fits your data
                   - byte[] for binary data (not Byte[])
                   - int is default for most operations
            
            ─────────────────────────────────────────────────────────────────────
            
            2. INTEGER TYPES: byte, short, int, long
               ─────────────────────────────────────────────────────────────────────
               BYTE:
                   - 8-bit signed two's complement
                   - Range: -128 to 127
                   - Use: Binary data, small ranges
                   
                   int unsignedByte = Byte.toUnsignedInt((byte) 200); // 200
                   // 200 as signed byte = -56, but unsigned = 200
            
               SHORT:
                   - 16-bit signed two's complement
                   - Range: -32,768 to 32,767
                   - Use: Memory-constrained scenarios
            
               INT:
                   - 32-bit signed two's complement
                   - Range: -2^31 to 2^31-1
                   - Default for most integer operations
            
               LONG:
                   - 64-bit signed two's complement
                   - Range: -2^63 to 2^63-1
                   - Use: Large numbers, timestamps
            
               MEMORY REPRESENTATION (Two's Complement):
                   For byte with value -56:
                   Binary: 11001000
                   To get positive: invert + 1
                   Inverted: 00110111
                   + 1:     00111000 = 56
            
               REAL-WORLD BACKEND EXAMPLE:
                   A file processing service:
                   - byte: File bytes, network packets
                   - int: Loop counters, array indices
                   - long: File sizes, database IDs
            
               INTERVIEW QUESTION:
                   "What is two's complement? How does overflow work in Java?
                   What is the result of: byte b = (byte) 128?"
            
               COMMON MISTAKES:
                   - int x = 1L; // Compilation error
                   - long l = 1; // OK, implicit widening
                   - byte b = 128; // Compilation error
            
            ─────────────────────────────────────────────────────────────────────
            
            3. FLOATING POINT TYPES: float, double
               ─────────────────────────────────────────────────────────────────────
               FLOAT:
                   - 32-bit IEEE 754 single precision
                   - ~6-7 decimal digits precision
                   - Suffix: f or F
            
               DOUBLE:
                   - 64-bit IEEE 754 double precision
                   - ~15-16 decimal digits precision
                   - Default for decimal literals
            
               IEEE 754 REPRESENTATION:
                   sign(1) | exponent(8 for float, 11 for double) | mantissa(23/52)
                   
                   Example: float 3.14
                   Sign: 0 (positive)
                   Exponent: 10000001 (biased)
                   Mantissa: 00111100000000000000000
            
               PRECISION ISSUES:
                   System.out.println(0.1 + 0.2); // 0.30000000000000004
                   System.out.println(1.0 / 3.0); // 0.3333333333333333
            
               REAL-WORLD BACKEND EXAMPLE:
                   A financial service:
                   - double: Scientific calculations, analytics
                   - BigDecimal: Financial calculations (NOT double!)
                   - float: Graphics, games (where precision less critical)
            
               INTERVIEW QUESTION:
                   "Why is 0.1 + 0.2 != 0.3 in Java?
                   What should you use for financial calculations?"
            
               COMMON MISTAKES:
                   - Using == for float/double comparison
                   - Using double for money
                   - Not understanding precision limits
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CHAR AND BOOLEAN
               ─────────────────────────────────────────────────────────────────────
               CHAR:
                   - 16-bit unsigned Unicode (UTF-16)
                   - Range: 0 to 65,535
                   - NOT a single byte!
            
                   char c1 = 'A';           // 65
                   char c2 = 65;            // 'A'
                   char c3 = '\\u0041';      // 'A' (Unicode)
                   char c4 = (char) 65535;  // Max value
            
               BOOLEAN:
                   - true or false
                   - JVM dependent size (typically 1 byte)
                   - Default: false
                   - Size in arrays: 1 byte per element
                   - Size in objects: may be padded to word boundary
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user profile service:
                   - char: Single character (title, gender)
                   - boolean: Active status, email verified, premium user
            
               INTERVIEW QUESTION:
                   "What is the size of a char? Can you store Unicode in char?
                   What is the default value of boolean?"
            
               COMMON MISTAKES:
                   - char c = -1; // Compilation error (unsigned)
                   - boolean b = 1; // Compilation error
            
            ─────────────────────────────────────────────────────────────────────
            
            5. DEFAULT VALUES
               ─────────────────────────────────────────────────────────────────────
               FIELD DEFAULTS:
                   Type      | Default Value
                   ---------|---------------
                   byte     | 0
                   short    | 0
                   int      | 0
                   long     | 0L
                   float    | 0.0f
                   double   | 0.0d
                   char     | '\\u0000'
                   boolean  | false
            
               LOCAL VARIABLES:
                   - NO default values!
                   - Must be initialized before use
                   - Compilation error if not initialized
            
               SIMPLE EXAMPLE:
                   class Example {
                       int field; // 0
                       
                       void method() {
                           int local; // Compilation error!
                           System.out.println(local);
                       }
                   }
            
               INTERVIEW QUESTION:
                   "What is the default value of int? What about local variables?
                   What happens if you use an uninitialized local variable?"
            
               COMMON MISTAKES:
                   - Assuming local variables have defaults
                   - Forgetting to initialize arrays
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Primitive data types are fundamental for:
            - Memory-efficient data storage
            - Performance-critical operations
            - Understanding Java's type system
            - Avoiding common pitfalls
            """);
    }
}