package phase7;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LESSON 21: CONCURRENT COLLECTIONS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Concurrent collections are thread-safe collections designed for
 * concurrent access. Unlike synchronized wrappers, they use
 * sophisticated algorithms to allow high concurrency.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Regular collections are not thread-safe
 * - synchronized wrappers have high contention
 * - Need high-performance concurrent data structures
 * - Different collections for different access patterns
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Shopping cart in e-commerce:
 * - Multiple users adding items concurrently
 * - ConcurrentHashMap for product inventory
 * - ConcurrentLinkedQueue for order processing
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Session cache:
 * - ConcurrentHashMap stores user sessions
 * - Multiple threads read/write sessions
 * - CopyOnWriteArrayList for session listeners
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What concurrent collections do you know?"
 * Answer: ConcurrentHashMap, CopyOnWriteArrayList,
 *         BlockingQueue, ConcurrentLinkedQueue, etc.
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using HashMap in multi-threaded context
 * - Using synchronizedMap instead of ConcurrentHashMap
 * - Not understanding iteration behavior
 * - Mixing concurrent and non-concurrent collections
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - ConcurrentHashMap: ~16 segments, high concurrency
 * - CopyOnWriteArrayList: fast reads, slow writes
 * - BlockingQueue: efficient producer-consumer
 * - Choose right collection for access pattern
 *
 * ============================================================
 * CONCURRENT COLLECTION TYPES
 * ============================================================
 *
 *   ConcurrentHashMap    CopyOnWriteArrayList
 *   +----------------+   +----------------+
 *   | High concurrency|   | Read-optimized |
 *   | Lock striping   |   | Copy on write  |
 *   | No null keys    |   | Iterators safe |
 *   +----------------+   +----------------+
 *
 *   BlockingQueue       ConcurrentLinkedQueue
 *   +----------------+   +----------------+
 *   | Producer-consumer|   | Non-blocking   |
 *   | Blocks when full |   | Lock-free      |
 *   | Multiple impls   |   | Unbounded      |
 *   +----------------+   +----------------+
 */

public class Lesson21_ConcurrentCollections {

    public static void main(String[] args) throws Exception {
        System.out.println("=== CONCURRENT COLLECTIONS ===\n");

        // ============================================================
        // EXAMPLE 1: ConcurrentHashMap
        // ============================================================
        System.out.println("--- Example 1: ConcurrentHashMap ---\n");

        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        // Multiple threads putting
        Thread[] putters = new Thread[5];
        for (int i = 0; i < putters.length; i++) {
            final int id = i;
            putters[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    concurrentMap.merge("key-" + id, 1, Integer::sum);
                }
            });
        }

        for (Thread t : putters) t.start();
        for (Thread t : putters) t.join();

        System.out.println("ConcurrentHashMap size: " + concurrentMap.size());
        System.out.println("Total entries: " + concurrentMap.values().stream().mapToInt(Integer::intValue).sum());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: ConcurrentHashMap compute methods
        // ============================================================
        System.out.println("--- Example 2: Atomic Operations ---\n");

        ConcurrentHashMap<String, AtomicInteger> atomicMap = new ConcurrentHashMap<>();

        atomicMap.computeIfAbsent("counter", k -> new AtomicInteger(0));
        atomicMap.computeIfPresent("counter", (k, v) -> {
            v.incrementAndGet();
            return v;
        });

        System.out.println("Counter value: " + atomicMap.get("counter").get());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: CopyOnWriteArrayList
        // ============================================================
        System.out.println("--- Example 3: CopyOnWriteArrayList ---\n");

        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();

        // Multiple readers
        Thread[] readers = new Thread[3];
        for (int i = 0; i < readers.length; i++) {
            final int id = i;
            readers[i] = new Thread(() -> {
                for (String s : cowList) {
                    System.out.println("Reader-" + id + " saw: " + s);
                }
            });
        }

        // Writer
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                cowList.add("Item-" + i);
                System.out.println("Writer added: Item-" + i);
            }
        });

        writer.start();
        for (Thread t : readers) t.start();

        writer.join();
        for (Thread t : readers) t.join();

        System.out.println("Final list size: " + cowList.size());
        System.out.println();

        // ============================================================
        // EXAMPLE 4: BlockingQueue (ArrayBlockingQueue)
        // ============================================================
        System.out.println("--- Example 4: ArrayBlockingQueue ---\n");

        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    blockingQueue.put(i);
                    System.out.println("Produced: " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Integer value = blockingQueue.take();
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
        // EXAMPLE 5: LinkedBlockingQueue
        // ============================================================
        System.out.println("--- Example 5: LinkedBlockingQueue ---\n");

        BlockingQueue<String> linkedQueue = new LinkedBlockingQueue<>();

        Thread producer2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    linkedQueue.put("Task-" + i);
                    System.out.println("Linked producer: Task-" + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    String task = linkedQueue.take();
                    System.out.println("Linked consumer: " + task);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer2.start();
        consumer2.start();
        producer2.join();
        consumer2.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: PriorityBlockingQueue
        // ============================================================
        System.out.println("--- Example 6: PriorityBlockingQueue ---\n");

        BlockingQueue<Integer> priorityQueue = new PriorityBlockingQueue<>();

        Thread priorityProducer = new Thread(() -> {
            int[] values = {5, 1, 3, 2, 4};
            for (int v : values) {
                priorityQueue.put(v);
                System.out.println("Priority producer: " + v);
            }
        });

        Thread priorityConsumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Integer value = priorityQueue.take();
                    System.out.println("Priority consumer (sorted): " + value);
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
        // EXAMPLE 7: SynchronousQueue
        // ============================================================
        System.out.println("--- Example 7: SynchronousQueue ---\n");

        BlockingQueue<Integer> syncQueue = new SynchronousQueue<>();

        Thread syncConsumer = new Thread(() -> {
            try {
                System.out.println("Sync consumer waiting...");
                Integer value = syncQueue.take();
                System.out.println("Sync consumer got: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread syncProducer = new Thread(() -> {
            try {
                Thread.sleep(100);
                System.out.println("Sync producer putting: 42");
                syncQueue.put(42);
                System.out.println("Sync producer done");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        syncConsumer.start();
        syncProducer.start();
        syncConsumer.join();
        syncProducer.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 8: ConcurrentLinkedQueue
        // ============================================================
        System.out.println("--- Example 8: ConcurrentLinkedQueue ---\n");

        ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>();

        Thread[] clqThreads = new Thread[3];
        for (int i = 0; i < clqThreads.length; i++) {
            final int id = i;
            clqThreads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    clq.add(id * 100 + j);
                }
            });
        }

        for (Thread t : clqThreads) t.start();
        for (Thread t : clqThreads) t.join();

        System.out.println("ConcurrentLinkedQueue size: " + clq.size());
        System.out.println();
    }
}
