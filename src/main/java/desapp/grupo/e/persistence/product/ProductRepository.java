package desapp.grupo.e.persistence.product;

import desapp.grupo.e.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
