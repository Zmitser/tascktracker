package com.codexsoft.webapp.repository;

import com.codexsoft.webapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT t FROM Task t WHERE t.project.projectId=:projectId")
    List<Task> findAllByProjectId(@Param("projectId")Long projectId);
}
