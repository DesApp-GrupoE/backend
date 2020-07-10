package desapp.grupo.e.persistence.user;

import desapp.grupo.e.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User getByEmail(String email);

    @Query("select u.secret from User u where u.id = :userId")
    String getSecretKey(@Param("userId") Long userId);

    @Modifying
    @Query("update User set auth2fa = :auth2fa where id = :userId")
    void enable2fa(@Param("auth2fa") Boolean auth2faEnabled, @Param("userId") Long userId);
}
