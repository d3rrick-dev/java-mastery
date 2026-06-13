package phase0;

/**
 * JAVA FUNDAMENTALS LESSON: Type Casting
 *
 * Phase 0: Java Fundamentals & Data Types
 *
 * This lesson covers:
 * 1. Implicit widening casting
 * 2. Explicit narrowing casting
 * 3. Numeric overflow/underflow
 * 4. Real-world examples
 * 5. Interview questions
 */

public class Lesson04_TypeCasting {
    public static void main(String[] args) {
        System.out.println("""
            === TYPE CASTING ===
            
            1. IMPLICIT WIDENING CASTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Java automatically converts smaller types to larger types.
            
               WIDENING ORDER:
                   byte → short → int → long → float → double
                   char → int → long → float → double
            
               WHY IT EXISTS:
                   - No data loss in conversion
                   - Safe automatic conversion
                   - Type compatibility
            
               SIMPLE EXAMPLE:
                   int i = 100;
                   long l = i;     // OK: implicit widening
                   double d = l;   // OK: implicit widening
                   float f = i;    // OK: implicit widening
            
               REAL-WORLD BACKEND EXAMPLE:
                   A metrics service:
                   - int counts: sum to long for totals
                   - int values: convert to double for averages
                   - No explicit casting needed
            
               INTERVIEW QUESTION:
                   "What is implicit casting? When does it occur?
                   What is the order of widening conversions?"
            
               COMMON MISTAKES:
                   - Assuming all conversions are implicit
                   - Not understanding precision loss in float→double
            
            ─────────────────────────────────────────────────────────────────────
            
            2. EXPLICIT NARROWING CASTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Converting larger types to smaller types requires explicit casting.
            
               SYNTAX:
                   targetType variable = (targetType) sourceValue;
            
               SIMPLE EXAMPLE:
                   double d = 123.456;
                   int i = (int) d;  // 123 (truncation)
                   
                   long l = 128L;
                   byte b = (byte) l; // -128 (overflow)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A data processing service:
                   - double metrics: cast to int for display
                   - long IDs: cast to int for legacy systems
                   - Handle truncation carefully
            
               INTERVIEW QUESTION:
                   "What is explicit casting? What happens to the decimal part?
                   What is the result of: (int) 123.999?"
            
               COMMON MISTAKES:
                   - Not handling data loss
                   - Silent overflow
            
            ─────────────────────────────────────────────────────────────────────
            
            3. NUMERIC OVERFLOW AND UNDERFLOW
               ─────────────────────────────────────────────────────────────────────
               INTEGER OVERFLOW:
                   - Wraps around in two's complement
                   - No exception thrown!
            
               SIMPLE EXAMPLE:
                   int max = Integer.MAX_VALUE; // 2147483647
                   int overflow = max + 1;      // -2147483648
                   
                   int min = Integer.MIN_VALUE; // -2147483648
                   int underflow = min - 1;     // 2147483647
            
               FLOATING POINT:
                   - Overflow: Infinity
                   - Underflow: 0.0 or -0.0
            
                   double d = Double.MAX_VALUE;
                   double overflow = d * 2; // Infinity
            
               REAL-WORLD BACKEND EXAMPLE:
                   A counter service:
                   - int counter: may overflow silently
                   - Use long for large counts
                   - Use BigInteger for arbitrary precision
                   - Validate input ranges
            
               INTERVIEW QUESTION:
                   "What happens on integer overflow? How to detect it?
                   What is the result of: Integer.MAX_VALUE + 1?"
            
               COMMON MISTAKES:
                   - Not checking for overflow
                   - Assuming Math operations throw exceptions
            
            ─────────────────────────────────────────────────────────────────────
            
            4. WRAPPER CLASS CASTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Wrapper classes can be cast between each other.
            
               SIMPLE EXAMPLE:
                   // Numeric wrappers
                   Number n = Integer.valueOf(42);
                   Object o = n;
                   Number n2 = (Number) o;
                   Integer i = (Integer) n2;
            
               REAL-WORLD BACKEND EXAMPLE:
                   A generic metrics service:
                   - Number for any numeric type
                   - Cast to specific type when needed
                   - Handle ClassCastException
            
               INTERVIEW QUESTION:
                   "Can you cast Integer to Long? What about Number to Integer?
                   What exception is thrown for invalid casts?"
            
               COMMON MISTAKES:
                   - ClassCastException
                   - Not checking instanceof first
            
            ─────────────────────────────────────────────────────────────────────
            
            5. PRACTICAL EXERCISES
               ─────────────────────────────────────────────────────────────────────
               EXERCISE 1: Overflow Detection
                   Write a method to detect integer overflow:
                   public static int safeAdd(int a, int b) {
                       if (b > 0 && a > Integer.MAX_VALUE - b) {
                           throw new ArithmeticException("Overflow");
                       }
                       if (b < 0 && a < Integer.MIN_VALUE - b) {
                           throw new ArithmeticException("Underflow");
                       }
                       return a + b;
                   }
            
               EXERCISE 2: Type Conversion Utility
                   Create a utility to convert between numeric types safely:
                   public static <T extends Number> T convert(Number value, Class<T> target) {
                       // Handle conversion with overflow check
                   }
            
               INTERVIEW QUESTION:
                   "How would you implement safe integer addition?
                   What about converting between numeric types?"
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Type casting is essential for:
            - Type compatibility
            - Data conversion
            - Understanding overflow behavior
            - Safe numeric operations
            """);
    }
}