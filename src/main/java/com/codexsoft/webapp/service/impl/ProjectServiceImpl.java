package com.codexsoft.webapp.service.impl;

import com.codexsoft.webapp.model.Project;
import com.codexsoft.webapp.repository.ProjectRepository;
import com.codexsoft.webapp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Override
    public Project create(Project project) {
        return repository.save(project);
    }

    @Override
    public Project findOne(Long commentId) {
        return repository.findOne(commentId);
    }

    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }

//    @Override
//    public List<Project> findAllByUserId(Long userId) {
//        return repository.findAllByUserId(userId);
//    }

    @Override
    public void delete(Long projectId) {
        repository.delete(projectId);
    }

    @Override
    public void update(Project project) {
        repository.save(project);
    }
}
