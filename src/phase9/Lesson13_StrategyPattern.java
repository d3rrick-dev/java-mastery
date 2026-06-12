package phase9;

/**
 * LESSON 13: STRATEGY PATTERN
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Strategy pattern lets you define a family of algorithms,
 * encapsulate each one, and make them interchangeable.
 * Like choosing different routes to work - same goal, different ways.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Multiple algorithms for same problem
 * - Switch algorithms at runtime
 * - Avoid conditional statements
 * - Open/Closed principle
 */

public class Lesson13_StrategyPattern {

    public static void main(String[] args) {
        System.out.println("=== STRATEGY PATTERN ===\n");

        // ============================================================
        // EXAMPLE 1: Payment strategies
        // ============================================================
        System.out.println("--- Example 1: Payment Strategies ---\n");

        ShoppingCart cart = new ShoppingCart();

        cart.setPaymentStrategy(new CreditCardPayment("1234-5678"));
        cart.checkout(100);

        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(200);

        cart.setPaymentStrategy(new CryptoPayment("0x123abc"));
        cart.checkout(300);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Sorting strategies
        // ============================================================
        System.out.println("--- Example 2: Sorting Strategies ---\n");

        List<Integer> numbers = List.of(5, 2, 8, 1, 9);

        Sorter bubbleSorter = new Sorter(new BubbleSort());
        System.out.println("Bubble sort: " + bubbleSorter.sort(numbers));

        Sorter quickSorter = new Sorter(new QuickSort());
        System.out.println("Quick sort: " + quickSorter.sort(numbers));
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Text formatting strategies
        // ============================================================
        System.out.println("--- Example 3: Text Formatting ---\n");

        TextFormatter upperFormatter = new TextFormatter(new UpperCaseFormat());
        System.out.println("Upper: " + upperFormatter.format("hello"));

        TextFormatter lowerFormatter = new TextFormatter(new LowerCaseFormat());
        System.out.println("Lower: " + lowerFormatter.format("HELLO"));

        TextFormatter camelFormatter = new TextFormatter(new CamelCaseFormat());
        System.out.println("Camel: " + camelFormatter.format("hello world"));
        System.out.println();
    }

    // ============================================================
    // STRATEGY EXAMPLES
    // ============================================================

    interface PaymentStrategy {
        void pay(double amount);
    }

    static class CreditCardPayment implements PaymentStrategy {
        private final String cardNumber;

        public CreditCardPayment(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " with Credit Card " + cardNumber);
        }
    }

    static class PayPalPayment implements PaymentStrategy {
        private final String email;

        public PayPalPayment(String email) {
            this.email = email;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " with PayPal (" + email + ")");
        }
    }

    static class CryptoPayment implements PaymentStrategy {
        private final String wallet;

        public CryptoPayment(String wallet) {
            this.wallet = wallet;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " with Crypto (" + wallet + ")");
        }
    }

    static class ShoppingCart {
        private PaymentStrategy paymentStrategy;

        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.paymentStrategy = strategy;
        }

        public void checkout(double amount) {
            paymentStrategy.pay(amount);
        }
    }

    interface SortStrategy {
        List<Integer> sort(List<Integer> list);
    }

    static class BubbleSort implements SortStrategy {
        @Override
        public List<Integer> sort(List<Integer> list) {
            List<Integer> result = new ArrayList<>(list);
            for (int i = 0; i < result.size() - 1; i++) {
                for (int j = 0; j < result.size() - i - 1; j++) {
                    if (result.get(j) > result.get(j + 1)) {
                        int temp = result.get(j);
                        result.set(j, result.get(j + 1));
                        result.set(j + 1, temp);
                    }
                }
            }
            return result;
        }
    }

    static class QuickSort implements SortStrategy {
        @Override
        public List<Integer> sort(List<Integer> list) {
            List<Integer> result = new ArrayList<>(list);
            result.sort(Integer::compareTo);
            return result;
        }
    }

    static class Sorter {
        private final SortStrategy strategy;

        public Sorter(SortStrategy strategy) {
            this.strategy = strategy;
        }

        public List<Integer> sort(List<Integer> list) {
            return strategy.sort(list);
        }
    }

    interface FormatStrategy {
        String format(String text);
    }

    static class UpperCaseFormat implements FormatStrategy {
        @Override
        public String format(String text) {
            return text.toUpperCase();
        }
    }

    static class LowerCaseFormat implements FormatStrategy {
        @Override
        public String format(String text) {
            return text.toLowerCase();
        }
    }

    static class CamelCaseFormat implements FormatStrategy {
        @Override
        public String format(String text) {
            StringBuilder result = new StringBuilder();
            boolean capitalize = true;
            for (char c : text.toCharArray()) {
                if (c == ' ') {
                    capitalize = true;
                } else if (capitalize) {
                    result.append(Character.toUpperCase(c));
                    capitalize = false;
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        }
    }

    static class TextFormatter {
        private final FormatStrategy strategy;

        public TextFormatter(FormatStrategy strategy) {
            this.strategy = strategy;
        }

        public String format(String text) {
            return strategy.format(text);
        }
    }
}
