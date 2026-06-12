package phase5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LESSON 9: Interview Pattern - Group By
 *
 * THEORY:
 * Grouping elements by a property is extremely common in interviews.
 * Used for: categorizing, aggregating, reporting.
 *
 * DATA STRUCTURES:
 * - HashMap: General grouping
 * - TreeMap: Sorted grouping
 */

public class Lesson09_InterviewPatterns_GroupBy {

    public static void main(String[] args) {
        System.out.println("=== LESSON 9: Group By ===\n");

        List<Student> students = List.of(
                new Student("Alice", "CS", 85),
                new Student("Bob", "Math", 90),
                new Student("Charlie", "CS", 78),
                new Student("Diana", "Math", 92),
                new Student("Eve", "Physics", 88),
                new Student("Frank", "CS", 95)
        );
        System.out.println("Students: " + students);

        // ============================================
        // 1. GROUP BY DEPARTMENT
        // ============================================
        System.out.println("\n--- 1. Group By Department ---");

        // Traditional: HashMap
        Map<String, List<Student>> byDept = new HashMap<>();
        for (Student s : students) {
            byDept.computeIfAbsent(s.department(), k -> new ArrayList<>()).add(s);
        }
        System.out.println("By department (traditional):");
        byDept.forEach((dept, list) -> System.out.println("  " + dept + ": " + list));

        // Stream: groupingBy
        Map<String, List<Student>> byDeptStream = students.stream()
                .collect(Collectors.groupingBy(Student::department));
        System.out.println("By department (stream):");
        byDeptStream.forEach((dept, list) -> System.out.println("  " + dept + ": " + list));

        // ============================================
        // 2. GROUP BY WITH AGGREGATION
        // ============================================
        System.out.println("\n--- 2. Group By with Aggregation ---");

        // Count students per department
        Map<String, Long> countByDept = students.stream()
                .collect(Collectors.groupingBy(Student::department, Collectors.counting()));
        System.out.println("Count by department: " + countByDept);

        // Average score per department
        Map<String, Double> avgByDept = students.stream()
                .collect(Collectors.groupingBy(Student::department,
                        Collectors.averagingInt(Student::score)));
        System.out.println("Average by department: " + avgByDept);

        // Sum scores per department
        Map<String, Integer> sumByDept = students.stream()
                .collect(Collectors.groupingBy(Student::department,
                        Collectors.summingInt(Student::score)));
        System.out.println("Sum by department: " + sumByDept);

        // ============================================
        // 3. GROUP BY WITH CUSTOM RESULT
        // ============================================
        System.out.println("\n--- 3. Group By with Custom Result ---");

        // Get names by department
        Map<String, List<String>> namesByDept = students.stream()
                .collect(Collectors.groupingBy(Student::department,
                        Collectors.mapping(Student::name, Collectors.toList())));
        System.out.println("Names by department: " + namesByDept);

        // ============================================
        // 4. MULTI-LEVEL GROUPING
        // ============================================
        System.out.println("\n--- 4. Multi-Level Grouping ---");

        // Group by department, then by score range
        Map<String, Map<String, List<Student>>> multiGroup = students.stream()
                .collect(Collectors.groupingBy(Student::department,
                        Collectors.groupingBy(s -> s.score() >= 90 ? "A" : "B")));
        System.out.println("Multi-level grouping:");
        multiGroup.forEach((dept, gradeMap) -> {
            System.out.println("  " + dept + ":");
            gradeMap.forEach((grade, list) -> System.out.println("    " + grade + ": " + list));
        });

        // ============================================
        // 5. CODING CHALLENGE
        // ============================================
        System.out.println("\n--- 5. Coding Challenge ---");

        // Challenge: Group strings by length
        List<String> words = List.of("apple", "bat", "cat", "dog", "elephant", "fish", "go");
        Map<Integer, List<String>> byLength = words.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("Words grouped by length: " + byLength);

        // ============================================
        // 6. COMMON MISTAKES
        // ============================================
        System.out.println("\n--- 6. Common Mistakes ---");

        // Mistake 1: Using groupingBy when partitioningBy is more appropriate
        // partitioningBy is for boolean conditions (2 groups)
        // groupingBy is for multiple groups

        // Mistake 2: Not using downstream collector
        // groupingBy returns Map<K, List<T>> by default
        // Use downstream collector for aggregation
    }

    // Student record
    record Student(String name, String department, int score) {}
}
