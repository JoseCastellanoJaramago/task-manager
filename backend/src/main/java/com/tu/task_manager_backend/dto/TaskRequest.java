package com.tu.task_manager_backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede superar 255 caracteres")
    private String title;

    @Size(max = 2000, message = "La descripción es muy larga")
    private String description;

    private LocalDate dueDate;

    @Min(value = 0, message = "Priority no puede ser negativo")
    private Integer priority;

    @Size(max = 20)
    private String status;

    @NotNull(message = "El user.id es obligatorio")
    private Long userId;

    // getters y setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}