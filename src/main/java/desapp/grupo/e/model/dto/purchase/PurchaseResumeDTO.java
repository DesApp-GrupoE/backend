package desapp.grupo.e.model.dto.purchase;

import desapp.grupo.e.model.dto.commerce.CommerceDTO;

import java.util.List;

public class PurchaseResumeDTO {

    private List<CommerceDTO> commerces;
    private List<PurchaseDTO> purchases;

    public List<CommerceDTO> getCommerces() {
        return commerces;
    }

    public void setCommerces(List<CommerceDTO> commerces) {
        this.commerces = commerces;
    }

    public List<PurchaseDTO> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseDTO> purchases) {
        this.purchases = purchases;
    }
}
