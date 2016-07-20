package com.codexsoft.webapp.web.developer;

import com.codexsoft.webapp.model.Project;
import com.codexsoft.webapp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/developer/projects")
public class DeveloperProjectRestController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Project> getAll(){
        return  projectService.findAll();
    }
}
