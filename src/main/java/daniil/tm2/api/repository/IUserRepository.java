package daniil.tm2.api.repository;

import daniil.tm2.entity.TMUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<TMUser, String> {

}
