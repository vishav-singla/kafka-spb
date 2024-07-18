package com.vishav.kafkapp.producer;

import org.springframework.kafka.core.KafkaTemplate;
import com.vishav.kafkapp.config.KafkaConfig;
import com.vishav.kafkapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public MessageProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, User user) {
        kafkaTemplate.send(topic, user);
    }
}