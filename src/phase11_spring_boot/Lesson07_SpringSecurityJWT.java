package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Spring Security with JWT
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson07_SpringSecurityJWT {
    public static void main(String[] args) {
        System.out.println("""
                === SPRING SECURITY WITH JWT ===

                1. JWT STRUCTURE
                   Header.Payload.Signature
                   - Header: algorithm (HS256)
                   - Payload: claims (user id, roles, expiry)
                   - Signature: HMAC-SHA256(header + payload, secret)

                2. SECURITY CONFIGURATION
                     @Configuration
                     @EnableWebSecurity
                     public class SecurityConfig {
                         @Bean
                         public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                             http.csrf().disable()
                                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                 .and()
                                 .authorizeHttpRequests(auth -> auth
                                     .requestMatchers("/api/auth/**").permitAll()
                                     .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                     .anyRequest().authenticated()
                                 )
                                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                             return http.build();
                         }
                     }

                3. JWT FILTER
                     public class JwtFilter extends OncePerRequestFilter {
                         @Override
                         protected void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response, FilterChain chain)
                                 throws ServletException, IOException {
                             String token = resolveToken(request);
                             if (token != null && jwtUtil.validateToken(token)) {
                                 Authentication auth = jwtUtil.getAuthentication(token);
                                 SecurityContextHolder.getContext().setAuthentication(auth);
                             }
                             chain.doFilter(request, response);
                         }
                     }

                BACKEND REAL-WORLD EXAMPLE:
                   - User logs in with username/password
                   - Server returns JWT token
                   - Client sends token in Authorization header
                   - Server validates token on each request
                   - Stateless, scalable, suitable for microservices

                COMMON MISTAKES:
                   - Storing sensitive data in JWT payload (it's base64, not encrypted)
                   - Not setting expiration (tokens never expire)
                   - Using weak secret keys
                   - Not handling token refresh
                   - Storing JWT in localStorage (XSS risk)
                """);
    }
}
