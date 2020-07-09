package daniil.tm2.controller.crud;

import daniil.tm2.api.service.IDomainService;
import daniil.tm2.api.service.IProjectService;
import daniil.tm2.entity.Domain;
import daniil.tm2.entity.Project;
import java.util.ArrayList;
import java.util.Optional;
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
        return projectService.getProjectById(
                Optional.ofNullable(name)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid project name: " + name))
        );
    }

    @PostMapping(value = "/saveProject", consumes="application/json")
    public void saveProject(@RequestBody Project project, @RequestParam String domainName) {
        
        // The deserialized Project has no domain set (as this would cause cyclic references in serialization).
        // This is why we need first to define the appropriate domain for this project,
        // before we actually persist it.
        domainService.merge(
                Optional.ofNullable(domainName)
                        .filter(n -> !n.isEmpty())
                        .flatMap(n -> Optional.of(Optional.ofNullable(domainService.findByName(n))
                                .orElse(new Domain(n, new ArrayList<>()))))
                        .flatMap(dom -> {
                            dom.getProjects().add(Optional.ofNullable(project)
                                    .orElseThrow(() -> new IllegalArgumentException("Project is null!")));
                            project.getTasks().forEach(t -> t.setProject(project));
                            project.setDomain(dom);
                            return Optional.of(dom);
                        }).orElseThrow(() -> new IllegalArgumentException("Invalid domain name: " + domainName))
        );
    }

    @DeleteMapping("/deleteProject/{name}")
    public void deleteProject(@PathVariable String name) {
        projectService.removeProjectById(
                Optional.ofNullable(name)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid project name: " + name))
        );
    }
}
