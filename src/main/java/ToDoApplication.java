import controller.TaskController;
import repository.MemoryTaskRepository;
import repository.TaskRepository;
import service.TaskService;

import java.util.Scanner;

public class ToDoApplication {
    public static void main(String[] args) {
        TaskRepository taskRepository = new MemoryTaskRepository();
        TaskService taskService = new TaskService(taskRepository);
        Scanner scanner = new Scanner(System.in);
        TaskController taskController = new TaskController(taskService, scanner);
        taskController.run();
        scanner.close();
    }
}
