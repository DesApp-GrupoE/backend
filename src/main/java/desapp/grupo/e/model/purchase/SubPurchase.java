package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.exception.BusinessException;
import desapp.grupo.e.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class SubPurchase {

    private Long id;
    private Long idCommerce;
    private List<Product> products;

    public SubPurchase() {
        this.products = new ArrayList<>();
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
        if(this.idCommerce != null && !this.idCommerce.equals(product.getIdCommerce())) {
            throw new BusinessException("Can't add a product of a different commerce in a subpurchase with a commerce already asociated");
        }
        this.idCommerce = product.getIdCommerce();
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public Double getTotalAmount() {
        return this.products.stream().mapToDouble(Product::getPrice).sum();
    }
}
