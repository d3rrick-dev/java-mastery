package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Spring Data JPA
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson05_SpringDataJPA {
    public static void main(String[] args) {
        System.out.println("""
                === SPRING DATA JPA ===

                1. REPOSITORY INTERFACE
                     public interface UserRepository extends JpaRepository<User, Long> {
                         // Derived query methods
                         List<User> findByEmail(String email);
                         List<User> findByAgeGreaterThan(int age);
                         Optional<User> findById(Long id);
                     }

                2. ENTITY
                     @Entity
                     public class User {
                         @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                         private Long id;
                         private String name;
                         private String email;
                         @OneToMany(mappedBy = "user")
                         private List<Order> orders;
                     }

                3. QUERY METHODS
                   - findBy... (e.g., findByName)
                   - findBy...And... (e.g., findByNameAndEmail)
                   - findBy...Or... (e.g., findByNameOrEmail)
                   - findBy...GreaterThan, LessThan, Like, In, etc.
                   - @Query for custom JPQL/SQL

                4. PAGINATION & SORTING
                   Page<User> findByAgeGreaterThan(int age, Pageable pageable);
                   // Usage: PageRequest.of(0, 10, Sort.by("name"))

                BACKEND REAL-WORLD EXAMPLE:
                   - UserRepository with findByEmail for login
                   - OrderRepository with findByUserId for user orders
                   - ProductRepository with search by category and price range

                COMMON MISTAKES:
                   - N+1 query problem (use @EntityGraph or JOIN FETCH)
                   - Returning entities directly (use DTOs)
                   - Not using pagination for large datasets
                   - Lazy loading outside transaction (LazyInitializationException)
                   - Using @Transactional on read-only methods (unnecessary)
                """);
    }
}
