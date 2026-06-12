package phase9;

import java.util.*;

/**
 * LESSON 14: OBSERVER PATTERN
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Observer pattern defines a one-to-many dependency.
 * When one object changes state, all dependents are notified.
 * Like a YouTube channel - when creator uploads, all subscribers get notified.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Loose coupling between objects
 * - Event-driven architecture
 * - Multiple listeners for same event
 * - Dynamic subscription/unsubscription
 */

public class Lesson14_ObserverPattern {

    public static void main(String[] args) {
        System.out.println("=== OBSERVER PATTERN ===\n");

        // ============================================================
        // EXAMPLE 1: Basic observer
        // ============================================================
        System.out.println("--- Example 1: Basic Observer ---\n");

        NewsAgency agency = new NewsAgency();
        agency.addObserver(new NewsChannel("Channel A"));
        agency.addObserver(new NewsChannel("Channel B"));

        agency.publishNews("Breaking: Java 21 released!");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Stock price observer
        // ============================================================
        System.out.println("--- Example 2: Stock Price Observer ---\n");

        StockTicker ticker = new StockTicker();
        ticker.addObserver(new StockDisplay());
        ticker.addObserver(new StockAlert());

        ticker.setPrice("AAPL", 150.0);
        ticker.setPrice("GOOGL", 2800.0);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Event listener pattern
        // ============================================================
        System.out.println("--- Example 3: Event Listener ---\n");

        Button button = new Button();
        button.addClickListener(e -> System.out.println("Button clicked!"));
        button.addClickListener(e -> System.out.println("Another handler!"));
        button.click();
        System.out.println();
    }

    // ============================================================
    // OBSERVER EXAMPLES
    // ============================================================

    interface Observer {
        void update(String message);
    }

    static class NewsChannel implements Observer {
        private final String name;

        public NewsChannel(String name) {
            this.name = name;
        }

        @Override
        public void update(String message) {
            System.out.println(name + " received: " + message);
        }
    }

    static class NewsAgency {
        private final List<Observer> observers = new ArrayList<>();

        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        public void publishNews(String news) {
            for (Observer observer : observers) {
                observer.update(news);
            }
        }
    }

    interface StockObserver {
        void onPriceChange(String symbol, double price);
    }

    static class StockTicker {
        private final List<StockObserver> observers = new ArrayList<>();
        private final Map<String, Double> prices = new HashMap<>();

        public void addObserver(StockObserver observer) {
            observers.add(observer);
        }

        public void setPrice(String symbol, double price) {
            prices.put(symbol, price);
            for (StockObserver observer : observers) {
                observer.onPriceChange(symbol, price);
            }
        }
    }

    static class StockDisplay implements StockObserver {
        @Override
        public void onPriceChange(String symbol, double price) {
            System.out.println("Display: " + symbol + " = $" + price);
        }
    }

    static class StockAlert implements StockObserver {
        @Override
        public void onPriceChange(String symbol, double price) {
            if (price > 1000) {
                System.out.println("ALERT: " + symbol + " is high: $" + price);
            }
        }
    }

    interface ClickListener {
        void onClick(ClickEvent event);
    }

    static class ClickEvent {
        private final long timestamp;
        private final int x, y;

        public ClickEvent(long timestamp, int x, int y) {
            this.timestamp = timestamp;
            this.x = x;
            this.y = y;
        }

        public long getTimestamp() { return timestamp; }
        public int getX() { return x; }
        public int getY() { return y; }
    }

    static class Button {
        private final List<ClickListener> listeners = new ArrayList<>();

        public void addClickListener(ClickListener listener) {
            listeners.add(listener);
        }

        public void removeClickListener(ClickListener listener) {
            listeners.remove(listener);
        }

        public void click() {
            ClickEvent event = new ClickEvent(System.currentTimeMillis(), 100, 200);
            for (ClickListener listener : listeners) {
                listener.onClick(event);
            }
        }
    }
}
