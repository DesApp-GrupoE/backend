package desapp.grupo.e.persistence.purchase;

import desapp.grupo.e.model.purchase.PurchaseTurn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseTurnRepository extends JpaRepository<PurchaseTurn, Long> {

    List<PurchaseTurn> findAllByIdCommerceAndDateTurnBetween(Long idCommerce, LocalDateTime dateFrom, LocalDateTime dateTo);

}
