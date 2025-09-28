package com.tu.task_manager_backend.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tu.task_manager_backend.dto.TaskRequest;
import com.tu.task_manager_backend.model.Task;
import com.tu.task_manager_backend.model.User;
import com.tu.task_manager_backend.repository.TaskRepository;
import com.tu.task_manager_backend.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;

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

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent()); // primero verificamos que hay algo dentro
        assertEquals("Tarea buscada", result.get().getTitle()); // usamos get() para acceder
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void createTaskFromRequest_setsAllFieldsCorrectly() {
        User user = new User();
        user.setId(1L);

        TaskRequest req = new TaskRequest();
        req.setTitle("Nueva tarea");
        req.setDescription("Descripción de la tarea");
        req.setDueDate(LocalDate.of(2025, 9, 30));
        req.setPriority(3);
        req.setStatus("PENDIENTE");
        req.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Task result = taskService.createTaskFromRequest(req);

        // Assert
        assertEquals("Nueva tarea", result.getTitle());
        assertEquals("Descripción de la tarea", result.getDescription());
        assertEquals(LocalDate.of(2025, 9, 30), result.getDueDate());
        assertEquals(3, result.getPriority());
        assertEquals("PENDIENTE", result.getStatus());
        assertFalse(result.getCompleted()); // siempre debe quedar en false
        assertEquals(user, result.getUser());

        verify(taskRepository, times(1)).save(any(Task.class));
        verify(userRepository, times(1)).findById(1L);
    }
}
