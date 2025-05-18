package com.aiyush.taskmanager.dto;

import com.aiyush.taskmanager.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class TaskRequestDto {

    private String title;
    private String description;
    private TaskStatus status;

}
