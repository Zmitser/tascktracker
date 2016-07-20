package com.codexsoft.webapp.web.developer;

import com.codexsoft.webapp.model.Task;
import com.codexsoft.webapp.service.TaskService;
import com.codexsoft.webapp.util.exception.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/developer/tasks")
public class DeveloperTaskRestController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public List<Task> getTasksByProject(@PathVariable Long projectId){
        return taskService.findAllByProjectId(projectId);
    }
    @RequestMapping(value = "/task/save", method = RequestMethod.POST)
    public void changeStatus(@RequestBody @Valid Task task,
                               BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        status.setComplete();
        try {
                Task existing = taskService.findOne(task.getTaskId());
                copyNonNullProperties(task, existing);
                taskService.update(existing);
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
