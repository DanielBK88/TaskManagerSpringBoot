package daniil.tm2.controller.crud;

import daniil.tm2.api.service.IDomainService;
import daniil.tm2.entity.Domain;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DomainCRUDController {

    @Autowired
    private IDomainService domainService;
    
    @GetMapping(value = "/findDomain/{name}", produces = "application/json")
    public Domain findByName(@PathVariable String name) {
        return domainService.findByName(
                Optional.ofNullable(name)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid domain name: " + name))
        );
    }
    
    @PostMapping(value = "/saveDomain", consumes="application/json")
    public void saveDomain(@RequestBody Domain domain) {
        prepareDeserializedDomainForInsertion(
                Optional.ofNullable(domain).orElseThrow(() -> new IllegalArgumentException("Domain is null!"))
        );
        domainService.merge(domain);
    }
    
    @DeleteMapping("/deleteDomain/{name}")
    public void deleteDomain(@PathVariable String name) {
        domainService.removeByName(
                Optional.ofNullable(name)
                        .filter(n -> !n.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid domain name: " + name))
        );
    }
    
    /**
     * The deserialized domain does not have all fields set:
     * The fields Project.domain and Task.project are not set, because they would cause cyclic references
     * during serialization. That's why we must set these fields now, before persisting the domain.
     **/
    private void prepareDeserializedDomainForInsertion(Domain domain) {
        Optional.ofNullable(domain).orElseThrow(() -> new IllegalArgumentException("Domain is null!"))
            .getProjects()
                .forEach(p -> {
                    p.setDomain(domain);
                    p.getTasks().forEach(t -> t.setProject(p));
                });
    }
}
