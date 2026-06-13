package phase11;

import java.io.*;

/**
 * LESSON 10: SERIALIZATION VS DESERIALIZATION
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Basic serialization
 * 2. serialVersionUID
 * 3. transient keyword
 * 4. Externalizable
 * 5. Interview questions
 */

public class Lesson10_SerializationVsDeserialization {
    public static void main(String[] args) {
        System.out.println("""
            === SERIALIZATION VS DESERIALIZATION ===
            
            1. BASIC SERIALIZATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Convert object to bytes for storage/transmission.
            
               WHY IT EXISTS:
               - Save object state to disk
               - Send objects over network
               - Deep cloning
            
               SIMPLE EXAMPLE:
                   // Implement Serializable:
                   class User implements Serializable {
                       private static final long serialVersionUID = 1L;
                       private String name;
                       private int age;
                   }
                   
                   // Serialize:
                   try (ObjectOutputStream oos = 
                           new ObjectOutputStream(new FileOutputStream("user.ser"))) {
                       oos.writeObject(user);
                   }
                   
                   // Deserialize:
                   try (ObjectInputStream ois = 
                           new ObjectInputStream(new FileInputStream("user.ser"))) {
                       User deserialized = (User) ois.readObject();
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   HTTP session management:
                   - Session objects serialized to Redis
                   - Shared across multiple servers
                   - Failover support
            
               INTERVIEW QUESTION:
                   "How does Java serialization work?
                   What is the serialization process?"
            
               COMMON MISTAKES:
                   - Not implementing Serializable
                   - Not handling NotSerializableException
            
            ─────────────────────────────────────────────────────────────────────
            
            2. SERIALVERSIONUID
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Unique identifier for class version compatibility.
            
               WHY IT EXISTS:
               - Version compatibility
               - Prevent deserialization errors
            
               SIMPLE EXAMPLE:
                   class User implements Serializable {
                       private static final long serialVersionUID = 1L;
                       // If class changes, update this value
                   }
                   
                   // Without explicit declaration:
                   // - Generated from class structure
                   // - Changes with any field/method change
                   // - Causes InvalidClassException
            
               REAL-WORLD BACKEND EXAMPLE:
                   A distributed cache:
                   - Serialized objects stored in Redis
                   - Application updated with new fields
                   - Explicit serialVersionUID prevents errors
            
               INTERVIEW QUESTION:
                   "What happens if serialVersionUID doesn't match?
                   Why should you always declare it explicitly?"
            
               COMMON MISTAKES:
                   - Not declaring serialVersionUID
                   - Not updating on breaking changes
            
            ─────────────────────────────────────────────────────────────────────
            
            3. TRANSIENT KEYWORD
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Fields marked transient are NOT serialized.
            
               WHY IT EXISTS:
               - Security (passwords, tokens)
               - Non-serializable fields
               - Derived/computed values
            
               SIMPLE EXAMPLE:
                   class UserWithSecret implements Serializable {
                       private String username;
                       private transient String password;  // Not serialized
                       private transient Thread thread;     // Not serializable
                   }
                   
                   // After deserialization:
                   // - password = null
                   // - thread = null
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user session object:
                   - username: serialized
                   - password: transient (security)
                   - database connection: transient (not serializable)
            
               INTERVIEW QUESTION:
                   "What is the transient keyword?
                   How to handle transient fields after deserialization?"
            
               COMMON MISTAKES:
                   - Forgetting transient on sensitive data
                   - Not handling null after deserialization
            
            ─────────────────────────────────────────────────────────────────────
            
            4. EXTERNALIZABLE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Manual control over serialization process.
            
               WHY IT EXISTS:
               - Performance optimization
               - Custom format control
            
               SIMPLE EXAMPLE:
                   class User implements Externalizable {
                       private String name;
                       private int age;
                       
                       public void writeExternal(ObjectOutput out) throws IOException {
                           out.writeObject(name);
                           out.writeInt(age);
                       }
                       
                       public void readExternal(ObjectInput in) throws IOException {
                           name = (String) in.readObject();
                           age = in.readInt();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A high-performance cache:
                   - Externalizable for compact format
                   - Custom compression
                   - Faster serialization/deserialization
            
               INTERVIEW QUESTION:
                   "What's the difference between Serializable and Externalizable?
                   When would you use each?"
            
               COMMON MISTAKES:
                   - Not implementing both methods
                   - Not calling defaultWriteObject/readObject
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Serialization is critical for:
            - Distributed systems
            - Caching
            - Session management
            - Data persistence
            """);
    }
}
