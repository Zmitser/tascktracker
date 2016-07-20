package com.codexsoft.webapp.service.impl;

import com.codexsoft.webapp.model.Task;
import com.codexsoft.webapp.repository.TaskRepository;
import com.codexsoft.webapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;

    @Override
    public Task create(Task task) {
        return repository.save(task);
    }

    @Override
    public Task findOne(Long taskId) {
        return repository.findOne(taskId);
    }

    @Override
    public List<Task> findAllByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId);
    }


    @Override
    public void delete(Long taskId) {
        repository.delete(taskId);
    }

    @Override
    public void update(Task task) {
        repository.save(task);
    }

    @Override
    @Transactional
    public void enable(Long id, boolean completed) {
        Task task = findOne(id);
        task.setIsCompleted(completed);
        repository.save(task);
    }
}
