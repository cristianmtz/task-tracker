package org.example;

import org.example.repository.TaskRepository;
import org.example.repository.TaskRepositoryImpl;
import org.example.service.TaskService;
import org.example.service.TaskServiceImpl;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public enum Command {
        ADD,
        UPDATE,
        DELETE,
        MARK_DONE,
        MARK_IN_PROGRESS,
        LIST,

        HELP,
        EXIT
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("ADD <description>              - Adds a new task with the given description.");
        System.out.println("UPDATE <id> <new_description>  - Updates the task with the given ID to have the new description.");
        System.out.println("DELETE <id>                    - Deletes the task with the given ID.");
        System.out.println("LIST [status]                  - Lists all tasks. Optionally filter by status (e.g., 'IN_PROGRESS').");
        System.out.println("MARK_IN_PROGRESS <id>          - Marks the task with the given ID as in progress.");
        System.out.println("MARK_DONE <id>                 - Marks the task with the given ID as done.");
        System.out.println("EXIT                           - Exits the application.");
        System.out.println("HELP                           - Shows this help message.");
    }


    public static void main(String[] args) throws IOException {

        final String PATH = "Task.json";

        TaskRepository taskRepository = new TaskRepositoryImpl(Path.of(PATH));
        TaskService taskService = new TaskServiceImpl(taskRepository);


        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;



        while (keepRunning){

            System.out.print("task-cli> ");
            String inputUser = scanner.nextLine().trim();

            String[] parts = inputUser.split("\\s+", 2); // Split only on the first space
            if (parts.length < 1) {
                System.out.println("No command entered.");
                continue;
            }

            String commandStr = parts[0].toUpperCase();
            String arguments = parts.length > 1 ? parts[1] : "";


            Command command;
            try {
                command = Command.valueOf(commandStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command: " + commandStr);
                System.out.println("Please enter a valid command. Type 'help' for a list of commands.");
                continue;
            }

            switch (command) {

                case ADD:
                    taskService.addTask(arguments);
                    break;
                case UPDATE:
                    String[] argParts = arguments.split("\\s+", 2);
                    Long updateId = Long.parseLong(argParts[0]);
                    String description = argParts[1];
                    taskService.uptadeTask(updateId, description);
                    break;
                case DELETE:
                    taskService.deleteTask(Long.parseLong(arguments));
                    break;
                case LIST:
                    taskService.listAllTask(arguments);
                    break;
                case MARK_IN_PROGRESS:
                    taskService.markTaskInProgress(Long.parseLong(arguments));
                    break;
                case MARK_DONE:
                    taskService.markTaskAsDone(Long.parseLong(arguments));
                    break;
                case HELP:
                    printHelp();
                    break;
                case EXIT:
                    taskService.saveTasks();
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}