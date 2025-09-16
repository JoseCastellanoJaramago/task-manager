package com.tu.task_manager_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tu.task_manager_backend.model.Task;
import com.tu.task_manager_backend.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    // Actualizar una tarea
    public Task update(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setDueDate(updatedTask.getDueDate());
                    task.setPriority(updatedTask.getPriority());
                    task.setStatus(updatedTask.getStatus());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    // Eliminar una tarea
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
