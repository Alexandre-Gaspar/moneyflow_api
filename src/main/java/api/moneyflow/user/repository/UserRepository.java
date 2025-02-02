package api.moneyflow.user.repository;

import api.moneyflow.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
