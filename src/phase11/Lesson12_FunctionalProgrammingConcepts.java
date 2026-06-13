package phase11;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * LESSON 12: FUNCTIONAL PROGRAMMING CONCEPTS
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Pure functions
 * 2. Immutability
 * 3. Higher-order functions
 * 4. Function composition
 * 5. Currying
 * 6. Interview questions
 */

public class Lesson12_FunctionalProgrammingConcepts {
    public static void main(String[] args) {
        System.out.println("""
            === FUNCTIONAL PROGRAMMING CONCEPTS ===
            
            1. PURE FUNCTIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Same input always produces same output, no side effects.
            
               WHY IT EXISTS:
               - Predictable behavior
               - Easy to test
               - Thread-safe
            
               SIMPLE EXAMPLE:
                   // Pure:
                   Function<Integer, Integer> square = n -> n * n;
                   square.apply(5);  // Always 25
                   
                   // Impure:
                   int factor = 2;
                   Function<Integer, Integer> impure = n -> n * n * factor;
                   factor = 3;  // Changes result!
            
               REAL-WORLD BACKEND EXAMPLE:
                   A pricing calculator:
                   - Pure: calculatePrice(basePrice, taxRate)
                   - Impure: calculatePrice(basePrice) using external config
                   - Pure is testable, impure is flexible
            
               INTERVIEW QUESTION:
                   "What is a pure function?
                   Why are they important in concurrent programming?"
            
               COMMON MISTAKES:
                   - Hidden dependencies
                   - Mutable state in closures
            
            ─────────────────────────────────────────────────────────────────────
            
            2. IMMUABILITY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Data cannot be changed after creation.
            
               WHY IT EXISTS:
               - Thread safety
               - Predictable state
               - Easier debugging
            
               SIMPLE EXAMPLE:
                   // Mutable:
                   List<Integer> mutable = new ArrayList<>(Arrays.asList(1, 2, 3));
                   mutable.add(4);  // Modifies original
                   
                   // Immutable:
                   List<Integer> immutable = List.of(1, 2, 3);
                   // immutable.add(4);  // UnsupportedOperationException
                   
                   // Create new:
                   List<Integer> newImmutable = new ArrayList<>(immutable);
                   newImmutable.add(4);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A configuration object:
                   - Final fields
                   - No setters
                   - Thread-safe sharing
            
               INTERVIEW QUESTION:
                   "What is immutability?
                   How to create immutable objects in Java?"
            
               COMMON MISTAKES:
                   - Mutable fields in "immutable" class
                   - Not defensive copying
            
            ─────────────────────────────────────────────────────────────────────
            
            3. HIGHER-ORDER FUNCTIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Functions that take/return other functions.
            
               WHY IT EXISTS:
               - Abstraction
               - Composition
               - Reusability
            
               SIMPLE EXAMPLE:
                   // Function returning function:
                   Function<Integer, Function<Integer, Integer>> multiplier =
                       factor -> n -> n * factor;
                   
                   Function<Integer, Integer> times3 = multiplier.apply(3);
                   times3.apply(4);  // 12
                   
                   // Function taking function:
                   numbers.stream()
                       .filter(n -> n > 0)  // Predicate function
                       .map(n -> n * 2);    // Function
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring configuration:
                   @Bean
                   public Function<String, Predicate<User>> roleFilter() {
                       return role -> user -> user.hasRole(role);
                   }
            
               INTERVIEW QUESTION:
                   "What is a higher-order function?
                   Give examples from Java 8 Stream API."
            
               COMMON MISTAKES:
                   - Not understanding function types
                   - Complex nested lambdas
            
            ─────────────────────────────────────────────────────────────────────
            
            4. FUNCTION COMPOSITION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Combine functions: f(g(x)).
            
               WHY IT EXISTS:
               - Build complex from simple
               - Reusable transformations
            
               SIMPLE EXAMPLE:
                   Function<Integer, Integer> doubleIt = n -> n * 2;
                   Function<Integer, Integer> addOne = n -> n + 1;
                   Function<Integer, Integer> square = n -> n * n;
                   
                   // Compose: square(addOne(doubleIt(x)))
                   Function<Integer, Integer> composed = 
                       square.compose(addOne).compose(doubleIt);
                   composed.apply(3);  // (3*2+1)^2 = 49
                   
                   // andThen: doubleIt then addOne
                   Function<Integer, Integer> chained = 
                       doubleIt.andThen(addOne);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A data transformation pipeline:
                   Function<String, User> parseUser = JSON::parse;
                   Function<User, User> validate = UserValidator::validate;
                   Function<User, User> enrich = UserEnricher::enrich;
                   
                   Function<String, User> process = 
                       parseUser.andThen(validate).andThen(enrich);
            
               INTERVIEW QUESTION:
                   "What is function composition?
                   How do compose() and andThen() differ?"
            
               COMMON MISTAKES:
                   - Order confusion
                   - Not handling exceptions
            
            ─────────────────────────────────────────────────────────────────────
            
            5. CURRYING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Transform f(a, b) into f(a)(b).
            
               WHY IT EXISTS:
               - Partial application
               - Function reuse
            
               SIMPLE EXAMPLE:
                   // Multi-argument:
                   BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
                   add.apply(3, 4);  // 7
                   
                   // Curried:
                   Function<Integer, Function<Integer, Integer>> curriedAdd =
                       a -> b -> a + b;
                   curriedAdd.apply(3).apply(4);  // 7
                   
                   // Partial application:
                   Function<Integer, Integer> add3 = curriedAdd.apply(3);
                   add3.apply(4);  // 7
            
               REAL-WORLD BACKEND EXAMPLE:
                   A configurable validator:
                   Function<BigDecimal, Predicate<Product>> priceValidator =
                       maxPrice -> product -> product.getPrice().compareTo(maxPrice) < 0;
                   
                   Predicate<Product> under100 = priceValidator.apply(
                       new BigDecimal("100")
                   );
            
               INTERVIEW QUESTION:
                   "What is currying?
                   How is it different from partial application?"
            
               COMMON MISTAKES:
                   - Not understanding the difference
                   - Over-complicating simple functions
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Functional programming is essential for:
            - Modern Java development
            - Stream processing
            - Concurrent programming
            - Clean, maintainable code
            """);
    }
}
