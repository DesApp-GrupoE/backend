package desapp.grupo.e.persistence.commerce;

import desapp.grupo.e.model.user.Commerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommerceRepository extends JpaRepository<Commerce, Long> {

    @Query(nativeQuery = true,
        value = "select count(*) > 0 from commerce where name = :name")
    boolean existCommerceInDatabase(@Param("name") String commerce);

    @Query(nativeQuery = true,
            value = "select * from commerce where user_id = :userId")
    Commerce findByUser(@Param("userId") Long userId);
}
