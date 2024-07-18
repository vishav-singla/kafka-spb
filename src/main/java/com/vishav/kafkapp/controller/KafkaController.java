package com.vishav.kafkapp.controller;

import com.vishav.kafkapp.model.User;
import com.vishav.kafkapp.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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