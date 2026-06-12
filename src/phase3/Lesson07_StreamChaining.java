package phase3;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 7: Stream Chaining
 *
 * THEORY:
 * Stream chaining is the practice of combining multiple stream operations
 * to create complex data processing pipelines.
 *
 * WHY IT EXISTS:
 * - Build complex queries from simple operations
 * - Maintain readability with declarative style
 * - Enable lazy evaluation and optimization
 * - Reduce intermediate variables
 */

public class Lesson07_StreamChaining {

    public static void main(String[] args) {
        System.out.println("=== LESSON 7: Stream Chaining ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. BASIC CHAINING
        // ============================================
        System.out.println("--- 1. Basic Chaining ---");

        // Chain: filter -> map -> collect
        List<String> activeEngineerNames = employees.stream()
                .filter(emp -> emp.active())
                .filter(emp -> "Engineering".equals(emp.department()))
                .map(emp -> emp.name().toUpperCase())
                .toList();
        System.out.println("Active engineer names: " + activeEngineerNames);

        // ============================================
        // 2. COMPLEX CHAINING
        // ============================================
        System.out.println("\n--- 2. Complex Chaining ---");

        // Complex query: Find top 3 highest paid active engineers
        List<Employee> top3Engineers = employees.stream()
                .filter(Employee::active)                                    // 1. Active only
                .filter(emp -> "Engineering".equals(emp.department()))      // 2. Engineers only
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))     // 3. Sort by salary desc
                .limit(3)                                                    // 4. Top 3
                .toList();                                                   // 5. Collect
        System.out.println("Top 3 engineers:");
        top3Engineers.forEach(emp -> System.out.println("  " + emp.name() + " - $" + emp.salary()));

        // ============================================
        // 3. CHAINING WITH GROUPING
        // ============================================
        System.out.println("\n--- 3. Chaining with Grouping ---");

        // Group by department, then filter groups
        Map<String, List<Employee>> deptGroups = employees.stream()
                .collect(Collectors.groupingBy(Employee::department));

        // Filter departments with more than 1 employee
        Map<String, List<Employee>> largeDepts = deptGroups.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        System.out.println("Departments with >1 employee: " + largeDepts.keySet());

        // ============================================
        // 4. CHAINING WITH COLLECTORS
        // ============================================
        System.out.println("\n--- 4. Chaining with Collectors ---");

        // Multiple collectors in sequence
        String report = employees.stream()
                .filter(emp -> emp.active())
                .map(emp -> emp.name() + " (" + emp.department() + ")")
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Active employees report: " + report);

        // ============================================
        // 5. REUSABLE CHAINED OPERATIONS
        // ============================================
        System.out.println("\n--- 5. Reusable Chained Operations ---");

        // Define reusable predicates
        java.util.function.Predicate<Employee> isActive = Employee::active;
        java.util.function.Predicate<Employee> isEngineer = emp -> "Engineering".equals(emp.department());
        java.util.function.Predicate<Employee> isHighEarner = emp -> emp.salary().compareTo(new BigDecimal("90000")) > 0;

        // Reuse in different queries
        System.out.println("Active engineers:");
        employees.stream()
                .filter(isActive.and(isEngineer))
                .map(Employee::name)
                .forEach(name -> System.out.println("  " + name));

        System.out.println("High earning active employees:");
        employees.stream()
                .filter(isActive.and(isHighEarner))
                .map(Employee::name)
                .forEach(name -> System.out.println("  " + name));

        // ============================================
        // 6. PERFORMANCE CONSIDERATIONS
        // ============================================
        System.out.println("\n--- 6. Performance Considerations ---");

        // Good: Filter early, reduce data as soon as possible
        long start1 = System.nanoTime();
        List<String> goodChain = employees.stream()
                .filter(Employee::active)                    // Filter first
                .filter(emp -> "Engineering".equals(emp.department()))
                .map(Employee::name)
                .limit(5)                                    // Limit early
                .toList();
        long time1 = System.nanoTime() - start1;

        // Bad: Process all data, then filter
        long start2 = System.nanoTime();
        List<String> badChain = employees.stream()
                .map(Employee::name)                         // Map all first
                .filter(name -> name.length() > 0)           // Then filter
                .filter(name -> name.startsWith("A"))        // Then filter more
                .limit(5)
                .toList();
        long time2 = System.nanoTime() - start2;

        System.out.println("Good chain (filter early): " + time1 + " ns");
        System.out.println("Bad chain (filter late): " + time2 + " ns");

        // ============================================
        // 7. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 7. Coding Challenge ---");

        // Challenge: Complex employee report
        String complexReport = employees.stream()
                .filter(Employee::active)
                .filter(emp -> emp.salary().compareTo(new BigDecimal("70000")) > 0)
                .sorted((e1, e2) -> e2.salary().compareTo(e1.salary()))
                .map(emp -> String.format("%s (%s) - $%s",
                        emp.name(), emp.department(), emp.salary()))
                .collect(Collectors.joining("\n", "High-Paid Active Employees:\n", "\n"));
        System.out.println(complexReport);

        // ============================================
        // 8. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 8. Common Mistakes ---");

        // Mistake 1: Too many chained operations (hard to read)
        // employees.stream()
        //     .filter(...)
        //     .filter(...)
        //     .filter(...)
        //     .filter(...)
        //     .filter(...)
        //     .map(...)
        //     .filter(...)
        //     .toList(); // Hard to understand

        // Better: Break into logical steps or use variables
        java.util.function.Predicate<Employee> qualified = emp ->
                emp.active() &&
                "Engineering".equals(emp.department()) &&
                emp.salary().compareTo(new BigDecimal("80000")) > 0 &&
                emp.age() > 25 &&
                emp.age() < 40;

        List<Employee> qualifiedEmps = employees.stream()
                .filter(qualified)
                .toList();
        System.out.println("Qualified employees: " + qualifiedEmps.size());

        // Mistake 2: Not understanding operation order
        // sorted() before filter() processes ALL elements
        // sorted() after filter() processes FEWER elements
    }

    private static List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", "Engineering", new BigDecimal("95000"), 30, LocalDate.of(2020, 1, 15), true));
        employees.add(new Employee(2, "Bob", "Sales", new BigDecimal("75000"), 28, LocalDate.of(2019, 5, 20), true));
        employees.add(new Employee(3, "Charlie", "Engineering", new BigDecimal("120000"), 35, LocalDate.of(2018, 3, 10), true));
        employees.add(new Employee(4, "Diana", "HR", new BigDecimal("65000"), 25, LocalDate.of(2021, 7, 1), true));
        employees.add(new Employee(5, "Eve", "Engineering", new BigDecimal("85000"), 32, LocalDate.of(2017, 11, 5), false));
        employees.add(new Employee(6, "Frank", "Sales", new BigDecimal("90000"), 40, LocalDate.of(2015, 2, 28), true));
        employees.add(new Employee(7, "Grace", "HR", new BigDecimal("70000"), 27, LocalDate.of(2020, 8, 15), true));
        return employees;
    }
}
