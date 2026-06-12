package phase7;

import java.util.concurrent.locks.*;

/**
 * LESSON 20: READWRITELOCK
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * ReadWriteLock maintains two locks: one for reading and one for writing.
 * Multiple threads can read simultaneously, but only one thread can write.
 * When writing, no one can read or write.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Read-heavy workloads benefit from concurrent reads
 * - synchronized allows only one thread at a time
 * - Better performance for read-mostly data
 * - Writers get exclusive access
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Library book:
 * - Multiple people can read the same book
 * - Only one person can write in it
 * - When someone writes, no one can read
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Configuration cache:
 * - 1000 threads reading config (concurrent reads)
 * - 1 thread updating config (exclusive write)
 * - ReadWriteLock allows all readers simultaneously
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is ReadWriteLock?"
 * Answer: Lock with separate read and write locks,
 *         allows concurrent reads, exclusive writes
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using write lock for reads (defeats the purpose)
 * - Not releasing locks in finally block
 * - Upgrading read lock to write lock (deadlock!)
 * - Using fair lock unnecessarily
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Read lock: very fast (just a counter increment)
 * - Write lock: exclusive, blocks all
 * - Best for read-heavy (80%+ reads)
 * - Writer starvation possible with many readers
 *
 * ============================================================
 * READWRITELOCK MODES
 * ============================================================
 *
 *   Read Lock              Write Lock
 *   +----------------+     +----------------+
 *   | Multiple       |     | Single         |
 *   | threads        |     | thread         |
 *   | Shared access  |     | Exclusive      |
 *   | No modification|     | Can modify     |
 *   +----------------+     +----------------+
 *   | readLock()     |     | writeLock()    |
 *   +----------------+     +----------------+
 */

public class Lesson20_ReadWriteLock {

    public static void main(String[] args) throws Exception {
        System.out.println("=== READWRITELOCK ===\n");

        // ============================================================
        // EXAMPLE 1: Basic ReadWriteLock
        // ============================================================
        System.out.println("--- Example 1: Basic ReadWriteLock ---\n");

        ReadWriteData data = new ReadWriteData();

        // Multiple readers
        Thread reader1 = new Thread(() -> data.read("Reader-1"));
        Thread reader2 = new Thread(() -> data.read("Reader-2"));
        Thread reader3 = new Thread(() -> data.read("Reader-3"));

        // One writer
        Thread writer = new Thread(() -> data.write("Writer", 42));

        reader1.start();
        reader2.start();
        reader3.start();

        Thread.sleep(100);
        writer.start();

        reader1.join();
        reader2.join();
        reader3.join();
        writer.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Concurrent reads
        // ============================================================
        System.out.println("--- Example 2: Concurrent Reads ---\n");

        ReadWriteData concurrentData = new ReadWriteData();
        concurrentData.write("Initial", 100);

        Thread[] readers = new Thread[5];
        for (int i = 0; i < readers.length; i++) {
            final int id = i;
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    concurrentData.read("Reader-" + id);
                }
            });
        }

        for (Thread t : readers) t.start();
        for (Thread t : readers) t.join();

        System.out.println("All readers completed concurrently!\n");

        // ============================================================
        // EXAMPLE 3: Write blocks reads
        // ============================================================
        System.out.println("--- Example 3: Write Blocks Reads ---\n");

        ReadWriteData blockingData = new ReadWriteData();
        blockingData.write("Initial", 0);

        Thread writer2 = new Thread(() -> {
            blockingData.write("Writer-2", 100);
        });

        Thread readerDuringWrite = new Thread(() -> {
            blockingData.read("Reader-During-Write");
        });

        writer2.start();
        Thread.sleep(50);
        readerDuringWrite.start();

        writer2.join();
        readerDuringWrite.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Read lock prevents write
        // ============================================================
        System.out.println("--- Example 4: Read Lock Prevents Write ---\n");

        ReadWriteData readPreventWrite = new ReadWriteData();
        readPreventWrite.write("Initial", 0);

        Thread longReader = new Thread(() -> {
            readPreventWrite.read("Long-Reader");
        });

        Thread waitingWriter = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            readPreventWrite.write("Waiting-Writer", 200);
        });

        longReader.start();
        waitingWriter.start();

        longReader.join();
        waitingWriter.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: StampedLock (Java 8+)
        // ============================================================
        System.out.println("--- Example 5: StampedLock ---\n");

        StampedLock stampedLock = new StampedLock();
        StampedData stampedData = new StampedData(stampedLock);

        Thread stampedReader = new Thread(() -> {
            long stamp = stampedLock.readLock();
            try {
                System.out.println("Stamped reader: value = " + stampedData.getValue());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                stampedLock.unlockRead(stamp);
            }
        });

        Thread stampedWriter = new Thread(() -> {
            long stamp = stampedLock.writeLock();
            try {
                stampedData.setValue(999);
                System.out.println("Stamped writer: set value = 999");
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        });

        stampedReader.start();
        stampedWriter.start();
        stampedReader.join();
        stampedWriter.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Optimistic read with StampedLock
        // ============================================================
        System.out.println("--- Example 6: Optimistic Read ---\n");

        StampedLock optLock = new StampedLock();
        StampedData optData = new StampedData(optLock);

        // Optimistic read (no lock, just a stamp)
        long stamp = optLock.tryOptimisticRead();
        int value = optData.getValue();
        System.out.println("Optimistic read: value = " + value);

        // Validate stamp
        if (!optLock.validate(stamp)) {
            System.out.println("Stamp invalidated, falling back to read lock");
            stamp = optLock.readLock();
            try {
                value = optData.getValue();
                System.out.println("Read lock value: " + value);
            } finally {
                optLock.unlockRead(stamp);
            }
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 7: When to use ReadWriteLock
        // ============================================================
        System.out.println("--- Example 7: When to Use ReadWriteLock ---\n");

        System.out.println("Use ReadWriteLock when:");
        System.out.println("1. Read operations significantly outnumber writes");
        System.out.println("2. Reads are frequent and writes are rare");
        System.out.println("3. Data is read-heavy (caches, configs)");
        System.out.println();
        System.out.println("Don't use when:");
        System.out.println("1. Write operations are frequent");
        System.out.println("2. Operations are quick (overhead not worth it)");
        System.out.println("3. Fairness is critical (use fair lock)");
    }

    // ============================================================
    // HELPER CLASSES
    // ============================================================

    static class ReadWriteData {
        private int data = 0;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();

        public void read(String readerName) {
            readLock.lock();
            try {
                System.out.println(readerName + " reading: " + data);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                readLock.unlock();
            }
        }

        public void write(String writerName, int value) {
            writeLock.lock();
            try {
                System.out.println(writerName + " writing: " + value);
                data = value;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                writeLock.unlock();
            }
        }
    }

    static class StampedData {
        private int value;
        private final StampedLock lock;

        public StampedData(StampedLock lock) {
            this.lock = lock;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
