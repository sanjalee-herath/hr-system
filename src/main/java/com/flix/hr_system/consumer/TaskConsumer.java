package com.flix.hr_system.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flix.hr_system.event.TaskEvent;
import com.flix.hr_system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumer {
    private final ObjectMapper objectMapper;
    private final TaskService taskService;

    @Autowired
    public TaskConsumer(ObjectMapper objectMapper, TaskService taskService) {
        this.objectMapper = objectMapper;
        this.taskService = taskService;
    }

    @KafkaListener(topics = "task-topic", groupId = "hr-task-consumer")
    public void consumeTask(String message) throws JsonProcessingException {
        TaskEvent taskEvent = objectMapper.readValue(message, TaskEvent.class);
        taskService.create(taskEvent);
        //System.out.println(taskEvent);
    }
}
