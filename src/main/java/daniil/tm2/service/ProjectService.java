package daniil.tm2.service;

import daniil.tm2.api.repository.IDomainRepository;
import daniil.tm2.api.repository.IProjectRepository;
import daniil.tm2.api.service.IProjectService;
import daniil.tm2.entity.Project;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Denis Volnenko
 */
@Service
@Transactional
public class ProjectService implements IProjectService {

    @Autowired
    private IProjectRepository projectRepository;
    
    @Autowired
    private IDomainRepository domainRepository;

    @Override
    public Project createProject(final String name, final String domainName) {
        return projectRepository.save(
                Optional.ofNullable(name)
                        .filter(n -> !n.isEmpty())
                        .flatMap(n -> Optional.of(new Project(n, Optional.ofNullable(domainName)
                                .filter(dn -> !dn.isEmpty())
                                .flatMap(dn -> domainRepository.findById(dn))
                                .orElseThrow(() -> new IllegalArgumentException(
                                        "Invalid domain name or domain not found!")))))
                        .orElseThrow(() -> new IllegalArgumentException("Invalid name of project to create: " + name))
                        );
    }

    @Override
    public Project merge(final Project project) {
        return projectRepository.save(
                Optional.ofNullable(project)
                        .orElseThrow(() -> new IllegalArgumentException("The project to merge is null!"))
        );
    }

    @Override
    public Project getProjectById(final String id) {
        return projectRepository.findById(
                Optional.ofNullable(id)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid id of project to find: " + id))
        ).orElse(null);
    }

    @Override
    public void removeProjectById(final String id) {
        projectRepository.delete(Optional.ofNullable(id)
                        .filter(i -> !i.isEmpty())
                        .flatMap(i -> projectRepository.findById(i))
                        .flatMap(p -> {
                            p.getDomain().getProjects().remove(p);
                            return Optional.of(p);
                        })
                        .orElseThrow(() -> new IllegalArgumentException("Invalid project id or no such project!"))
                );
    }

    @Override
    public List<Project> getListProject() {
        return projectRepository.findAll();
    }

    @Override
    public void clear() {
        projectRepository.deleteAll();
    }

    @Override
    public void merge(Project... projects) {
        Arrays.stream(projects)
                .map(proj -> Optional.ofNullable(proj).orElseThrow(
                        () -> new IllegalArgumentException("Cannot merge null project!")
                ))
                .forEach(this::merge);
    }

}
