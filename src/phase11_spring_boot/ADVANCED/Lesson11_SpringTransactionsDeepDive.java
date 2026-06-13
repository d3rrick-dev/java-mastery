package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Transactions Deep Dive
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. ACID principles
 * 2. @Transactional
 * 3. Propagation levels
 * 4. Isolation levels
 * 5. Rollback behavior
 * 6. Transaction boundaries
 * 7. Common transactional pitfalls
 * 8. Distributed transaction concepts
 */

public class Lesson11_SpringTransactionsDeepDive {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING TRANSACTIONS DEEP DIVE ===
            
            1. ACID PRINCIPLES
               ─────────────────────────────────────────────────────────────────────
               ATOMICITY:
                   - All operations in transaction succeed or all fail
                   - No partial state changes
                   - Implemented via rollback
            
               CONSISTENCY:
                   - Database remains in valid state
                   - Constraints and rules enforced
                   - Business invariants maintained
            
               ISOLATION:
                   - Concurrent transactions don't interfere
                   - Different levels: READ_UNCOMMITTED to SERIALIZABLE
                   - Trade-off: consistency vs performance
            
               DURABILITY:
                   - Committed changes persist
                   - Survives system failures
                   - Written to persistent storage
            
               INTERNAL MECHANICS:
                   - TransactionManager creates TransactionStatus
                   - TransactionInterceptor manages boundaries
                   - PlatformTransactionManager handles actual transactions
                   - DataSourceTransactionManager for single DB
                   - JtaTransactionManager for distributed transactions
            
               INTERVIEW QUESTION:
                   "Explain ACID properties in the context of database transactions.
                   How does Spring ensure atomicity?"
            
               COMMON MISTAKES:
                   - Not understanding isolation trade-offs
                   - Assuming transactions are free
                   - Not considering deadlock scenarios
            
            ─────────────────────────────────────────────────────────────────────
            
            2. @TRANSACTIONAL ANNOTATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               @Transactional enables declarative transaction management,
               wrapping method execution in a transaction.
            
               WHY IT EXISTS:
               - Clean separation of transaction logic
               - No boilerplate transaction code
               - Consistent transaction handling
            
               INTERNAL MECHANICS:
                   - TransactionInterceptor implements MethodInterceptor
                   - Creates/retrieves transaction via TransactionManager
                   - Commits on success, rolls back on exception
                   - Uses AOP proxy (self-invocation doesn't work)
            
               SIMPLE EXAMPLE:
                   @Service
                   public class TransferService {
                       @Transactional
                       public void transfer(Long fromId, Long toId, BigDecimal amount) {
                           Account from = accountRepository.findById(fromId);
                           Account to = accountRepository.findById(toId);
                           from.setBalance(from.getBalance().subtract(amount));
                           to.setBalance(to.getBalance().add(amount));
                           accountRepository.save(from);
                           accountRepository.save(to);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment service uses @Transactional with REQUIRES_NEW
                   to ensure each payment is independently committed, even
                   if the parent order creation fails.
            
               INTERVIEW QUESTION:
                   "Why must @Transactional be on public methods only?
                   What happens with self-invocation?"
            
               COMMON MISTAKES:
                   - @Transactional on private methods (ignored)
                   - Self-invocation bypasses proxy
                   - Not handling checked exceptions
            
               PERFORMANCE & SCALABILITY:
                   - Transactions hold database connections
                   - Keep transactions short
                   - Consider read-only transactions
            
            ─────────────────────────────────────────────────────────────────────
            
            3. PROPAGATION LEVELS
               ─────────────────────────────────────────────────────────────────────
               PROPAGATION TYPES:
                   - REQUIRED: Join existing or create new (default)
                   - REQUIRES_NEW: Always create new, suspend existing
                   - SUPPORTS: Join if exists, else non-transactional
                   - NOT_SUPPORTED: Always non-transactional
                   - NEVER: Must not be transactional
                   - MANDATORY: Must join existing
                   - NESTED: Execute in nested transaction
            
               INTERNAL MECHANICS:
                   - TransactionStatus holds transaction state
                   - PROPAGATION_REQUIRES_NEW suspends current transaction
                   - Nested transactions use savepoints
            
               SIMPLE EXAMPLE:
                   @Service
                   public class OrderService {
                       @Transactional
                       public void createOrder(Order order) {
                           orderRepository.save(order);
                           paymentService.processPayment(order.getId()); // New transaction
                       }
                   }
                   
                   @Service
                   public class PaymentService {
                       @Transactional(propagation = Propagation.REQUIRES_NEW)
                       public void processPayment(Long orderId) {
                           // Always runs in new transaction
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An audit service uses REQUIRES_NEW to ensure audit logs
                   are written even if the parent transaction rolls back.
            
               INTERVIEW QUESTION:
                   "When would you use REQUIRES_NEW vs REQUIRED?
                   What is the performance impact of transaction suspension?"
            
               COMMON MISTAKES:
                   - Not understanding transaction suspension
                   - Using REQUIRES_NEW unnecessarily
                   - Not considering deadlock scenarios
            
               PERFORMANCE & SCALABILITY:
                   - REQUIRES_NEW creates new transaction
                   - Transaction suspension has overhead
                   - Consider connection pool sizing
            
            ─────────────────────────────────────────────────────────────────────
            
            4. ISOLATION LEVELS
               ─────────────────────────────────────────────────────────────────────
               ISOLATION LEVELS:
                   - READ_UNCOMMITTED: Dirty reads possible
                   - READ_COMMITTED: No dirty reads (default in PostgreSQL)
                   - REPEATABLE_READ: No non-repeatable reads (MySQL default)
                   - SERIALIZABLE: Full isolation, no anomalies
            
               PHENOMENA:
                   - Dirty Read: Read uncommitted changes
                   - Non-Repeatable Read: Same query returns different results
                   - Phantom Read: New rows appear in result set
            
               SIMPLE EXAMPLE:
                   @Service
                   public class FinancialService {
                       @Transactional(isolation = Isolation.SERIALIZABLE)
                       public void transferFunds(Account from, Account to, BigDecimal amount) {
                           // Highest isolation for financial data
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A stock trading system uses SERIALIZABLE isolation for
                   trade execution to prevent race conditions, but uses
                   READ_COMMITTED for market data queries.
            
               INTERVIEW QUESTION:
                   "What isolation level does your database use by default?
                   How do you handle deadlocks?"
            
               COMMON MISTAKES:
                   - Not understanding database defaults
                   - Using too high isolation (performance impact)
                   - Not handling deadlock exceptions
            
               PERFORMANCE & SCALABILITY:
                   - Higher isolation = more locks = lower throughput
                   - Consider optimistic locking for high contention
                   - Use appropriate isolation per use case
            
            ─────────────────────────────────────────────────────────────────────
            
            5. ROLLBACK BEHAVIOR
               ─────────────────────────────────────────────────────────────────────
               DEFAULT BEHAVIOR:
                   - Unchecked exceptions (RuntimeException, Error) trigger rollback
                   - Checked exceptions do NOT trigger rollback
                   - Can be customized with rollbackFor/noRollbackFor
            
               SIMPLE EXAMPLE:
                   @Service
                   public class UserService {
                       @Transactional(rollbackFor = Exception.class)
                       public void createUser(User user) throws Exception {
                           userRepository.save(user);
                           notificationService.sendWelcome(user); // If this throws checked exception
                           // Transaction still rolls back
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A file processing service uses rollbackFor to ensure
                   database changes are rolled back if file operations fail.
            
               INTERVIEW QUESTION:
                   "How do you configure rollback for checked exceptions?
                   What is the difference between rollbackFor and noRollbackFor?"
            
               COMMON MISTAKES:
                   - Not understanding default rollback behavior
                   - rollbackFor = Exception.class (too broad)
                   - Not testing rollback scenarios
            
               PERFORMANCE & SCALABILITY:
                   - Transactions hold database connections
                   - Keep transactions short
                   - Consider read-only transactions
            
            ─────────────────────────────────────────────────────────────────────
            
            6. TRANSACTION BOUNDARIES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Transaction boundaries define where transactions start
               and end. In Spring, they're typically method boundaries.
            
               INTERNAL MECHANICS:
                   - TransactionSynchronizationManager holds thread-local state
                   - TransactionInterceptor creates boundaries
                   - TransactionStatus controls commit/rollback
            
               SIMPLE EXAMPLE:
                   @Service
                   public class OrderService {
                       @Transactional
                       public Order createOrder(OrderRequest request) {
                           Order order = new Order(request);
                           orderRepository.save(order);
                           // Transaction boundary ends here
                           return order;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A service method that calls multiple repository methods
                   within a single transaction, ensuring all changes are
                   atomic. If any repository call fails, all changes roll back.
            
               INTERVIEW QUESTION:
                   "How do you manage transaction boundaries across
                   multiple service calls? What is the recommended approach?"
            
               COMMON MISTAKES:
                   - Too wide transaction boundaries
                   - Not considering read-only transactions
                   - Long-running transactions
            
               PERFORMANCE & SCALABILITY:
                   - Keep transactions short
                   - Use read-only for queries
                   - Consider batch processing
            
            ─────────────────────────────────────────────────────────────────────
            
            7. COMMON TRANSACTIONAL PITFALLS
               ─────────────────────────────────────────────────────────────────────
               PITFALLS:
                   - Self-invocation bypasses proxy
                   - Not handling checked exceptions
                   - Too high isolation level
                   - Long-running transactions
                   - Not using read-only for queries
                   - Forgetting rollbackFor
                   - Not considering connection pool limits
            
               EXAMPLE - SELF INVOCATION:
                   @Service
                   public class UserService {
                       @Transactional
                       public void createUserAndSendEmail(User user) {
                           userRepository.save(user);
                           sendEmail(user); // This call bypasses proxy!
                       }
                       
                       @Transactional
                       public void sendEmail(User user) {
                           // Not actually transactional!
                       }
                   }
            
               SOLUTION:
                   @Service
                   public class UserService {
                       @Autowired
                       private UserService self; // Self-injection
                       
                       @Transactional
                       public void createUserAndSendEmail(User user) {
                           userRepository.save(user);
                           self.sendEmail(user); // Now goes through proxy
                       }
                   }
            
               INTERVIEW QUESTION:
                   "What is the self-invocation problem in Spring transactions?
                   How do you solve it?"
            
               COMMON MISTAKES:
                   - Not understanding proxy limitations
                   - Overusing self-injection
            
               PERFORMANCE & SCALABILITY:
                   - Transactions hold database connections
                   - Keep transactions short
                   - Consider read-only transactions
            
            ─────────────────────────────────────────────────────────────────────
            
            8. DISTRIBUTED TRANSACTION CONCEPTS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Distributed transactions span multiple databases or services,
               requiring coordination for atomicity.
            
               PATTERNS:
                   - Two-Phase Commit (2PC): XA transactions
                   - Saga: Compensating transactions
                   - Eventual Consistency: Accept temporary inconsistency
            
               SAGA PATTERN:
                   @Service
                   public class OrderSaga {
                       @Transactional
                       public void createOrder(Order order) {
                           try {
                               orderService.create(order);
                               paymentService.charge(order.getPayment());
                               inventoryService.reserve(order.getItems());
                           } catch (Exception e) {
                               // Compensate
                               orderService.cancel(order.getId());
                               throw e;
                           }
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An e-commerce order spans:
                   - Order service (creates order)
                   - Payment service (charges card)
                   - Inventory service (reserves items)
                   - Shipping service (schedules delivery)
                   - If any step fails, compensating actions undo previous steps
            
               INTERVIEW QUESTION:
                   "How do you handle distributed transactions in microservices?
                   What is the Saga pattern?"
            
               COMMON MISTAKES:
                   - Trying to use XA in microservices
                   - Not designing for eventual consistency
                   - Not handling partial failures
            
               PERFORMANCE & SCALABILITY:
                   - 2PC has high latency
                   - Saga allows independent scaling
                   - Eventual consistency improves performance
            
               ALTERNATIVES:
                   - Event-driven eventual consistency
                   - Outbox pattern
                   - Transactional messaging
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Transactions are essential for:
            - Data consistency
            - Error handling
            - Distributed systems
            - Performance optimization
            """);
    }
}