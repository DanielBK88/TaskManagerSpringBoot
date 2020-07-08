package daniil.tm2.api.repository;


import daniil.tm2.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Denis Volnenko
 */
public interface IDomainRepository extends JpaRepository<Domain, String> {
    
}
