package phase9;

import java.io.*;

/**
 * LESSON 8: SERIALIZATION
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Serialization converts an object into a byte stream.
 * Deserialization converts a byte stream back into an object.
 * Like packing a box (serialize) and unpacking it (deserialize).
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Save object state to file/database
 * - Send objects over network
 * - Cache objects
 * - Deep cloning
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Session storage:
 * - Serialize user session to Redis
 * - Deserialize when user returns
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Message queue:
 * - Serialize message to bytes
 * - Send over network
 * - Deserialize on consumer
 */

public class Lesson08_Serialization {

    public static void main(String[] args) throws Exception {
        System.out.println("=== SERIALIZATION ===\n");

        // ============================================================
        // EXAMPLE 1: Basic serialization
        // ============================================================
        System.out.println("--- Example 1: Basic Serialization ---\n");

        User user = new User("Alice", 30, "alice@example.com");
        System.out.println("Original: " + user);

        // Serialize
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(user);
        }

        byte[] serialized = baos.toByteArray();
        System.out.println("Serialized size: " + serialized.length + " bytes");

        // Deserialize
        try (ObjectInputStream ois = new ObjectInputStream(
            new ByteArrayInputStream(serialized))) {
            User deserialized = (User) ois.readObject();
            System.out.println("Deserialized: " + deserialized);
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 2: transient keyword
        // ============================================================
        System.out.println("--- Example 2: Transient Fields ---\n");

        UserWithTransient transientUser = new UserWithTransient("Bob", 25, "bob@example.com");
        System.out.println("Before: password=" + transientUser.getPassword());

        // Serialize and deserialize
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos2)) {
            oos.writeObject(transientUser);
        }

        try (ObjectInputStream ois = new ObjectInputStream(
            new ByteArrayInputStream(baos2.toByteArray()))) {
            UserWithTransient deserialized = (UserWithTransient) ois.readObject();
            System.out.println("After: password=" + deserialized.getPassword());
            System.out.println("(transient field not serialized)");
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: serialVersionUID
        // ============================================================
        System.out.println("--- Example 3: serialVersionUID ---\n");

        System.out.println("serialVersionUID ensures version compatibility");
        System.out.println("  - Same UID = compatible versions");
        System.out.println("  - Different UID = InvalidClassException");
        System.out.println("  - Always declare explicitly");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Externalizable
        // ============================================================
        System.out.println("--- Example 4: Externalizable ---\n");

        ExternalUser externalUser = new ExternalUser("Charlie", 35);
        System.out.println("Original: " + externalUser);

        ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos3)) {
            oos.writeObject(externalUser);
        }

        try (ObjectInputStream ois = new ObjectInputStream(
            new ByteArrayInputStream(baos3.toByteArray()))) {
            ExternalUser deserialized = (ExternalUser) ois.readObject();
            System.out.println("Deserialized: " + deserialized);
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Serialization best practices
        // ============================================================
        System.out.println("--- Example 5: Best Practices ---\n");

        System.out.println("1. Implement Serializable only when needed");
        System.out.println("2. Declare serialVersionUID explicitly");
        System.out.println("3. Use transient for sensitive data");
        System.out.println("4. Consider Externalizable for performance");
        System.out.println("5. Be careful with deserialization (security)");
        System.out.println("6. Consider JSON/XML for interoperability");
    }

    // ============================================================
    // SERIALIZABLE CLASSES
    // ============================================================

    static class User implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;
        private int age;
        private String email;

        public User(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + ", email='" + email + "'}";
        }
    }

    static class UserWithTransient implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;
        private int age;
        private transient String password;  // Not serialized

        public UserWithTransient(String name, int age, String password) {
            this.name = name;
            this.age = age;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + "}";
        }
    }

    static class ExternalUser implements Externalizable {
        private String name;
        private int age;

        public ExternalUser() {}  // Required no-arg constructor

        public ExternalUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(name);
            out.writeInt(age);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            age = in.readInt();
        }

        @Override
        public String toString() {
            return "ExternalUser{name='" + name + "', age=" + age + "}";
        }
    }
}
