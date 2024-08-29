package org.example.repository;

import org.example.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskRepositoryImpl implements TaskRepository{


    private final Path filePath;

    public TaskRepositoryImpl(Path filePath) {
        this.filePath = filePath;
    }


    @Override
    public Set<Task> loadTask() throws IOException {

        if(!Files.exists(filePath)){
            return new HashSet<>();
        }
        return Mapper.jsonToTasks(Files.readString(filePath));

    }

    @Override
    public void saveTasks(Set<Task> tasks) throws IOException {

        if(!Files.exists(filePath)){
            Files.createFile(filePath);
        }
        Files.writeString(filePath, Mapper.tasksToJson(tasks));
    }
}
