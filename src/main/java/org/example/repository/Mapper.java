package org.example.repository;

import org.example.model.Status;
import org.example.model.Task;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class Mapper {

    public static Set<Task> jsonToTasks(String json){
        if (json == null || json.isEmpty()){
            return new HashSet<>();
        }

        return Arrays.stream(json.substring(2, json.length() - 2).split("},\\{"))
                .map(Mapper::jsonToTask)
                .collect(toSet());
    }

    private static Task jsonToTask(String json){
        String[] keyValuesPairs = json.replaceAll("\"","").split(",\\s*");

        long id = 0L;
        String description = null;
        Status status = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        for (String keyValuePair: keyValuesPairs){
            String key = keyValuePair.substring(0, keyValuePair.indexOf(":")).trim();
            String value = keyValuePair.substring(keyValuePair.indexOf(":") + 1).trim();
            switch (key) {
                case "id" -> id = Long.parseLong(value);
                case "description" -> description = value;
                case "status" -> status = Status.valueOf(value);
                case "createdTime" -> createdAt = LocalDateTime.parse(value);
                case "updatedTime" -> updatedAt = LocalDateTime.parse(value);
            }

        }

        return  new Task(id, description, status, createdAt, updatedAt);

    }


    private static String taskToJson(Task task) {
        return "{\n" +
                "\t\"id\": \"" + task.getId() + "\",\n" +
                "\t\"description\": \"" + task.getDescription() + "\",\n" +
                "\t\"status\": \"" + task.getStatus() + "\",\n" +
                "\t\"createdTime\": \"" + task.getCreatedAt() + "\",\n" +
                "\t\"updatedTime\": \"" + task.getUpdatedAt() + "\"\n" +
                "}";
    }


    public static String tasksToJson(Set<Task> tasks){
        if(tasks.isEmpty()){
            return "[]";
        }
        return tasks.stream()
                .map(Mapper::taskToJson)
                .collect(joining(",", "[","]"));
    }


}
