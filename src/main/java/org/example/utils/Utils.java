package org.example.utils;

import org.example.model.Task;

import java.util.Set;

public class Utils {

    public static Long getNewId(Set<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .reduce(Long::max)
                .orElse(0L) + 1;
    }
}
