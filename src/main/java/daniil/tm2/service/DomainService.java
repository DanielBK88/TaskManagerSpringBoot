package daniil.tm2.service;

import daniil.tm2.api.repository.IDomainRepository;
import daniil.tm2.api.repository.IProjectRepository;
import daniil.tm2.api.repository.ITaskRepository;
import daniil.tm2.api.service.IDomainService;
import daniil.tm2.entity.Domain;
import daniil.tm2.entity.Project;
import daniil.tm2.entity.Task;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Denis Volnenko
 */
@Service
@Transactional
public class DomainService implements IDomainService {
    
    @Autowired
    private IProjectRepository projectRepository;
    
    @Autowired
    private ITaskRepository taskRepository;
    
    @Autowired
    private IDomainRepository domainRepository;
    
    @PostConstruct
    private void initDomain() {
        Domain domain = new Domain();
        domain.setName("MAIN DOMAIN");
        Project projectA = new Project();
        projectA.setName("Project A");
        projectA.setDomain(domain);
        Project projectB = new Project();
        projectB.setName("Project B");
        projectB.setDomain(domain);
        Task taskA1 = new Task();
        taskA1.setName("Task A1");
        taskA1.setProject(projectA);
        Task taskA2 = new Task();
        taskA2.setProject(projectA);
        taskA2.setName("Task A2");
        Task taskB1 = new Task();
        taskB1.setName("Task B1");
        taskB1.setProject(projectB);
        projectA.setTasks(Arrays.asList(taskA1, taskA2));
        projectB.setTasks(Arrays.asList(taskB1));
        domain.setProjects(Arrays.asList(projectA, projectB));
        domainRepository.save(domain);
    }

    @Override
    public void merge(Domain domain) {
        domainRepository.save(Optional.ofNullable(domain)
                .orElseThrow(() -> new IllegalArgumentException("The domain to merge is null!")));
    }

    @Override
    public Domain createDomain(String name) {
        return domainRepository.save(        
                Optional.ofNullable(name)
                    .filter(n -> !n.isEmpty())
                    .flatMap(n -> Optional.of(new Domain(n)))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid name of domain to create!"))
        );
    }

    @Override
    public void removeByName(String name) {
        domainRepository.deleteById(
                Optional.ofNullable(name)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid name of domain to remove!"))
        );
    }

    @Override
    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    @Override
    public Domain findByName(String name) {
        return domainRepository.findById(
                Optional.ofNullable(name)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid name of domain to find!"))
        ).orElse(null);
    }

    @Override
    public long count() {
        return domainRepository.count();
    }

}
