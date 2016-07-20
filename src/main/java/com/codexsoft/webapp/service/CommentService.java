package com.codexsoft.webapp.service;


import com.codexsoft.webapp.model.Comment;

import java.util.List;

public interface CommentService {

    Comment create(Comment comment);
    Comment findOne(Long commentId);
    List<Comment> findAllByTaskId(Long taskId);
    void delete(Long commentId);
    void update(Comment comment);

}
