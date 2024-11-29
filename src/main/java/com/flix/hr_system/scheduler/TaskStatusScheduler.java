package com.flix.hr_system.scheduler;

import com.flix.hr_system.entity.Task;
import com.flix.hr_system.service.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class TaskStatusScheduler {
    private final TaskService taskService;

    public TaskStatusScheduler(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(fixedDelay = 1000*10)
    public void printTaskStatus() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime start = localDateTime.withMinute(0).withSecond(0);
        LocalDateTime end = localDateTime.withMinute(0).withSecond(0).plusHours(1);
        Set<Object> tasks = taskService.getTasksByStartDateRange(start, end);

        System.out.println(tasks);
    }
}
