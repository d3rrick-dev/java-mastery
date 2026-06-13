package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Security (Advanced)
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Authentication flow
 * 2. Authorization flow
 * 3. Filter chain
 * 4. JWT implementation
 * 5. Refresh tokens
 * 6. OAuth2
 * 7. OpenID Connect
 * 8. Method-level security
 * 9. Role-based access control
 * 10. Security best practices
 * 11. Common vulnerabilities
 */

public class Lesson10_SpringSecurityAdvanced {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING SECURITY (ADVANCED) ===
            
            1. AUTHENTICATION FLOW
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Authentication verifies user identity through credentials,
               producing an Authentication object stored in SecurityContext.
            
               WHY IT EXISTS:
               - Secure user identification
               - Flexible authentication mechanisms
               - Integration with security protocols
            
               INTERNAL MECHANICS:
                   - AuthenticationManager authenticates credentials
                   - AuthenticationProvider handles specific auth types
                   - UserDetailsService loads user details
                   - PasswordEncoder validates passwords
                   - SecurityContextHolder holds Authentication
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableWebSecurity
                   public class SecurityConfig {
                       @Bean
                       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                           http.authenticationProvider(authenticationProvider());
                           return http.build();
                       }
                       
                       @Bean
                       public AuthenticationProvider authenticationProvider() {
                           DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                           provider.setUserDetailsService(userDetailsService());
                           provider.setPasswordEncoder(passwordEncoder());
                           return provider;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A multi-tenant application:
                   - Custom AuthenticationProvider validates tenant + credentials
                   - UserDetailsService loads tenant-specific user data
                   - AuthenticationSuccessHandler logs successful login
                   - AuthenticationFailureHandler implements rate limiting
            
               INTERVIEW QUESTION:
                   "Explain the authentication flow in Spring Security.
                   How does AuthenticationManager differ from AuthenticationProvider?"
            
               COMMON MISTAKES:
                   - Not using secure password encoding
                   - Not handling authentication events
                   - Not considering session fixation
            
               PERFORMANCE & SCALABILITY:
                   - Cache user details for performance
                   - Consider JWT for stateless auth
                   - Use connection pooling for user store
            
            ─────────────────────────────────────────────────────────────────────
            
            2. AUTHORIZATION FLOW
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Authorization determines if authenticated user can access
               resources based on roles, permissions, or custom logic.
            
               WHY IT EXISTS:
               - Access control enforcement
               - Fine-grained permissions
               - Audit trail support
            
               INTERNAL MECHANICS:
                   - AccessDecisionManager makes decisions
                   - AccessDecisionVoter votes on access
                   - RoleVoter checks ROLE_ prefixed authorities
                   - AuthenticatedVoter checks authentication level
                   - PreInvocationAuthorizationAdvice for @PreAuthorize
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableWebSecurity
                   public class SecurityConfig {
                       @Bean
                       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                           http.authorizeHttpRequests(auth -> auth
                               .requestMatchers("/api/admin/**").hasRole("ADMIN")
                               .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                               .anyRequest().authenticated()
                           );
                           return http.build();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A document management system:
                   - @PreAuthorize checks document ownership
                   - Custom PermissionEvaluator for fine-grained access
                   - Method-level security for service methods
            
               INTERVIEW QUESTION:
                   "How does Spring Security's authorization work?
                   What is the difference between hasRole and hasAuthority?"
            
               COMMON MISTAKES:
                   - Not enabling method security
                   - hasRole("ADMIN") vs hasAuthority("ROLE_ADMIN")
                   - Not handling authorization failures
            
            ─────────────────────────────────────────────────────────────────────
            
            3. FILTER CHAIN
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Spring Security uses servlet filters to process requests,
               each handling specific security concerns.
            
               WHY IT EXISTS:
               - Modular security processing
               - Ordered execution
               - Extensible architecture
            
               STANDARD FILTERS:
                   - SecurityContextPersistenceFilter: Load SecurityContext
                   - LogoutFilter: Handle logout
                   - UsernamePasswordAuthenticationFilter: Form login
                   - RequestCacheAwareFilter: Cache requests
                   - SecurityContextHolderAwareRequestFilter: Servlet API integration
                   - AnonymousAuthenticationFilter: Anonymous auth
                   - SessionManagementFilter: Session control
                   - ExceptionTranslationFilter: Handle security exceptions
                   - FilterSecurityInterceptor: Authorization
            
               INTERNAL MECHANICS:
                   - FilterChainProxy holds multiple filter chains
                   - RequestMatcher determines which chain to use
                   - VirtualFilterChain executes filters in order
            
               SIMPLE EXAMPLE:
                   @Bean
                   public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
                       http.securityMatcher("/api/**")
                           .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                           .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
                       return http.build();
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A rate-limiting filter:
                   - Extracts API key from header
                   - Checks Redis for request count
                   - Rejects if over limit
                   - Placed early in filter chain
            
               INTERVIEW QUESTION:
                   "How do you add a custom filter to Spring Security?
                   What is the order of standard filters?"
            
               COMMON MISTAKES:
                   - Wrong filter order
                   - Not handling filter exceptions
                   - Forgetting to call chain.doFilter()
            
               PERFORMANCE & SCALABILITY:
                   - Place expensive filters later
                   - Cache security decisions
                   - Use async for non-blocking checks
            
            ─────────────────────────────────────────────────────────────────────
            
            4. JWT IMPLEMENTATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               JSON Web Tokens provide stateless authentication,
               containing claims that can be validated without server state.
            
               WHY IT EXISTS:
               - Stateless authentication
               - Cross-service token sharing
               - No session storage needed
            
               INTERNAL MECHANICS:
                   - JwtDecoder validates and parses tokens
                   - JwtEncoder creates tokens
                   - NimbusJwtDecoder for RSA/ECDSA
                   - HMAC-SHA algorithms for symmetric
            
               SIMPLE EXAMPLE:
                   @Bean
                   public JwtDecoder jwtDecoder() {
                       return NimbusJwtDecoder.withSecretKey(
                           new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")).build();
                   }
                   
                   @Component
                   public class JwtAuthenticationFilter extends OncePerRequestFilter {
                       @Override
                       protected void doFilterInternal(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     FilterChain filterChain) throws ServletException, IOException {
                           String token = resolveToken(request);
                           if (token != null && jwtValidator.isValid(token)) {
                               Authentication auth = jwtService.getAuthentication(token);
                               SecurityContextHolder.getContext().setAuthentication(auth);
                           }
                           filterChain.doFilter(request, response);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservices architecture:
                   - Auth service issues JWT with user claims
                   - Each service validates independently
                   - No shared session state
                   - Token contains tenant information
            
               INTERVIEW QUESTION:
                   "How do you handle JWT token revocation?
                   What is the difference between access and refresh tokens?"
            
               COMMON MISTAKES:
                   - Not validating token signature
                   - Storing sensitive data in JWT
                   - Not setting expiration
                   - Not handling token refresh
            
               PERFORMANCE & SCALABILITY:
                   - JWT validation is fast (no DB lookup)
                   - Consider token caching for revocation
                   - Use short-lived access tokens
            
            ─────────────────────────────────────────────────────────────────────
            
            5. OAUTH2 & OPENID CONNECT
               ─────────────────────────────────────────────────────────────────────
               OAUTH2:
                   - Authorization framework
                   - Four grant types: authorization_code, client_credentials,
                     password, refresh_token
                   - Scopes define access level
            
               OPENID CONNECT:
                   - Identity layer on top of OAuth2
                   - ID token for user identity
                   - UserInfo endpoint
                   - Standard claims (sub, name, email)
            
               SIMPLE EXAMPLE:
                   @Configuration
                   public class OAuth2Config {
                       @Bean
                       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                           http.oauth2Login(oauth2 -> oauth2
                               .loginPage("/oauth2/authorization/google")
                           );
                           return http.build();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A SaaS application:
                   - OAuth2 for API access
                   - OpenID Connect for user login
                   - Multiple identity providers (Google, GitHub, Azure AD)
                   - Scope-based access control
            
               INTERVIEW QUESTION:
                   "What is the difference between OAuth2 and OpenID Connect?
                   How do you implement resource server validation?"
            
               COMMON MISTAKES:
                   - Not validating token audience
                   - Not handling token expiration
                   - Not using PKCE for public clients
            
               PERFORMANCE & SCALABILITY:
                   - Use introspection for opaque tokens
                   - Cache public keys
                   - Consider token caching
            
            ─────────────────────────────────────────────────────────────────────
            
            6. METHOD-LEVEL SECURITY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
               enable security at the method level.
            
               WHY IT EXISTS:
               - Fine-grained access control
               - Business logic security
               - Clean separation from web layer
            
               INTERNAL MECHANICS:
                   - PrePostAnnotationSecurityMetadataSource extracts metadata
                   - PrePostAuthorizationAdvice handles pre-invocation
                   - Expression-based access control
            
               SIMPLE EXAMPLE:
                   @Service
                   @EnableGlobalMethodSecurity(prePostEnabled = true)
                   public class DocumentService {
                       @PreAuthorize("@documentSecurity.canAccess(authentication, #id)")
                       public Document findById(Long id) {
                           return documentRepository.findById(id);
                       }
                       
                       @PreAuthorize("hasRole('ADMIN') or hasAuthority('DOCUMENT_WRITE')")
                       public Document create(Document document) {
                           return documentRepository.save(document);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A multi-tenant document service:
                   - @PreAuthorize checks tenant ownership
                   - Custom bean method for complex logic
                   - @PostFilter filters returned collections
            
               INTERVIEW QUESTION:
                   "How do you implement custom permission checks in @PreAuthorize?
                   What is the difference between @PreAuthorize and @Secured?"
            
               COMMON MISTAKES:
                   - Not enabling method security
                   - Complex SpEL expressions
                   - Not handling authorization failures
            
               PERFORMANCE & SCALABILITY:
                   - Cache security decisions
                   - Avoid expensive operations in SpEL
                   - Consider pre-computed permissions
            
            ─────────────────────────────────────────────────────────────────────
            
            7. SECURITY BEST PRACTICES
               ─────────────────────────────────────────────────────────────────────
               BEST PRACTICES:
                   - Use strong password encoding (BCrypt, Argon2)
                   - Enable HTTPS
                   - Set secure cookie flags
                   - Implement CSRF protection
                   - Use security headers
                   - Rate limiting
                   - Audit logging
            
               SIMPLE EXAMPLE:
                   @Bean
                   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                       http.headers(headers -> headers
                           .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
                           .frameOptions().deny()
                           .xssProtection().block(true)
                       );
                       return http.build();
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A financial API:
                   - JWT with short expiration (15 min)
                   - Refresh token rotation
                   - IP-based rate limiting
                   - Security audit logging
                   - CORS restrictions
            
               INTERVIEW QUESTION:
                   "How do you secure a REST API? What security headers
                   should you always include?"
            
               COMMON MISTAKES:
                   - Not using HTTPS
                   - Weak password policies
                   - Not handling security events
            
               PERFORMANCE & SCALABILITY:
                   - Security checks add latency
                   - Cache authentication decisions
                   - Use efficient algorithms
            
            ─────────────────────────────────────────────────────────────────────
            
            8. COMMON VULNERABILITIES
               ─────────────────────────────────────────────────────────────────────
               VULNERABILITIES:
                   - SQL Injection: Use parameterized queries
                   - XSS: Escape output, use CSP
                   - CSRF: Use tokens, SameSite cookies
                   - Broken Authentication: Strong passwords, MFA
                   - Security Misconfiguration: Secure defaults
                   - Sensitive Data Exposure: Encryption, masking
                   - Broken Access Control: Deny by default
            
               PREVENTION:
                   - Use Spring Security defaults
                   - Regular security audits
                   - Dependency vulnerability scanning
                   - Penetration testing
            
               INTERVIEW QUESTION:
                   "What is the OWASP Top 10? How does Spring Security
                   help prevent these vulnerabilities?"
            
               COMMON MISTAKES:
                   - Not keeping dependencies updated
                   - Not following security best practices
                   - Not testing security configurations
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Security is essential for:
            - Authentication and authorization
            - JWT and OAuth2 integration
            - Method-level security
            - Security best practices
            """);
    }
}