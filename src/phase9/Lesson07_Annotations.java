package phase9;

import java.lang.annotation.*;
import java.lang.reflect.*;

/**
 * LESSON 7: ANNOTATIONS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Annotations are metadata added to code. They don't change
 * behavior directly but can be used by tools, frameworks,
 * or the compiler.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Add metadata to code
 * - Configure frameworks
 * - Generate code (compile-time)
 * - Runtime processing
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Spring framework:
 * - @Autowired - inject dependency
 * - @RequestMapping - map URL
 * - @Transactional - manage transactions
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * REST API:
 * - @GET, @POST, @PUT, @DELETE
 * - @Path, @QueryParam
 * - Framework uses annotations to route requests
 */

public class Lesson07_Annotations {

    public static void main(String[] args) {
        System.out.println("=== ANNOTATIONS ===\n");

        // ============================================================
        // EXAMPLE 1: Built-in annotations
        // ============================================================
        System.out.println("--- Example 1: Built-in Annotations ---\n");

        System.out.println("@Override - Method overrides parent");
        System.out.println("@Deprecated - Mark as obsolete");
        System.out.println("@SuppressWarnings - Suppress compiler warnings");
        System.out.println("@FunctionalInterface - Mark functional interface");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Custom annotation
        // ============================================================
        System.out.println("--- Example 2: Custom Annotation ---\n");

        // Process annotation
        if (AnnotatedClass.class.isAnnotationPresent(Author.class)) {
            Author author = AnnotatedClass.class.getAnnotation(Author.class);
            System.out.println("Author: " + author.name());
            System.out.println("Version: " + author.version());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Annotation with elements
        // ============================================================
        System.out.println("--- Example 3: Annotation Elements ---\n");

        for (Method method : AnnotatedClass.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Task.class)) {
                Task task = method.getAnnotation(Task.class);
                System.out.println("Method: " + method.getName());
                System.out.println("  Priority: " + task.priority());
                System.out.println("  Assignee: " + task.assignee());
            }
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Meta-annotations
        // ============================================================
        System.out.println("--- Example 4: Meta-Annotations ---\n");

        System.out.println("@Retention - When annotation is available");
        System.out.println("  SOURCE - Only in source");
        System.out.println("  CLASS - In class file");
        System.out.println("  RUNTIME - Available at runtime");
        System.out.println();
        System.out.println("@Target - Where annotation can be used");
        System.out.println("  TYPE, METHOD, FIELD, PARAMETER, etc.");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Repeating annotations
        // ============================================================
        System.out.println("--- Example 5: Repeating Annotations ---\n");

        System.out.println("Java 8+ allows repeating annotations:");
        System.out.println("  @Schedule(monday = \"9:00\")");
        System.out.println("  @Schedule(tuesday = \"10:00\")");
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Type annotations
        // ============================================================
        System.out.println("--- Example 6: Type Annotations ---\n");

        System.out.println("Java 8+ allows annotations on types:");
        System.out.println("  @NonNull String name;");
        System.out.println("  List<@NonNull String> list;");
        System.out.println("  Used by static analysis tools");
        System.out.println();
    }

    // ============================================================
    // CUSTOM ANNOTATIONS
    // ============================================================

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Author {
        String name();
        String version() default "1.0";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Task {
        int priority() default 0;
        String assignee() default "unassigned";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @interface Review {
        String reviewer();
    }

    @Author(name = "John Doe", version = "2.0")
    static class AnnotatedClass {

        @Task(priority = 1, assignee = "Alice")
        public void highPriorityTask() {
            System.out.println("High priority task");
        }

        @Task(priority = 5)
        public void lowPriorityTask() {
            System.out.println("Low priority task");
        }

        @Review(reviewer = "Bob")
        public void reviewedMethod() {
            System.out.println("Reviewed method");
        }
    }
}
