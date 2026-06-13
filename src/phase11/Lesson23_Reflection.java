package phase11;

import java.lang.reflect.*;

/**
 * LESSON 23: REFLECTION
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Getting class information
 * 2. Getting fields
 * 3. Getting methods
 * 4. Creating objects dynamically
 * 5. Interview questions
 */

public class Lesson23_Reflection {
    public static void main(String[] args) {
        System.out.println("""
            === REFLECTION ===
            
            1. GETTING CLASS INFORMATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Inspect class metadata at runtime.
            
               WHY IT EXISTS:
               - Dynamic class loading
               - Framework development
            
               SIMPLE EXAMPLE:
                   String str = "hello";
                   Class<?> clazz = str.getClass();
                   
                   // Or:
                   Class<?> clazz = String.class;
                   Class<?> clazz = Class.forName("java.lang.String");
                   
                   // Information:
                   // - getName(): Full class name
                   // - getSimpleName(): Class name only
                   // - getSuperclass(): Parent class
                   // - getInterfaces(): Implemented interfaces
            
               REAL-WORLD BACKEND EXAMPLE:
                   A plugin system:
                   Class<?> pluginClass = Class.forName(pluginClassName);
                   if (PluginInterface.class.isAssignableFrom(pluginClass)) {
                       PluginInterface plugin = (PluginInterface) 
                           pluginClass.getDeclaredConstructor().newInstance();
                   }
            
               INTERVIEW QUESTION:
                   "What is reflection?
                   How to get a Class object?"
            
               COMMON MISTAKES:
                   - Not handling ClassNotFoundException
                   - Not understanding class loading
            
            ─────────────────────────────────────────────────────────────────────
            
            2. GETTING FIELDS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Access and modify class fields dynamically.
            
               WHY IT EXISTS:
               - Dynamic property access
               - Framework features
            
               SIMPLE EXAMPLE:
                   Class<Person> personClass = Person.class;
                   
                   // Get all fields:
                   for (Field field : personClass.getDeclaredFields()) {
                       System.out.println(field.getName() + " (" + field.getType() + ")");
                   }
                   
                   // Access private field:
                   Field nameField = personClass.getDeclaredField("name");
                   nameField.setAccessible(true);  // Bypass access control
                   String name = (String) nameField.get(person);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A serialization framework:
                   for (Field field : objClass.getDeclaredFields()) {
                       field.setAccessible(true);
                       Object value = field.get(obj);
                       // Serialize value
                   }
            
               INTERVIEW QUESTION:
                   "How to access private fields via reflection?
                   What is setAccessible()?"
            
               COMMON MISTAKES:
                   - Security exceptions
                   - Not handling IllegalAccessException
            
            ─────────────────────────────────────────────────────────────────────
            
            3. GETTING METHODS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Invoke methods dynamically.
            
               WHY IT EXISTS:
               - Dynamic method dispatch
               - Framework features
            
               SIMPLE EXAMPLE:
                   // Get method:
                   Method getName = personClass.getMethod("getName");
                   
                   // Invoke:
                   String name = (String) getName.invoke(person);
                   
                   // Get all methods:
                   for (Method method : personClass.getDeclaredMethods()) {
                       System.out.println(method.getName());
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A REST framework:
                   Method method = controllerClass.getMethod(handlerMethodName);
                   Object result = method.invoke(controller, request);
            
               INTERVIEW QUESTION:
                   "How to invoke a method via reflection?
                   What exceptions can occur?"
            
               COMMON MISTAKES:
                   - Not handling InvocationTargetException
                   - Wrong parameter types
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CREATING OBJECTS DYNAMICALLY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Instantiate objects without knowing type at compile time.
            
               WHY IT EXISTS:
               - Plugin architectures
               - Dependency injection
            
               SIMPLE EXAMPLE:
                   // Get constructor:
                   Constructor<Person> constructor = 
                       personClass.getConstructor(String.class, int.class);
                   
                   // Create instance:
                   Person person = constructor.newInstance("Alice", 30);
                   
                   // Or default constructor:
                   Person person = personClass.getDeclaredConstructor().newInstance();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A dependency injection container:
                   Class<?> beanClass = Class.forName(beanClassName);
                   Object bean = beanClass.getDeclaredConstructor().newInstance();
            
               INTERVIEW QUESTION:
                   "How to create an object via reflection?
                   What is the difference between newInstance() and getConstructor()?"
            
               COMMON MISTAKES:
                   - Not handling InstantiationException
                   - Not handling NoSuchMethodException
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Reflection is essential for:
            - Framework development
            - Dynamic programming
            - Testing tools
            - Serialization
            """);
    }
}
