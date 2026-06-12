package phase5;

import common.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * LESSON 3: Stream with Map
 *
 * THEORY:
 * Maps are commonly used with Streams for key-value operations.
 * Streams provide powerful ways to transform, filter, and aggregate Map data.
 *
 * COMMON OPERATIONS:
 * - Filtering map entries
 * - Transforming map values
 * - Grouping by map keys
 * - Inverting maps
 */

public class Lesson03_StreamWithMap {

    public static void main(String[] args) {
        System.out.println("=== LESSON 3: Stream with Map ===\n");

        // ============================================
        // 1. CREATING MAPS FROM STREAMS
        // ============================================
        System.out.println("--- 1. Creating Maps from Streams ---");

        List<Employee> employees = createSampleEmployees();

        // Map: ID -> Name
        Map<Integer, String> idToName = employees.stream()
                .collect(Collectors.toMap(
                        Employee::id,
                        Employee::name
                ));
        System.out.println("ID to Name: " + idToName);

        // Map: Name -> Salary
        Map<String, BigDecimal> nameToSalary = employees.stream()
                .collect(Collectors.toMap(
                        Employee::name,
                        Employee::salary
                ));
        System.out.println("Name to Salary: " + nameToSalary);

        // ============================================
        // 2. FILTERING MAP ENTRIES
        // ============================================
        System.out.println("\n--- 2. Filtering Map Entries ---");

        // Filter entries where salary > 80000
        Map<String, BigDecimal> highEarners = employees.stream()
                .filter(emp -> emp.salary().compareTo(new BigDecimal("80000")) > 0)
                .collect(Collectors.toMap(
                        Employee::name,
                        Employee::salary
                ));
        System.out.println("High earners: " + highEarners);

        // Filter map entries
        Map<Integer, String> filteredMap = idToName.entrySet().stream()
                .filter(entry -> entry.getKey() <= 3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        System.out.println("Filtered map (ID <= 3): " + filteredMap);

        // ============================================
        // 3. TRANSFORMING MAP VALUES
        // ============================================
        System.out.println("\n--- 3. Transforming Map Values ---");

        // Transform values (e.g., add 10% bonus)
        Map<String, BigDecimal> withBonus = employees.stream()
                .collect(Collectors.toMap(
                        Employee::name,
                        emp -> emp.salary().multiply(new BigDecimal("1.1"))
                ));
        System.out.println("Salaries with 10% bonus: " + withBonus);

        // ============================================
        // 4. GROUPING BY MAP KEYS
        // ============================================
        System.out.println("\n--- 4. Grouping by Map Keys ---");

        // Group employees by department
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::department));
        System.out.println("By department: " + byDept.keySet());

        // Group by active status
        Map<Boolean, List<Employee>> byActive = employees.stream()
                .collect(Collectors.partitioningBy(Employee::active));
        System.out.println("Active: " + byActive.get(true).size());
        System.out.println("Inactive: " + byActive.get(false).size());

        // ============================================
        // 5. INVERTING MAPS
        // ============================================
        System.out.println("\n--- 5. Inverting Maps ---");

        // Original: ID -> Name
        // Inverted: Name -> ID
        Map<String, Integer> inverted = employees.stream()
                .collect(Collectors.toMap(
                        Employee::name,
                        Employee::id
                ));
        System.out.println("Inverted (Name -> ID): " + inverted);

        // ============================================
        // 6. CODING CHALLENGE EXAMPLE
        // ============================================
        System.out.println("\n--- 6. Coding Challenge ---");

        // Challenge: Create frequency map of departments
        Map<String, Long> deptFreq = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()
                ));
        System.out.println("Department frequency: " + deptFreq);

        // Challenge: Find department with most employees
        String topDept = deptFreq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
        System.out.println("Department with most employees: " + topDept);

        // ============================================
        // 7. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 7. Common Mistakes ---");

        // Mistake 1: Duplicate keys
        // toMap() throws IllegalStateException if duplicate keys
        // Use toMap(keyMapper, valueMapper, mergeFunction) to handle duplicates
        Map<String, Employee> byName = employees.stream()
                .collect(Collectors.toMap(
                        Employee::name,
                        emp -> emp,
                        (existing, replacement) -> existing  // Keep existing
                ));
        System.out.println("By name (handling duplicates): " + byName.keySet());

        // Mistake 2: Forgetting that maps are not ordered
        // Use LinkedHashMap or TreeMap if you need order
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
