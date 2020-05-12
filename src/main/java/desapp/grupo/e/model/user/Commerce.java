package desapp.grupo.e.model.user;

import desapp.grupo.e.model.purchase.PurchaseTurn;
import desapp.grupo.e.model.product.Offer;


import java.util.ArrayList;
import java.util.List;

public class Commerce {

    private Long id;
    private List<PurchaseTurn> purchaseTurns;
    private List<Offer> offers;

    public Commerce(String name, String surname, String email, String password) {
        purchaseTurns = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PurchaseTurn> getPurchaseTurns() {
        return purchaseTurns;
    }

    public void setPurchaseTurns(List<PurchaseTurn> purchaseTurns) {
        this.purchaseTurns = purchaseTurns;
    }

    public void addPurchaseTurn(PurchaseTurn purchaseTurn) {
        if(this.purchaseTurns.stream().noneMatch(turn -> turn.getDateTurn().equals(purchaseTurn.getDateTurn()))) {
            this.purchaseTurns.add(purchaseTurn);
        }
    }

    public List<Offer> getOffers() {
        return offers;
    }    

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public void removePurchaseTurn(PurchaseTurn purchaseTurn) {
        this.purchaseTurns.remove(purchaseTurn);
    }
}
