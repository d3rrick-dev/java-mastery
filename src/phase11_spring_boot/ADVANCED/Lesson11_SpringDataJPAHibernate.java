package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Data JPA & Hibernate (Advanced)
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Entity lifecycle
 * 2. Persistence context
 * 3. Dirty checking
 * 4. First-level cache
 * 5. Second-level cache
 * 6. Lazy vs eager loading
 * 7. N+1 problem
 * 8. Fetch strategies
 * 9. JPQL
 * 10. Criteria API
 * 11. Specifications
 * 12. Query optimization
 * 13. Database indexing considerations
 */

public class Lesson11_SpringDataJPAHibernate {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING DATA JPA & HIBERNATE (ADVANCED) ===
            
            1. ENTITY LIFECYCLE
               ─────────────────────────────────────────────────────────────────────
               LIFECYCLE STATES:
                   - New (Transient): Not yet persisted
                   - Managed (Persistent): In persistence context
                   - Detached: Was managed, now closed context
                   - Removed: Scheduled for deletion
            
               WHY IT EXISTS:
               - Automatic dirty checking
               - Lazy loading support
               - Optimistic locking
            
               INTERNAL MECHANICS:
                   - EntityManager manages entity state
                   - Persistence context holds managed entities
                   - Hibernate Session implements EntityManager
                   - Flush mode controls when changes are written
            
               SIMPLE EXAMPLE:
                   @Entity
                   public class User {
                       @Id
                       @GeneratedValue
                       private Long id;
                       private String name;
                       // Transient state until persist()
                   }
                   
                   @Service
                   public class UserService {
                       @PersistenceContext
                       private EntityManager em;
                       
                       public User createUser(User user) {
                           em.persist(user); // Now managed
                           return user; // Still managed
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user registration flow:
                   - User object created (transient)
                   - Persisted to database (managed)
                   - Transaction commits, entity becomes detached
                   - Returned to client (detached)
            
               INTERVIEW QUESTION:
                   "Explain the difference between transient, managed,
                   and detached entity states. When does Hibernate
                   synchronize changes to the database?"
            
               COMMON MISTAKES:
                   - Not understanding lazy loading in detached state
                   - Modifying detached entities (no effect)
                   - Not handling LazyInitializationException
            
               PERFORMANCE & SCALABILITY:
                   - Large persistence context affects memory
                   - Consider clear() for batch operations
                   - Use appropriate flush mode
            
            ─────────────────────────────────────────────────────────────────────
            
            2. PERSISTENCE CONTEXT & DIRTY CHECKING
               ─────────────────────────────────────────────────────────────────────
               PERSISTENCE CONTEXT:
                   - First-level cache (Session-scoped)
                   - Holds managed entities
                   - Ensures identity consistency
                   - Automatic dirty checking
            
               DIRTY CHECKING:
                   - Hibernate tracks entity changes
                   - On flush, generates UPDATE statements
                   - No explicit update needed
            
               INTERNAL MECHANICS:
                   - PersistenceContext holds entity snapshots
                   - DirtyCheckingVisitor compares states
                   - FlushEntityEventListener handles updates
            
               SIMPLE EXAMPLE:
                   @Service
                   @Transactional
                   public class UserService {
                       public void updateUser(Long id, String newName) {
                           User user = userRepository.findById(id);
                           user.setName(newName); // Dirty checking detects change
                           // No need to call save() - flush will update
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A batch update service:
                   - Loads 10,000 entities
                   - Modifies each entity
                   - Hibernate generates 10,000 UPDATE statements
                   - Consider stateless session for performance
            
               INTERVIEW QUESTION:
                   "How does Hibernate's dirty checking work?
                   What is the performance impact of large persistence contexts?"
            
               COMMON MISTAKES:
                   - Not understanding automatic dirty checking
                   - Large persistence context causing memory issues
                   - Not calling flush() when needed
            
               PERFORMANCE & SCALABILITY:
                   - Dirty checking adds overhead
                   - Consider @SelectBeforeUpdate
                   - Use stateless session for bulk operations
            
            ─────────────────────────────────────────────────────────────────────
            
            3. FIRST-LEVEL & SECOND-LEVEL CACHE
               ─────────────────────────────────────────────────────────────────────
               FIRST-LEVEL CACHE:
                   - Session-scoped (always enabled)
                   - Prevents duplicate queries in same session
                   - No configuration needed
            
               SECOND-LEVEL CACHE:
                   - SessionFactory-scoped (optional)
                   - Shared across sessions
                   - Requires cache provider (Redis, Caffeine)
            
               SIMPLE EXAMPLE:
                   @Entity
                   @Cacheable
                   @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
                   public class Product {
                       @Id
                       private Long id;
                       private String name;
                   }
                   
                   // Configuration
                   @Bean
                   public CacheManager cacheManager() {
                       return new JCacheManagerAccessor()
                           .getCacheManager("my-cache");
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A product catalog:
                   - First-level cache for request-scoped queries
                   - Second-level cache for frequently accessed products
                   - Cache invalidation on product updates
            
               INTERVIEW QUESTION:
                   "How does Hibernate's second-level cache work?
                   What cache concurrency strategies are available?"
            
               COMMON MISTAKES:
                   - Not configuring cache provider
                   - Wrong cache strategy
                   - Not handling cache invalidation
            
               PERFORMANCE & SCALABILITY:
                   - Second-level cache reduces DB load
                   - Consider cache size and eviction
                   - Monitor cache hit ratio
            
            ─────────────────────────────────────────────────────────────────────
            
            4. LAZY VS EAGER LOADING & N+1 PROBLEM
               ─────────────────────────────────────────────────────────────────────
               LAZY LOADING:
                   - Related entities loaded on access
                   - Proxy objects
                   - Prevents unnecessary queries
            
               EAGER LOADING:
                   - Related entities loaded immediately
                   - JOIN or separate SELECT
                   - Can cause cartesian product
            
               N+1 PROBLEM:
                   - 1 query to load parent entities
                   - N queries to load children
                   - Performance killer
            
               SIMPLE EXAMPLE:
                   // N+1 problem
                   @Entity
                   public class Order {
                       @OneToMany(fetch = FetchType.LAZY)
                       private List<OrderItem> items;
                   }
                   
                   // Solution: JOIN FETCH
                   @Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = :id")
                   Order findByIdWithItems(@Param("id") Long id);
                   
                   // Or @EntityGraph
                   @EntityGraph(attributePaths = "items")
                   List<Order> findByCustomerId(Long customerId);
            
               REAL-WORLD BACKEND EXAMPLE:
                   An order history API:
                   - Without optimization: 1 + N queries
                   - With JOIN FETCH: 1 query with JOIN
                   - With @EntityGraph: Clean code, optimized query
            
               INTERVIEW QUESTION:
                   "How do you identify and fix the N+1 problem?
                   What is the difference between JOIN FETCH and @EntityGraph?"
            
               COMMON MISTAKES:
                   - Not understanding lazy loading
                   - Eager fetching everything
                   - Not using batch fetching
            
               PERFORMANCE & SCALABILITY:
                   - Use lazy loading by default
                   - Consider @BatchSize
                   - Monitor query count
            
            ─────────────────────────────────────────────────────────────────────
            
            5. FETCH STRATEGIES
               ─────────────────────────────────────────────────────────────────────
               STRATEGIES:
                   - FetchType.LAZY: Load on access
                   - FetchType.EAGER: Load immediately
                   - @BatchSize: Batch loading
                   - @Fetch(FetchMode.SUBSELECT): Subselect loading
                   - @Fetch(FetchMode.JOIN): Join loading
            
               SIMPLE EXAMPLE:
                   @Entity
                   public class Author {
                       @OneToMany
                       @BatchSize(size = 10)
                       private List<Book> books;
                   }
                   
                   // Or in query
                   @Query("SELECT a FROM Author a WHERE a.id IN :ids")
                   @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
                   List<Author> findByIds(@Param("ids") List<Long> ids);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A library system:
                   - Authors with many books
                   - Use @BatchSize to load books in batches
                   - Avoid cartesian product with JOIN
            
               INTERVIEW QUESTION:
                   "How do you optimize fetch strategies for a
                   one-to-many relationship? What is the impact
                   of cartesian product?"
            
               COMMON MISTAKES:
                   - Not understanding fetch mode
                   - Wrong batch size
                   - Not testing with realistic data
            
               PERFORMANCE & SCALABILITY:
                   - Monitor query execution plans
                   - Use appropriate fetch size
                   - Consider pagination
            
            ─────────────────────────────────────────────────────────────────────
            
            6. JPQL & CRITERIA API
               ─────────────────────────────────────────────────────────────────────
               JPQL:
                   - Object-oriented query language
                   - Similar to SQL but uses entities
                   - Type-safe with IDE support
            
               CRITERIA API:
                   - Type-safe query building
                   - Programmatic query construction
                   - Dynamic query building
            
               SIMPLE EXAMPLE:
                   // JPQL
                   @Query("SELECT u FROM User u WHERE u.age > :age AND u.active = true")
                   List<User> findActiveUsersOlderThan(@Param("age") int age);
                   
                   // Criteria API
                   public List<User> findUsers(UserSearchCriteria criteria) {
                       CriteriaBuilder cb = em.getCriteriaBuilder();
                       CriteriaQuery<User> query = cb.createQuery(User.class);
                       Root<User> root = query.from(User.class);
                       
                       List<Predicate> predicates = new ArrayList<>();
                       if (criteria.getName() != null) {
                           predicates.add(cb.like(root.get("name"), "%" + criteria.getName() + "%"));
                       }
                       if (criteria.getMinAge() != null) {
                           predicates.add(cb.greaterThanOrEqualTo(root.get("age"), criteria.getMinAge()));
                       }
                       
                       query.where(predicates.toArray(new Predicate[0]));
                       return em.createQuery(query).getResultList();
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A search API:
                   - Dynamic filters based on user input
                   - Criteria API builds query dynamically
                   - Specifications for reusable predicates
            
               INTERVIEW QUESTION:
                   "When would you use Criteria API over JPQL?
                   How do you handle dynamic queries?"
            
               COMMON MISTAKES:
                   - Not using parameters (SQL injection)
                   - Complex JPQL hard to maintain
                   - Not testing query performance
            
               PERFORMANCE & SCALABILITY:
                   - Use query plan cache
                   - Consider pagination
                   - Monitor slow queries
            
            ─────────────────────────────────────────────────────────────────────
            
            7. SPECIFICATIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Specifications provide type-safe, reusable query predicates
               that can be combined for complex queries.
            
               WHY IT EXISTS:
               - Composable query predicates
               - Type-safe query building
               - Reusable across repositories
            
               INTERNAL MECHANICS:
                   - JpaSpecificationExecutor provides specification support
                   - Specification uses CriteriaBuilder
                   - Predicates can be combined with and/or
            
               SIMPLE EXAMPLE:
                   public class UserSpecifications {
                       public static Specification<User> hasName(String name) {
                           return (root, query, cb) -> 
                               cb.like(root.get("name"), "%" + name + "%");
                       }
                       
                       public static Specification<User> hasAgeGreaterThan(int age) {
                           return (root, query, cb) -> 
                               cb.greaterThan(root.get("age"), age);
                       }
                   }
                   
                   @Repository
                   public interface UserRepository extends JpaRepository<User, Long>,
                                                           JpaSpecificationExecutor<User> {
                   }
                   
                   // Usage
                   userRepository.findAll(
                       Specification.where(UserSpecifications.hasName("John"))
                           .and(UserSpecifications.hasAgeGreaterThan(18))
                   );
            
               REAL-WORLD BACKEND EXAMPLE:
                   An admin panel with complex filters:
                   - Name, email, status, date range, role
                   - Each filter is a Specification
                   - Combine based on user input
                   - Type-safe and maintainable
            
               INTERVIEW QUESTION:
                   "How do you implement dynamic queries with Spring Data JPA?
                   What is the advantage of Specifications over @Query?"
            
               COMMON MISTAKES:
                   - Not handling null parameters
                   - Complex specifications hard to test
                   - Not considering query performance
            
            ─────────────────────────────────────────────────────────────────────
            
            8. QUERY OPTIMIZATION & INDEXING
               ─────────────────────────────────────────────────────────────────────
               OPTIMIZATION TECHNIQUES:
                   - Use EXPLAIN to analyze queries
                   - Add database indexes
                   - Use pagination
                   - Consider read replicas
                   - Use projections for partial data
            
               INDEXING CONSIDERATIONS:
                   - Index frequently queried columns
                   - Composite indexes for multi-column queries
                   - Consider index size vs query speed
                   - Monitor index usage
            
               SIMPLE EXAMPLE:
                   // Projection for partial data
                   public interface UserSummary {
                       String getName();
                       String getEmail();
                   }
                   
                   @Query("SELECT u.name as name, u.email as email FROM User u WHERE u.active = true")
                   List<UserSummary> findActiveUserSummaries();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A user search API:
                   - Index on name, email, status columns
                   - Use projections to avoid loading full entities
                   - Pagination to limit result set
                   - Read replica for heavy queries
            
               INTERVIEW QUESTION:
                   "How do you optimize a slow JPA query?
                   What database indexes would you add for a user search?"
            
               COMMON MISTAKES:
                   - Not analyzing query execution plans
                   - Missing indexes on foreign keys
                   - Loading unnecessary data
            
               PERFORMANCE & SCALABILITY:
                   - Monitor slow query log
                   - Use connection pooling
                   - Consider read replicas
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Data JPA & Hibernate mastery is essential for:
            - Performance optimization
            - Query tuning
            - Scalability
            - Data consistency
            """);
    }
}