package desapp.grupo.e.persistence.purchase;

import desapp.grupo.e.model.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByUserId(Long userId);

    Purchase getByTurnId(Long turnId);

    @Query(nativeQuery = true,
        value = "select * from purchase where commerce_id = (select id from commerce where user_id = :user_id)")
    List<Purchase> findAllPurchasesFromCommerceByUser(@Param("user_id") Long userId);
}
