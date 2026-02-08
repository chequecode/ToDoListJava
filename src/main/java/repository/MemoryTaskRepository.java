package repository;

import entity.Task;
import exception.TaskNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryTaskRepository implements TaskRepository {
    private final Map<Long, Task> tasks = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Task findById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            try {
                throw new TaskNotFoundException("cant find task with " + id);
            } catch (TaskNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return task;
    }

    @Override
    public void deleteById(Long id) {
        if (!tasks.containsKey(id)) {
            try {
                throw new TaskNotFoundException("task not found: " + id);
            } catch (TaskNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        tasks.remove(id);
    }

    @Override
    public List<Task> findAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task saveTask(Task task) {
        if (task.getId() == null) {
            task.setId(nextId++);
        }
        tasks.put(task.getId(), task);
        return task;
    }
}
