package com.codexsoft.webapp.service;


import com.codexsoft.webapp.model.Task;

import java.util.List;

public interface TaskService {

    Task create(Task task);
    Task findOne(Long taskId);
    List<Task> findAllByProjectId(Long projectId);
    void delete(Long taskId);
    void update(Task task);
}
