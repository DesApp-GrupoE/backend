package desapp.grupo.e.model.user;

import desapp.grupo.e.model.builder.commerce.CommerceBuilder;
import desapp.grupo.e.model.builder.purchase.PurchaseTurnBuilder;
import desapp.grupo.e.model.purchase.PurchaseTurn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CommerceTest {

    private Commerce commerce;

    @BeforeEach
    public void setUp() {
        commerce = CommerceBuilder.aCommerce()
                .anyCommerce()
                .withId(1L)
                .build();
    }

    @Test
    public void aNewCommerceHasNotTurnsCharged() {
        Assertions.assertTrue(commerce.getPurchaseTurns().isEmpty());
    }

    @Test
    public void addAPurchaseTurnThenTheListIsNotEmpty() {
        PurchaseTurn purchaseTurn = PurchaseTurnBuilder.aPurchaseTurn().anyPurchaseTurn().build();

        commerce.addPurchaseTurn(purchaseTurn);

        Assertions.assertFalse(commerce.getPurchaseTurns().isEmpty());
    }

    @Test
    public void aCommerceCanNotAddMoreThanOnePurchaseTurnInSameDayAndHour() {
        LocalDateTime dateTurn = LocalDateTime.of(2020, 3, 1, 15, 0);
        PurchaseTurn purchaseTurn1 = PurchaseTurnBuilder.aPurchaseTurn().withDateTurn(dateTurn).build();
        PurchaseTurn purchaseTurn2 = PurchaseTurnBuilder.aPurchaseTurn().withDateTurn(dateTurn).build();
        commerce.addPurchaseTurn(purchaseTurn1);

        commerce.addPurchaseTurn(purchaseTurn2);

        Assertions.assertEquals(1, commerce.getPurchaseTurns().size());
    }

    @Test
    public void aCommerceCanDeleteAPurchaseTurn() {
        PurchaseTurn purchaseTurn = PurchaseTurnBuilder.aPurchaseTurn().anyPurchaseTurn().build();
        commerce.addPurchaseTurn(purchaseTurn);

        commerce.removePurchaseTurn(purchaseTurn);

        Assertions.assertTrue(commerce.getPurchaseTurns().isEmpty());
    }
}
