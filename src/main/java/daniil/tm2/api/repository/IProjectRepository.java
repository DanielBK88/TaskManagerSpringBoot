package daniil.tm2.api.repository;

import daniil.tm2.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Denis Volnenko
 */
public interface IProjectRepository extends JpaRepository<Project, String> {
}

