package repository;

import entity.Task;

import java.util.List;

public interface TaskRepository {
    Task findById(Long id);
    void deleteById(Long id);
    List<Task> findAllTasks();
    Task saveTask(Task task);
}
