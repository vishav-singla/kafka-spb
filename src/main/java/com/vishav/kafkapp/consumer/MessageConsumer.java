package com.vishav.kafkapp.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.vishav.kafkapp.model.User;

@Service
public class MessageConsumer {

    @KafkaListener(topics = "kafka-demo", groupId = "my-group-id")
    public void listen(User user) {
        System.out.println("Received message: " + user);
    }
}
