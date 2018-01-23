package events.project.repositories;

import events.project.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);


}