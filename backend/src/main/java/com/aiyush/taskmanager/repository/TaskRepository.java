package com.aiyush.taskmanager.repository;

import com.aiyush.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findUserById(Long userId);

    List<Task> findByUserUsername(String username);

    Optional<Task> findByIdAndUserUsername(Long id,String username);

}
