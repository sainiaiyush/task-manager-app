package com.aiyush.taskmanager.mapper;

import com.aiyush.taskmanager.dto.TaskRequestDto;
import com.aiyush.taskmanager.dto.TaskResponseDTO;
import com.aiyush.taskmanager.entity.Task;
import com.aiyush.taskmanager.entity.User;

import java.time.LocalDateTime;

public class TaskMapper {

    public static Task toEntity(TaskRequestDto taskRequestDto, User user){

        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setStatus(taskRequestDto.getStatus());
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        return task;
    }

    public static TaskResponseDTO toDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }

    public static void updateTask(Task task, TaskRequestDto dto) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        // Don't modify createdAt
    }

}
