package com.flix.hr_system.service;

import com.flix.hr_system.entity.Task;
import com.flix.hr_system.event.TaskEvent;
import com.flix.hr_system.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TaskService(TaskRepository taskRepository, RedisTemplate<String, Object> redisTemplate) {
        this.taskRepository = taskRepository;
        this.redisTemplate = redisTemplate;
    }

    public void create(TaskEvent taskEvent) {
        Task task = new Task();
        task.setId(String.valueOf(taskEvent.getId()));
        task.setTitle(taskEvent.getTitle());
        task.setStartDate(taskEvent.getStartDate());
        task.setEndDate(taskEvent.getEndDate());
        task.setStatus(taskEvent.getStatus());
        taskRepository.save(task);

        String key = "taskSortedByStartDate";
        double startTime = task.getStartDate().toEpochSecond(ZoneOffset.UTC);
        redisTemplate.opsForZSet().add(key, task.getId(), startTime);

    }

    public List<Task> findAllTask() {
        List<Task> taskList = new ArrayList<>();
        taskRepository.findAll().forEach(taskList::add);
        return taskList;
    }

    public Set<Object> getTasksByStartDateRange(LocalDateTime start, LocalDateTime end) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

        double startTime = start.toEpochSecond(ZoneOffset.UTC);
        double endTime = end.toEpochSecond(ZoneOffset.UTC);
        // Range query based on startDate
        return zSetOperations.rangeByScore("taskSortedByStartDate", startTime, endTime);
    }

}