package desapp.grupo.e.persistence.product;
import desapp.grupo.e.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query(nativeQuery = true,
        value = "select count(*) > 0 from product where commerce_id = :commerce_id and name = :name")
    boolean existProductInCommerce(@Param("commerce_id") Long commerce_id, @Param("name") String name);

    List<Product> findByCommerceIdOrderById(Long commerceId);
}
