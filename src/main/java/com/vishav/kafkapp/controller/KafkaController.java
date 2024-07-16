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

    @PostMapping("/send")
    public String sendMessage(@RequestBody User user) {
        messageProducer.sendMessage("kafka-demo", user);
        return "Message sent: " + user;
    }
}
