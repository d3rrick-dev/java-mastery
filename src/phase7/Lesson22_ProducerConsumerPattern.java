package phase7;

import java.util.concurrent.*;

/**
 * LESSON 22: PRODUCER CONSUMER PATTERN
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Producer-Consumer is a pattern where producers create data
 * and put it in a shared buffer, while consumers take data
 * from the buffer and process it. The buffer decouples
 * producers from consumers.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Decouples production from consumption
 * - Handles different speeds (fast producer, slow consumer)
 * - Smooths out workload spikes
 * - Enables parallel processing
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Restaurant kitchen:
 * - Chefs (producers) prepare food
 * - Waiters (consumers) serve food
 * - Pass-through window (buffer) holds food
 * - Chefs don't wait for waiters, waiters don't wait for chefs
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Message queue system:
 * - Producers: API endpoints receiving requests
 * - Buffer: Message queue (Kafka, RabbitMQ)
 * - Consumers: Background workers processing requests
 * - Decouples request rate from processing rate
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "Implement producer-consumer pattern"
 * Answer: Use BlockingQueue, producers put(), consumers take()
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using busy-wait instead of blocking
 * - Not handling interruption properly
 * - Using unbounded queue (memory leak)
 * - Forgetting poison pill for shutdown
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Bounded queue prevents OOM
 * - Blocking is more efficient than busy-wait
 * - Queue size affects throughput
 * - Too small = producers blocked, too large = memory waste
 *
 * ============================================================
 * PRODUCER-CONSUMER ARCHITECTURE
 * ============================================================
 *
 *   Producers          Buffer          Consumers
 *   +---------+     +---------+     +---------+
 *   | Produce  | --> |  Queue  | --> | Consume  |
 *   | Data     |     | (Block) |     | Data     |
 *   +---------+     +---------+     +---------+
 *   | Thread 1 |     | put()   |     | Thread 1 |
 *   | Thread 2 |     | take()  |     | Thread 2 |
 *   | Thread 3 |     | offer() |     | Thread 3 |
 *   +---------+     +---------+     +---------+
 */

public class Lesson22_ProducerConsumerPattern {

    public static void main(String[] args) throws Exception {
        System.out.println("=== PRODUCER CONSUMER PATTERN ===\n");

        // ============================================================
        // EXAMPLE 1: Basic Producer-Consumer with BlockingQueue
        // ============================================================
        System.out.println("--- Example 1: Basic Producer-Consumer ---\n");

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Producer
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Consumer
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Integer value = queue.take();
                    System.out.println("Consumed: " + value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Multiple producers and consumers
        // ============================================================
        System.out.println("--- Example 2: Multiple Producers/Consumers ---\n");

        BlockingQueue<String> multiQueue = new LinkedBlockingQueue<>(10);

        // Multiple producers
        Thread[] producers = new Thread[3];
        for (int i = 0; i < producers.length; i++) {
            final int id = i;
            producers[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    try {
                        String task = "Task-P" + id + "-" + j;
                        multiQueue.put(task);
                        System.out.println("Producer-" + id + ": " + task);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        // Multiple consumers
        Thread[] consumers = new Thread[2];
        for (int i = 0; i < consumers.length; i++) {
            final int id = i;
            consumers[i] = new Thread(() -> {
                for (int j = 0; j < 7; j++) {
                    try {
                        String task = multiQueue.take();
                        System.out.println("Consumer-" + id + ": " + task);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        for (Thread t : producers) t.start();
        for (Thread t : consumers) t.start();

        for (Thread t : producers) t.join();
        for (Thread t : consumers) t.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Poison pill pattern for shutdown
        // ============================================================
        System.out.println("--- Example 3: Poison Pill Shutdown ---\n");

        BlockingQueue<String> poisonQueue = new LinkedBlockingQueue<>();
        final String POISON_PILL = "POISON";

        Thread producer3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    poisonQueue.put("Task-" + i);
                    System.out.println("Produced: Task-" + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // Send poison pill
            try {
                poisonQueue.put(POISON_PILL);
                System.out.println("Sent poison pill");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer3 = new Thread(() -> {
            try {
                while (true) {
                    String task = poisonQueue.take();
                    if (POISON_PILL.equals(task)) {
                        System.out.println("Received poison pill, shutting down");
                        break;
                    }
                    System.out.println("Processed: " + task);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer3.start();
        consumer3.start();
        producer3.join();
        consumer3.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Custom blocking queue with wait/notify
        // ============================================================
        System.out.println("--- Example 4: Custom Blocking Queue ---\n");

        CustomBlockingQueue customQueue = new CustomBlockingQueue(5);

        Thread customProducer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    customQueue.put(i);
                    System.out.println("Custom produced: " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread customConsumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Integer value = customQueue.take();
                    System.out.println("Custom consumed: " + value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        customProducer.start();
        customConsumer.start();
        customProducer.join();
        customConsumer.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Priority-based producer-consumer
        // ============================================================
        System.out.println("--- Example 5: Priority-Based Processing ---\n");

        BlockingQueue<PriorityTask> priorityQueue = new PriorityBlockingQueue<>();

        Thread priorityProducer = new Thread(() -> {
            priorityQueue.put(new PriorityTask("Low", 3));
            priorityQueue.put(new PriorityTask("High", 1));
            priorityQueue.put(new PriorityTask("Medium", 2));
            priorityQueue.put(new PriorityTask("Critical", 0));
        });

        Thread priorityConsumer = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                try {
                    PriorityTask task = priorityQueue.take();
                    System.out.println("Processed: " + task.name);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        priorityProducer.start();
        priorityConsumer.start();
        priorityProducer.join();
        priorityConsumer.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Real-world backend example
        // ============================================================
        System.out.println("--- Example 6: Backend Example ---\n");

        System.out.println("Web server with producer-consumer:");
        System.out.println("1. HTTP requests arrive (producers)");
        System.out.println("2. Requests go to blocking queue");
        System.out.println("3. Worker threads process requests (consumers)");
        System.out.println("4. Queue size limits memory usage");
        System.out.println("5. Workers can be scaled independently");
        System.out.println();
    }

    // ============================================================
    // HELPER CLASSES
    // ============================================================

    static class CustomBlockingQueue {
        private final int[] buffer;
        private int count = 0;
        private int putIndex = 0;
        private int takeIndex = 0;
        private final Object lock = new Object();

        public CustomBlockingQueue(int capacity) {
            this.buffer = new int[capacity];
        }

        public void put(int value) throws InterruptedException {
            synchronized (lock) {
                while (count == buffer.length) {
                    lock.wait();  // Wait if full
                }
                buffer[putIndex] = value;
                putIndex = (putIndex + 1) % buffer.length;
                count++;
                lock.notifyAll();
            }
        }

        public int take() throws InterruptedException {
            synchronized (lock) {
                while (count == 0) {
                    lock.wait();  // Wait if empty
                }
                int value = buffer[takeIndex];
                takeIndex = (takeIndex + 1) % buffer.length;
                count--;
                lock.notifyAll();
                return value;
            }
        }
    }

    static class PriorityTask implements Comparable<PriorityTask> {
        public final String name;
        public final int priority;

        public PriorityTask(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityTask other) {
            return Integer.compare(this.priority, other.priority);
        }
    }
}
