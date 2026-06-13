package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Testing (Advanced)
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Unit testing
 * 2. Integration testing
 * 3. Slice testing
 * 4. MockMvc
 * 5. Testcontainers
 * 6. Contract testing
 * 7. Mockito best practices
 */

public class Lesson16_TestingAdvanced {
    public static void main(String[] args) {
        System.out.println("""
            === TESTING (ADVANCED) ===
            
            1. UNIT TESTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Unit tests verify individual components in isolation,
               mocking dependencies.
            
               WHY IT EXISTS:
               - Fast feedback
               - Easy debugging
               - Documentation
            
               INTERNAL MECHANICS:
                   - Mockito creates mock objects
                   - Mock behavior defined with when/thenReturn
                   - Verification ensures interactions
                   - ArgumentCaptor captures arguments
            
               SIMPLE EXAMPLE:
                   @ExtendWith(MockitoExtension.class)
                   class UserServiceTest {
                       @Mock
                       private UserRepository userRepository;
                       
                       @InjectMocks
                       private UserService userService;
                       
                       @Test
                       void shouldCreateUser() {
                           User user = new User("John", "john@example.com");
                           when(userRepository.save(user)).thenReturn(user);
                           
                           User result = userService.createUser(user);
                           
                           assertThat(result).isNotNull();
                           verify(userRepository).save(user);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A service with multiple dependencies:
                   - Mock database repository
                   - Mock external API client
                   - Mock message publisher
                   - Verify all interactions
            
               INTERVIEW QUESTION:
                   "How do you mock a final class in Mockito?
                   What is the difference between @Mock and @Spy?"
            
               COMMON MISTAKES:
                   - Over-mocking
                   - Not verifying important interactions
                   - Mocking implementation details
            
               PERFORMANCE & SCALABILITY:
                   - Unit tests are fast
                   - Run in parallel
                   - Consider test data builders
            
            ─────────────────────────────────────────────────────────────────────
            
            2. INTEGRATION TESTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Integration tests verify multiple components work
               together, often with real dependencies.
            
               WHY IT EXISTS:
               - Verify component integration
               - Catch configuration issues
               - Test real behavior
            
               INTERNAL MECHANICS:
                   - @SpringBootTest loads full context
                   - TestEntityManager for JPA tests
                   - Embedded databases for isolation
                   - Real HTTP calls with MockMvc
            
               SIMPLE EXAMPLE:
                   @SpringBootTest
                   @AutoConfigureMockMvc
                   @TestPropertySource(properties = {
                       "spring.datasource.url=jdbc:h2:mem:testdb"
                   })
                   class UserControllerIntegrationTest {
                       @Autowired
                       private MockMvc mockMvc;
                       
                       @Autowired
                       private UserRepository userRepository;
                       
                       @Test
                       void shouldCreateUser() throws Exception {
                           mockMvc.perform(post("/api/users")
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content("{\"name\":\"John\",\"email\":\"john@example.com\"}"))
                               .andExpect(status().isCreated())
                               .andExpect(jsonPath("$.name").value("John"));
                           
                           assertThat(userRepository.count()).isEqualTo(1);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A full API test:
                   - Testcontainers for real database
                   - Mock external services
                   - Test security
                   - Test validation
            
               INTERVIEW QUESTION:
                   "How do you test a Spring Boot application with
                   a real database? What is the difference between
                   @SpringBootTest and @WebMvcTest?"
            
               COMMON MISTAKES:
                   - Slow tests (use @DataJpaTest for repository tests)
                   - Not cleaning up test data
                   - Not testing error scenarios
            
               PERFORMANCE & SCALABILITY:
                   - Integration tests are slower
                   - Use parallel execution
                   - Consider test data management
            
            ─────────────────────────────────────────────────────────────────────
            
            3. SLICE TESTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Slice tests focus on specific layers, loading only
               necessary beans for faster execution.
            
               WHY IT EXISTS:
               - Faster than full context
               - Focused testing
               - Layer isolation
            
               SLICE ANNOTATIONS:
                   - @WebMvcTest: MVC layer only
                   - @DataJpaTest: JPA layer only
                   - @JsonTest: JSON serialization
                   - @RestClientTest: REST clients
                   - @SpringBootTest(classes = ...): Custom slice
            
               SIMPLE EXAMPLE:
                   @WebMvcTest(UserController.class)
                   class UserControllerTest {
                       @Autowired
                       private MockMvc mockMvc;
                       
                       @MockBean
                       private UserService userService;
                       
                       @Test
                       void shouldReturnUser() throws Exception {
                           when(userService.findById(1L))
                               .thenReturn(new User(1L, "John"));
                           
                           mockMvc.perform(get("/api/users/1"))
                               .andExpect(status().isOk())
                               .andExpect(jsonPath("$.name").value("John"));
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A well-structured test suite:
                   - @WebMvcTest for controllers
                   - @DataJpaTest for repositories
                   - @JsonTest for DTOs
                   - @SpringBootTest for end-to-end
            
               INTERVIEW QUESTION:
                   "When would you use @WebMvcTest vs @SpringBootTest?
                   How do you mock beans in slice tests?"
            
               COMMON MISTAKES:
                   - Not understanding slice scope
                   - Missing @MockBean for dependencies
                   - Not testing security in @WebMvcTest
            
               PERFORMANCE & SCALABILITY:
                   - Slice tests are fast
                   - Run in parallel
                   - Consider test execution time
            
            ─────────────────────────────────────────────────────────────────────
            
            4. MOCKMVC
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               MockMvc provides a testing framework for Spring MVC,
               allowing HTTP request simulation without server.
            
               WHY IT EXISTS:
               - Test MVC without server
               - Verify request/response
               - Test security
               - Test filters
            
               INTERNAL MECHANICS:
                   - MockMvcBuilders creates MockMvc
                   - StandaloneSetup for specific controllers
                   - WebAppContext for full context
                   - ResultActions for assertions
            
               SIMPLE EXAMPLE:
                   @AutoConfigureMockMvc
                   @SpringBootTest
                   class UserControllerTest {
                       @Autowired
                       private MockMvc mockMvc;
                       
                       @Test
                       void shouldCreateUser() throws Exception {
                           mockMvc.perform(post("/api/users")
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content("{\"name\":\"John\"}")
                                   .header("Authorization", "Bearer token"))
                               .andExpect(status().isCreated())
                               .andExpect(header().exists("Location"))
                               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A comprehensive API test:
                   - Test all endpoints
                   - Test authentication/authorization
                   - Test validation errors
                   - Test pagination
            
               INTERVIEW QUESTION:
                   "How do you test file upload with MockMvc?
                   What is the difference between mockMvc and restTemplate?"
            
               COMMON MISTAKES:
                   - Not testing error scenarios
                   - Not testing security
                   - Not using proper content types
            
               PERFORMANCE & SCALABILITY:
                   - MockMvc is fast
                   - Consider test parallelization
                   - Use shared MockMvc instance
            
            ─────────────────────────────────────────────────────────────────────
            
            5. TESTCONTAINERS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Testcontainers provides lightweight, throwaway
               containers for integration testing.
            
               WHY IT EXISTS:
               - Real dependencies in tests
               - Consistent test environment
               - No local setup required
            
               INTERNAL MECHANICS:
                   - GenericContainer manages containers
                   - Docker required for running
                   - @Container annotation for lifecycle
                   - Wait strategies for readiness
            
               SIMPLE EXAMPLE:
                   @SpringBootTest
                   @Testcontainers
                   class UserRepositoryTest {
                       @Container
                       static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
                           .withDatabaseName("testdb")
                           .withUsername("test")
                           .withPassword("test");
                       
                       @DynamicPropertySource
                       static void configureProperties(DynamicPropertyRegistry registry) {
                           registry.add("spring.datasource.url", postgres::getJdbcUrl);
                           registry.add("spring.datasource.username", postgres::getUsername);
                           registry.add("spring.datasource.password", postgres::getPassword);
                       }
                       
                       @Autowired
                       private UserRepository userRepository;
                       
                       @Test
                       void shouldSaveUser() {
                           User user = new User("John", "john@example.com");
                           User saved = userRepository.save(user);
                           assertThat(saved.getId()).isNotNull();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A comprehensive test environment:
                   - PostgreSQL container
                   - Redis container
                   - Kafka container
                   - All tests run against real services
            
               INTERVIEW QUESTION:
                   "How do you speed up Testcontainers tests?
                   What is the difference between @Container and
                   @Testcontainers?"
            
               COMMON MISTAKES:
                   - Not using static containers
                   - Not waiting for container readiness
                   - Not cleaning up test data
            
               PERFORMANCE & SCALABILITY:
                   - Containers add startup time
                   - Use container reuse
                   - Consider parallel execution
            
            ─────────────────────────────────────────────────────────────────────
            
            6. CONTRACT TESTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Contract testing ensures service providers and consumers
               agree on API contracts.
            
               WHY IT EXISTS:
               - Prevent breaking changes
               - Consumer-driven contracts
               - Automated verification
            
               INTERNAL MECHANICS:
                   - Pact framework for contract testing
                   - Provider verifies contract
                   - Consumer defines expectations
                   - Pact broker stores contracts
            
               SIMPLE EXAMPLE:
                   // Consumer test
                   @ExtendWith(PactConsumerTestExt.class)
                   class UserServiceContractTest {
                       @Pact(provider = "user-service", consumer = "order-service")
                       public RequestResponsePact getUser(PactDslWithProvider builder) {
                           return builder
                               .given("user exists")
                               .uponReceiving("get user request")
                               .path("/users/1")
                               .method("GET")
                               .willRespondWith()
                               .status(200)
                               .body(new PactDslJsonBody()
                                   .integerType("id", 1)
                                   .stringType("name", "John"))
                               .toPact();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservices contract:
                   - Consumer defines expected API
                   - Provider verifies against contract
                   - CI/CD fails on contract mismatch
                   - Pact broker for contract storage
            
               INTERVIEW QUESTION:
                   "How do you implement contract testing?
                   What is the difference between provider and consumer tests?"
            
               COMMON MISTAKES:
                   - Not maintaining contracts
                   - Not testing edge cases
                   - Not versioning contracts
            
               PERFORMANCE & SCALABILITY:
                   - Contract tests are fast
                   - Run in CI pipeline
                   - Consider contract versioning
            
            ─────────────────────────────────────────────────────────────────────
            
            7. MOCKITO BEST PRACTICES
               ─────────────────────────────────────────────────────────────────────
               BEST PRACTICES:
                   - Use @ExtendWith(MockitoExtension.class)
                   - Verify only important interactions
                   - Use argument matchers appropriately
                   - Avoid mocking final classes (use mockito-inline)
                   - Use strict stubbing
                   - Reset mocks between tests
            
               SIMPLE EXAMPLE:
                   @ExtendWith(MockitoExtension.class)
                   class UserServiceTest {
                       @Mock
                       private UserRepository userRepository;
                       
                       @InjectMocks
                       private UserService userService;
                       
                       @Test
                       void shouldCreateUser() {
                           // Given
                           User user = new User("John");
                           when(userRepository.save(any(User.class)))
                               .thenAnswer(invocation -> {
                                   User u = invocation.getArgument(0);
                                   u.setId(1L);
                                   return u;
                               });
                           
                           // When
                           User result = userService.createUser(user);
                           
                           // Then
                           assertThat(result.getId()).isEqualTo(1L);
                           verify(userRepository).save(user);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A well-tested service:
                   - Unit tests for business logic
                   - Integration tests for data access
                   - Contract tests for API
                   - End-to-end tests for critical flows
            
               INTERVIEW QUESTION:
                   "How do you mock a void method in Mockito?
                   What is the difference between doThrow and thenThrow?"
            
               COMMON MISTAKES:
                   - Over-mocking
                   - Not using strict stubbing
                   - Mocking implementation details
            
               PERFORMANCE & SCALABILITY:
                   - Unit tests are fast
                   - Consider test data builders
                   - Run tests in parallel
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Testing is essential for:
            - Code quality
            - Regression prevention
            - Documentation
            - Confidence in changes
            """);
    }
}