package com.tu.task_manager_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tu.task_manager_backend.model.Task;
import com.tu.task_manager_backend.repository.TaskRepository;
import com.tu.task_manager_backend.service.TaskService;

@SpringBootTest
class TaskManagerBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Mock
    TaskRepository repo;
    @InjectMocks
    TaskService service;

    @Test
    void createTask_callsRepoSave() {
        Task t = new Task();
        t.setTitle("Prueba");
        when(repo.save(any(Task.class))).thenAnswer(i -> {
            ((Task) i.getArgument(0)).setId(1L);
            return i.getArgument(0);
        });

        Task saved = service.createTask(t);

        verify(repo, times(1)).save(t);
        assertEquals(1L, saved.getId());
    }

    @Test
    void getAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(new Task()));
        List<Task> list = service.getAll();
        assertFalse(list.isEmpty());


    }
