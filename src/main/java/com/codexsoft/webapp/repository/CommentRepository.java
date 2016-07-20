package com.codexsoft.webapp.repository;


import com.codexsoft.webapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query("SELECT c FROM Comment c WHERE c.task.taskId=:taskId")
    List<Comment> findAllByTaskId(@Param("taskId")Long taskId);
}
