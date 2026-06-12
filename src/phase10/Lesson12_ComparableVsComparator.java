package phase10;

import java.util.*;

/**
 * LESSON 12: COMPARABLE VS COMPARATOR
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Comparable: "I can compare myself to another" (natural ordering)
 * Comparator: "I can compare two things" (external ordering)
 * Like comparing by height (natural) vs by name (external).
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Comparable: Default sorting for a class
 * - Comparator: Multiple sorting strategies
 * - Flexibility without modifying class
 */

public class Lesson12_ComparableVsComparator {

    public static void main(String[] args) {
        System.out.println("=== COMPARABLE VS COMPARATOR ===\n");

        // ============================================================
        // EXAMPLE 1: Comparable (natural ordering)
        // ============================================================
        System.out.println("--- Example 1: Comparable ---\n");

        List<PersonComparable> people = new ArrayList<>();
        people.add(new PersonComparable("Charlie", 30));
        people.add(new PersonComparable("Alice", 25));
        people.add(new PersonComparable("Bob", 35));

        System.out.println("Before sort: " + people.stream().map(PersonComparable::name).toList());
        Collections.sort(people);
        System.out.println("After sort (by name): " + people.stream().map(PersonComparable::name).toList());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Comparator (external ordering)
        // ============================================================
        System.out.println("--- Example 2: Comparator ---\n");

        List<Person> people2 = new ArrayList<>();
        people2.add(new Person("Charlie", 30));
        people2.add(new Person("Alice", 25));
        people2.add(new Person("Bob", 35));

        // Sort by age
        people2.sort(Comparator.comparingInt(Person::age));
        System.out.println("By age: " + people2.stream().map(Person::name).toList());

        // Sort by name
        people2.sort(Comparator.comparing(Person::name));
        System.out.println("By name: " + people2.stream().map(Person::name).toList());

        // Sort by age descending
        people2.sort(Comparator.comparingInt(Person::age).reversed());
        System.out.println("By age desc: " + people2.stream().map(Person::name).toList());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Multiple comparators
        // ============================================================
        System.out.println("--- Example 3: Multiple Comparators ---\n");

        List<Person> people3 = new ArrayList<>();
        people3.add(new Person("Alice", 30, "NYC"));
        people3.add(new Person("Bob", 25, "LA"));
        people3.add(new Person("Charlie", 30, "NYC"));
        people3.add(new Person("Diana", 25, "LA"));

        // Sort by city, then age, then name
        people3.sort(
            Comparator.comparing(Person::city)
                .thenComparingInt(Person::age)
                .thenComparing(Person::name)
        );

        System.out.println("Sorted by city, age, name:");
        people3.forEach(p -> System.out.println("  " + p));
        System.out.println();
    }

    // ============================================================
    // COMPARABLE VS COMPARATOR
    // ============================================================

    static class PersonComparable implements Comparable<PersonComparable> {
        private final String name;
        private final int age;

        public PersonComparable(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name() { return name; }
        public int age() { return age; }

        @Override
        public int compareTo(PersonComparable other) {
            return this.name.compareTo(other.name);  // Natural order by name
        }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }

    static class Person {
        private final String name;
        private final int age;
        private final String city;

        public Person(String name, int age) {
            this(name, age, "");
        }

        public Person(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }

        public String name() { return name; }
        public int age() { return age; }
        public String city() { return city; }

        @Override
        public String toString() {
            return name + "(" + age + ", " + city + ")";
        }
    }
}
