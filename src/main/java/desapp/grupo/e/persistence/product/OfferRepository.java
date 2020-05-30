package desapp.grupo.e.persistence.product;

import desapp.grupo.e.model.product.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
