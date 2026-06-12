package phase9;

import java.util.*;

/**
 * LESSON 3: ENUMS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Enum (enumeration) is a special type that represents a fixed
 * set of constants. Like days of week, months, or status codes.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Type safety (no invalid values)
 * - Self-documenting code
 * - Can have fields, methods, constructors
 * - Used in switch statements
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Order status:
 * - PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
 * - Type-safe, no invalid states
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * HTTP status codes:
 * - enum HttpStatus { OK(200), NOT_FOUND(404), ... }
 * - Type-safe status handling
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is an enum?"
 * Answer: Type-safe fixed set of constants,
 *         can have fields and methods
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using int constants instead of enums
 * - Not using enum methods
 * - Comparing with == instead of equals
 * - Not using switch with enums
 */

public class Lesson03_Enums {

    public static void main(String[] args) {
        System.out.println("=== ENUMS ===\n");

        // ============================================================
        // EXAMPLE 1: Basic enum
        // ============================================================
        System.out.println("--- Example 1: Basic Enum ---\n");

        Day today = Day.MONDAY;
        System.out.println("Today: " + today);
        System.out.println("Ordinal: " + today.ordinal());
        System.out.println("Name: " + today.name());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Enum with fields
        // ============================================================
        System.out.println("--- Example 2: Enum with Fields ---\n");

        HttpStatus status = HttpStatus.OK;
        System.out.println("Status: " + status);
        System.out.println("Code: " + status.getCode());
        System.out.println("Description: " + status.getDescription());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Enum with methods
        // ============================================================
        System.out.println("--- Example 3: Enum with Methods ---\n");

        for (HttpStatus s : HttpStatus.values()) {
            System.out.println(s + " -> " + s.getCode());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Enum in switch
        // ============================================================
        System.out.println("--- Example 4: Enum in Switch ---\n");

        OrderStatus orderStatus = OrderStatus.PROCESSING;
        String message = switch (orderStatus) {
            case PENDING -> "Order is pending";
            case PROCESSING -> "Order is being processed";
            case SHIPPED -> "Order has been shipped";
            case DELIVERED -> "Order delivered";
            case CANCELLED -> "Order cancelled";
        };
        System.out.println("Message: " + message);
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Enum with behavior
        // ============================================================
        System.out.println("--- Example 5: Enum with Behavior ---\n");

        for (Operation op : Operation.values()) {
            System.out.println(op + "(" + op.getSymbol() + "): " + op.apply(10, 5));
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 6: EnumSet and EnumMap
        // ============================================================
        System.out.println("--- Example 6: EnumSet and EnumMap ---\n");

        EnumSet<Day> workingDays = EnumSet.of(
            Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY,
            Day.THURSDAY, Day.FRIDAY
        );
        System.out.println("Working days: " + workingDays);

        EnumMap<HttpStatus, String> statusMap = new EnumMap<>(HttpStatus.class);
        statusMap.put(HttpStatus.OK, "Success");
        statusMap.put(HttpStatus.NOT_FOUND, "Not Found");
        System.out.println("Status map: " + statusMap);
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Enum best practices
        // ============================================================
        System.out.println("--- Example 7: Best Practices ---\n");

        System.out.println("Use enums for:");
        System.out.println("  - Fixed set of constants");
        System.out.println("  - Type-safe values");
        System.out.println("  - State machines");
        System.out.println("  - Strategy pattern");
        System.out.println();
        System.out.println("Avoid:");
        System.out.println("  - Too many enum values");
        System.out.println("  - Changing enum values frequently");
        System.out.println("  - Using ordinal() (fragile)");
    }

    // ============================================================
    // ENUM DEFINITIONS
    // ============================================================

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    enum HttpStatus {
        OK(200, "OK"),
        CREATED(201, "Created"),
        BAD_REQUEST(400, "Bad Request"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_ERROR(500, "Internal Server Error");

        private final int code;
        private final String description;

        HttpStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static HttpStatus fromCode(int code) {
            for (HttpStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown code: " + code);
        }
    }

    enum OrderStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    enum Operation {
        ADD("+") {
            double apply(double a, double b) { return a + b; }
        },
        SUBTRACT("-") {
            double apply(double a, double b) { return a - b; }
        },
        MULTIPLY("*") {
            double apply(double a, double b) { return a * b; }
        },
        DIVIDE("/") {
            double apply(double a, double b) { return a / b; }
        };

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        abstract double apply(double a, double b);
    }
}
