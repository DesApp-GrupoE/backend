package desapp.grupo.e.persistence.category.alert;

import desapp.grupo.e.model.product.CategoryAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryAlertRepository extends JpaRepository<CategoryAlert, Long> {

    @Query(nativeQuery = true,
        value = "select * from alert_category where id_user = :idUser and id = :id")
    Optional<CategoryAlert> findByIdAndUser(@Param("idUser") Long idUser, @Param("id") Long id);

    @Query(nativeQuery = true,
        value = "select count(*) > 0 from alert_category where id_user = :idUser and category = :category")
    boolean existCategoryInUser(@Param("idUser") Long idUser, @Param("category") String category);
}
