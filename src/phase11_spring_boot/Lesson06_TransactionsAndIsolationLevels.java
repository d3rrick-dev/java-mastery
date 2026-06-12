package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Transactions and Isolation Levels
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson06_TransactionsAndIsolationLevels {
    public static void main(String[] args) {
        System.out.println("""
                === TRANSACTIONS AND ISOLATION LEVELS ===

                1. @TRANSACTIONAL ANNOTATION
                     @Transactional
                     public void transferMoney(Long fromId, Long toId, BigDecimal amount) {
                         User from = repo.findById(fromId).orElseThrow();
                         User to = repo.findById(toId).orElseThrow();
                         from.setBalance(from.getBalance().subtract(amount));
                         to.setBalance(to.getBalance().add(amount));
                         repo.save(from);
                         repo.save(to);
                     }
                   // If exception occurs, both saves are rolled back

                2. ISOLATION LEVELS
                   - READ_UNCOMMITTED: Dirty reads possible
                   - READ_COMMITTED: No dirty reads (default in most DBs)
                   - REPEATABLE_READ: No dirty/non-repeatable reads
                   - SERIALIZABLE: Full isolation, no anomalies

                3. PROPAGATION BEHAVIORS
                   - REQUIRED: Join existing or create new (default)
                   - REQUIRES_NEW: Always create new, suspend existing
                   - SUPPORTS: Join if exists, else non-transactional
                   - NOT_SUPPORTED: Always non-transactional
                   - MANDATORY: Must join existing, error if none
                   - NEVER: Must not be transactional, error if exists

                4. READ-ONLY TRANSACTIONS
                     @Transactional(readOnly = true)
                     public List<User> findAll() {
                         return repo.findAll();
                     }
                   // Optimization: no dirty checking, no flush

                BACKEND REAL-WORLD EXAMPLE:
                   - Bank transfer: withdraw from A, deposit to B
                   - If deposit fails, withdraw is rolled back
                   - Uses REQUIRED propagation
                   - Uses SERIALIZABLE for financial transactions

                COMMON MISTAKES:
                   - Not using @Transactional on public methods only
                   - Calling @Transactional method from same class (self-invocation)
                   - Using default isolation without understanding trade-offs
                   - Not handling rollback for checked exceptions (rollbackFor)
                   - Long-running transactions causing locks
                """);
    }
}
