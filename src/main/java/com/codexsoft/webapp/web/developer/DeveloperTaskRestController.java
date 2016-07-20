package com.codexsoft.webapp.web.developer;

import com.codexsoft.webapp.model.Task;
import com.codexsoft.webapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/developer/tasks")
public class DeveloperTaskRestController {
    @Autowired
    private TaskService taskService;
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public List<Task> getTasksByProject(@PathVariable Long projectId){
        return taskService.findAllByProjectId(projectId);
    }
}
