package phase7;

import java.util.*;

/**
 * LESSON 24: IMMUTABILITY
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * An immutable object is one whose state cannot be changed after
 * creation. Like a printed document - you can't modify it, only
 * create a new one with changes.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Immutable objects are inherently thread-safe
 * - No synchronization needed
 * - Can be safely shared between threads
 * - No defensive copying required
 * - Safe to use as HashMap keys
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Currency amount:
 * - $100 is always $100
 * - To "change" it, create $150
 * - Original $100 still exists unchanged
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API request/response objects:
 * - Immutable request objects
 * - Safe to pass between threads
 * - No accidental modification
 * - Safe to cache
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "Why is String immutable?"
 * Answer: Thread-safe, can be cached, safe as HashMap key,
 *         security (can't be modified after creation)
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Forgetting to make getters return copies for mutable fields
 * - Not marking class as final
 * - Allowing 'this' to escape during construction
 * - Mutable fields in supposedly immutable class
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - No synchronization overhead
 * - Safe to share without copying
 * - Can be cached and reused
 * - Memory overhead for new objects on each change
 *
 * ============================================================
 * IMMUTABILITY RULES
 * ============================================================
 *
 *   1. Don't provide setters
 *   2. Make all fields final
 *   3. Make class final (or use static factory)
 *   4. Don't allow 'this' to escape
 *   5. Return copies of mutable fields
 *   6. Don't share mutable internals
 */

public class Lesson24_Immutability {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== IMMUTABILITY ===\n");

        // ============================================================
        // EXAMPLE 1: Immutable class
        // ============================================================
        System.out.println("--- Example 1: Immutable Class ---\n");

        ImmutablePerson person = new ImmutablePerson("Alice", 30);
        System.out.println("Person: " + person);
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Mutable class (for comparison)
        // ============================================================
        System.out.println("--- Example 2: Mutable Class ---\n");

        MutablePerson mutable = new MutablePerson("Bob", 25);
        System.out.println("Before: " + mutable);
        mutable.setAge(26);
        System.out.println("After: " + mutable);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Immutable with mutable fields
        // ============================================================
        System.out.println("--- Example 3: Immutable with Mutable Fields ---\n");

        Date birthDate = new Date();
        ImmutablePersonWithDate personWithDate = new ImmutablePersonWithDate("Charlie", 20 , birthDate);

        System.out.println("Birth date: " + personWithDate.getBirthDate());

        // Try to modify original
        birthDate.setTime(0);

        // Original is protected by copy
        System.out.println("Birth date after modifying original: " + personWithDate.getBirthDate());
        System.out.println("(Original is protected by defensive copy)\n");

        // ============================================================
        // EXAMPLE 4: Immutable collections
        // ============================================================
        System.out.println("--- Example 4: Immutable Collections ---\n");

        List<String> mutableList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> immutableList = Collections.unmodifiableList(mutableList);

        System.out.println("Immutable list: " + immutableList);

        try {
            immutableList.add("d");
        } catch (UnsupportedOperationException e) {
            System.out.println("Can't modify immutable list: " + e.getMessage());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Thread safety of immutable objects
        // ============================================================
        System.out.println("--- Example 5: Thread Safety ---\n");

        ImmutablePoint point = new ImmutablePoint(10, 20);

        Thread[] readers = new Thread[10];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Thread(() -> {
                // Safe to read without synchronization
                System.out.println("Thread sees: " + point);
            });
        }

        for (Thread t : readers) t.start();
        for (Thread t : readers) t.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Immutable as HashMap key
        // ============================================================
        System.out.println("--- Example 6: Immutable as HashMap Key ---\n");

        Map<ImmutablePerson, String> map = new HashMap<>();
        ImmutablePerson key1 = new ImmutablePerson("Alice", 30);
        ImmutablePerson key2 = new ImmutablePerson("Alice", 30);  // Same values

        map.put(key1, "Engineer");
        System.out.println("key1 == key2: " + (key1 == key2));
        System.out.println("key1.equals(key2): " + key1.equals(key2));
        System.out.println("Value for key2: " + map.get(key2));
        System.out.println("(Works because immutable and proper equals/hashCode)\n");

        // ============================================================
        // EXAMPLE 7: Builder pattern for immutability
        // ============================================================
        System.out.println("--- Example 7: Builder Pattern ---\n");

        ImmutablePersonBuilt personBuilt = new ImmutablePersonBuilt.Builder()
            .name("Diana")
            .age(28)
            .build();

        System.out.println("Built person: " + personBuilt);
        System.out.println();

        // ============================================================
        // EXAMPLE 8: When to use immutability
        // ============================================================
        System.out.println("--- Example 8: When to Use Immutability ---\n");

        System.out.println("Use immutability when:");
        System.out.println("1. Object will be shared between threads");
        System.out.println("2. Object will be used as Map key");
        System.out.println("3. Object represents value (money, point, etc.)");
        System.out.println("4. Caching is important");
        System.out.println();
        System.out.println("Avoid immutability when:");
        System.out.println("1. Object has many fields (memory overhead)");
        System.out.println("2. Frequent modifications needed");
        System.out.println("3. Object is large (copying expensive)");
    }

    // ============================================================
    // IMMUTABLE CLASSES
    // ============================================================

    static final class ImmutablePerson {
        private final String name;
        private final int age;

        public ImmutablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ImmutablePerson)) return false;
            ImmutablePerson that = (ImmutablePerson) o;
            return age == that.age && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

    static final class ImmutablePersonWithDate {
        private final String name;
        private final int age;
        private final Date birthDate;  // Mutable field!

        public ImmutablePersonWithDate(String name, int age, Date birthDate) {
            this.name = name;
            this.age = age;
            this.birthDate = new Date(birthDate.getTime());  // Defensive copy
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Date getBirthDate() {
            return new Date(birthDate.getTime());  // Return copy
        }
    }

    static final class ImmutablePoint {
        private final int x;
        private final int y;

        public ImmutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ImmutablePoint)) return false;
            ImmutablePoint that = (ImmutablePoint) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static class MutablePerson {
        private String name;
        private int age;

        public MutablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    static class ImmutablePersonBuilt {
        private final String name;
        private final int age;

        private ImmutablePersonBuilt(Builder builder) {
            this.name = builder.name;
            this.age = builder.age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }

        static class Builder {
            private String name;
            private int age;

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder age(int age) {
                this.age = age;
                return this;
            }

            public ImmutablePersonBuilt build() {
                return new ImmutablePersonBuilt(this);
            }
        }
    }
}
