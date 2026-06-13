package phase11;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * LESSON 11: JAVA 8 FEATURES
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Lambda expressions
 * 2. Functional interfaces
 * 3. Stream API
 * 4. Optional
 * 5. Default methods
 * 6. Interview questions
 */

public class Lesson11_Java8Features {
    public static void main(String[] args) {
        System.out.println("""
            === JAVA 8 FEATURES ===
            
            1. LAMBDA EXPRESSIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Anonymous functions that enable functional programming.
            
               WHY IT EXISTS:
               - Concise code
               - Functional programming support
               - Better multicore utilization
            
               SIMPLE EXAMPLE:
                   // Before Java 8:
                   Runnable oldRunnable = new Runnable() {
                       @Override
                       public void run() {
                           System.out.println("Old style");
                       }
                   };
                   
                   // Java 8+:
                   Runnable newRunnable = () -> System.out.println("Lambda");
                   
                   // With parameters:
                   (int a, int b) -> a + b
                   (a, b) -> a + b  // Type inference
                   a -> a * 2       // Single param
                   () -> "Hello"    // No params, return value
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring event handler:
                   applicationContext.publishEvent(() -> 
                       userRepository.save(new User("John"))
                   );
                   
                   // Or in streams:
                   users.stream()
                       .filter(u -> u.isActive())
                       .map(User::getName)
                       .toList();
            
               INTERVIEW QUESTION:
                   "What is a lambda expression?
                   How does it capture variables (closure)?"
            
               COMMON MISTAKES:
                   - Capturing mutable variables
                   - Not understanding type inference
            
            ─────────────────────────────────────────────────────────────────────
            
            2. FUNCTIONAL INTERFACES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Interfaces with single abstract method.
            
               WHY IT EXISTS:
               - Lambda target types
               - API design
            
               SIMPLE EXAMPLE:
                   // Built-in:
                   Predicate<Integer> isEven = n -> n % 2 == 0;
                   Function<Integer, String> intToString = n -> "Number: " + n;
                   Consumer<String> printer = s -> System.out.println(s);
                   Supplier<Double> random = () -> Math.random();
                   
                   // Custom:
                   @FunctionalInterface
                   interface Processor<T> {
                       void process(T item);
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring configuration:
                   @Bean
                   public Function<String, User> userMapper() {
                       return username -> userRepository.findByUsername(username);
                   }
            
               INTERVIEW QUESTION:
                   "What is a functional interface?
                   What is the @FunctionalInterface annotation for?"
            
               COMMON MISTAKES:
                   - Not understanding SAM
                   - Multiple abstract methods
            
            ─────────────────────────────────────────────────────────────────────
            
            3. STREAM API
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Process sequences of elements with functional style.
            
               WHY IT EXISTS:
               - Declarative data processing
               - Lazy evaluation
               - Parallel support
            
               SIMPLE EXAMPLE:
                   List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                   
                   List<Integer> evenSquares = numbers.stream()
                       .filter(n -> n % 2 == 0)
                       .map(n -> n * n)
                       .toList();
                   
                   // Parallel:
                   numbers.parallelStream()
                       .filter(isEven)
                       .forEach(System.out::println);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A data processing pipeline:
                   orders.stream()
                       .filter(Order::isCompleted)
                       .map(Order::getTotal)
                       .reduce(BigDecimal.ZERO, BigDecimal::add);
            
               INTERVIEW QUESTION:
                   "What is lazy evaluation in streams?
                   When does intermediate operation execute?"
            
               COMMON MISTAKES:
                   - Multiple terminal operations
                   - Not understanding laziness
            
            ─────────────────────────────────────────────────────────────────────
            
            4. OPTIONAL
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Container for nullable values, avoiding null checks.
            
               WHY IT EXISTS:
               - Avoid NullPointerException
               - Explicit null handling
            
               SIMPLE EXAMPLE:
                   Optional<String> optional = Optional.of("Hello");
                   Optional<String> empty = Optional.empty();
                   
                   // Safe access:
                   String value = optional.orElse("Default");
                   optional.ifPresent(System.out::println);
                   
                   // Chaining:
                   Optional<String> result = optional
                       .filter(s -> s.length() > 3)
                       .map(String::toUpperCase);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A repository method:
                   public Optional<User> findByEmail(String email) {
                       return userRepository.findByEmail(email);
                   }
                   
                   // Usage:
                   userRepository.findByEmail(email)
                       .ifPresentOrElse(
                           this::sendWelcomeEmail,
                           () -> log.warn("User not found")
                       );
            
               INTERVIEW QUESTION:
                   "What is Optional?
                   How does it help prevent NullPointerException?"
            
               COMMON MISTAKES:
                   - Using Optional for fields
                       - Not for method parameters
                       - Not for serialization
            
            ─────────────────────────────────────────────────────────────────────
            
            5. DEFAULT METHODS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Methods with implementation in interfaces.
            
               WHY IT EXISTS:
               - Backward compatibility
               - Utility methods in interfaces
            
               SIMPLE EXAMPLE:
                   interface Vehicle {
                       void start();
                       
                       default void stop() {
                           System.out.println("Stopping...");
                       }
                   }
                   
                   // Multiple inheritance conflict:
                   interface A {
                       default void greet() { System.out.println("A"); }
                   }
                   interface B {
                       default void greet() { System.out.println("B"); }
                   }
                   class C implements A, B {
                       public void greet() { A.super.greet(); }  // Resolve
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring repository interface:
                   @Repository
                   public interface UserRepository extends JpaRepository<User, Long> {
                       // Default methods from parent
                       // Custom default methods:
                       default List<User> findActiveUsers() {
                           return findByActiveTrue();
                       }
                   }
            
               INTERVIEW QUESTION:
                   "What is a default method?
                   How to resolve conflicts in multiple inheritance?"
            
               COMMON MISTAKES:
                   - Not understanding conflict resolution
                   - Using for state
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Java 8 features are essential for:
            - Modern Java development
            - Functional programming
            - Stream processing
            - API design
            """);
    }
}
