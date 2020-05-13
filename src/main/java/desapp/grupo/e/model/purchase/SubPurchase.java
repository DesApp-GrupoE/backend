package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.exception.BusinessException;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class SubPurchase {

    private Long id;
    private Long idCommerce;
    private List<Product> products;
    private List<Offer> offers;

    public SubPurchase() {
        // Dejo constructor vacio para mapping de hibernate
    }

    public SubPurchase(long idCommerce) {
        this.products = new ArrayList<>();
        this.offers = new ArrayList<>();
        this.idCommerce = idCommerce;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCommerce() {
        return idCommerce;
    }

    public void setIdCommerce(Long idCommerce) {
        this.idCommerce = idCommerce;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) throws BusinessException {
        if(!this.idCommerce.equals(product.getIdCommerce())) {
            throw new BusinessException("Can't add a product of a different commerce in a subpurchase with a commerce already asociated");
        }
        this.idCommerce = product.getIdCommerce();
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public Double getTotalAmount() {
        double totalAmount = this.products.stream().mapToDouble(Product::getPrice).sum();
        double totalAmountWithDiscountApplied = this.offers.stream().mapToDouble(Offer::getTotalAmountWithDiscountApplied).sum();
        return totalAmount + totalAmountWithDiscountApplied;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public void addOffer(Offer offer) {
        this.offers.add(offer);
    }

    public void removeOffer(Offer offer) {
        this.offers.remove(offer);
    }
}
