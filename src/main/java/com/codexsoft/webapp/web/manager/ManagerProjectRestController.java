package com.codexsoft.webapp.web.manager;

import com.codexsoft.webapp.model.Project;
import com.codexsoft.webapp.service.ProjectService;
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
@RequestMapping("/rest/manager/projects")
public class ManagerProjectRestController {

    @Autowired
    private ProjectService projectService;




    @RequestMapping(value = "/project/delete/{projectId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long projectId){
        projectService.delete(projectId);
    }

    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
    public Project get(@PathVariable Long projectId){
        return projectService.findOne(projectId);
    }

    @RequestMapping(value = "/project/save",method = RequestMethod.POST)
    public void createOrUpdate(@RequestBody @Valid Project project, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        status.setComplete();
        try {
            if (project.getProjectId() == null) {
                projectService.create(project);
            } else {
                Project existing = projectService.findOne(project.getProjectId());
                copyNonNullProperties(project, existing);
                projectService.update(existing);
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
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
