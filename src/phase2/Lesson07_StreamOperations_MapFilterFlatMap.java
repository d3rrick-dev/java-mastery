package phase2;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * LESSON 7: map(), filter(), flatMap() - Deep Dive
 *
 * THEORY:
 * These are the three most commonly used intermediate operations.
 *
 * filter(Predicate)  : Keep elements matching condition
 * map(Function)      : Transform each element to another type
 * flatMap(Function)  : Flatten nested structures (e.g., List<List<T>> -> List<T>)
 */

public class Lesson07_StreamOperations_MapFilterFlatMap {

    public static void main(String[] args) {
        System.out.println("=== LESSON 7: map(), filter(), flatMap() ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. filter() - KEEP MATCHING ELEMENTS
        // ============================================
        System.out.println("--- 1. filter() ---");

        // filter keeps elements where Predicate returns true
        List<Employee> activeEngineers = employees.stream()
                .filter(emp -> emp.active())                              // Keep active
                .filter(emp -> "Engineering".equals(emp.department()))    // Keep engineers
                .toList();

        System.out.println("Active engineers:");
        activeEngineers.forEach(emp -> System.out.println("  " + emp.name()));

        // ============================================
        // 2. map() - TRANSFORM EACH ELEMENT
        // ============================================
        System.out.println("\n--- 2. map() ---");

        // map transforms each element using a Function
        List<String> names = employees.stream()
                .map(emp -> emp.name())
                .toList();
        System.out.println("Names: " + names);

        // map to different type
        List<Integer> ages = employees.stream()
                .map(emp -> emp.age())
                .toList();
        System.out.println("Ages: " + ages);

        // map with transformation
        List<String> upperNames = employees.stream()
                .map(emp -> emp.name().toUpperCase())
                .toList();
        System.out.println("Upper names: " + upperNames);

        // ============================================
        // 3. flatMap() - FLATTEN NESTED STRUCTURES
        // ============================================
        System.out.println("\n--- 3. flatMap() ---");

        // Scenario: Each employee has a list of skills
        List<EmployeeWithSkills> skilledEmployees = createSkilledEmployees();

        // Without flatMap: List<List<String>>
        List<List<String>> skillsPerEmp = new ArrayList<>();
        for (EmployeeWithSkills emp : skilledEmployees) {
            skillsPerEmp.add(emp.skills());
        }
        System.out.println("Skills per employee (nested): " + skillsPerEmp);

        // With flatMap: List<String>
        List<String> allSkills = skilledEmployees.stream()
                .flatMap(emp -> emp.skills().stream())
                .distinct()
                .toList();
        System.out.println("All unique skills (flattened): " + allSkills);

        // ============================================
        // 4. REAL-WORLD EXAMPLE: PROCESSING NESTED DATA
        // ============================================
        System.out.println("\n--- 4. Real-world Example ---");

        // Scenario: Orders with multiple items
        List<Order> orders = createSampleOrders();

        // Get all product names from all orders
        List<String> allProducts = orders.stream()
                .flatMap(order -> order.items().stream())
                .map(Item::name)
                .distinct()
                .toList();
        System.out.println("All products: " + allProducts);

        // Get total quantity of all items
        int totalQuantity = orders.stream()
                .flatMap(order -> order.items().stream())
                .mapToInt(Item::quantity)
                .sum();
        System.out.println("Total quantity: " + totalQuantity);

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Given list of sentences, find all unique words
        List<String> sentences = List.of(
                "Java streams are powerful",
                "Streams enable functional programming",
                "Java is awesome"
        );

        List<String> uniqueWords = sentences.stream()
                .flatMap(sentence -> java.util.Arrays.stream(sentence.split(" ")))
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .toList();
        System.out.println("Unique words: " + uniqueWords);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using map() when you need flatMap()
        List<EmployeeWithSkills> emps = createSkilledEmployees();
        // Wrong: Returns Stream<List<String>>
        // List<List<String>> wrong = emps.stream()
        //         .map(emp -> emp.skills())
        //         .toList();

        // Correct: Returns Stream<String>
        List<String> correct = emps.stream()
                .flatMap(emp -> emp.skills().stream())
                .toList();
        System.out.println("Correct flatMap result: " + correct);

        // Mistake 2: Forgetting that flatMap needs a Stream return
        // emps.stream().flatMap(emp -> emp.skills()); // ERROR: skills() returns List, not Stream
        emps.stream().flatMap(emp -> emp.skills().stream()); // Correct

        // Mistake 3: Using flatMap when map is sufficient
        // employees.stream().flatMap(emp -> Stream.of(emp.name())); // Unnecessary
        employees.stream().map(emp -> emp.name()); // Correct
    }

    // Helper class for flatMap example
    record EmployeeWithSkills(String name, List<String> skills) {}

    // Helper class for real-world example
    record Item(String name, int quantity, BigDecimal price) {}
    record Order(int id, List<Item> items) {}

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        return employees;
    }

    private static List<EmployeeWithSkills> createSkilledEmployees() {
        List<EmployeeWithSkills> emps = new ArrayList<>();
        emps.add(new EmployeeWithSkills("Alice", List.of("Java", "Spring", "SQL")));
        emps.add(new EmployeeWithSkills("Bob", List.of("Python", "Django")));
        emps.add(new EmployeeWithSkills("Charlie", List.of("Java", "Kubernetes", "Docker")));
        emps.add(new EmployeeWithSkills("Diana", List.of("HR", "Communication")));
        return emps;
    }

    private static List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, List.of(
                new Item("Laptop", 2, new BigDecimal("1200")),
                new Item("Mouse", 5, new BigDecimal("25"))
        )));
        orders.add(new Order(2, List.of(
                new Item("Keyboard", 3, new BigDecimal("75")),
                new Item("Monitor", 2, new BigDecimal("300"))
        )));
        orders.add(new Order(3, List.of(
                new Item("Laptop", 1, new BigDecimal("1200")),
                new Item("Mouse", 2, new BigDecimal("25"))
        )));
        return orders;
    }
}
