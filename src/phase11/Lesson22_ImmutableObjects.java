package phase11;

import java.util.*;

/**
 * LESSON 22: IMMUTABLE OBJECTS
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Immutable class
 * 2. Mutable vs Immutable
 * 3. Benefits
 * 4. Creating immutable objects
 * 5. Interview questions
 */

public class Lesson22_ImmutableObjects {
    public static void main(String[] args) {
        System.out.println("""
            === IMMUTABLE OBJECTS ===
            
            1. IMMUTABLE CLASS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Objects that cannot be modified after creation.
            
               WHY IT EXISTS:
               - Thread safety
               - Predictable state
               - Easy reasoning
            
               SIMPLE EXAMPLE:
                   final class ImmutablePerson {
                       private final String name;
                       private final int age;
                       
                       public ImmutablePerson(String name, int age) {
                           this.name = name;
                           this.age = age;
                       }
                       
                       public String name() { return name; }
                       public int age() { return age; }
                   }
                   
                   // No setters, no modification
                   // All fields final
            
               REAL-WORLD BACKEND EXAMPLE:
                   A value object:
                   public record Money(BigDecimal amount, Currency currency) {
                       // Java 14+ record is immutable
                   }
            
               INTERVIEW QUESTION:
                   "What is an immutable object?
                   How to create one in Java?"
            
               COMMON MISTAKES:
                   - Not making class final
                   - Exposing mutable fields
            
            ─────────────────────────────────────────────────────────────────────
            
            2. MUTABLE VS IMMUTABLE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Mutable objects can change, immutable cannot.
            
               WHY IT EXISTS:
               - Different use cases
               - Trade-offs
            
               SIMPLE EXAMPLE:
                   // Mutable:
                   MutablePerson mutable = new MutablePerson("Bob", 25);
                   mutable.setAge(26);  // Can modify!
                   
                   // Immutable:
                   ImmutablePerson immutable = new ImmutablePerson("Charlie", 35);
                   // immutable.setAge(36);  // Compile error! No setter
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user session:
                   // Mutable:
                   public class UserSession {
                       private String userId;
                       private long lastAccess;
                   }
                   
                   // Immutable:
                   public record UserSession(String userId, long lastAccess) {}
            
               INTERVIEW QUESTION:
                   "What are the differences between mutable and immutable?
                   When to use each?"
            
               COMMON MISTAKES:
                   - Not understanding thread safety
                   - Defensive copying issues
            
            ─────────────────────────────────────────────────────────────────────
            
            3. BENEFITS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Immutable objects have significant advantages.
            
               WHY IT EXISTS:
               - Safety
               - Simplicity
            
               SIMPLE EXAMPLE:
                   ImmutablePerson p1 = new ImmutablePerson("Diana", 28);
                   ImmutablePerson p2 = p1;  // Safe to share
                   
                   // Thread-safe by default
                   // No defensive copying needed
                   // Can be used as HashMap key
            
               REAL-WORLD BACKEND EXAMPLE:
                   A configuration object:
                   public record DatabaseConfig(String url, String username, String password) {
                       // Safe to share across threads
                       // No need for synchronization
                   }
            
               INTERVIEW QUESTION:
                   "What are the benefits of immutable objects?
                   Why are they thread-safe?"
            
               COMMON MISTAKES:
                   - Not understanding benefits
                   - Not using for shared state
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CREATING IMMUTABLE OBJECTS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Follow specific rules to ensure immutability.
            
               WHY IT EXISTS:
               - Correctness
               - Safety
            
               SIMPLE EXAMPLE:
                   // Rules for immutable class:
                   // 1. Class declared final
                   // 2. All fields private final
                   // 3. No setters
                   // 4. All mutable fields deeply copied
                   // 5. Constructor validates fields
                   
                   final class ImmutableUser {
                       private final String name;
                       private final List<String> roles;
                       
                       public ImmutableUser(String name, List<String> roles) {
                           this.name = name;
                           this.roles = new ArrayList<>(roles);  // Defensive copy
                       }
                       
                       public List<String> getRoles() {
                           return Collections.unmodifiableList(roles);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A request DTO:
                   public record CreateUserRequest(
                       String username,
                       String email,
                       List<String> roles
                   ) {
                       public CreateUserRequest {
                           roles = List.copyOf(roles);  // Defensive copy
                       }
                   }
            
               INTERVIEW QUESTION:
                   "How to make a class immutable?
                   What are the key rules?"
            
               COMMON MISTAKES:
                   - Not defensive copying
                   - Not making class final
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Immutable objects are essential for:
            - Thread safety
            - Clean code
            - Value objects
            - Functional programming
            """);
    }
}
