package phase9;

import java.io.*;

/**
 * LESSON 11: SINGLETON PATTERN
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Singleton ensures a class has only one instance and provides
 * a global point of access to it. Like a president - only one
 * at a time.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Control resource access (database connection, thread pool)
 * - Single configuration point
 * - Memory efficiency (one instance)
 * - Global access point
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Database connection pool:
 * - Only one pool instance
 * - All code uses same pool
 * - Controlled resource usage
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Configuration manager:
 * - Load config once
 * - All services use same config
 * - Thread-safe access
 */

public class Lesson11_SingletonPattern {

    public static void main(String[] args) throws Exception {
        System.out.println("=== SINGLETON PATTERN ===\n");

        // ============================================================
        // EXAMPLE 1: Eager initialization
        // ============================================================
        System.out.println("--- Example 1: Eager Initialization ---\n");

        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        System.out.println("Same instance: " + (eager1 == eager2));
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Lazy initialization (not thread-safe)
        // ============================================================
        System.out.println("--- Example 2: Lazy (Not Thread-Safe) ---\n");

        System.out.println("LazySingleton without synchronization:");
        System.out.println("  - Two threads could create two instances");
        System.out.println("  - Race condition in null check");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Thread-safe lazy (synchronized)
        // ============================================================
        System.out.println("--- Example 3: Thread-Safe Lazy ---\n");

        SynchronizedSingleton sync1 = SynchronizedSingleton.getInstance();
        SynchronizedSingleton sync2 = SynchronizedSingleton.getInstance();
        System.out.println("Same instance: " + (sync1 == sync2));
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Double-checked locking
        // ============================================================
        System.out.println("--- Example 4: Double-Checked Locking ---\n");

        DoubleCheckedSingleton dc1 = DoubleCheckedSingleton.getInstance();
        DoubleCheckedSingleton dc2 = DoubleCheckedSingleton.getInstance();
        System.out.println("Same instance: " + (dc1 == dc2));
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Bill Pugh (holder pattern)
        // ============================================================
        System.out.println("--- Example 5: Bill Pugh (Holder) ---\n");

        HolderSingleton holder1 = HolderSingleton.getInstance();
        HolderSingleton holder2 = HolderSingleton.getInstance();
        System.out.println("Same instance: " + (holder1 == holder2));
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Enum singleton
        // ============================================================
        System.out.println("--- Example 6: Enum Singleton ---\n");

        EnumSingleton enum1 = EnumSingleton.INSTANCE;
        EnumSingleton enum2 = EnumSingleton.INSTANCE;
        System.out.println("Same instance: " + (enum1 == enum2));
        System.out.println("Value: " + enum1.getValue());
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Serialization test
        // ============================================================
        System.out.println("--- Example 7: Serialization Safety ---\n");

        // Serialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(enum1);
        }

        // Deserialize
        try (ObjectInputStream ois = new ObjectInputStream(
            new ByteArrayInputStream(baos.toByteArray()))) {
            EnumSingleton deserialized = (EnumSingleton) ois.readObject();
            System.out.println("Same after deserialization: " + (enum1 == deserialized));
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 8: When to use singleton
        // ============================================================
        System.out.println("--- Example 8: When to Use ---\n");

        System.out.println("Use Singleton for:");
        System.out.println("  - Database connection pools");
        System.out.println("  - Logging frameworks");
        System.out.println("  - Configuration managers");
        System.out.println("  - Thread pools");
        System.out.println();
        System.out.println("Avoid Singleton for:");
        System.out.println("  - Testing (hard to mock)");
        System.out.println("  - Global state (hidden dependencies)");
        System.out.println("  - When dependency injection is better");
    }

    // ============================================================
    // SINGLETON IMPLEMENTATIONS
    // ============================================================

    // 1. Eager initialization
    static class EagerSingleton {
        private static final EagerSingleton INSTANCE = new EagerSingleton();

        private EagerSingleton() {}

        public static EagerSingleton getInstance() {
            return INSTANCE;
        }
    }

    // 2. Lazy initialization (not thread-safe)
    static class LazySingleton {
        private static LazySingleton instance;

        private LazySingleton() {}

        public static LazySingleton getInstance() {
            if (instance == null) {
                instance = new LazySingleton();
            }
            return instance;
        }
    }

    // 3. Thread-safe with synchronized
    static class SynchronizedSingleton {
        private static SynchronizedSingleton instance;

        private SynchronizedSingleton() {}

        public static synchronized SynchronizedSingleton getInstance() {
            if (instance == null) {
                instance = new SynchronizedSingleton();
            }
            return instance;
        }
    }

    // 4. Double-checked locking
    static class DoubleCheckedSingleton {
        private static volatile DoubleCheckedSingleton instance;

        private DoubleCheckedSingleton() {}

        public static DoubleCheckedSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckedSingleton();
                    }
                }
            }
            return instance;
        }
    }

    // 5. Bill Pugh (Holder) - recommended
    static class HolderSingleton {
        private HolderSingleton() {}

        private static class Holder {
            private static final HolderSingleton INSTANCE = new HolderSingleton();
        }

        public static HolderSingleton getInstance() {
            return Holder.INSTANCE;
        }
    }

    // 6. Enum singleton - best approach
    enum EnumSingleton {
        INSTANCE;

        private final String value = "Singleton Value";

        public String getValue() {
            return value;
        }
    }
}
