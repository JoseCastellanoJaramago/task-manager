package com.tu.task_manager_backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tu.task_manager_backend.model.Task;
import com.tu.task_manager_backend.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setTitle("Nueva tarea");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.create(task);

        assertNotNull(result);
        assertEquals("Nueva tarea", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task();
        task1.setTitle("Tarea 1");

        Task task2 = new Task();
        task2.setTitle("Tarea 2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAll();

        assertEquals(2, tasks.size());
        assertEquals("Tarea 1", tasks.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Tarea buscada");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Tarea buscada", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }
}
