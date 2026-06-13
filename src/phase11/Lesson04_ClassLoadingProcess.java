package phase11;

/**
 * LESSON 4: CLASS LOADING PROCESS
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Loading phases
 * 2. Class loaders hierarchy
 * 3. ClassLoader methods
 * 4. When classes are loaded
 * 5. Interview questions
 */

public class Lesson04_ClassLoadingProcess {
    public static void main(String[] args) {
        System.out.println("""
            === CLASS LOADING PROCESS ===
            
            1. LOADING PHASES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Class loading is the process of reading .class files, verifying them,
               and making them ready for execution.
            
               WHY IT EXISTS:
               - Load classes on demand (lazy loading)
               - Verify bytecode safety
               - Prepare memory for class data
            
               THE 5 PHASES:
                   1. LOADING
                      - Read .class file bytes
                      - Create Class object in method area
                      - Create ClassLoader instance
                      
                   2. VERIFICATION
                      - Check bytecode format
                      - Verify access modifiers
                      - Check final classes not subclassed
                      
                   3. PREPARATION
                      - Allocate memory for static variables
                      - Initialize to default values
                      
                   4. RESOLUTION
                      - Replace symbolic references with direct references
                      - Resolve method/field references
                      
                   5. INITIALIZATION
                      - Execute static initializers
                      - Assign static variable initial values
            
               SIMPLE EXAMPLE:
                   // Explicit loading:
                   Class<?> cls = Class.forName("com.example.MyClass");
                   
                   // Implicit loading:
                   MyClass obj = new MyClass();  // Triggers loading
            
               REAL-WORLD BACKEND EXAMPLE:
                   A plugin system:
                   - Load plugins at runtime
                   - Custom ClassLoader for isolation
                   - Handle ClassCastException
            
               INTERVIEW QUESTION:
                   "What are the 5 phases of class loading?
                   What is the parent-first delegation model?"
            
               COMMON MISTAKES:
                   - Not understanding delegation
                   - Classloader leaks
            
            ─────────────────────────────────────────────────────────────────────
            
            2. CLASS LOADER HIERARCHY
               ─────────────────────────────────────────────────────────────────────
               BOOTSTRAP CLASSLOADER:
                   - Loads core Java classes (rt.jar)
                   - Native implementation
                   - No parent (null)
            
               EXTENSION CLASSLOADER:
                   - Loads JRE extensions
                   - Parent: Bootstrap
            
               SYSTEM CLASSLOADER:
                   - Loads application classes
                   - Parent: Extension
            
               CUSTOM CLASSLOADER:
                   - User-defined
                   - Parent: System (usually)
            
               DELEGATION MODEL:
                   ClassLoader.loadClass():
                       1. Check if already loaded
                       2. Delegate to parent
                       3. Try to load itself
            
               SIMPLE EXAMPLE:
                   ClassLoader cl = MyClass.class.getClassLoader();
                   // cl: System ClassLoader
                   // cl.getParent(): Extension
                   // cl.getParent().getParent(): null (Bootstrap)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A web application:
                   - Tomcat creates WebAppClassLoader
                   - Delegates to System ClassLoader
                   - Isolates webapp classes
            
               INTERVIEW QUESTION:
                   "What is the delegation model?
                   Why can't Bootstrap ClassLoader be accessed directly?"
            
               COMMON MISTAKES:
                   - Not understanding parent delegation
                   - ClassCastException with same class
            
            ─────────────────────────────────────────────────────────────────────
            
            3. CLASSLOADER METHODS
               ─────────────────────────────────────────────────────────────────────
               KEY METHODS:
                   - loadClass(String name): Load class
                   - findClass(String name): Find class bytes
                   - defineClass(): Define class from bytes
                   - resolveClass(): Resolve references
            
               SIMPLE EXAMPLE:
                   ClassLoader cl = Thread.currentThread().getContextClassLoader();
                   Class<?> cls = cl.loadClass("com.example.MyClass");
                   Object instance = cls.newInstance();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A framework:
                   - Load classes dynamically
                   - Use reflection
                   - Handle ClassNotFoundException
            
               INTERVIEW QUESTION:
                   "How to create a custom ClassLoader?
                   What is the difference between loadClass and findClass?"
            
               COMMON MISTAKES:
                   - Not overriding findClass
                   - Not handling exceptions
            
            ─────────────────────────────────────────────────────────────────────
            
            4. WHEN CLASSES ARE LOADED
               ─────────────────────────────────────────────────────────────────────
               TRIGGERS:
                   - new keyword: new MyClass()
                   - Class.forName(): Explicit loading
                   - Static access: MyClass.staticMethod()
                   - Reflection: getDeclaredMethod()
            
               SIMPLE EXAMPLE:
                   // Lazy loading:
                   public void method() {
                       if (condition) {
                           MyClass obj = new MyClass();  // Loaded here
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A conditional service:
                   - Load expensive class only when needed
                   - Reduce startup time
                   - Use Class.forName for plugins
            
               INTERVIEW QUESTION:
                   "When is a class loaded?
                   What is lazy loading?"
            
               COMMON MISTAKES:
                   - Not understanding lazy loading
                   - Premature class loading
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Class loading is essential for:
            - Understanding JVM startup
            - Debugging ClassNotFoundException
            - Building plugin systems
            - Performance optimization
            """);
    }
}
