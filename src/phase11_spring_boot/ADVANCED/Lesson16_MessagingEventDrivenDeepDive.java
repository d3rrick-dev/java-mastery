package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Messaging & Event-Driven Systems Deep Dive
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. RabbitMQ
 * 2. Kafka
 * 3. Event-driven architecture
 * 4. Message ordering
 * 5. Retry mechanisms
 * 6. Dead letter queues
 * 7. Idempotency
 */

public class Lesson16_MessagingEventDrivenDeepDive {
    public static void main(String[] args) {
        System.out.println("""
            === MESSAGING & EVENT-DRIVEN SYSTEMS DEEP DIVE ===
            
            1. RABBITMQ
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               RabbitMQ is a message broker implementing AMQP, providing
               reliable message delivery with flexible routing.
            
               WHY IT EXISTS:
               - Reliable message delivery
               - Flexible routing (exchanges, queues)
               - Message acknowledgments
               - Dead letter exchanges
            
               INTERNAL MECHANICS:
                   - ConnectionFactory creates connections
                   - RabbitAdmin declares exchanges/queues
                   - RabbitTemplate sends/receives messages
                   - SimpleMessageListenerContainer handles listeners
            
               SIMPLE EXAMPLE:
                   @Configuration
                   public class RabbitMQConfig {
                       @Bean
                       public Queue orderQueue() {
                           return QueueBuilder.durable("orders")
                               .withArgument("x-dead-letter-exchange", "orders.dlx")
                               .build();
                       }
                       
                       @Bean
                       public DirectExchange orderExchange() {
                           return new DirectExchange("orders");
                       }
                   }
                   
                   @Service
                   public class OrderPublisher {
                       private final RabbitTemplate rabbitTemplate;
                       
                       public void publishOrder(Order order) {
                           rabbitTemplate.convertAndSend("orders", order);
                       }
                   }
                   
                   @Component
                   public class OrderListener {
                       @RabbitListener(queues = "orders")
                       public void handleOrder(Order order) {
                           // Process order
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An order processing system:
                   - Orders published to "orders" queue
                   - Multiple consumers for parallel processing
                   - Dead letter queue for failed orders
                   - Retry with exponential backoff
            
               INTERVIEW QUESTION:
                   "How do you ensure message delivery in RabbitMQ?
                   What is the difference between direct and topic exchange?"
            
               COMMON MISTAKES:
                   - Not acknowledging messages
                   - Not handling connection failures
                   - Not configuring dead letter queues
            
               PERFORMANCE & SCALABILITY:
                   - Use connection pooling
                   - Consider message batching
                   - Monitor queue depth
            
            ─────────────────────────────────────────────────────────────────────
            
            2. KAFKA
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Kafka is a distributed streaming platform, providing
               high-throughput, fault-tolerant message processing.
            
               WHY IT EXISTS:
               - High throughput
               - Fault tolerance
               - Real-time processing
               - Event sourcing
            
               INTERNAL MECHANICS:
                   - KafkaTemplate sends messages
                   - @KafkaListener receives messages
                   - ConsumerFactory creates consumers
                   - ProducerFactory creates producers
                   - Offset management
            
               SIMPLE EXAMPLE:
                   @Configuration
                   public class KafkaConfig {
                       @Bean
                       public ProducerFactory<String, Order> producerFactory() {
                           Map<String, Object> config = new HashMap<>();
                           config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
                           config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                           config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
                           return new DefaultKafkaProducerFactory<>(config);
                       }
                   }
                   
                   @Service
                   public class OrderEventPublisher {
                       private final KafkaTemplate<String, Order> kafkaTemplate;
                       
                       public void publishOrderCreated(Order order) {
                           kafkaTemplate.send("orders", order.getId().toString(), order);
                       }
                   }
                   
                   @Component
                   public class OrderEventListener {
                       @KafkaListener(topics = "orders", groupId = "order-processing")
                       public void handleOrder(Order order) {
                           // Process order
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A real-time analytics pipeline:
                   - Events published to Kafka topics
                   - Stream processing with Kafka Streams
                   - Multiple consumer groups for different processing
                   - Exactly-once semantics
            
               INTERVIEW QUESTION:
                   "How do you ensure message ordering in Kafka?
                   What is the difference between at-most-once and exactly-once?"
            
               COMMON MISTAKES:
                   - Not configuring partitions correctly
                   - Not handling consumer rebalancing
                   - Not managing offsets properly
            
               PERFORMANCE & SCALABILITY:
                   - More partitions = higher throughput
                   - Consider replication factor
                   - Monitor consumer lag
            
            ─────────────────────────────────────────────────────────────────────
            
            3. EVENT-DRIVEN ARCHITECTURE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Event-driven architecture uses events to trigger
               and communicate between decoupled services.
            
               WHY IT EXISTS:
               - Loose coupling
               - Scalability
               - Resilience
               - Real-time processing
            
               PATTERNS:
                   - Event Notification: Event signals something happened
                   - Event Carrying State: Event contains data
                   - Event Sourcing: Events are source of truth
                   - CQRS: Separate read/write models
            
               SIMPLE EXAMPLE:
                   // Event
                   public record OrderCreatedEvent(
                       Long orderId,
                       String customerId,
                       BigDecimal amount,
                       List<Item> items
                   ) {}
                   
                   // Publisher
                   @Service
                   public class OrderService {
                       private final ApplicationEventPublisher events;
                       
                       public Order createOrder(OrderRequest request) {
                           Order order = orderRepository.save(new Order(request));
                           events.publishEvent(new OrderCreatedEvent(
                               order.getId(),
                               order.getCustomerId(),
                               order.getAmount(),
                               order.getItems()
                           ));
                           return order;
                       }
                   }
                   
                   // Handler
                   @Component
                   public class OrderEventHandler {
                       @EventListener
                       @Async
                       public void handle(OrderCreatedEvent event) {
                           inventoryService.reserveItems(event.items());
                           paymentService.charge(event.customerId(), event.amount());
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An e-commerce system:
                   - Order service publishes events
                   - Inventory service listens and reserves
                   - Payment service listens and charges
                   - Notification service sends emails
                   - Each service independent
            
               INTERVIEW QUESTION:
                   "What are the benefits of event-driven architecture?
                   How do you handle event ordering across services?"
            
               COMMON MISTAKES:
                   - Not making event handlers idempotent
                   - Not handling event duplication
                   - Not considering eventual consistency
            
               PERFORMANCE & SCALABILITY:
                   - Events enable horizontal scaling
                   - Consider message ordering
                   - Monitor event processing lag
            
            ─────────────────────────────────────────────────────────────────────
            
            4. MESSAGE ORDERING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Message ordering ensures events are processed in
               the correct sequence for consistency.
            
               WHY IT EXISTS:
               - Data consistency
               - Business logic correctness
               - Audit trail integrity
            
               STRATEGIES:
                   - Single partition: Kafka guarantees order
                   - Message sequencing: Add sequence number
                   - Event versioning: Include version/timestamp
                   - Idempotent processing: Handle out-of-order
            
               SIMPLE EXAMPLE:
                   // Kafka with single partition for ordering
                   @KafkaListener(topics = "user-events", 
                                  groupId = "user-processing",
                                  properties = {"partition.assignment.strategy=range"})
                   public void handleUserEvent(UserEvent event) {
                       // Events for same user key are ordered
                   }
                   
                   // Or with sequencing
                   public record SequencedEvent<T>(
                       long sequence,
                       T payload
                   ) {}
            
               REAL-WORLD BACKEND EXAMPLE:
                   A bank account service:
                   - All events for same account go to same partition
                   - Events processed in order
                   - Balance always consistent
            
               INTERVIEW QUESTION:
                   "How do you ensure message ordering in a distributed system?
                   What is the trade-off between ordering and scalability?"
            
               COMMON MISTAKES:
                   - Not considering ordering requirements
                   - Too much ordering (limits scalability)
                   - Not handling out-of-order events
            
               PERFORMANCE & SCALABILITY:
                   - Ordering limits parallelism
                   - Consider partitioning strategy
                   - Use idempotent processing
            
            ─────────────────────────────────────────────────────────────────────
            
            5. RETRY MECHANISMS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Retry mechanisms handle transient failures,
               improving system resilience.
            
               WHY IT EXISTS:
               - Handle transient failures
               - Improve reliability
               - Prevent data loss
            
               STRATEGIES:
                   - Fixed delay: Wait same time between retries
                   - Exponential backoff: Increasing delays
                   - Random backoff: Add jitter
                   - Max attempts: Limit retry count
            
               SIMPLE EXAMPLE:
                   @Retryable(
                       value = {SQLException.class, RemoteException.class},
                       maxAttempts = 3,
                       backoff = @Backoff(delay = 1000, multiplier = 2)
                   )
                   public void processOrder(Order order) {
                       // May fail transiently
                   }
                   
                   @Recover
                   public void recover(SQLException e, Order order) {
                       // Handle after all retries exhausted
                       deadLetterService.send(order);
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment service:
                   - Retry on network timeout
                   - Exponential backoff (1s, 2s, 4s)
                   - Max 3 attempts
                   - Dead letter queue for persistent failures
            
               INTERVIEW QUESTION:
                   "How do you implement retry with exponential backoff?
                   What is the circuit breaker pattern?"
            
               COMMON MISTAKES:
                   - Not limiting retry attempts
                   - Not handling retry exhaustion
                   - Retrying non-transient errors
            
               PERFORMANCE & SCALABILITY:
                   - Retries add latency
                   - Consider async retry
                   - Monitor retry rate
            
            ─────────────────────────────────────────────────────────────────────
            
            6. DEAD LETTER QUEUES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Dead letter queues (DLQ) store messages that cannot
               be processed after retries.
            
               WHY IT EXISTS:
               - Prevent message loss
               - Enable manual intervention
               - Debugging failed messages
            
               SIMPLE EXAMPLE:
                   @Bean
                   public Queue orderQueue() {
                       return QueueBuilder.durable("orders")
                           .withArgument("x-dead-letter-exchange", "orders.dlx")
                           .withArgument("x-message-ttl", 60000) // 1 min TTL
                           .withArgument("x-max-length", 1000)
                           .build();
                   }
                   
                   @RabbitListener(queues = "orders")
                   public void handleOrder(Order order) {
                       try {
                           processOrder(order);
                       } catch (Exception e) {
                           // Message goes to DLQ after max retries
                           throw new AmqpRejectAndRequeueException("Failed", e);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment processing system:
                   - Failed payments go to DLQ
                   - Manual review for suspicious transactions
                   - Alerting on DLQ depth
                   - Reprocessing capability
            
               INTERVIEW QUESTION:
                   "How do you configure a dead letter queue in RabbitMQ?
                   What is the difference between reject and requeue?"
            
               COMMON MISTAKES:
                   - Not monitoring DLQ
                   - Not handling DLQ messages
                   - Infinite requeue loops
            
               PERFORMANCE & SCALABILITY:
                   - DLQ prevents queue blockage
                   - Consider DLQ processing
                   - Monitor DLQ size
            
            ─────────────────────────────────────────────────────────────────────
            
            7. IDEMPOTENCY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Idempotent operations produce the same result
               regardless of how many times they're executed.
            
               WHY IT EXISTS:
               - Handle duplicate messages
               - Retry safety
               - Distributed system reliability
            
               STRATEGIES:
                   - Idempotent keys: Unique operation identifier
                   - Deduplication: Track processed messages
                   - Upsert: Insert or update
                   - State-based: Check current state
            
               SIMPLE EXAMPLE:
                   @Entity
                   public class Payment {
                       @Id
                       private String idempotencyKey; // Unique per payment attempt
                       private String status;
                       private BigDecimal amount;
                   }
                   
                   @Service
                   public class PaymentService {
                       public Payment processPayment(PaymentRequest request) {
                           return paymentRepository.findByIdempotencyKey(request.getIdempotencyKey())
                               .orElseGet(() -> {
                                   Payment payment = new Payment(request);
                                   return paymentRepository.save(payment);
                               });
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment API:
                   - Client provides idempotency key
                   - Server checks if already processed
                   - Returns existing result if duplicate
                   - Prevents double charges
            
               INTERVIEW QUESTION:
                   "How do you implement idempotency in a REST API?
                   What is the difference between idempotency and
                   exactly-once processing?"
            
               COMMON MISTAKES:
                   - Not considering idempotency
                   - Weak idempotency keys
                   - Not handling concurrent requests
            
               PERFORMANCE & SCALABILITY:
                   - Idempotency check adds overhead
                   - Consider caching idempotency keys
                   - Use efficient deduplication
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Messaging and event-driven systems enable:
            - Loose coupling
            - Scalability
            - Resilience
            - Real-time processing
            """);
    }
}