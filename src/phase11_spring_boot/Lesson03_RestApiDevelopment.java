package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: REST API Development
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson03_RestApiDevelopment {
    public static void main(String[] args) {
        System.out.println("""
                === REST API DEVELOPMENT ===

                1. REST CONTROLLERS
                     @RestController
                     public class UserController {
                         @GetMapping("/users")
                         public List<User> getUsers() { ... }

                         @PostMapping("/users")
                         public User createUser(@RequestBody UserDTO dto) { ... }
                     }

                2. HTTP METHODS & STATUS CODES
                   - GET:   200 OK, 404 Not Found
                   - POST:  201 Created, 400 Bad Request
                   - PUT:   200 OK, 204 No Content
                   - DELETE: 204 No Content, 404 Not Found

                3. DTOs (Data Transfer Objects)
                   - Separate from Entity (don't expose DB schema)
                   - Used for request/response payloads
                   - Can use MapStruct for mapping

                4. SERVICE LAYER
                     @Service
                     public class UserService {
                         private final UserRepository repo;
                         public List<User> getAllUsers() {
                             return repo.findAll();
                         }
                     }

                5. VALIDATION
                     public record UserDTO(
                         @NotBlank String name,
                         @Email String email,
                         @Min(18) int age
                     ) {}

                BACKEND REAL-WORLD EXAMPLE:
                   - E-commerce API: /api/products, /api/orders
                   - Returns JSON, validates input, handles errors
                   - Uses DTOs to hide internal entity structure

                COMMON MISTAKES:
                   - Exposing entities directly (security risk)
                   - Not using proper HTTP status codes
                   - Putting business logic in controllers
                   - Not validating input (NullPointerException risks)
                   - Returning ResponseEntity for everything (overkill)
                """);
    }
}
