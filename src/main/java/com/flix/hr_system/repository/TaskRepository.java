package com.flix.hr_system.repository;

import com.flix.hr_system.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, String> {
}
