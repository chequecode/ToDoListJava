package controller;

import entity.Task;
import entity.TaskStatus;
import exception.TaskNotFoundException;
import service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TaskController {
    private final TaskService taskService;
    private final Scanner scanner;

    public TaskController(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("enter command (add, list, edit, delete, filter, sort, exit): ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "add" -> addTask();
                    case "list" -> printTasks(taskService.getAllTasks());
                    case "edit" -> {
                        if (parts.length < 2 || parts[1].isEmpty()) {
                            System.out.println("example: edit [id]");
                            break;
                        }
                        Long id = parseLong(parts[1]);
                        editTask(id);
                        System.out.println("task edited");
                    }
                    case "delete" -> {
                        if (parts.length < 2 || parts[1].isEmpty()) {
                            System.out.println("example: delete [id]");
                            break;
                        }
                        Long id = parseLong(parts[1]);
                        taskService.deleteTask(id);
                        System.out.println("task deleted");
                    }
                    case "filter" -> {
                        if (parts.length < 2 || parts[1].isEmpty()) {
                            System.out.println("example: filter [done/todo/in_progress]");
                            break;
                        }
                        TaskStatus status = parseStatus(parts[1]);
                        printTasks(taskService.filterByStatus(status));
                    }
                    case "sort" -> printTasks(taskService.sortByStatus());
                    case "exit" -> {
                        System.out.println("exiting");
                        return;
                    }
                    default -> System.out.println("unknown command");
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
                break;
            }
        }
    }

    private void addTask() {
        Task task = taskDetails(new Task(null, "", "", null, TaskStatus.TODO));
        taskService.createTask(task);
        System.out.println("task added " + task);
    }

    private void editTask(Long id) throws TaskNotFoundException {
        Task taskToEdit = taskService.getById(id);
        taskDetails(taskToEdit);
        taskService.updateTask(id, taskToEdit);
        System.out.println("task updated " + taskToEdit);
    }

    private Task taskDetails(Task task) {
        System.out.println("task name: ");
        task.setName(scanner.nextLine());

        System.out.println("task description: ");
        task.setDescription(scanner.nextLine());

        System.out.println("deadline (year-MM-dd): ");
        String date = scanner.nextLine();
        if (!date.isEmpty()) {
            try {
                task.setDeadline(LocalDate.parse(date));
            } catch (DateTimeParseException e) {
                System.out.println("unknown date");
            }
        }

        System.out.println("status [TODO/IN_PROGRESS/DONE]: ");
        String status = scanner.nextLine().toUpperCase();
        if (!status.isEmpty()) {
            try {
                task.setTaskStatus(TaskStatus.valueOf(status));
            } catch (IllegalArgumentException e) {
                System.out.println("unknown status");
            }
        }
        return task;
    }

    private void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("no tasks found");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private TaskStatus parseStatus(String str) {
        try {
            return TaskStatus.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("wrong status");
        }
    }

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("wrong id");
        }
    }
}
