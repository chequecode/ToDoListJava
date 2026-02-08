package service;

import entity.Task;
import entity.TaskStatus;
import exception.TaskNotFoundException;
import repository.TaskRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.saveTask(task);
    }

    public Task updateTask(Long id, Task taskDetails) throws TaskNotFoundException {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new TaskNotFoundException("task not found: " + id);
        }
        task.setId(taskDetails.getId());
        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());
        task.setDeadline(taskDetails.getDeadline());
        task.setTaskStatus(taskDetails.getTaskStatus());
        return taskRepository.saveTask(task);
    }
    public void deleteTask(Long id) throws TaskNotFoundException {
        taskRepository.deleteById(id);
    }

    public Task getById(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id);
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAllTasks();
    }

    public List<Task> filterByStatus(TaskStatus status) {
        return taskRepository.findAllTasks()
                .stream()
                .filter(task -> task.getTaskStatus() == status)
                .collect(Collectors.toList());
    }
    public List<Task> sortByStatus() {
        return taskRepository.findAllTasks()
                .stream()
                .sorted(Comparator.comparing(Task::getTaskStatus))
                .collect(Collectors.toList());
    }
}
