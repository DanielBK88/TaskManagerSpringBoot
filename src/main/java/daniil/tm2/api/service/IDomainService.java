package daniil.tm2.api.service;

import daniil.tm2.entity.Domain;
import java.util.List;

/**
 * @author Denis Volnenko
 */
public interface IDomainService {
    
    Domain createDomain(String name);
    
    void removeByName(String name);
    
    void merge(Domain domain);
    
    List<Domain> findAll();
    
    Domain findByName(String name);

}
