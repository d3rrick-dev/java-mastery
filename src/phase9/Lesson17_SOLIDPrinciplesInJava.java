package phase9;

/**
 * LESSON 17: SOLID PRINCIPLES IN JAVA
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * SOLID is 5 design principles for writing maintainable code.
 * Like building with LEGO - each piece has one job,
 * and they connect cleanly.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Reduce coupling
 * - Increase cohesion
 * - Easier testing
 * - Easier maintenance
 */

public class Lesson17_SOLIDPrinciplesInJava {

    public static void main(String[] args) {
        System.out.println("=== SOLID PRINCIPLES IN JAVA ===\n");

        // ============================================================
        // S - Single Responsibility Principle
        // ============================================================
        System.out.println("--- S: Single Responsibility ---\n");

        System.out.println("A class should have ONE reason to change.");
        System.out.println("Bad: User class handles validation, DB, email");
        System.out.println("Good: Separate User, UserValidator, UserRepository, EmailService");
        System.out.println();

        // ============================================================
        // O - Open/Closed Principle
        // ============================================================
        System.out.println("--- O: Open/Closed ---\n");

        System.out.println("Open for extension, closed for modification.");
        System.out.println();

        // Bad: Need to modify class for each new payment type
        // class PaymentProcessor {
        //   void process(Payment p) {
        //     if (p.type == "CREDIT") ...
        //     else if (p.type == "DEBIT") ...
        //   }
        // }

        // Good: Use interface
        PaymentProcessor processor = new PaymentProcessor();
        processor.process(new CreditCardPayment());
        processor.process(new PayPalPayment());
        System.out.println();

        // ============================================================
        // L - Liskov Substitution Principle
        // ============================================================
        System.out.println("--- L: Liskov Substitution ---\n");

        System.out.println("Subclasses should replace parent classes without breaking.");
        System.out.println("Bad: Penguin extends Bird but can't fly");
        System.out.println("Good: Separate FlyingBird and NonFlyingBird");
        System.out.println();

        // ============================================================
        // I - Interface Segregation Principle
        // ============================================================
        System.out.println("--- I: Interface Segregation ---\n");

        System.out.println("Many specific interfaces > one general interface.");
        System.out.println("Bad: Worker interface with work(), eat(), sleep()");
        System.out.println("Good: Workable, Eatable, Sleepable interfaces");
        System.out.println();

        // ============================================================
        // D - Dependency Inversion Principle
        // ============================================================
        System.out.println("--- D: Dependency Inversion ---\n");

        System.out.println("Depend on abstractions, not concretions.");
        System.out.println("Bad: Store depends on MySQLDatabase");
        System.out.println("Good: Store depends on Database interface");
        System.out.println();

        // ============================================================
        // EXAMPLE: All principles together
        // ============================================================
        System.out.println("--- Example: All Principles ---\n");

        NotificationService service = new NotificationService(new EmailSender());
        service.notify("Hello SOLID!");
        System.out.println();
    }

    // ============================================================
    // SOLID EXAMPLES
    // ============================================================

    // O: Open/Closed - Payment interface
    interface Payment {
        void pay(double amount);
    }

    static class CreditCardPayment implements Payment {
        @Override
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " via Credit Card");
        }
    }

    static class PayPalPayment implements Payment {
        @Override
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " via PayPal");
        }
    }

    static class PaymentProcessor {
        public void process(Payment payment) {
            payment.pay(100.0);
        }
    }

    // D: Dependency Inversion
    interface NotificationSender {
        void send(String message);
    }

    static class EmailSender implements NotificationSender {
        @Override
        public void send(String message) {
            System.out.println("Email sent: " + message);
        }
    }

    static class SmsSender implements NotificationSender {
        @Override
        public void send(String message) {
            System.out.println("SMS sent: " + message);
        }
    }

    static class NotificationService {
        private final NotificationSender sender;

        // D: Depend on abstraction
        public NotificationService(NotificationSender sender) {
            this.sender = sender;
        }

        public void notify(String message) {
            sender.send(message);
        }
    }
}
