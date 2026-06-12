package phase5;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * LESSON 2: Stream with Set
 *
 * THEORY:
 * Sets are used with Streams when you need unique elements.
 * Streams make it easy to convert between List and Set.
 *
 * COMMON OPERATIONS:
 * - Remove duplicates from List
 * - Find unique elements
 * - Set operations (union, intersection, difference)
 */

public class Lesson02_StreamWithSet {

    public static void main(String[] args) {
        System.out.println("=== LESSON 2: Stream with Set ===\n");

        List<Employee> employees = createSampleEmployees();

        // ============================================
        // 1. LIST TO SET (REMOVE DUPLICATES)
        // ============================================
        System.out.println("--- 1. List to Set (Remove Duplicates) ---");

        List<String> namesWithDups = List.of("Alice", "Bob", "Alice", "Charlie", "Bob", "Alice");
        System.out.println("With duplicates: " + namesWithDups);

        // Using distinct()
        List<String> unique1 = namesWithDups.stream()
                .distinct()
                .toList();
        System.out.println("Using distinct(): " + unique1);

        // Using collect to Set
        Set<String> unique2 = namesWithDups.stream()
                .collect(Collectors.toSet());
        System.out.println("Using toSet(): " + unique2);

        // Using LinkedHashSet to preserve order
        Set<String> unique3 = namesWithDups.stream()
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
        System.out.println("Using LinkedHashSet: " + unique3);

        // ============================================
        // 2. FINDING UNIQUE ELEMENTS
        // ============================================
        System.out.println("\n--- 2. Finding Unique Elements ---");

        // Unique departments
        Set<String> departments = employees.stream()
                .map(Employee::department)
                .collect(Collectors.toSet());
        System.out.println("Unique departments: " + departments);

        // Unique names
        Set<String> uniqueNames = employees.stream()
                .map(Employee::name)
                .collect(Collectors.toSet());
        System.out.println("Unique names: " + uniqueNames);

        // ============================================
        // 3. SET OPERATIONS WITH STREAMS
        // ============================================
        System.out.println("\n--- 3. Set Operations with Streams ---");

        Set<Integer> set1 = new HashSet<>(List.of(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<>(List.of(4, 5, 6, 7, 8));

        System.out.println("Set 1: " + set1);
        System.out.println("Set 2: " + set2);

        // Union
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union);

        // Intersection
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection);

        // Difference (set1 - set2)
        Set<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference (1-2): " + difference);

        // ============================================
        // 4. FINDING COMMON ELEMENTS
        // ============================================
        System.out.println("\n--- 4. Finding Common Elements ---");

        List<String> list1 = List.of("A", "B", "C", "D");
        List<String> list2 = List.of("C", "D", "E", "F");

        // Find common elements
        Set<String> common = new HashSet<>(list1);
        common.retainAll(list2);
        System.out.println("Common elements: " + common);

        // Using streams
        Set<String> common2 = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toSet());
        System.out.println("Common (stream): " + common2);

        // ============================================
        // 5. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Find employees with unique departments
        // (departments that have only one employee)
        Map<String, Long> deptCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()
                ));

        Set<String> uniqueDepts = deptCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println("Departments with 1 employee: " + uniqueDepts);

        // Challenge: Find duplicate names
        Map<String, Long> nameCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::name,
                        Collectors.counting()
                ));

        Set<String> duplicateNames = nameCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println("Duplicate names: " + duplicateNames);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using toSet() when you need order
        // toSet() returns HashSet (no order guarantee)
        // Use toCollection(LinkedHashSet::new) for insertion order

        // Mistake 2: Forgetting that Set uses equals() and hashCode()
        // Custom objects need proper equals/hashCode

        // Mistake 3: Using Set for ordered operations
        // If you need sorted order, use TreeSet
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
