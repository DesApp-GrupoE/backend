package desapp.grupo.e.persistence.product;
import desapp.grupo.e.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query(nativeQuery = true,
        value = "select count(*) > 0 from product where name = :name")
    boolean existProductInDatabase(@Param("name") String product);
}
