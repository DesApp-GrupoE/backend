package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.exception.BusinessException;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.service.log.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Purchase {

    public Long id;
    public Long idUser;
    public LocalDateTime datePurchase;
    public List<SubPurchase> subPurchases;

    public Purchase() {
        this.subPurchases = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(LocalDateTime datePurchase) {
        this.datePurchase = datePurchase;
    }

    public List<SubPurchase> getSubPurchases() {
        return subPurchases;
    }

    public void setSubPurchases(List<SubPurchase> subPurchases) {
        this.subPurchases = subPurchases;
    }

    public void addProduct(Product product) {
        SubPurchase subPurchaseObtained = getSubPurchaseByIdCommerce(product.getIdCommerce())
                .orElseGet(() -> this.createAndReturnNewSubPurchase(product.getIdCommerce()));
        try {
            subPurchaseObtained.addProduct(product);
        } catch (BusinessException e) {
            // No debería entrar en el catch ya que controlamos que siempre se
            // guarde un product en el subpurchase que le corresponde
            Log.debug(e.getMessage());
        }
    }

    private SubPurchase createAndReturnNewSubPurchase(Long idCommerce) {
        SubPurchase subPurchase = new SubPurchase(idCommerce);
        this.subPurchases.add(subPurchase);
        return subPurchase;
    }

    public void removeProduct(Product product) {
        Optional<SubPurchase> optSubPurchase = getSubPurchaseByIdCommerce(product.getIdCommerce());
        if(optSubPurchase.isPresent()) {
            SubPurchase subPurchase = optSubPurchase.get();
            subPurchase.removeProduct(product);
            removeSubPurchase(subPurchase);
        }
    }

    private Optional<SubPurchase> getSubPurchaseByIdCommerce(Long idCommerce) {
        return this.subPurchases.stream()
                .filter(subPurchase -> subPurchase.getIdCommerce().equals(idCommerce))
                .findFirst();
    }

    public Double getTotalAmount() {
        return this.subPurchases.stream()
                .mapToDouble(SubPurchase::getTotalAmount)
                .sum();
    }

    public void addOffer(Offer offer) {
        SubPurchase subPurchaseObtained = getSubPurchaseByIdCommerce(offer.getIdCommerce())
                .orElseGet(() -> this.createAndReturnNewSubPurchase(offer.getIdCommerce()));

        subPurchaseObtained.addOffer(offer);
    }

    public void removeOffer(Offer offer) {
        Optional<SubPurchase> optSubPurchase = getSubPurchaseByIdCommerce(offer.getIdCommerce());
        if(optSubPurchase.isPresent()) {
            SubPurchase subPurchase = optSubPurchase.get();
            subPurchase.removeOffer(offer);
            removeSubPurchase(subPurchase);
        }
    }

    private void removeSubPurchase(SubPurchase subPurchase) {
        if(subPurchase.getProducts().isEmpty() && subPurchase.getOffers().isEmpty()) {
            this.subPurchases.remove(subPurchase);
        }
    }
}
