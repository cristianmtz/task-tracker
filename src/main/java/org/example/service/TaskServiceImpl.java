package org.example.service;

import org.example.model.Status;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.example.utils.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TaskServiceImpl implements TaskService{

    private final Set<Task> tasks;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) throws IOException {
        this.tasks = taskRepository.loadTask();
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(String description){

        Task task = new Task(
                Utils.getNewId(tasks),
                description,
                Status.NOT_DONE,
                LocalDateTime.now(),
                LocalDateTime.now());
        tasks.add(task);
    }

    @Override
    public void deleteTask(Long id){

        Task isTaskFound = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

        tasks.remove(isTaskFound);
    }

    @Override
    public void saveTasks() {

        try {
            taskRepository.saveTasks(tasks);
            System.out.println("Saving the tasks..");
        }catch (IOException e){
            System.out.println("Something went wrong and couldn't save the tasks!");
        }
    }


    @Override
    public void uptadeTask(Long id, String description)  {

        Task isTaskPresent = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));


        Task updatedTask = new Task(
                Utils.getNewId(tasks),
                description,
                isTaskPresent.getStatus(),
                isTaskPresent.getCreatedAt(),
                LocalDateTime.now());

        tasks.remove(isTaskPresent);
        tasks.add(updatedTask);

    }

    @Override
    public void markTaskInProgress(Long id)  {

        markTaskStatus(id, Status.IN_PROGRESS);
    }

    @Override
    public void markTaskAsDone(Long id)  {
        markTaskStatus(id, Status.DONE);
    }

    @Override
    public void listAllTask(String... status) {
        List<Task> filteredTasks;

        if (status.length > 0 && !status[0].isEmpty()) {
            try {
                Status filter = Status.valueOf(status[0].toUpperCase());
                filteredTasks = tasks.stream()
                        .filter(task -> task.getStatus() == filter)
                        .sorted()
                        .toList();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status: " + status[0]);
                // If invalid status, return an empty list or handle as needed
                filteredTasks = List.of();
            }
        } else {
            // No status filter provided, display all tasks
            filteredTasks = tasks.stream()
                    .sorted()
                    .toList();
        }

        // Print the tasks
        filteredTasks.forEach(System.out::println);
    }

    private void markTaskStatus(Long id, Status newStatus)  {

        Task taskFound  = findTask(id);

        Task task = new Task(
                taskFound.getId(),
                taskFound.getDescription(),
                newStatus,
                taskFound.getCreatedAt(),
                taskFound.getUpdatedAt());

        tasks.remove(taskFound);
        tasks.add(task);

    }


    private Task findTask(Long id){
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

}


