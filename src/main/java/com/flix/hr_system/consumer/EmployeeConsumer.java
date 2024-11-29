package com.flix.hr_system.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {
    @KafkaListener(topics = "employee-topic", groupId = "hr-employee-consumer")
    public void consumeEmployee(String message) {
        System.out.println("Received message: " + message);
    }
}
