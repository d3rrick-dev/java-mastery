package phase9;

import java.lang.reflect.*;

/**
 * LESSON 6: REFLECTION
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Reflection allows you to inspect and modify classes, methods,
 * and fields at runtime. Like having a mirror that shows you
 * the internal structure of objects.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Inspect classes at runtime
 * - Create objects dynamically
 * - Call methods dynamically
 * - Access private fields
 * - Used by frameworks (Spring, Hibernate)
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Dependency injection framework:
 * - Scan classes for @Autowired annotations
 * - Create instances dynamically
 * - Inject dependencies via reflection
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * ORM framework:
 * - Map database columns to object fields
 * - Use reflection to set/get field values
 * - No manual getter/setter calls
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is reflection?"
 * Answer: Ability to inspect/modify classes at runtime,
 *         used by frameworks
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Overusing reflection (performance hit)
 * - Breaking encapsulation
 * - Not handling exceptions
 * - Caching reflection results
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Reflection is slower than direct access
 * - Security checks add overhead
 * - Cache Method/Field objects
 * - Use setAccessible(true) carefully
 */

public class Lesson06_Reflection {

    public static void main(String[] args) throws Exception {
        System.out.println("=== REFLECTION ===\n");

        // ============================================================
        // EXAMPLE 1: Getting class information
        // ============================================================
        System.out.println("--- Example 1: Class Information ---\n");

        Class<?> clazz = String.class;
        System.out.println("Class name: " + clazz.getName());
        System.out.println("Simple name: " + clazz.getSimpleName());
        System.out.println("Package: " + clazz.getPackageName());
        System.out.println("Superclass: " + clazz.getSuperclass());
        System.out.println("Is interface: " + clazz.isInterface());
        System.out.println("Is array: " + clazz.isArray());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Getting fields
        // ============================================================
        System.out.println("--- Example 2: Getting Fields ---\n");

        Class<?> personClass = PersonReflect.class;
        System.out.println("Fields:");
        for (Field field : personClass.getDeclaredFields()) {
            System.out.println("  " + field.getName() + " (" + field.getType().getSimpleName() + ")");
            System.out.println("    Modifiers: " + Modifier.toString(field.getModifiers()));
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Getting methods
        // ============================================================
        System.out.println("--- Example 3: Getting Methods ---\n");

        System.out.println("Methods:");
        for (Method method : personClass.getDeclaredMethods()) {
            System.out.println("  " + method.getName() + "()");
            System.out.println("    Return: " + method.getReturnType().getSimpleName());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Creating objects
        // ============================================================
        System.out.println("--- Example 4: Creating Objects ---\n");

        // Using newInstance() (deprecated)
        // PersonReflect person = personClass.getDeclaredConstructor().newInstance();

        // Better way
        Constructor<?> constructor = personClass.getConstructor(String.class, int.class);
        Object person = constructor.newInstance("Alice", 30);
        System.out.println("Created: " + person);
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Accessing fields
        // ============================================================
        System.out.println("--- Example 5: Accessing Fields ---\n");

        Field nameField = personClass.getDeclaredField("name");
        nameField.setAccessible(true);  // Access private field
        String name = (String) nameField.get(person);
        System.out.println("Name via reflection: " + name);

        nameField.set(person, "Bob");
        System.out.println("Modified name: " + person);
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Calling methods
        // ============================================================
        System.out.println("--- Example 6: Calling Methods ---\n");

        Method getNameMethod = personClass.getMethod("getName");
        String reflectedName = (String) getNameMethod.invoke(person);
        System.out.println("getName() via reflection: " + reflectedName);
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Annotations
        // ============================================================
        System.out.println("--- Example 7: Annotations ---\n");

        for (Method method : personClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Deprecated.class)) {
                System.out.println(method.getName() + " is deprecated");
            }
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 8: When to use reflection
        // ============================================================
        System.out.println("--- Example 8: When to Use ---\n");

        System.out.println("Use reflection for:");
        System.out.println("  - Frameworks (Spring, Hibernate)");
        System.out.println("  - Testing frameworks (JUnit)");
        System.out.println("  - Dependency injection");
        System.out.println("  - Serialization libraries");
        System.out.println();
        System.out.println("Avoid reflection for:");
        System.out.println("  - Business logic");
        System.out.println("  - Performance-critical code");
        System.out.println("  - When direct access works");
    }

    // ============================================================
    // HELPER CLASS
    // ============================================================

    static class PersonReflect {
        private String name;
        private int age;

        public PersonReflect(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Deprecated
        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
}
