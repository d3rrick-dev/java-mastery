package phase9;

/**
 * LESSON: Advanced Generics in Java
 * Phase 9: Modern Java
 * This lesson covers:
 * 1. Generic classes and interfaces
 * 2. Generic methods
 * 3. Bounded type parameters
 * 4. Wildcards (extends, super, unbounded)
 * 5. Type erasure and its implications
 * 6. Bridge methods
 * 7. Generic constructors
 * 8. Generic enums
 * 9. Generic annotations
 * 10. Recursive generics (F-bounded types)
 * 11. Variance (covariance, contravariance, invariance)
 * 12. Capture conversion
 * 13. Generic array creation
 * 14. Reflection with generics
 * 15. Common interview questions
 */

public class Lesson04_Generics_Advanced {
    public static void main(String[] args) {
        System.out.println("""
            === ADVANCED GENERICS IN JAVA ===
            
            1. GENERIC CLASSES AND INTERFACES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Generic classes and interfaces allow you to define type parameters
               that can be used throughout the class or interface definition.
            
               WHY IT EXISTS:
               - Type safety at compile time
               - Code reusability across types
               - Elimination of ClassCastException
               - Better documentation and IDE support
            
               INTERNAL MECHANICS:
               - Type parameters are erased at compile time (type erasure)
               - Bridge methods are generated for type compatibility
               - Generic signatures are stored in bytecode for reflection
               
               SIMPLE EXAMPLE:
                   public class GenericContainer<T> {
                       private T value;
                       
                       public void set(T value) { this.value = value; }
                       public T get() { return value; }
                   }
                   
                   // Usage:
                   GenericContainer<String> stringContainer = new GenericContainer<>();
                   stringContainer.set("Hello");
                   String value = stringContainer.get(); // No cast needed
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic repository pattern:
                   
                   public interface Repository<T, ID> {
                       T findById(ID id);
                       List<T> findAll();
                       T save(T entity);
                       void delete(T entity);
                   }
                   
                   public class UserRepository implements Repository<User, Long> {
                       // Implementation for User entity with Long ID
                   }
               
               INTERVIEW QUESTION:
                   "How do generics improve type safety compared to raw types?
                   What happens at runtime with generic type information?"
               
               COMMON MISTAKES:
                   - Using raw types (List instead of List<String>)
                   - Not understanding that generics are compile-time only
                   - Confusing type parameters with type arguments
               
               PERFORMANCE & SCALABILITY:
                   - No runtime overhead (type erasure)
                   - Better performance than manual casting
                   - Type safety prevents runtime errors
               
               ALTERNATIVES:
                   - Raw types (pre-Java 5)
                   - Manual casting with instanceof checks
                   - Using Object base type
               
            ─────────────────────────────────────────────────────────────────────
            
            2. GENERIC METHODS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Generic methods have their own type parameters, independent of
               the class they're defined in.
               
               WHY IT EXISTS:
               - Type-safe utility methods
               - Works with any type without class-level generics
               - Better code reuse
               
               INTERNAL MECHANICS:
               - Type parameters declared before return type
               - Type inference determines actual type at call site
               - Type erasure applies to method type parameters
               
               SIMPLE EXAMPLE:
                   public class GenericUtils {
                       // Generic method
                       public static <T> T identity(T value) {
                           return value;
                       }
                       
                       // Generic method with bounds
                       public static <T extends Comparable<T>> T max(T a, T b) {
                           return a.compareTo(b) > 0 ? a : b;
                       }
                       
                       // Multiple type parameters
                       public static <K, V> Map.Entry<K, V> entry(K key, V value) {
                           return new AbstractMap.SimpleEntry<>(key, value);
                       }
                   }
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic pagination utility:
                   
                   public class PageUtils {
                       public static <T> Page<T> emptyPage() {
                           return new Page<>(Collections.emptyList(), 0, 0, 0);
                       }
                       
                       public static <T> Page<T> of(List<T> content, int page, int size, long total) {
                           return new Page<>(content, page, size, total);
                       }
                   }
               
               INTERVIEW QUESTION:
                   "What is the difference between generic class and generic method?
                   How does type inference work for generic methods?"
               
               COMMON MISTAKES:
                   - Not understanding type inference limitations
                   - Using generic methods when class-level generics suffice
                   - Forgetting to declare type parameters
               
            ─────────────────────────────────────────────────────────────────────
            
            3. BOUNDED TYPE PARAMETERS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Bounded type parameters restrict the types that can be used
               as type arguments, using extends or super clauses.
               
               WHY IT EXISTS:
               - Type safety with constraints
               - Access to specific methods/operations
               - Compile-time validation
               
               BOUNDS TYPES:
                   - T extends Class & Interface1 & Interface2 (multiple bounds)
                   - T super Class (only for wildcards)
                   - T extends Comparable<T> (recursive bound)
               
               SIMPLE EXAMPLE:
                   // Upper bound - T must extend Number
                   public class NumericBox<T extends Number> {
                       private T value;
                       
                       public double doubleValue() {
                           return value.doubleValue(); // Safe to call
                       }
                   }
                   
                   // Multiple bounds
                   public class BoundedExample<T extends Number & Comparable<T>> {
                       // T must be Number AND Comparable
                   }
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic service that requires entities to be identifiable:
                   
                   public interface Identifiable<ID> {
                       ID getId();
                   }
                   
                   public class EntityService<T extends Identifiable<Long> & Serializable> {
                       public void process(T entity) {
                           Long id = entity.getId(); // Safe to call
                           // Can serialize entity
                       }
                   }
               
               INTERVIEW QUESTION:
                   "What is the difference between extends and super in bounds?
                   Can you have multiple bounds? What is the order?"
               
               COMMON MISTAKES:
                   - Confusing extends with implements
                   - Not understanding that bounds are ANDed together
                   - Using bounds when not necessary
               
            ─────────────────────────────────────────────────────────────────────
            
            4. WILDCARDS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Wildcards (?) allow flexibility in generic type arguments,
               enabling covariance and contravariance.
               
               WHY IT EXISTS:
               - Flexible API design
               - Producer-extends, consumer-super pattern
               - Type-safe heterogeneous collections
               
               WILDCARD TYPES:
                   - Unbounded: List<?> (unknown type)
                   - Upper bounded: List<? extends Number> (producer)
                   - Lower bounded: List<? super Integer> (consumer)
               
               SIMPLE EXAMPLE:
                   // Unbounded wildcard
                   public static void printList(List<?> list) {
                       for (Object item : list) {
                           System.out.println(item);
                       }
                   }
                   
                   // Upper bounded wildcard (producer)
                   public static double sum(List<? extends Number> numbers) {
                       return numbers.stream().mapToDouble(Number::doubleValue).sum();
                   }
                   
                   // Lower bounded wildcard (consumer)
                   public static void addIntegers(List<? super Integer> list) {
                       list.add(1);
                       list.add(2);
                   }
               
               REAL-WORLD BACKEND EXAMPLE:
                   A flexible event handler:
                   
                   public class EventProcessor {
                       // Accept any event type
                       public void process(List<? extends Event> events) {
                           events.forEach(this::handle);
                       }
                       
                       // Add any event type to a list
                       public void collect(List<? super Event> destination, Event event) {
                           destination.add(event);
                       }
                   }
               
               INTERVIEW QUESTION:
                   "What is PECS (Producer Extends, Consumer Super)?
                   When would you use each wildcard type?"
               
               COMMON MISTAKES:
                   - Using ? when specific type is needed
                   - Not understanding capture conversion
                   - Confusing extends and super
               
            ─────────────────────────────────────────────────────────────────────
            
            5. TYPE ERASURE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Type erasure removes generic type information at compile time,
               replacing type parameters with their bounds or Object.
               
               WHY IT EXISTS:
               - Backward compatibility with pre-generic code
               - No runtime overhead
               - JVM doesn't need to understand generics
               
               INTERNAL MECHANICS:
                   - Unbounded types become Object
                   - Bounded types become their upper bound
                   - Casts are inserted where needed
                   - Bridge methods are generated for overriding
                   - Generic signatures stored in Signature attribute
               
               SIMPLE EXAMPLE:
                   // Before erasure:
                   public class Box<T> {
                       private T value;
                       public T get() { return value; }
                       public void set(T value) { this.value = value; }
                   }
                   
                   // After erasure:
                   public class Box {
                       private Object value;
                       public Object get() { return value; }
                       public void set(Object value) { this.value = value; }
                   }
               
               REAL-WORLD BACKEND EXAMPLE:
                   Understanding type erasure helps avoid:
                   
                   public class TypeErasureTrap {
                       // This doesn't work as expected!
                       public void checkType(List<String> strings, List<Integer> ints) {
                           // Both lists are List<Object> at runtime!
                           System.out.println(strings.getClass() == ints.getClass()); // true!
                       }
                   }
               
               INTERVIEW QUESTION:
                   "What is type erasure? How does it affect runtime behavior?
                   Why can't you create generic arrays?"
               
               COMMON MISTAKES:
                   - Not understanding that instanceof doesn't work with generics
                   - Trying to create generic arrays
                   - Assuming runtime type information exists
               
            ─────────────────────────────────────────────────────────────────────
            
            6. BRIDGE METHODS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Bridge methods are synthetic methods generated by the compiler
               to maintain polymorphism with generic types.
               
               WHY IT EXISTS:
               - Type erasure compatibility
               - Override relationship preservation
               - Binary compatibility
               
               INTERNAL MECHANICS:
                   - Generated when extending generic classes
                     - Marked as synthetic in bytecode
                     - Delegates to actual generic method
               
               SIMPLE EXAMPLE:
                   public class IntegerBox extends Box<Integer> {
                       // Compiler generates:
                       // public Object get() { return this.get(); }
                       // This is the bridge method
                   }
               
               REAL-WORLD BACKEND EXAMPLE:
                   When implementing generic interfaces:
                   
                   public class UserComparator implements Comparator<User> {
                       @Override
                       public int compare(User a, User b) {
                           return a.getId().compareTo(b.getId());
                       }
                       // Compiler generates bridge method:
                       // public int compare(Object a, Object b) {
                       //     return compare((User) a, (User) b);
                       // }
                   }
               
               INTERVIEW QUESTION:
                   "What are bridge methods? When are they generated?
                   How can you see them in bytecode?"
               
               COMMON MISTAKES:
                   - Not understanding why bridge methods exist
                   - Confusing with regular overridden methods
               
            ─────────────────────────────────────────────────────────────────────
            
            7. RECURSIVE GENERICS (F-BOUNDED TYPES)
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               F-bounded types use recursive generic bounds to enable
               fluent APIs and type-safe self-referential methods.
               
               WHY IT EXISTS:
               - Type-safe method chaining
               - Fluent API design
               - Compile-time type checking
               
               SIMPLE EXAMPLE:
                   public class Calculator<T extends Calculator<T>> {
                       private int value;
                       
                       @SuppressWarnings("unchecked")
                       public T add(int x) {
                           value += x;
                           return (T) this;
                       }
                       
                       public int getValue() { return value; }
                   }
                   
                   public class AdvancedCalculator extends Calculator<AdvancedCalculator> {
                       public AdvancedCalculator multiply(int x) {
                           value *= x;
                           return this;
                       }
                   }
                   
                   // Usage:
                   AdvancedCalculator calc = new AdvancedCalculator()
                       .add(5)
                       .multiply(2);
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic builder pattern:
                   
                   public abstract class Builder<T extends Builder<T>> {
                       public abstract T self();
                       public abstract void build();
                   }
                   
                   public class UserBuilder extends Builder<UserBuilder> {
                       @Override
                       public UserBuilder self() { return this; }
                       @Override
                       public void build() { /* create user */ }
                   }
               
               INTERVIEW QUESTION:
                   "What is the Curiously Recurring Template Pattern (CRTP)?
                   How does it relate to F-bounded types in Java?"
               
               COMMON MISTAKES:
                   - Overcomplicating with unnecessary bounds
                   - Not understanding the type safety benefits
               
            ─────────────────────────────────────────────────────────────────────
            
            8. VARIANCE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Variance describes how subtyping between complex types relates to
               subtyping between their component types.
               
               WHY IT EXISTS:
               - Type system flexibility
               - Safe substitution
               - API design patterns
               
               VARIANCE TYPES:
                   - Covariant: A<T> <: A<S> if T <: S (preserves ordering)
                   - Contravariant: A<T> :> A<S> if T <: S (reverses ordering)
                   - Invariant: A<T> and A<S> are unrelated (default in Java)
               
               SIMPLE EXAMPLE:
                   // Arrays are covariant (can cause runtime errors)
                   Object[] objects = new String[10]; // OK
                   objects[0] = Integer.valueOf(1); // ArrayStoreException!
                   
                   // Generics are invariant by default
                   List<Object> objects2 = new ArrayList<String>(); // Compile error!
                   
                   // Wildcards provide covariance/contravariance
                   List<? extends Object> objects3 = new ArrayList<String>(); // OK
                   List<? super String> strings = new ArrayList<Object>(); // OK
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic event bus:
                   
                   public class EventBus {
                       // Covariant handler - can handle any subtype
                       private final Map<Class<? extends Event>, List<EventHandler<? super Event>>> handlers;
                   }
               
               INTERVIEW QUESTION:
                   "Why are Java generics invariant by default?
                   What is the difference between array covariance and generic invariance?"
               
               COMMON MISTAKES:
                   - Not understanding why invariance is safer
                   - Confusing variance with casting
               
            ─────────────────────────────────────────────────────────────────────
            
            9. GENERIC ARRAY CREATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Creating arrays of generic types is restricted due to type erasure.
               
               WHY IT EXISTS:
               - Type safety at runtime
               - Preventing ArrayStoreException
               
               RESTRICTIONS:
                   - new T[10] - not allowed
                   - new T[][] - not allowed
                   - Generic array creation in variable initializer - not allowed
               
               WORKAROUNDS:
                   - Use @SuppressWarnings("unchecked") with explicit cast
                   - Use List<T> instead of T[]
                   - Pass array type as parameter
               
               SIMPLE EXAMPLE:
                   @SuppressWarnings("unchecked")
                   public <T> T[] createArray(Class<T> type, int size) {
                       return (T[]) java.lang.reflect.Array.newInstance(type, size);
                   }
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic cache with array backing:
                   
                   public class ArrayCache<T> {
                       private final T[] elements;
                       
                       @SuppressWarnings("unchecked")
                       public ArrayCache(Class<T> type, int size) {
                           this.elements = (T[]) Array.newInstance(type, size);
                       }
                   }
               
               INTERVIEW QUESTION:
                   "Why can't you create generic arrays?
                   What are the workarounds and their trade-offs?"
               
               COMMON MISTAKES:
                   - Trying to create generic arrays directly
                   - Not understanding the runtime implications
               
            ─────────────────────────────────────────────────────────────────────
            
            10. REFLECTION WITH GENERICS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Generic type information is available at runtime through reflection
               via the Signature attribute.
               
               WHY IT EXISTS:
               - Framework development
               - Serialization libraries
               - Dependency injection frameworks
               
               INTERNAL MECHANICS:
                   - GenericSignatureFormatSignature attribute in bytecode
                   - ParameterizedType for generic types
                   - TypeVariable for type parameters
                   - GenericArrayType for generic arrays
               
               SIMPLE EXAMPLE:
                   public class ReflectionExample {
                       public void inspectGenericType() {
                           Type superclass = this.getClass().getGenericSuperclass();
                           if (superclass instanceof ParameterizedType pt) {
                               Type[] typeArgs = pt.getActualTypeArguments();
                               System.out.println("Type argument: " + typeArgs[0]);
                           }
                       }
                   }
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic repository factory:
                   
                   public class RepositoryFactory {
                       @SuppressWarnings("unchecked")
                       public <T> Repository<T> create(Class<? extends Repository<T>> repoClass) {
                           // Extract entity type from generic interface
                           Type[] interfaces = repoClass.getGenericInterfaces();
                           for (Type iface : interfaces) {
                               if (iface instanceof ParameterizedType pt && 
                                   pt.getRawType() == Repository.class) {
                                   Type entityType = pt.getActualTypeArguments()[0];
                                   // Use entityType for configuration
                               }
                           }
                           return repoClass.getDeclaredConstructor().newInstance();
                       }
                   }
               
               INTERVIEW QUESTION:
                   "How can you get generic type information at runtime?
                   What are the limitations?"
               
               COMMON MISTAKES:
                   - Not understanding that type info is limited
                   - Assuming full type information is available
               
            ─────────────────────────────────────────────────────────────────────
            
            11. GENERIC LAMBDAS AND FUNCTIONAL INTERFACES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Generic functional interfaces enable type-safe higher-order functions.
               
               WHY IT EXISTS:
               - Type-safe function composition
               - Generic stream operations
               - Functional programming patterns
               
               SIMPLE EXAMPLE:
                   // Generic function
                   Function<String, Integer> parser = Integer::parseInt;
                   
                   // Generic predicate
                   Predicate<List<String>> isEmpty = List::isEmpty;
                   
                   // Generic bi-function
                   BiFunction<String, Integer, String> repeater = (s, n) -> s.repeat(n);
               
               REAL-WORLD BACKEND EXAMPLE:
                   A generic validation framework:
                   
                   public class Validator<T> {
                       private final List<Predicate<T>> rules = new ArrayList<>();
                       
                       public Validator<T> addRule(Predicate<T> rule) {
                           rules.add(rule);
                           return this;
                       }
                       
                       public boolean validate(T item) {
                           return rules.stream().allMatch(p -> p.test(item));
                       }
                   }
               
               INTERVIEW QUESTION:
                   "How do generics work with lambda expressions?
                   What is the target type for generic lambdas?"
               
               COMMON MISTAKES:
                   - Not understanding type inference with lambdas
                   - Confusing generic functional interfaces
               
            ─────────────────────────────────────────────────────────────────────
            
            12. COMMON INTERVIEW QUESTIONS
               ─────────────────────────────────────────────────────────────────────
               Q1: "What is the difference between List<T> and List<?>"?
               A1: List<T> is a specific type, List<?> is an unknown type.
                   You can't add to List<?> (except null), but you can read Object.
               
               Q2: "Can you use generics with primitives?"
               A2: No, generics work with reference types only.
                   Use wrapper types (Integer, Double) or specialized libraries.
               
               Q3: "What is the difference between <? super T> and <? extends T>"?
               A3: <? extends T> is for producers (read), <? super T> is for consumers (write).
                   PECS: Producer Extends, Consumer Super.
               
               Q4: "How do you create a generic singleton?"
               A4: You can't have a true generic singleton. Use enum with constant
                   for each type, or use a factory pattern.
               
               Q5: "What are the limitations of generics in Java?"
               A5: - Cannot create generic arrays
                   - Cannot use primitives as type parameters
                   - Cannot use static fields with type parameters
                   - Cannot use instanceof with generic types
                   - Type parameters cannot be used in static context
               
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Generics are essential for:
            - Type-safe collections and APIs
            - Eliminating ClassCastException
            - Better code documentation
            - Framework development
            - Functional programming patterns
            """);
    }
}