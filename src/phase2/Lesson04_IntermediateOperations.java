package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 4: Intermediate Operations
 *
 * THEORY:
 * Intermediate operations transform a Stream into another Stream.
 * They are LAZY - they don't execute until a terminal operation is called.
 * They return a NEW Stream, they don't modify the original.
 *
 * COMMON INTERMEDIATE OPERATIONS:
 * - filter(Predicate)    : Keep elements matching condition
 * - map(Function)        : Transform each element
 * - flatMap(Function)    : Flatten nested structures
 * - distinct()           : Remove duplicates
 * - sorted()             : Sort elements
 * - peek(Consumer)       : Perform action (debugging)
 * - limit(long)          : Keep first N elements
 * - skip(long)           : Skip first N elements
 */

public class Lesson04_IntermediateOperations {

    public static void main(String[] args) {
        System.out.println("=== LESSON 4: Intermediate Operations ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. filter() - KEEP ELEMENTS MATCHING CONDITION
        // ============================================
        System.out.println("--- 1. filter() ---");
        System.out.println("Syntax: stream.filter(Predicate<T>) -> Stream<T>");

        List<Employee> activeEmployees = employees.stream()
                .filter(emp -> emp.active())
                .toList();
        System.out.println("Active employees: " + activeEmployees.size());

        List<Employee> engineers = employees.stream()
                .filter(emp -> "Engineering".equals(emp.department()))
                .toList();
        System.out.println("Engineers: " + engineers.size());

        // Multiple filters (AND logic)
        List<Employee> activeEngineers = employees.stream()
                .filter(emp -> emp.active())
                .filter(emp -> "Engineering".equals(emp.department()))
                .toList();
        System.out.println("Active engineers: " + activeEngineers.size());

        // ============================================
        // 2. map() - TRANSFORM EACH ELEMENT
        // ============================================
        System.out.println("\n--- 2. map() ---");
        System.out.println("Syntax: stream.map(Function<T,R>) -> Stream<R>");

        // Map Employee to String (name)
        List<String> names = employees.stream()
                .map(emp -> emp.name())
                .toList();
        System.out.println("Names: " + names);

        // Map Employee to Integer (age)
        List<Integer> ages = employees.stream()
                .map(emp -> emp.age())
                .toList();
        System.out.println("Ages: " + ages);

        // Map to transformed value
        List<String> upperNames = employees.stream()
                .map(emp -> emp.name().toUpperCase())
                .toList();
        System.out.println("Upper names: " + upperNames);

        // ============================================
        // 3. flatMap() - FLATTEN NESTED STRUCTURES
        // ============================================
        System.out.println("\n--- 3. flatMap() ---");
        System.out.println("Syntax: stream.flatMap(Function<T,Stream<R>>) -> Stream<R>");

        // Example: List of departments per employee (simulated)
        List<List<String>> departmentsPerEmp = List.of(
                List.of("Engineering", "R&D"),
                List.of("Sales"),
                List.of("Engineering", "Management"),
                List.of("HR"),
                List.of("Engineering")
        );

        // flatMap flattens List<List<String>> to List<String>
        List<String> allDepartments = departmentsPerEmp.stream()
                .flatMap(list -> list.stream())
                .distinct()
                .toList();
        System.out.println("All unique departments: " + allDepartments);

        // Real example: Split sentences into words
        List<String> sentences = List.of("Hello World", "Java Streams", "Functional Programming");
        List<String> words = sentences.stream()
                .flatMap(sentence -> java.util.Arrays.stream(sentence.split(" ")))
                .toList();
        System.out.println("Words: " + words);

        // ============================================
        // 4. distinct() - REMOVE DUPLICATES
        // ============================================
        System.out.println("\n--- 4. distinct() ---");
        System.out.println("Syntax: stream.distinct() -> Stream<T>");

        List<String> namesWithDups = List.of("Alice", "Bob", "Alice", "Charlie", "Bob");
        List<String> uniqueNames = namesWithDups.stream()
                .distinct()
                .toList();
        System.out.println("Unique names: " + uniqueNames);

        // ============================================
        // 5. sorted() - SORT ELEMENTS
        // ============================================
        System.out.println("\n--- 5. sorted() ---");
        System.out.println("Syntax: stream.sorted() -> Stream<T> (natural order)");
        System.out.println("Syntax: stream.sorted(Comparator) -> Stream<T> (custom order)");

        // Natural order (names)
        List<String> sortedNames = employees.stream()
                .map(emp -> emp.name())
                .sorted()
                .toList();
        System.out.println("Sorted names: " + sortedNames);

        // Custom order (by salary descending)
        List<Employee> sortedBySalary = employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .toList();
        System.out.println("By salary (high to low):");
        sortedBySalary.forEach(emp -> System.out.println("  " + emp.name() + " - " + emp.salary()));

        // ============================================
        // 6. peek() - PERFORM ACTION (DEBUGGING)
        // ============================================
        System.out.println("\n--- 6. peek() ---");
        System.out.println("Syntax: stream.peek(Consumer) -> Stream<T>");

        // peek is useful for debugging - see elements in pipeline
        long activeCount = employees.stream()
                .filter(emp -> emp.active())
                .peek(emp -> System.out.println("  After filter: " + emp.name()))
                .map(emp -> emp.name().toUpperCase())
                .peek(name -> System.out.println("  After map: " + name))
                .count();
        System.out.println("Active count: " + activeCount);

        // ============================================
        // 7. limit() - KEEP FIRST N ELEMENTS
        // ============================================
        System.out.println("\n--- 7. limit() ---");
        System.out.println("Syntax: stream.limit(long) -> Stream<T>");

        List<Employee> top2 = employees.stream()
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .limit(2)
                .toList();
        System.out.println("Top 2 earners:");
        top2.forEach(emp -> System.out.println("  " + emp.name() + " - " + emp.salary()));

        // ============================================
        // 8. skip() - SKIP FIRST N ELEMENTS
        // ============================================
        System.out.println("\n--- 8. skip() ---");
        System.out.println("Syntax: stream.skip(long) -> Stream<T>");

        List<Employee> skipFirst2 = employees.stream()
                .sorted((e1, e2) -> e1.salary().compareTo(e2.salary()))
                .skip(2)
                .toList();
        System.out.println("After skipping 2 lowest earners:");
        skipFirst2.forEach(emp -> System.out.println("  " + emp.name() + " - " + emp.salary()));

        // ============================================
        // 9. CHAINING INTERMEDIATE OPERATIONS
        // ============================================
        System.out.println("\n--- 9. Chaining ---");

        List<String> result = employees.stream()
                .filter(emp -> emp.active())                          // 1. Keep active
                .filter(emp -> "Engineering".equals(emp.department())) // 2. Keep engineers
                .map(emp -> emp.name())                               // 3. Get names
                .map(String::toUpperCase)                             // 4. Convert to upper
                .sorted()                                             // 5. Sort alphabetically
                .distinct()                                           // 6. Remove duplicates
                .toList();                                            // 7. Collect
        System.out.println("Chained result: " + result);

        // ============================================
        // 10. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 10. Common Mistakes ---");

        // Mistake 1: Modifying source in intermediate operation
        List<Employee> modifiable = new ArrayList<>(employees);
        try {
            modifiable.stream()
                    .filter(emp -> {
                        if (emp.id() == 1) {
                            modifiable.remove(emp); // ConcurrentModificationException!
                        }
                        return emp.active();
                    })
                    .count();
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName());
        }

        // Mistake 2: Using peek for side effects (not recommended)
        // employees.stream().peek(emp -> saveToDatabase(emp)).toList(); // BAD practice
        // Use forEach for side effects, not peek

        // Mistake 3: Forgetting that intermediate ops are lazy
        java.util.stream.Stream<Employee> lazy = employees.stream()
                .filter(emp -> {
                    System.out.println("Filtering: " + emp.name());
                    return emp.active();
                });
        System.out.println("Stream created, no filtering yet");
        lazy.count();  // NOW filtering happens
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        return employees;
    }
}
