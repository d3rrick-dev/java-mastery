package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Validation and Exception Handling
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson04_ValidationAndExceptionHandling {
    public static void main(String[] args) {
        System.out.println("""
                === VALIDATION AND EXCEPTION HANDLING ===

                1. VALIDATION WITH JAKARTA VALIDATION
                   - @NotBlank, @NotNull, @Email, @Min, @Max, @Size
                   - @Valid triggers validation on nested objects
                   - @Validated on controller method or class

                2. GLOBAL EXCEPTION HANDLING
                     @RestControllerAdvice
                     public class GlobalExceptionHandler {
                         @ExceptionHandler(MethodArgumentNotValidException.class)
                         public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
                             List<String> errors = ex.getBindingResult()
                                 .getFieldErrors()
                                 .stream()
                                 .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                 .toList();
                             return ResponseEntity.badRequest().body(new ErrorResponse(errors));
                         }
                     }

                3. CUSTOM EXCEPTIONS
                     public class ResourceNotFoundException extends RuntimeException {
                         public ResourceNotFoundException(String message) {
                             super(message);
                         }
                     }

                4. ERROR RESPONSE DTO
                     public record ErrorResponse(
                         int status,
                         String message,
                         List<String> errors,
                         long timestamp
                     ) {}

                BACKEND REAL-WORLD EXAMPLE:
                   - User registration with email validation
                   - Returns 400 with field-level errors
                   - Returns 404 when user not found
                   - Returns 500 with generic message for unexpected errors

                COMMON MISTAKES:
                   - Not using @Valid on @RequestBody
                   - Catching Exception (too broad)
                   - Exposing stack traces in production
                   - Not handling MethodArgumentNotValidException
                   - Using ResponseEntity everywhere (verbose)
                """);
    }
}
