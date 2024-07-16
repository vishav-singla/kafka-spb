package com.vishav.kafkapp.producer;

import com.vishav.kafkapp.config.KafkaConfig;
import com.vishav.kafkapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    @Autowired
    public MessageProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, User user) {
        kafkaTemplate.send(topic, user);
    }
}
