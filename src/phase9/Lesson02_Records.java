package phase9;

import java.util.*;

/**
 * LESSON 2: RECORDS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Records are a special kind of class that holds immutable data.
 * They automatically generate constructor, getters, equals(),
 * hashCode(), and toString().
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Reduce boilerplate code
 * - Make data classes concise
 * - Immutable by default
 * - Perfect for DTOs, value objects
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * API response:
 * - Before: 50 lines of boilerplate
 * - After: 1 line record declaration
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Database entity:
 * - Record for immutable data
 * - Automatic equals/hashCode for collections
 * - Thread-safe by default
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is a record in Java?"
 * Answer: Immutable data class with auto-generated
 *         constructor, getters, equals, hashCode, toString
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Trying to make records mutable
 * - Adding too many fields
 * - Using records for complex behavior
 * - Not understanding compact constructor
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Less code = smaller class files
 * - Immutable = thread-safe (no sync needed)
 * - Can be used as HashMap keys safely
 * - Pattern matching for instanceof (Java 16+)
 */

public class Lesson02_Records {

    public static void main(String[] args) {
        System.out.println("=== RECORDS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic record
        // ============================================================
        System.out.println("--- Example 1: Basic Record ---\n");

        Person person = new Person("Alice", 30, "alice@example.com");
        System.out.println("Person: " + person);
        System.out.println("Name: " + person.name());
        System.out.println("Age: " + person.age());
        System.out.println("Email: " + person.email());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Record equals and hashCode
        // ============================================================
        System.out.println("--- Example 2: equals and hashCode ---\n");

        Person person1 = new Person("Bob", 25, "bob@example.com");
        Person person2 = new Person("Bob", 25, "bob@example.com");
        Person person3 = new Person("Bob", 26, "bob@example.com");

        System.out.println("person1.equals(person2): " + person1.equals(person2));
        System.out.println("person1.equals(person3): " + person1.equals(person3));
        System.out.println("person1.hashCode(): " + person1.hashCode());
        System.out.println("person2.hashCode(): " + person2.hashCode());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Record in collections
        // ============================================================
        System.out.println("--- Example 3: Records in Collections ---\n");

        Set<Person> people = new HashSet<>();
        people.add(new Person("Alice", 30, "alice@example.com"));
        people.add(new Person("Bob", 25, "bob@example.com"));
        people.add(new Person("Alice", 30, "alice@example.com"));  // Duplicate

        System.out.println("Set size: " + people.size());
        System.out.println("(Duplicates automatically handled by equals/hashCode)");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Record with validation
        // ============================================================
        System.out.println("--- Example 4: Record with Validation ---\n");

        try {
            Person invalid = new Person("", -5, "invalid");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Nested records
        // ============================================================
        System.out.println("--- Example 5: Nested Records ---\n");

        Address address = new Address("123 Main St", "New York", "10001");
        PersonWithAddress personWithAddress = new PersonWithAddress("Charlie", address);
        System.out.println("Person: " + personWithAddress);
        System.out.println("City: " + personWithAddress.address().city());
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Record vs class
        // ============================================================
        System.out.println("--- Example 6: Record vs Class ---\n");

        System.out.println("Record:");
        System.out.println("  - Immutable by default");
        System.out.println("  - Auto-generated equals/hashCode/toString");
        System.out.println("  - Cannot extend (implicitly final)");
        System.out.println("  - All fields are final");
        System.out.println();
        System.out.println("Class:");
        System.out.println("  - Mutable by default");
        System.out.println("  - Manual equals/hashCode/toString");
        System.out.println("  - Can extend other classes");
        System.out.println("  - Fields can be modified");
        System.out.println();

        // ============================================================
        // EXAMPLE 7: When to use records
        // ============================================================
        System.out.println("--- Example 7: When to Use Records ---\n");

        System.out.println("Use records for:");
        System.out.println("  - DTOs (Data Transfer Objects)");
        System.out.println("  - Value objects");
        System.out.println("  - Configuration objects");
        System.out.println("  - API request/response");
        System.out.println("  - Map keys");
        System.out.println();
        System.out.println("Don't use for:");
        System.out.println("  - Entities with identity");
        System.out.println("  - Objects with complex behavior");
        System.out.println("  - Mutable state");
    }

    // ============================================================
    // RECORD DEFINITIONS
    // ============================================================

    record Person(String name, int age, String email) {
        // Compact constructor for validation
        public Person {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Invalid age: " + age);
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email: " + email);
            }
        }
    }

    record Address(String street, String city, String zipCode) {}

    record PersonWithAddress(String name, Address address) {}
}
