package phase12.SENIOR;

/**
 * INTERVIEW QUESTION: How does HashMap work internally?
 *
 * Difficulty: SENIOR
 */

public class Lesson03_HowDoesHashMapWorkInternally {
    public static void main(String[] args) {
        System.out.println("=== HASHMAP INTERNALS ===\n");
        System.out.println("Data Structure:");
        System.out.println("  - Array of Node<K,V> (buckets)");
        System.out.println("  - Each Node: hash, key, value, next (linked list)");
        System.out.println("  - Java 8+: linked list converts to red-black tree at threshold (8)");
        System.out.println();
        System.out.println("Put Operation:");
        System.out.println("  1. Calculate hash: hash(key.hashCode())");
        System.out.println("  2. Index = (n-1) & hash (n = table length)");
        System.out.println("  3. If bucket empty: insert new Node");
        System.out.println("  4. If collision: traverse linked list/tree");
        System.out.println("  5. If key exists: replace value");
        System.out.println("  6. If tree: insert into tree");
        System.out.println();
        System.out.println("Get Operation:");
        System.out.println("  1. Calculate hash and index");
        System.out.println("  2. Traverse bucket (list or tree)");
        System.out.println("  3. Compare keys using equals()");
        System.out.println("  4. Return value if found");
        System.out.println();
        System.out.println("Resizing:");
        System.out.println("  - Triggered when size > threshold (capacity * loadFactor)");
        System.out.println("  - Default: capacity=16, loadFactor=0.75");
        System.out.println("  - New capacity = 2 * old capacity");
        System.out.println("  - Rehash all elements (expensive)");
        System.out.println();
        System.out.println("Senior-Level Insights:");
        System.out.println("  - Hash function: spread bits to reduce collisions");
        System.out.println("  - Treeification: O(log n) instead of O(n) for collisions");
        System.out.println("  - Untreeification: when size drops below 6");
        System.out.println("  - ConcurrentHashMap: different implementation (segments/CAS)");
    }
}
