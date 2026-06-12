package phase11;

import java.lang.reflect.*;

/**
 * LESSON 23: REFLECTION
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Reflection allows inspecting and modifying classes,
 * methods, and fields at runtime. Like having X-ray vision
 * for your code.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Inspect classes at runtime
 * - Create objects dynamically
 * - Call methods dynamically
 * - Frameworks (Spring, Hibernate) use it extensively
 */

public class Lesson23_Reflection {

    public static void main(String[] args) {
        System.out.println("=== REFLECTION ===\n");

        // ============================================================
        // EXAMPLE 1: Getting class information
        // ============================================================
        System.out.println("--- Example 1: Class Information ---\n");

        String str = "hello";
        Class<?> clazz = str.getClass();

        System.out.println("Class name: " + clazz.getName());
        System.out.println("Simple name: " + clazz.getSimpleName());
        System.out.println("Package: " + clazz.getPackageName());
        System.out.println("Superclass: " + clazz.getSuperclass());
        System.out.println("Interfaces: " + Arrays.toString(clazz.getInterfaces()));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Getting fields
        // ============================================================
        System.out.println("--- Example 2: Getting Fields ---\n");

        Class<Person> personClass = Person.class;

        System.out.println("Fields:");
        for (Field field : personClass.getDeclaredFields()) {
            System.out.println("  " + field.getName() + " (" + field.getType().getSimpleName() + ")");
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Getting methods
        // ============================================================
        System.out.println("--- Example 3: Getting Methods ---\n");

        System.out.println("Methods:");
        for (Method method : personClass.getDeclaredMethods()) {
            System.out.println("  " + method.getName() + " - " + method.getReturnType().getSimpleName());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Creating objects dynamically
        // ============================================================
        System.out.println("--- Example 4: Dynamic Object Creation ---\n");

        try {
            Constructor<Person> constructor = personClass.getConstructor(String.class, int.class);
            Person person = constructor.newInstance("Alice", 30);
            System.out.println("Created: " + person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Calling methods dynamically
        // ============================================================
        System.out.println("--- Example 5: Dynamic Method Call ---\n");

        try {
            Person person = new Person("Bob", 25);
            Method getName = personClass.getMethod("getName");
            String name = (String) getName.invoke(person);
            System.out.println("Name via reflection: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    // ============================================================
    // REFLECTION EXAMPLES
    // ============================================================

    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
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
            return name + "(" + age + ")";
        }
    }

    // ============================================================
    // REFLECTION DETAILS
    // ============================================================
    /*
     * Reflection API:
     *
     * 1. Class<?> - Represents class
     *    - Class.forName("com.example.MyClass")
     *    - obj.getClass()
     *    - MyClass.class
     *
     * 2. Field - Represents field
     *    - getDeclaredFields()
     *    - setAccessible(true) for private fields
     *
     * 3. Method - Represents method
     *    - getDeclaredMethods()
     *    - invoke(obj, args...)
     *
     * 4. Constructor<T> - Represents constructor
     *    - getConstructor(params...)
     *    - newInstance(args...)
     *
     * 5. Modifier - Field modifiers
     *    - Modifier.isPublic(), isPrivate(), etc.
     *
     * Use Cases:
     * - Frameworks (Spring, JPA, Jackson)
     * - Testing frameworks
     * - IDE features
     * - Dependency injection
     *
     * Drawbacks:
     * - Slower than direct access
     * - Breaks encapsulation
     * - Security risks
     * - Compile-time safety lost
     */
}
