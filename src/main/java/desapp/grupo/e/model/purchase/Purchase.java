package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.exception.BusinessException;
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
        SubPurchase subPurchaseObtained = getSubPurchaseByIdCommerce(product)
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
        Optional<SubPurchase> optSubPurchase = getSubPurchaseByIdCommerce(product);
        if(optSubPurchase.isPresent()) {
            SubPurchase subPurchase = optSubPurchase.get();
            subPurchase.removeProduct(product);
            if(subPurchase.getProducts().isEmpty()) {
                this.subPurchases.remove(subPurchase);
            }
        }
    }

    private Optional<SubPurchase> getSubPurchaseByIdCommerce(Product product) {
        return this.subPurchases.stream()
                .filter(subPurchase -> subPurchase.getIdCommerce().equals(product.getIdCommerce()))
                .findFirst();
    }

    public Double getTotalAmount() {
        return this.subPurchases.stream()
                .mapToDouble(SubPurchase::getTotalAmount)
                .sum();
    }
}
