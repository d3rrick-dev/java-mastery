package phase9;

/**
 * LESSON 10: BUILDER PATTERN
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Builder pattern creates complex objects step by step.
 * Instead of a constructor with many parameters, you use
 * a builder to set optional parameters fluently.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Telescoping constructors problem
 * - Too many constructor parameters
 * - Optional parameters
 * - Immutable objects with many fields
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Order object:
 * - Required: customerId, items
 * - Optional: discount, shippingAddress, notes
 * - Builder makes it clean
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API request builder:
 * - Required: endpoint, method
 * - Optional: headers, body, timeout, retries
 * - Fluent API is readable
 */

public class Lesson10_BuilderPattern {

    public static void main(String[] args) {
        System.out.println("=== BUILDER PATTERN ===\n");

        // ============================================================
        // EXAMPLE 1: Basic builder
        // ============================================================
        System.out.println("--- Example 1: Basic Builder ---\n");

        Order order = new Order.Builder(123, List.of("item1", "item2"))
            .discount(0.1)
            .shippingAddress("123 Main St")
            .notes("Fragile")
            .build();

        System.out.println("Order: " + order);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Builder with validation
        // ============================================================
        System.out.println("--- Example 2: Builder with Validation ---\n");

        try {
            Order invalid = new Order.Builder(-1, List.of())
                .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Fluent API
        // ============================================================
        System.out.println("--- Example 3: Fluent API ---\n");

        HttpRequest request = new HttpRequest.Builder("https://api.example.com")
            .method("POST")
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer token")
            .body("{\"key\": \"value\"}")
            .timeout(5000)
            .build();

        System.out.println("Request: " + request);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: When to use builder
        // ============================================================
        System.out.println("--- Example 4: When to Use ---\n");

        System.out.println("Use Builder when:");
        System.out.println("  - Class has many parameters (4+)");
        System.out.println("  - Many parameters are optional");
        System.out.println("  - Need immutable objects");
        System.out.println("  - Constructor would be confusing");
        System.out.println();
        System.out.println("Don't use when:");
        System.out.println("  - Few required parameters");
        System.out.println("  - All parameters are required");
        System.out.println("  - Simple data class (use record)");
    }

    // ============================================================
    // BUILDER EXAMPLES
    // ============================================================

    static class Order {
        private final int customerId;
        private final List<String> items;
        private final double discount;
        private final String shippingAddress;
        private final String notes;

        private Order(Builder builder) {
            this.customerId = builder.customerId;
            this.items = builder.items;
            this.discount = builder.discount;
            this.shippingAddress = builder.shippingAddress;
            this.notes = builder.notes;
        }

        public static class Builder {
            private final int customerId;
            private final List<String> items;
            private double discount = 0.0;
            private String shippingAddress = "";
            private String notes = "";

            public Builder(int customerId, List<String> items) {
                if (customerId <= 0) {
                    throw new IllegalArgumentException("Customer ID must be positive");
                }
                if (items == null || items.isEmpty()) {
                    throw new IllegalArgumentException("Items cannot be empty");
                }
                this.customerId = customerId;
                this.items = items;
            }

            public Builder discount(double discount) {
                if (discount < 0 || discount > 1) {
                    throw new IllegalArgumentException("Discount must be 0-1");
                }
                this.discount = discount;
                return this;
            }

            public Builder shippingAddress(String address) {
                this.shippingAddress = address;
                return this;
            }

            public Builder notes(String notes) {
                this.notes = notes;
                return this;
            }

            public Order build() {
                return new Order(this);
            }
        }

        @Override
        public String toString() {
            return "Order{customerId=" + customerId + ", items=" + items +
                ", discount=" + discount + ", address='" + shippingAddress + "'}";
        }
    }

    static class HttpRequest {
        private final String url;
        private final String method;
        private final Map<String, String> headers;
        private final String body;
        private final int timeout;

        private HttpRequest(Builder builder) {
            this.url = builder.url;
            this.method = builder.method;
            this.headers = builder.headers;
            this.body = builder.body;
            this.timeout = builder.timeout;
        }

        public static class Builder {
            private final String url;
            private String method = "GET";
            private Map<String, String> headers = new HashMap<>();
            private String body = "";
            private int timeout = 30000;

            public Builder(String url) {
                this.url = url;
            }

            public Builder method(String method) {
                this.method = method;
                return this;
            }

            public Builder header(String key, String value) {
                this.headers.put(key, value);
                return this;
            }

            public Builder body(String body) {
                this.body = body;
                return this;
            }

            public Builder timeout(int timeout) {
                this.timeout = timeout;
                return this;
            }

            public HttpRequest build() {
                return new HttpRequest(this);
            }
        }

        @Override
        public String toString() {
            return "HttpRequest{method='" + method + "', url='" + url + "'}";
        }
    }
}
