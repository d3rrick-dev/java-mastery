package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Caching Deep Dive
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Cache abstraction
 * 2. @Cacheable
 * 3. @CachePut
 * 4. @CacheEvict
 * 5. Redis integration
 * 6. Cache invalidation strategies
 * 7. Cache-aside pattern
 */

public class Lesson12_SpringCachingDeepDive {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING CACHING DEEP DIVE ===
            
            1. CACHE ABSTRACTION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Spring's cache abstraction provides a consistent API for
               different caching providers (Caffeine, Redis, EhCache).
            
               WHY IT EXISTS:
               - Provider-agnostic caching
               - Declarative caching with annotations
               - Easy switching between providers
            
               INTERNAL MECHANICS:
                   - CacheInterceptor intercepts @Cacheable methods
                   - CacheManager provides cache instances
                   - Cache abstraction uses key generation strategies
                   - CacheResolver resolves cache names
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableCaching
                   public class CacheConfig {
                       @Bean
                       public CacheManager cacheManager() {
                           return new ConcurrentMapCacheManager("users", "products");
                       }
                   }
                   
                   @Service
                   public class UserService {
                       @Cacheable("users")
                       public User findById(Long id) {
                           return userRepository.findById(id);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A product catalog service caches product details in Redis
                   with 1-hour TTL. Cache is invalidated when product
                   is updated, ensuring fresh data while reducing database load.
            
               INTERVIEW QUESTION:
                   "How does Spring's cache abstraction work internally?
                   What is the difference between @Cacheable and @CachePut?"
            
               COMMON MISTAKES:
                   - Forgetting @EnableCaching
                   - Not considering cache key generation
                   - Caching too much data
            
               PERFORMANCE & SCALABILITY:
                   - Cache hit ratio is critical
                   - Consider cache size limits
                   - Monitor cache eviction patterns
            
               ALTERNATIVES:
                   - Manual caching with CacheManager
                   - JCache (JSR-107)
                   - Direct Redis client
            
            ─────────────────────────────────────────────────────────────────────
            
            2. @CACHEABLE, @CACHEPUT, @CACHEEVICT
               ─────────────────────────────────────────────────────────────────────
               @CACHEABLE:
                   - Caches method result
                   - Skips method execution if key exists
                   - Key generated from method parameters
                   
                   @Cacheable(value = "users", key = "#id")
                   public User findById(Long id) { ... }
                   
                   @Cacheable(value = "users", key = "{#id, #projection}")
                   public User findById(Long id, Class<?> projection) { ... }
            
               @CACHEPUT:
                   - Always executes method
                   - Updates cache with result
                   - Useful for updates
                   
                   @CachePut(value = "users", key = "#user.id")
                   public User updateUser(User user) { ... }
            
               @CACHEEVICT:
                   - Removes entries from cache
                   - beforeInvocation: evict before method call
                   - allEntries: clear entire cache
                   
                   @CacheEvict(value = "users", key = "#id")
                   public void deleteUser(Long id) { ... }
                   
                   @CacheEvict(value = "users", allEntries = true)
                   public void evictAllUsers() { ... }
            
               KEY GENERATION:
                   - Default: SimpleKeyGenerator (params hash)
                   - SpEL: #id, #user.id, or custom key generator
                   - Custom KeyGenerator bean
            
               INTERVIEW QUESTION:
                   "When would you use @CacheEvict with allEntries=true?
                   How do you handle cache stampede?"
            
               COMMON MISTAKES:
                   - @Cacheable on void methods (no effect)
                   - Not evicting on updates
                   - Cache key collisions
            
               PERFORMANCE & SCALABILITY:
                   - Use cache warming for predictable load
                   - Consider read-through for hot data
                   - Monitor cache hit ratio
            
            ─────────────────────────────────────────────────────────────────────
            
            3. REDIS INTEGRATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Redis provides distributed caching with high performance
               and persistence options.
            
               WHY IT EXISTS:
               - Shared cache across instances
               - High performance (in-memory)
               - Persistence and replication
            
               INTERNAL MECHANICS:
                   - RedisCacheManager creates RedisCache instances
                   - RedisConnectionFactory manages connections
                   - Lettuce or Jedis as Redis client
                   - Serialization via RedisSerializer
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableCaching
                   public class RedisCacheConfig {
                       @Bean
                       public RedisConnectionFactory redisConnectionFactory() {
                           return new LettuceConnectionFactory(
                               new RedisStandaloneConfiguration("localhost", 6379));
                       }
                       
                       @Bean
                       public CacheManager cacheManager(RedisConnectionFactory factory) {
                           RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                               .entryTtl(Duration.ofHours(1))
                               .serializeKeysWith(RedisSerializationContext.SerializationPair
                                   .fromSerializer(new StringRedisSerializer()))
                               .serializeValuesWith(RedisSerializationContext.SerializationPair
                                   .fromSerializer(new GenericJackson2JsonRedisSerializer()));
                           
                           return RedisCacheManager.builder(factory)
                               .cacheDefaults(config)
                               .build();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A session store uses Redis with:
                   - 30-minute TTL for session expiration
                   - JSON serialization for complex objects
                   - Connection pooling for performance
                   - Redis cluster for high availability
            
               INTERVIEW QUESTION:
                   "How do you configure Redis for high availability?
                   What serialization format do you recommend?"
            
               COMMON MISTAKES:
                   - Not configuring TTL (memory leak)
                   - Using default serialization (not efficient)
                   - Not handling Redis connection failures
            
               PERFORMANCE & SCALABILITY:
                   - Redis is single-threaded
                   - Pipeline commands for batch operations
                   - Consider Redis cluster for scale
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CACHE INVALIDATION STRATEGIES
               ─────────────────────────────────────────────────────────────────────
               STRATEGIES:
                   - Time-based (TTL): Automatic expiration
                   - Write-through: Update cache on write
                   - Write-behind: Async cache update
                   - Cache-aside: Application manages cache
                   - Read-through: Cache handles miss
            
               CACHE-ASIDE PATTERN:
                   @Service
                   public class ProductService {
                       @Cacheable("products")
                       public Product findById(Long id) {
                           return productRepository.findById(id);
                       }
                       
                       @CacheEvict(value = "products", key = "#product.id")
                       @Transactional
                       public Product update(Product product) {
                           return productRepository.save(product);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A news feed service uses:
                   - Cache-aside for article content (1 hour TTL)
                   - Write-through for user preferences
                   - Cache invalidation on article updates
            
               INTERVIEW QUESTION:
                   "What is cache stampede and how do you prevent it?
                   How do you handle cache invalidation in distributed systems?"
            
               COMMON MISTAKES:
                   - Not invalidating on updates
                   - Cache stampede under load
                   - Not handling cache miss gracefully
            
               PERFORMANCE & SCALABILITY:
                   - Consider read-through for hot data
                   - Use cache warming for predictable load
                   - Monitor cache hit ratio
            
            ─────────────────────────────────────────────────────────────────────
            
            5. CACHE STAMPEDE PREVENTION
               ─────────────────────────────────────────────────────────────────────
               PROBLEM:
                   When cache expires, multiple requests hit database
                   simultaneously, causing overload.
            
               SOLUTIONS:
                   - Early expiration (stale while revalidate)
                   - Single flight (lock on cache miss)
                   - Probabilistic early expiration
                   - Request coalescing
            
               IMPLEMENTATION:
                   @Service
                   public class ProductService {
                       private final ConcurrentHashMap<Long, CompletableFuture<Product>> pendingRequests = 
                           new ConcurrentHashMap<>();
                       
                       @Cacheable("products")
                       public Product findById(Long id) {
                           return pendingRequests.computeIfAbsent(id, this::loadProduct)
                               .join();
                       }
                       
                       private CompletableFuture<Product> loadProduct(Long id) {
                           return CompletableFuture.supplyAsync(() -> 
                               productRepository.findById(id));
                       }
                   }
            
               INTERVIEW QUESTION:
                   "How do you prevent cache stampede in a high-traffic application?
                   What is the single flight pattern?"
            
               COMMON MISTAKES:
                   - Not considering concurrent access
                   - Blocking all requests on cache miss
            
               PERFORMANCE & SCALABILITY:
                   - Consider async loading
                   - Use bounded cache
                   - Monitor cache performance
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Caching is essential for:
            - Performance optimization
            - Scalability
            - Reducing database load
            - Distributed caching
            """);
    }
}