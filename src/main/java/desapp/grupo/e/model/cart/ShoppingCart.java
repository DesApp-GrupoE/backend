package desapp.grupo.e.model.cart;

import desapp.grupo.e.model.purchase.Purchase;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collection;
import java.util.stream.Collectors;

public class ShoppingCart {

    private List<CartProduct> cartProducts;

    public ShoppingCart() {
        this.cartProducts = new ArrayList<>();
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    /**
     * Add new cart product to cart product's list if this doesn't exists.
     * If the product exists, then check if belongs to same offer.
     * If belongs to same offer sum quantities else add as new cart product.
     * The offers should be treated as different cart product.
     * @param newCartProduct to add
     */
    public void addProduct(CartProduct newCartProduct) {
        Optional<CartProduct> optCartProduct = this.cartProducts.stream()
                .filter(cp -> areFromSameCommerceAndProductAndOffer(cp, newCartProduct))
                .findFirst();
        if(optCartProduct.isPresent()) {
            CartProduct cartProduct = optCartProduct.get();
            cartProduct.addQuantity(newCartProduct.getQuantity());
        } else {
            this.cartProducts.add(newCartProduct);
        }
    }

    private boolean areFromSameCommerceAndProductAndOffer(CartProduct cartProduct1, CartProduct cartProduct2) {
        return areSameProductAndCommerce(cartProduct1, cartProduct2) &&
                areSameOffer(cartProduct1, cartProduct2);
    }

    private boolean areSameProductAndCommerce(CartProduct cartProduct1, CartProduct cartProduct2) {
        return cartProduct1.getCommerceId().equals(cartProduct2.getCommerceId()) &&
                cartProduct1.getProductId().equals(cartProduct2.getProductId());
    }

    private boolean areSameOffer(CartProduct cartProduct1, CartProduct cartProduct2) {
        if(cartProduct1.getOfferId() == null && cartProduct2.getOfferId() == null) {
            return true;
        } else if((cartProduct1.getOfferId() != null && cartProduct2.getOfferId() == null) ||
                (cartProduct1.getOfferId() == null && cartProduct2.getOfferId() != null)) {
            return false;
        } else {
            return cartProduct1.getOfferId().equals(cartProduct2.getOfferId());
        }

    }

    public void removeProduct(CartProduct cartProduct) {
        this.cartProducts.remove(cartProduct);
    }

    public double getTotalAmount() {
        return this.cartProducts.stream().mapToDouble(CartProduct::calculateAmount).sum();
    }

    public List<Purchase> generatePurchase() {
        Collection<List<CartProduct>> productsByCommerce = this.cartProducts.stream()
                .collect(Collectors.groupingBy(CartProduct::getCommerceId))
                .values();

        return productsByCommerce.stream()
                .map(this::createPurchase)
                .collect(Collectors.toList());
    }

    private Purchase createPurchase(List<CartProduct> listCartProduct) {
        Purchase purchase = new Purchase(listCartProduct.get(0).getCommerceId());
        purchase.setCartProducts(listCartProduct);
        return purchase;
    }

    public void removeProductById(Long productId) {
        this.cartProducts.removeIf(cp -> cp.getProductId().equals(productId) && cp.getOfferId() == null);
    }

    public void removeOfferById(Long offerId) {
        this.cartProducts.removeIf(cartProduct -> cartProduct.getOfferId() != null && cartProduct.getOfferId().equals(offerId));
    }

    public void updateProductQuantity(Long productId, Integer quantity) {
        this.cartProducts.stream()
                .filter(cp -> cp.getProductId().equals(productId))
                .findFirst()
                .ifPresent(cp -> cp.setQuantity(quantity));
    }
}
