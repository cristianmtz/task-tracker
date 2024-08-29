package org.example.repository;

import org.example.model.Task;

import java.io.IOException;
import java.util.Set;

public interface TaskRepository {

    Set<Task> loadTask() throws IOException;

    void saveTasks(Set<Task> tasks) throws  IOException;

}
