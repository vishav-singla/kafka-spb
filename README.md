<body>
    <h1>Kafka Application Structure</h1>

    <h2>application.yml</h2>
<pre>
spring:
application:
name: kafkapp
main:
allow-bean-definition-overriding: true

kafka:
bootstrap-servers: localhost:9092
consumer:
auto-offset-reset: earliest
key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
value-deserializer: org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
properties:
spring.deserializer.value.delegate.class: org.springframework.kafka.support.serializer.JsonDeserializer
spring.json.trusted.packages: "com.vishav.kafkapp.model"
producer:
key-serializer: org.apache.kafka.common.serialization.StringSerializer
value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
listener:
missing-topics-fatal: false
</pre>

    <h2>KafkaConfig.java</h2>
    <pre>
@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic topic1() {
        return new NewTopic("kafka-demo-1", 3, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("kafka-demo-2", 2, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("kafka-demo-3", 4, (short) 1);
    }

    @Bean
    public ProducerFactory&lt;String, Object&gt; producerFactory() {
        Map&lt;String, Object&gt; configProps = new HashMap&lt;&gt;();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory&lt;&gt;(configProps);
    }

    @Bean
    public KafkaTemplate&lt;String, Object&gt; kafkaTemplate() {
        return new KafkaTemplate&lt;&gt;(producerFactory());
    }
}
</pre>

    <h2>MessageConsumer.java</h2>
    <pre>
@Service
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @KafkaListener(topics = {"kafka-demo-1", "kafka-demo-2", "kafka-demo-3"}, 
                   groupId = "group-1")
    public void listenGroup1(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        logger.info("Group-1 received message from topic {}: {}", topic, user);
    }

    @KafkaListener(topics = {"kafka-demo-1", "kafka-demo-2"}, 
                   groupId = "group-2")
    public void listenGroup2(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        logger.info("Group-2 received message from topic {}: {}", topic, user);
    }

    @KafkaListener(topics = "kafka-demo-3", 
                   groupId = "group-3")
    public void listenGroup3(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        logger.info("Group-3 received message from topic {}: {}", topic, user);
    }
}
</pre>

    <h2>KafkaController.java</h2>
    <pre>
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send/topic1")
    public String sendMessageTopic1(@RequestBody User user) {
        messageProducer.sendMessage("kafka-demo-1", user);
        return "Message sent to kafka-demo-1: " + user;
    }

    @PostMapping("/send/topic2")
    public String sendMessageTopic2(@RequestBody User user) {
        messageProducer.sendMessage("kafka-demo-2", user);
        return "Message sent to kafka-demo-2: " + user;
    }

    @PostMapping("/send/topic3")
    public String sendMessageTopic3(@RequestBody User user) {
        messageProducer.sendMessage("kafka-demo-3", user);
        return "Message sent to kafka-demo-3: " + user;
    }
}
</pre>

    <h2>MessageProducer.java</h2>
    <pre>
@Component
public class MessageProducer {

    private final KafkaTemplate&lt;String, Object&gt; kafkaTemplate;

    @Autowired
    public MessageProducer(KafkaTemplate&lt;String, Object&gt; kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, User user) {
        kafkaTemplate.send(topic, user);
    }
}
</pre>

    <h2>User.java</h2>
    <pre>
public class User {
private String name;
private int age;

    // Getters and setters

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + "}";
    }
}
</pre>

    <h2>Testing Commands</h2>
    <pre>
# Send to topic1 (kafka-demo-1)
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"age\":30}" http://localhost:8080/api/kafka/send/topic1

# Send to topic2 (kafka-demo-2)
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Jane Doe\",\"age\":25}" http://localhost:8080/api/kafka/send/topic2

# Send to topic3 (kafka-demo-3)
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Bob Smith\",\"age\":40}" http://localhost:8080/api/kafka/send/topic3
</pre>
</body>
