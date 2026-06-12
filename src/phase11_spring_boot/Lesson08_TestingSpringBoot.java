package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Testing Spring Boot Applications
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson08_TestingSpringBoot {
    public static void main(String[] args) {
        System.out.println("""
                === TESTING SPRING BOOT ===

                1. UNIT TESTING WITH MOCKITO
                     @ExtendWith(MockitoExtension.class)
                     class UserServiceTest {
                         @Mock
                         private UserRepository userRepository;

                         @InjectMocks
                         private UserService userService;

                         @Test
                         void shouldReturnUserWhenIdExists() {
                             when(userRepository.findById(1L))
                                 .thenReturn(Optional.of(new User(1L, "John")));
                             User user = userService.getUserById(1L);
                             assertEquals("John", user.getName());
                         }
                     }

                2. INTEGRATION TESTING
                     @SpringBootTest
                     @AutoConfigureMockMvc
                     class UserControllerIntegrationTest {
                         @Autowired
                         private MockMvc mockMvc;

                         @Test
                         void shouldReturnUser() throws Exception {
                             mockMvc.perform(get("/api/users/1")
                                     .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(status().isOk())
                                 .andExpect(jsonPath("$.name").value("John"));
                         }
                     }

                3. TEST CONTAINERS (Testcontainers)
                   - Spin up real database for integration tests
                   - @Container PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>();
                   - Tests run against real DB, not H2

                BACKEND REAL-WORLD EXAMPLE:
                   - Unit test: Service layer with mocked repository
                   - Integration test: Full API endpoint with test DB
                   - Contract test: Verify API contract with consumers

                COMMON MISTAKES:
                   - Testing implementation instead of behavior
                   - Not using @Mock and @InjectMocks correctly
                   - Integration tests that are too slow (use testcontainers wisely)
                   - Not testing edge cases and error scenarios
                   - Over-mocking (testing mocks, not real code)
                """);
    }
}
