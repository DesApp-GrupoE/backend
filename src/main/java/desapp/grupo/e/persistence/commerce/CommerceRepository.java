package desapp.grupo.e.persistence.commerce;

import desapp.grupo.e.model.user.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommerceRepository extends JpaRepository<Commerce, Long> {

    @Query(nativeQuery = true,
        value = "select * from commerce where id_user = :idUser and id = :id")
    Optional<Commerce> findByIdAndUser(@Param("idUser") Long idUser, @Param("id") Long id);

    @Query(nativeQuery = true,
        value = "select count(*) > 0 from commerce where name = :name")
    boolean existCommerceInDatabase(@Param("name") String commerce);


    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "update commerce set id_user = null where id_user = :idUser and id = :id")
    void removeFk(@Param("idUser") Long idUser, @Param("id") Long id);

    @Query(nativeQuery = true,
            value = "select * from commerce where user_id = :userId")
    Commerce findByUser(@Param("userId") Long userId);
}
