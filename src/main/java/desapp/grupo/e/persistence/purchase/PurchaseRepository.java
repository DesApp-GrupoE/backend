package desapp.grupo.e.persistence.purchase;

import desapp.grupo.e.model.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByUserId(Long userId);
}
