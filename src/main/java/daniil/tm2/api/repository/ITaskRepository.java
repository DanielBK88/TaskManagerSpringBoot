package daniil.tm2.api.repository;

import daniil.tm2.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Denis Volnenko
 */
public interface ITaskRepository extends JpaRepository<Task, String> {
    
}
