package com.aiyush.taskmanager.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aiyush.taskmanager.entity.Task;
import com.aiyush.taskmanager.entity.TaskStatus;
import com.aiyush.taskmanager.repository.TaskRepository;
import com.aiyush.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp(){
        task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.TODO);
        task.setTitle("Test Task");
        task.setDescription("Test description");
    }

    @Test
    public void testCreateTask(){
        when(taskRepository.save(task)).thenReturn(task);
        Task created = taskService.createTask(task);

        assertNotNull(created);
        assertEquals("Test Task",created.getTitle());
        verify(taskRepository,times(1)).save(task);
    }


    @Test
    public void getTaskById_FOUND(){
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        Optional<Task> found = taskService.getTaskById(task.getId());

        assertTrue(found.isPresent());

        assertEquals("Test Task",found.get().getTitle());

    }

    @Test
    public void getTaskById_NOT_FOUND(){
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Task> found = taskService.getTaskById(2L);

        assertFalse(found.isPresent());

    }

}
