package com.codexsoft.webapp.service;


import com.codexsoft.webapp.model.Project;

import java.util.List;

public interface ProjectService {

    Project create(Project project);
    Project findOne(Long commentId);
    List<Project> findAll();
//    List<Project> findAllByUserId(Long userId);
    void delete(Long projectId);
    void update(Project project);
}
