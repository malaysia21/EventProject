package events.project.repositories;

import events.project.modelEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium dla encji User
 * @version 1.1
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}