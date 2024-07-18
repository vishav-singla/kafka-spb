package com.vishav.kafkapp.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.vishav.kafkapp.model.User;

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