package com.aiyush.taskmanager.controller;

import com.aiyush.taskmanager.dto.TaskResponseDTO;
import com.aiyush.taskmanager.entity.Task;
import com.aiyush.taskmanager.entity.User;
import com.aiyush.taskmanager.dto.TaskRequestDto;
import com.aiyush.taskmanager.mapper.TaskMapper;
import com.aiyush.taskmanager.repository.TaskRepository;
import com.aiyush.taskmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // Create task
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDto requestDTO, Principal principal) {
        User user = getCurrentUser(principal);
        Task task = TaskMapper.toEntity(requestDTO, user);
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(TaskMapper.toDTO(savedTask));
    }

    // Get all tasks for current user
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks(Principal principal) {
        User user = getCurrentUser(principal);
        List<TaskResponseDTO> tasks = taskRepository.findByUserUsername(user.getUsername()).stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    // Get single task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id, Principal principal) {
        User user = getCurrentUser(principal);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty() || !optionalTask.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TaskMapper.toDTO(optionalTask.get()));
    }

    // Update task
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id,
                                                      @RequestBody TaskRequestDto requestDTO,
                                                      Principal principal) {
        User user = getCurrentUser(principal);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty() || !optionalTask.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        Task task = optionalTask.get();
        TaskMapper.updateTask(task, requestDTO);
        Task updatedTask = taskRepository.save(task);

        return ResponseEntity.ok(TaskMapper.toDTO(updatedTask));
    }

    // Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Principal principal) {
        User user = getCurrentUser(principal);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty() || !optionalTask.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.delete(optionalTask.get());
        return ResponseEntity.noContent().build();
    }

    // Helper method
    private User getCurrentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
