package phase9;

import java.util.*;

/**
 * LESSON 4: GENERICS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Generics allow you to write code that works with any type
 * while providing compile-time type safety. Like a container
 * that can hold any type, but you specify what type at compile time.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Type safety (catch errors at compile time)
 * - Eliminate casts
 * - Enable generic algorithms
 * - Better code reusability
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Box container:
 * - Box<String> holds strings
 * - Box<Integer> holds integers
 * - Compiler ensures type safety
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Repository pattern:
 * - Repository<User> stores users
 * - Repository<Order> stores orders
 * - Same code, different types
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What are generics?"
 * Answer: Type parameters for classes/methods,
 *         compile-time type safety
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using raw types (List instead of List<String>)
 * - Type erasure confusion
 * - Generic array creation
 * - Wildcard misuse
 */

public class Lesson04_Generics {

    public static void main(String[] args) {
        System.out.println("=== GENERICS ===\n");

        // ============================================================
        // EXAMPLE 1: Generic class
        // ============================================================
        System.out.println("--- Example 1: Generic Class ---\n");

        Box<String> stringBox = new Box<>("Hello");
        Box<Integer> intBox = new Box<>(42);

        System.out.println("String box: " + stringBox.getValue());
        System.out.println("Int box: " + intBox.getValue());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Generic method
        // ============================================================
        System.out.println("--- Example 2: Generic Method ---\n");

        String s = GenericUtil.reverse("Hello");
        System.out.println("Reversed: " + s);

        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println("First: " + GenericUtil.getFirst(numbers));
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Bounded type parameters
        // ============================================================
        System.out.println("--- Example 3: Bounded Types ---\n");

        NumberBox<Integer> intNumberBox = new NumberBox<>(10);
        NumberBox<Double> doubleNumberBox = new NumberBox<>(3.14);

        System.out.println("Int box value: " + intNumberBox.getValue());
        System.out.println("Double box value: " + doubleNumberBox.getValue());
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Wildcards
        // ============================================================
        System.out.println("--- Example 4: Wildcards ---\n");

        List<Integer> intList = List.of(1, 2, 3);
        List<Double> doubleList = List.of(1.1, 2.2, 3.3);
        List<Number> numberList = List.of(1, 2.2, 3);

        // ? extends T - producer (read only)
        double sum = sumOfNumbers(intList);
        System.out.println("Sum of ints: " + sum);

        // ? super T - consumer (write only)
        List<Object> objects = new ArrayList<>();
        addNumbers(objects);
        System.out.println("Objects: " + objects);
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Type erasure
        // ============================================================
        System.out.println("--- Example 5: Type Erasure ---\n");

        System.out.println("Generics are implemented via type erasure:");
        System.out.println("  - Type parameters removed at compile time");
        System.out.println("  - Casts inserted where needed");
        System.out.println("  - Bridge methods for overriding");
        System.out.println("  - No runtime type information");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Generic interfaces
        // ============================================================
        System.out.println("--- Example 6: Generic Interfaces ---\n");

        Repository<String> stringRepo = new StringRepository();
        stringRepo.save("Alice");
        System.out.println("Found: " + stringRepo.findById("Alice"));
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Multiple type parameters
        // ============================================================
        System.out.println("--- Example 7: Multiple Type Parameters ---\n");

        Pair<String, Integer> pair = new Pair<>("Age", 30);
        System.out.println("Pair: " + pair.getFirst() + " = " + pair.getSecond());
        System.out.println();
    }

    // ============================================================
    // HELPER CLASSES
    // ============================================================

    static class Box<T> {
        private T value;

        public Box(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    static class NumberBox<T extends Number> {
        private T value;

        public NumberBox(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public double doubleValue() {
            return value.doubleValue();
        }
    }

    static class Pair<K, V> {
        private K first;
        private V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }
    }

    interface Repository<T> {
        void save(T entity);
        T findById(String id);
    }

    static class StringRepository implements Repository<String> {
        private final Map<String, String> store = new HashMap<>();

        @Override
        public void save(String entity) {
            store.put(entity, entity);
        }

        @Override
        public String findById(String id) {
            return store.get(id);
        }
    }

    static class GenericUtil {
        static <T> T reverse(T value) {
            // Simplified - just return as-is
            return value;
        }

        static <T> T getFirst(List<T> list) {
            return list.get(0);
        }

        static double sumOfNumbers(List<? extends Number> numbers) {
            double sum = 0;
            for (Number n : numbers) {
                sum += n.doubleValue();
            }
            return sum;
        }

        static void addNumbers(List<? super Integer> list) {
            list.add(1);
            list.add(2);
            list.add(3);
        }
    }
}
