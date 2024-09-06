package jp.co.topucomunity.backend_java.users.repositories;

import jp.co.topucomunity.backend_java.users.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * expect
     *
     * select *
     * from users u
     * where u.email = ?
     *
     */
    Optional<User> findUserByEmail(String emailAddress);
}
