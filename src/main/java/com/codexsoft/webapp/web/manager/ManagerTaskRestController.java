package com.codexsoft.webapp.web.manager;

import com.codexsoft.webapp.model.Project;
import com.codexsoft.webapp.model.Task;
import com.codexsoft.webapp.model.User;
import com.codexsoft.webapp.service.ProjectService;
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
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/rest/manager/tasks")
public class ManagerTaskRestController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/task/delete/{taskId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long taskId){
         taskService.delete(taskId);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public Task get(@PathVariable Long taskId){
        return taskService.findOne(taskId);
    }

    @RequestMapping(value = "/task/save", method = RequestMethod.POST)
    public void createOrUpdate(@RequestBody @Valid Task task,
                               BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        status.setComplete();
        try {
            if (task.getTaskId() == null) {
                User user = userService.findOne(task.getUser().getUserId());
                Project project = projectService.findOne(task.getProject().getProjectId());
                task.setProject(project);
                task.setUser(user);
                taskService.create(task);
            } else {
                Task existing = taskService.findOne(task.getTaskId());
                copyNonNullProperties(task, existing);
                taskService.update(existing);
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
