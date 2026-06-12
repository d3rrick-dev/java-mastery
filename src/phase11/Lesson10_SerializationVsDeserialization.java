package phase11;

import java.io.*;

/**
 * LESSON 10: SERIALIZATION VS DESERIALIZATION
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Serialization: Convert object to bytes (for storage/transmission)
 * Deserialization: Convert bytes back to object
 * Like packing a box (serialize) and unpacking it (deserialize).
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Save object state to disk
 * - Send objects over network
 * - Deep cloning
 * - Session management in web apps
 */

public class Lesson10_SerializationVsDeserialization {

    public static void main(String[] args) {
        System.out.println("=== SERIALIZATION VS DESERIALIZATION ===\n");

        // ============================================================
        // EXAMPLE 1: Basic serialization
        // ============================================================
        System.out.println("--- Example 1: Basic Serialization ---\n");

        User user = new User("Alice", 30, "alice@example.com");
        System.out.println("Original: " + user);

        // Serialize
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("user.ser"))) {
            oos.writeObject(user);
            System.out.println("Serialized to user.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("user.ser"))) {
            User deserialized = (User) ois.readObject();
            System.out.println("Deserialized: " + deserialized);
            System.out.println("Equal: " + user.equals(deserialized));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 2: serialVersionUID
        // ============================================================
        System.out.println("--- Example 2: serialVersionUID ---\n");

        System.out.println("serialVersionUID ensures version compatibility:");
        System.out.println("  - Generated from class structure");
        System.out.println("  - If class changes, UID changes");
        System.out.println("  - Mismatch causes InvalidClassException");
        System.out.println();
        System.out.println("Best practice: Declare explicitly");
        System.out.println("  private static final long serialVersionUID = 1L;");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: transient keyword
        // ============================================================
        System.out.println("--- Example 3: Transient Fields ---\n");

        UserWithSecret userWithSecret = new UserWithSecret("Bob", "secret123");
        System.out.println("Original password: " + userWithSecret.getPassword());

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("user_secret.ser"))) {
            oos.writeObject(userWithSecret);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("user_secret.ser"))) {
            UserWithSecret deserialized = (UserWithSecret) ois.readObject();
            System.out.println("Deserialized password: " + deserialized.getPassword());
            System.out.println("(transient fields are NOT serialized)");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Externalizable
        // ============================================================
        System.out.println("--- Example 4: Externalizable ---\n");

        System.out.println("Serializable (default):");
        System.out.println("  - Automatic serialization");
        System.out.println("  - Uses reflection");
        System.out.println("  - Slower, larger output");
        System.out.println();
        System.out.println("Externalizable (manual):");
        System.out.println("  - Implement writeExternal()/readExternal()");
        System.out.println("  - Full control over serialization");
        System.out.println("  - Faster, smaller output");
        System.out.println();
    }

    // ============================================================
    // SERIALIZATION EXAMPLES
    // ============================================================

    static class User implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String name;
        private final int age;
        private final String email;

        public User(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }

        @Override
        public String toString() {
            return name + " (" + age + ", " + email + ")";
        }
    }

    static class UserWithSecret implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String username;
        private transient String password;  // Not serialized

        public UserWithSecret(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return username + " (password: " + password + ")";
        }
    }
}
