package com.codexsoft.webapp.service.impl;

import com.codexsoft.webapp.model.Comment;
import com.codexsoft.webapp.repository.CommentRepository;
import com.codexsoft.webapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Override
    public Comment create(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public Comment findOne(Long commentId) {
        return repository.findOne(commentId);
    }

    @Override
    public List<Comment> findAllByTaskId(Long taskId) {
        return repository.findAllByTaskId(taskId);
    }

    @Override
    public void delete(Long commentId) {
        repository.delete(commentId);
    }

    @Override
    public void update(Comment comment) {
        repository.save(comment);
    }
}
