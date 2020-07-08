package daniil.tm2.controller.crud;

import daniil.tm2.api.service.IDomainService;
import daniil.tm2.api.service.IProjectService;
import daniil.tm2.entity.Domain;
import daniil.tm2.entity.Project;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectCRUDController {

    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private IDomainService domainService;

    @GetMapping(value = "/findProject/{name}", produces = "application/json")
    public Project findByName(@PathVariable String name) {
        return projectService.getProjectById(name);
    }

    @PostMapping(value = "/saveProject", consumes="application/json")
    public void saveProject(@RequestBody Project project, @RequestParam String domainName) {
        
        // The deserialized Project has no domain set (as this would cause cyclic references in serialization).
        // This is why we need first to define the appropriate domain for this project,
        // before we actually persist it.
        Domain domain = domainService.findByName(domainName);
        if (domain == null) {
            domain = new Domain();
            domain.setName(domainName);
            domain.setProjects(new ArrayList<>());
        }
        domain.getProjects().add(project);
        project.setDomain(domain);
        project.getTasks().forEach(t -> {
            t.setProject(project);
        });
        domainService.merge(domain);
    }

    @DeleteMapping("/deleteProject/{name}")
    public void deleteProject(@PathVariable String name) {
        projectService.removeProjectById(name);
    }
}
