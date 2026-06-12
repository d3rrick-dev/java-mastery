package phase11;

import java.util.*;

/**
 * LESSON 22: IMMUTABLE OBJECTS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Immutable objects cannot be modified after creation.
 * Like a printed document - you can't change it, only make copies.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Thread-safe by default
 * - No defensive copying needed
 * - Can be used as HashMap keys
 * - Easier to reason about
 */

public class Lesson22_ImmutableObjects {

    public static void main(String[] args) {
        System.out.println("=== IMMUTABLE OBJECTS ===\n");

        // ============================================================
        // EXAMPLE 1: Immutable class
        // ============================================================
        System.out.println("--- Example 1: Immutable Class ---\n");

        ImmutablePerson person = new ImmutablePerson("Alice", 30);
        System.out.println("Person: " + person);
        System.out.println("Name: " + person.name());
        System.out.println("Age: " + person.age());
        System.out.println("Cannot be modified after creation");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Mutable vs Immutable
        // ============================================================
        System.out.println("--- Example 2: Mutable vs Immutable ---\n");

        // Mutable
        MutablePerson mutable = new MutablePerson("Bob", 25);
        System.out.println("Before: " + mutable);
        mutable.setAge(26);  // Can modify!
        System.out.println("After: " + mutable);
        System.out.println();

        // Immutable
        ImmutablePerson immutable = new ImmutablePerson("Charlie", 35);
        System.out.println("Before: " + immutable);
        // immutable.setAge(36);  // Compile error! No setter
        System.out.println("After: " + immutable);  // Same
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Benefits
        // ============================================================
        System.out.println("--- Example 3: Benefits ---\n");

        ImmutablePerson p1 = new ImmutablePerson("Diana", 28);
        ImmutablePerson p2 = p1;  // Safe to share

        System.out.println("p1 == p2: " + (p1 == p2));  // Same reference
        System.out.println("Both can safely use same object");
        System.out.println();

        // Use as HashMap key
        Map<ImmutablePerson, String> map = new HashMap<>();
        map.put(p1, "Engineer");
        System.out.println("HashMap key: " + map.get(p1));
        System.out.println("Immutable objects are good map keys");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Creating immutable objects
        // ============================================================
        System.out.println("--- Example 4: How to Create ---\n");

        System.out.println("Rules for immutable class:");
        System.out.println("  1. Class declared final");
        System.out.println("  2. All fields private final");
        System.out.println("  3. No setters");
        System.out.println("  4. All mutable fields deeply copied");
        System.out.println("  5. Constructor validates fields");
        System.out.println();
    }

    // ============================================================
    // IMMUTABLE OBJECTS
    // ============================================================

    // Immutable class
    static final class ImmutablePerson {
        private final String name;
        private final int age;

        public ImmutablePerson(String name, int age) {
            if (name == null) throw new IllegalArgumentException("Name cannot be null");
            if (age < 0 || age > 150) throw new IllegalArgumentException("Invalid age");
            this.name = name;
            this.age = age;
        }

        public String name() { return name; }
        public int age() { return age; }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }

    // Mutable class (for comparison)
    static class MutablePerson {
        private String name;
        private int age;

        public MutablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
}
