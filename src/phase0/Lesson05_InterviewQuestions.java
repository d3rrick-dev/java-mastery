package phase0;

/**
 * JAVA FUNDAMENTALS LESSON: Interview Questions
 *
 * Phase 0: Java Fundamentals & Data Types
 *
 * This lesson covers common interview questions and edge cases.
 */

public class Lesson05_InterviewQuestions {
    public static void main(String[] args) {
        System.out.println("""
            === INTERVIEW QUESTIONS & EDGE CASES ===
            
            1. PRIMITIVE VS WRAPPER QUESTIONS
               ─────────────────────────────────────────────────────────────────────
               Q: What is the output?
                   Integer a = 127;
                   Integer b = 127;
                   System.out.println(a == b);
                   
                   Integer c = 128;
                   Integer d = 128;
                   System.out.println(c == d);
               
               A: true, false (Integer cache)
            
               Q: What is the output?
                   int x = 0;
                   System.out.println(x++);
                   System.out.println(++x);
                   System.out.println(x);
               
               A: 0, 2, 2
            
               Q: What is the output?
                   int a = 1;
                   int b = 2;
                   int c = a + b;
                   String s = "" + a + b + c;
                   System.out.println(s);
               
               A: "123" (string concatenation)
            
               INTERVIEW TIP:
                   - Know the cache range
                   - Understand == vs equals
                   - Know operator precedence
            
            ─────────────────────────────────────────────────────────────────────
            
            2. TYPE CONVERSION QUESTIONS
  �────────────────────────────────────────────────────────────────────
               Q: What is the output?
                   byte b = (byte) 128;
                   System.out.println(b);
               
               A: -128 (overflow in two's complement)
            
               Q: What is the output?
                   int i = Integer.MAX_VALUE;
                   long l = i + 100L;
                   System.out.println(l);
               
               A: -2147483549 (int overflow before long conversion)
            
               Q: What is the output?
                   double d1 = 0.1;
                   double d2 = 0.2;
                   System.out.println(d1 + d2 == 0.3);
               
               A: false (floating point precision)
            
               INTERVIEW TIP:
                   - Understand two's complement
                   - Know floating point precision issues
                   - Use BigDecimal for money
            
            ─────────────────────────────────────────────────────────────────────
            
            3. MEMORY AND DEFAULT VALUE QUESTIONS
               ─────────────────────────────────────────────────────────────────────
               Q: What is the default value?
                   class Example {
                       int x;
                       Integer y;
                       public void method() {
                           int z;
                           // System.out.println(z); // Error!
                       }
                   }
               
               A: x=0, y=null, z not initialized
            
               Q: What is the output?
                   String s1 = "hello";
                   String s2 = "hello";
                   String s3 = new String("hello");
                   System.out.println(s1 == s2);
                   System.out.println(s1 == s3);
               
               A: true, false
            
               Q: How much memory for int[1000]?
               
               A: ~4000 bytes (1000 * 4) + object overhead
            
               INTERVIEW TIP:
                   - Know default values
                   - Understand String pool
                   - Consider object overhead
            
            ─────────────────────────────────────────────────────────────────────
            
            4. COMMON TRICK QUESTIONS
               ─────────────────────────────────────────────────────────────────────
               Q: What is the output?
                   int x = 5;
                   System.out.println(x > 3 && x < 7);
                   System.out.println(x > 3 || x < 7);
                   System.out.println(x > 3 ^ x < 7);
                   System.out.println(x > 3 & x < 7);
               
               A: true, true, false, true
               (&& and || are short-circuit, & and | are not)
            
               Q: What is the output?
                   for (int i = 0; i < 10; i++) {
                       if (i % 2 == 0) continue;
                       if (i > 5) break;
                       System.out.print(i + " ");
                   }
               
               A: 1 3 (odd numbers, break at 7)
            
               Q: What is the output?
                   int x = 10;
                   {
                       int y = 20;
                       System.out.println(x + y);
                   }
                   // System.out.println(y); // Error!
               
               A: 30 (block scope)
            
               INTERVIEW TIP:
                   - Know short-circuit operators
                   - Understand scope rules
                   - Practice tricky conditions
            
            ─────────────────────────────────────────────────────────────────────
            
            5. PRACTICAL EXERCISES
               ─────────────────────────────────────────────────────────────────────
               EXERCISE 1: Type Safety
                   Write a method that safely converts Object to int:
                   public static int safeToInt(Object obj) {
                       if (obj == null) return 0;
                       if (obj instanceof Number) {
                           return ((Number) obj).intValue();
                       }
                       if (obj instanceof String) {
                           return Integer.parseInt((String) obj);
                       }
                       throw new IllegalArgumentException("Cannot convert to int");
                   }
            
               EXERCISE 2: Memory Calculator
                   Calculate memory for:
                   - int[1000]: ~4000 bytes
                   - Integer[1000]: ~24000 bytes
                   - String[1000] with "test": ~40000+ bytes
            
               EXERCISE 3: Overflow Detection
                   Implement safe arithmetic:
                   public static boolean willOverflow(int a, int b) {
                       return (b > 0 && a > Integer.MAX_VALUE - b) ||
                              (b < 0 && a < Integer.MIN_VALUE - b);
                   }
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Master these concepts for interviews:
            - Primitive vs wrapper differences
            - Type conversion rules
            - Memory model basics
            - Common edge cases
            - Default values
            """);
    }
}