package desapp.grupo.e.persistence.purchase;

import desapp.grupo.e.model.purchase.DeliveryType;
import desapp.grupo.e.model.purchase.PurchaseTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PurchaseTurnRepository extends JpaRepository<PurchaseTurn, Long> {

    List<PurchaseTurn> findAllByIdCommerceAndDateTurnBetweenOrderByDateTurn(Long idCommerce, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<PurchaseTurn> findAllByIdCommerceAndIdUserIsNullAndDateTurnBetweenOrderByDateTurn(Long idCommerce, LocalDateTime dateFrom, LocalDateTime dateTo);

    Optional<PurchaseTurn> findFirstByIdCommerceAndDeliveryType(Long commerceId, DeliveryType deliveryType);

    @Modifying
    @Query("update PurchaseTurn set idUser = :idUser where id = :idTurn")
    void addUserToTurn(@Param("idUser") Long idUser, @Param("idTurn") Long idTurn);

    @Query(nativeQuery = true,
        value = "select * from purchase_turn where id_user is not null and date_turn < now() + INTERVAL '15 minute'")
    List<PurchaseTurn> findNextTurnsToExpire();
}
