package daniil.tm2.api.repository;

import daniil.tm2.entity.role.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<UserRole, String> {

}
