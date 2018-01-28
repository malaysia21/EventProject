package events.project.repositories;

import events.project.modelEntity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repozytorium dla encji UserRole
 * @version 1.1
 */

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);


}