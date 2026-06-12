package phase9;

/**
 * LESSON 12: FACTORY PATTERN
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Factory pattern creates objects without specifying exact class.
 * Like a restaurant kitchen - you order food, kitchen decides
 * how to make it.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Decouple object creation from usage
 * - Centralize object creation logic
 * - Easy to add new types
 * - Type safety
 */

public class Lesson12_FactoryPattern {

    public static void main(String[] args) {
        System.out.println("=== FACTORY PATTERN ===\n");

        // ============================================================
        // EXAMPLE 1: Simple factory
        // ============================================================
        System.out.println("--- Example 1: Simple Factory ---\n");

        PaymentProcessor paypal = PaymentProcessorFactory.create("paypal");
        PaymentProcessor stripe = PaymentProcessorFactory.create("stripe");

        paypal.process(100);
        stripe.process(200);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Factory method pattern
        // ============================================================
        System.out.println("--- Example 2: Factory Method ---\n");

        NotificationFactory factory = new EmailNotificationFactory();
        Notification notification = factory.create();
        notification.send("Hello via email");

        factory = new SMSNotificationFactory();
        notification = factory.create();
        notification.send("Hello via SMS");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Abstract factory
        // ============================================================
        System.out.println("--- Example 3: Abstract Factory ---\n");

        GUIFactory windowsFactory = new WindowsFactory();
        Button windowsButton = windowsFactory.createButton();
        windowsButton.render();

        GUIFactory macFactory = new MacFactory();
        Button macButton = macFactory.createButton();
        macButton.render();
        System.out.println();
    }

    // ============================================================
    // FACTORY EXAMPLES
    // ============================================================

    interface PaymentProcessor {
        void process(double amount);
    }

    static class PayPalProcessor implements PaymentProcessor {
        @Override
        public void process(double amount) {
            System.out.println("Processing $" + amount + " via PayPal");
        }
    }

    static class StripeProcessor implements PaymentProcessor {
        @Override
        public void process(double amount) {
            System.out.println("Processing $" + amount + " via Stripe");
        }
    }

    static class PaymentProcessorFactory {
        public static PaymentProcessor create(String type) {
            return switch (type.toLowerCase()) {
                case "paypal" -> new PayPalProcessor();
                case "stripe" -> new StripeProcessor();
                default -> throw new IllegalArgumentException("Unknown: " + type);
            };
        }
    }

    interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {
        @Override
        public void send(String message) {
            System.out.println("Sending email: " + message);
        }
    }

    static class SMSNotification implements Notification {
        @Override
        public void send(String message) {
            System.out.println("Sending SMS: " + message);
        }
    }

    abstract static class NotificationFactory {
        public abstract Notification create();
    }

    static class EmailNotificationFactory extends NotificationFactory {
        @Override
        public Notification create() {
            return new EmailNotification();
        }
    }

    static class SMSNotificationFactory extends NotificationFactory {
        @Override
        public Notification create() {
            return new SMSNotification();
        }
    }

    interface Button {
        void render();
    }

    interface GUIFactory {
        Button createButton();
    }

    static class WindowsButton implements Button {
        @Override
        public void render() {
            System.out.println("Rendering Windows button");
        }
    }

    static class MacButton implements Button {
        @Override
        public void render() {
            System.out.println("Rendering Mac button");
        }
    }

    static class WindowsFactory implements GUIFactory {
        @Override
        public Button createButton() {
            return new WindowsButton();
        }
    }

    static class MacFactory implements GUIFactory {
        @Override
        public Button createButton() {
            return new MacButton();
        }
    }
}
