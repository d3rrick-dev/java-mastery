package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Scheduling & Async Processing
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. @Scheduled
 * 2. Cron expressions
 * 3. Async execution
 * 4. @Async
 * 5. Task executors
 * 6. Background job processing
 * 7. Thread pools
 */

public class Lesson09_SpringSchedulingAsync {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING SCHEDULING & ASYNC PROCESSING ===
            
            1. @SCHEDULED
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               @Scheduled enables method execution at specified intervals
               or cron expressions.
            
               WHY IT EXISTS:
               - Scheduled task execution
               - No external scheduler needed
               - Integration with Spring lifecycle
            
               INTERNAL MECHANICS:
                   - ScheduledAnnotationBeanPostProcessor processes @Scheduled
                   - ScheduledTaskRegistrar manages task scheduler
                   - ScheduledExecutorService executes tasks
                   - TaskScheduler abstraction for different implementations
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableScheduling
                   public class SchedulingConfig {
                       @Bean
                       public TaskScheduler taskScheduler() {
                           ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
                           scheduler.setPoolSize(10);
                           scheduler.setThreadNamePrefix("scheduled-task-");
                           return scheduler;
                       }
                   }
                   
                   @Component
                   public class CleanupService {
                       @Scheduled(fixedRate = 3600000) // Every hour
                       public void cleanupExpiredSessions() {
                           sessionRepository.deleteExpired();
                       }
                       
                       @Scheduled(cron = "0 0 2 * * ?") // Daily at 2 AM
                       public void generateDailyReport() {
                           reportService.generate();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A notification service:
                   - @Scheduled(fixedRate = 60000) checks for pending notifications
                   - @Scheduled(cron = "0 0 0 * * MON-FRI") sends daily reports
                   - @Scheduled(cron = "0 */5 * * * *") runs every 5 minutes for cleanup
            
               INTERVIEW QUESTION:
                   "What is the difference between fixedRate and fixedDelay?
                   How do you handle exceptions in scheduled tasks?"
            
               COMMON MISTAKES:
                   - Not enabling @EnableScheduling
                   - Long-running tasks blocking subsequent executions
                   - Not handling exceptions (task stops)
            
               PERFORMANCE & SCALABILITY:
                   - Use thread pool for concurrent tasks
                   - Consider misfire instructions
                   - Monitor task execution time
            
            ─────────────────────────────────────────────────────────────────────
            
            2. CRON EXPRESSIONS
               ─────────────────────────────────────────────────────────────────────
               CRON FORMAT:
                   second minute hour day-of-month month day-of-week
                   
                   * * * * * * 
                   | | | | | |
                   | | | | | +-- day of week (0-7, 0 and 7 are Sunday)
                   | | | | +---- month (1-12)
                   | | | +------ day of month (1-31)
                   | | +-------- hour (0-23)
                   | +---------- minute (0-59)
                   +------------ second (0-59)
            
               EXAMPLES:
                   "0 0 12 * * ?"           Daily at noon
                   "0 0 0 * * ?"            Daily at midnight
                   "0 0/30 9-17 * * ?"      Every 30 min, 9 AM to 5 PM
                   "0 0 0 1 1 ?"            Every Jan 1st
                   "0 0 0 1 * *"            First day of every month
                   "0 0 0 * * 1"            Every Monday
                   "0 0/5 9-17 * * MON-FRI" Every 5 min, Mon-Fri, 9 AM to 5 PM
            
               MISFIRE INSTRUCTIONS:
                   - MISFIRE_INSTRUCTION_CANCEL: Skip missed execution
                   - MISFIRE_INSTRUCTION_IGNORE: Skip and continue
                   - MISFIRE_INSTRUCTION_EXECUTE: Run immediately
            
               SIMPLE EXAMPLE:
                   @Scheduled(cron = "0 0/10 * * * *", 
                              cron::spring.task.scheduling.pool.size=10)
                   public void runEvery10Seconds() { ... }
            
               INTERVIEW QUESTION:
                   "Write a cron expression for every weekday at 9 AM.
                   How do you handle misfired tasks?"
            
               COMMON MISTAKES:
                   - Incorrect cron syntax
                   - Not testing cron expressions
                   - Not handling timezone
            
            ─────────────────────────────────────────────────────────────────────
            
            3. @ASYNC
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               @Async enables asynchronous method execution, returning
               immediately while method runs in background.
            
               WHY IT EXISTS:
               - Non-blocking operations
               - Better resource utilization
                   - Improved responsiveness
            
               INTERNAL MECHANICS:
                   - AsyncExecutionInterceptor intercepts @Async methods
                   - TaskExecutor runs in separate thread
                   - AsyncUncaughtExceptionHandler handles exceptions
                   - Proxy-based, self-invocation doesn't work
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableAsync
                   public class AsyncConfig {
                       @Bean(name = "taskExecutor")
                       public Executor taskExecutor() {
                           ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
                           executor.setCorePoolSize(5);
                           executor.setMaxPoolSize(10);
                           executor.setQueueCapacity(100);
                           executor.setThreadNamePrefix("async-task-");
                           executor.setRejectedExecutionHandler(new CallerRunsPolicy());
                           return executor;
                       }
                   }
                   
                   @Service
                   public class EmailService {
                       @Async("taskExecutor")
                       public void sendEmail(Email email) {
                           // Long-running email sending
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A file processing service:
                   - @Async processes large files in background
                   - Returns immediately with job ID
                   - Client polls for completion status
            
               INTERVIEW QUESTION:
                   "How does @Async work internally? What happens if
                   the thread pool is exhausted?"
            
               COMMON MISTAKES:
                   - Not enabling @EnableAsync
                   - Self-invocation bypasses async
                   - Not handling exceptions
                   - Not configuring thread pool
            
               PERFORMANCE & SCALABILITY:
                   - Thread pool size affects throughput
                   - Queue size prevents memory exhaustion
                   - Consider rejection policies
            
            ─────────────────────────────────────────────────────────────────────
            
            4. TASK EXECUTORS
               ─────────────────────────────────────────────────────────────────────
               EXECUTOR TYPES:
                   - SimpleAsyncTaskExecutor: New thread per task
                   - ThreadPoolTaskExecutor: Configurable thread pool
                   - WorkManagerTaskExecutor: JCA WorkManager
                   - ConcurrentTaskExecutor: Existing ExecutorService
                   - TaskExecutorAdapter: ExecutorService to TaskExecutor
            
               CONFIGURATION:
                   @Bean
                   public Executor taskExecutor() {
                       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
                       executor.setCorePoolSize(10);
                       executor.setMaxPoolSize(50);
                       executor.setQueueCapacity(200);
                       executor.setKeepAliveSeconds(60);
                       executor.setThreadNamePrefix("business-");
                       executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
                       executor.setWaitForTasksToCompleteOnShutdown(true);
                       executor.setAwaitTerminationSeconds(30);
                       return executor;
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A notification service uses separate executors:
                   - emailExecutor: 5 threads, 100 queue
                   - smsExecutor: 10 threads, 50 queue
                   - pushExecutor: 20 threads, 200 queue
            
               INTERVIEW QUESTION:
                   "How do you choose the right thread pool size?
                   What is the difference between core and max pool size?"
            
               COMMON MISTAKES:
                   - Not setting queue capacity
                   - Not handling rejection
                   - Not considering task duration
            
               PERFORMANCE & SCALABILITY:
                   - Monitor thread pool metrics
                   - Use bounded queues
                   - Consider task prioritization
            
            ─────────────────────────────────────────────────────────────────────
            
            5. BACKGROUND JOB PROCESSING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Background jobs handle long-running or deferred work
               outside the request-response cycle.
            
               PATTERNS:
                   - Fire-and-forget: @Async
                   - Polling: Scheduled tasks
                   - Queue-based: Spring Integration, RabbitMQ
                   - Database-backed: Job repository
            
               SIMPLE EXAMPLE:
                   @Entity
                   public class Job {
                       @Id
                       private String id;
                       private String type;
                       private String payload;
                       private JobStatus status;
                       private Instant createdAt;
                       private Instant completedAt;
                   }
                   
                   @Service
                   public class JobProcessor {
                       @Scheduled(fixedDelay = 1000)
                       @Transactional
                       public void processPendingJobs() {
                           List<Job> jobs = jobRepository.findTop10ByStatus(JobStatus.PENDING);
                           jobs.forEach(this::processJob);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A document processing service:
                   - Jobs stored in database
                   - Worker threads process jobs
                   - Status updates for monitoring
                   - Retry mechanism for failures
            
               INTERVIEW QUESTION:
                   "How do you ensure exactly-once processing in background jobs?
                   What is the difference between @Scheduled and @Async?"
            
               COMMON MISTAKES:
                   - Not handling job failures
                   - Not implementing retry logic
                   - Not considering idempotency
            
               PERFORMANCE & SCALABILITY:
                   - Consider job partitioning
                   - Monitor queue depth
                   - Use multiple workers
            
               ALTERNATIVES:
                   - Quartz scheduler
                   - Spring Batch
                   - Message queues
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Scheduling & Async enable:
            - Background processing
            - Scheduled tasks
            - Non-blocking operations
            - Scalable task execution
            """);
    }
}