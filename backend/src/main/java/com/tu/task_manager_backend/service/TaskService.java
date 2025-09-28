package com.tu.task_manager_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tu.task_manager_backend.dto.TaskRequest;
import com.tu.task_manager_backend.model.Task;
import com.tu.task_manager_backend.model.User;
import com.tu.task_manager_backend.repository.TaskRepository;
import com.tu.task_manager_backend.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Crear una tarea
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    // Obtener todas las tareas
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    // Buscar una tarea por ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Actualizar una tarea
    public Task update(Long id, TaskRequest req) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(req.getTitle());
                    task.setDescription(req.getDescription());
                    task.setDueDate(req.getDueDate());
                    task.setPriority(req.getPriority());
                    task.setStatus(req.getStatus());

                    User user = userRepository.findById(req.getUserId())
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + req.getUserId()));
                    task.setUser(user);

                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id " + id));
    }

    // Eliminar una tarea
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Task createTaskFromRequest(TaskRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id " + req.getUserId()));

        Task t = new Task();
        t.setTitle(req.getTitle());
        t.setDescription(req.getDescription());
        t.setDueDate(req.getDueDate());
        t.setPriority(req.getPriority());
        t.setStatus(req.getStatus());
        t.setCompleted(false);
        t.setUser(user);

        return taskRepository.save(t);
    }

    public Task updateFromRequest(Long id, TaskRequest req) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task no encontrada con id " + id));

        if (req.getTitle() != null) {
            existing.setTitle(req.getTitle());
        }
        existing.setDescription(req.getDescription());
        existing.setDueDate(req.getDueDate());
        existing.setPriority(req.getPriority());
        existing.setStatus(req.getStatus());

        if (req.getUserId() != null) {
            User user = userRepository.findById(req.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id " + req.getUserId()));
            existing.setUser(user);
        }

        return taskRepository.save(existing);
    }
}
