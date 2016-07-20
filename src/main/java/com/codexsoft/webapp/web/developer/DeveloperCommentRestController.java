package com.codexsoft.webapp.web.developer;

import com.codexsoft.webapp.LoggedUser;
import com.codexsoft.webapp.model.Comment;
import com.codexsoft.webapp.model.Task;
import com.codexsoft.webapp.model.User;
import com.codexsoft.webapp.service.CommentService;
import com.codexsoft.webapp.service.TaskService;
import com.codexsoft.webapp.service.UserService;
import com.codexsoft.webapp.util.exception.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/developer/comments")
public class DeveloperCommentRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public List<Comment> getCommentsByTask(@PathVariable Long taskId, Principal principal){
        return commentService.findAllByTaskId(taskId);
    }

    @RequestMapping(value = "/comment/delete/{commentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long commentId){
        commentService.delete(commentId);
    }

    @RequestMapping(value = "/comment/{commentId}", method = RequestMethod.GET)
    public Comment get(@PathVariable Long commentId){
        return commentService.findOne(commentId);
    }

    @RequestMapping(value = "/comment/save", method = RequestMethod.POST)
    public void createOrUpdate(@RequestBody @Valid Comment comment,
                               BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        status.setComplete();
        try {
            if (comment.getCommentId() == null) {
                Task task = taskService.findOne(comment.getTask().getTaskId());
                User byUsername = userService.findByUsername(LoggedUser.get().getUsername());
                comment.setTask(task);
                comment.setUser(byUsername);
                commentService.create(comment);
            } else {
                Comment existing = commentService.findOne(comment.getCommentId());
                copyNonNullProperties(comment, existing);
                commentService.update(existing);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data incorrect!");
        }
    }

    private static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
