package org.example.service;

import java.io.IOException;

public interface TaskService {


    void addTask(String description) ;

    void deleteTask(Long id) ;

    void saveTasks() ;

     void listAllTask(String... status);

     void uptadeTask(Long id, String description);

     void markTaskInProgress(Long id) ;

    void markTaskAsDone(Long id) ;

}
