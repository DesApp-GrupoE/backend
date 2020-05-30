package desapp.grupo.e.model.cart;

import desapp.grupo.e.model.purchase.Purchase;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ShoppingCart {

    private Long userId;
    private List<CartProduct> cartProducts;
    private List<CartOfferProduct> cartOfferProducts;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        this.cartProducts = new ArrayList<>();
        this.cartOfferProducts = new ArrayList<>();
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public List<CartOfferProduct> getCartOfferProducts() {
        return cartOfferProducts;
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
                .filter(cp -> areFromSameCommerceAndProduct(cp, newCartProduct))
                .findFirst();
        if(optCartProduct.isPresent()) {
            CartProduct cartProduct = optCartProduct.get();
            cartProduct.addQuantity(newCartProduct.getQuantity());
        } else {
            this.cartProducts.add(newCartProduct);
        }
    }

    public void addOffer(CartOfferProduct newOffer) {
        Optional<CartOfferProduct> optOffer = this.cartOfferProducts.stream()
                .filter(offer -> areFromSameCommerceAndOffer(offer, newOffer))
                .findFirst();
        if(optOffer.isPresent()) {
            CartOfferProduct cartOfferProduct = optOffer.get();
            cartOfferProduct.addQuantity(newOffer.getQuantity());
        } else {
            this.cartOfferProducts.add(newOffer);
        }
    }

    private boolean areFromSameCommerceAndOffer(CartOfferProduct offer, CartOfferProduct newOffer) {
        return offer.getCommerceId().equals(newOffer.getCommerceId()) &&
                offer.getOfferId().equals(newOffer.getOfferId());
    }

    private boolean areFromSameCommerceAndProduct(CartProduct cartProduct1, CartProduct cartProduct2) {
        return cartProduct1.getCommerceId().equals(cartProduct2.getCommerceId()) &&
                cartProduct1.getProductId().equals(cartProduct2.getProductId());
    }

    public void removeProduct(CartProduct cartProduct) {
        this.cartProducts.remove(cartProduct);
    }

    public void removeOffer(CartOfferProduct cartOfferProduct) {
        this.cartOfferProducts.remove(cartOfferProduct);
    }

    public double getTotalAmount() {
        double productsAmount = this.cartProducts.stream().mapToDouble(CartProduct::calculateAmount).sum();
        double offerAmount = this.cartOfferProducts.stream().mapToDouble(CartOfferProduct::calculateTotalAmount).sum();
        return productsAmount + offerAmount;
    }

    public List<Purchase> generatePurchase() {
        Collection<List<CartProduct>> productsByCommerce = this.cartProducts.stream()
                .collect(Collectors.groupingBy(CartProduct::getCommerceId))
                .values();

        Map<Long, List<CartOfferProduct>> mapCartOfferProducts = createMapCartOfferProductsByCommerceId();

        return productsByCommerce.stream()
                .map(cp -> createPurchase(cp, mapCartOfferProducts))
                .collect(Collectors.toList());
    }

    private Map<Long, List<CartOfferProduct>> createMapCartOfferProductsByCommerceId() {
        Map<Long, List<CartOfferProduct>> cartProductOfferMap = new HashMap<>();
        this.cartOfferProducts.forEach(offer -> {
            if(!cartProductOfferMap.containsKey(offer.getCommerceId())) {
                cartProductOfferMap.put(offer.getCommerceId(), new ArrayList<>());
            }
            cartProductOfferMap.get(offer.getCommerceId()).add(offer);
        });
        return cartProductOfferMap;
    }

    private Purchase createPurchase(List<CartProduct> listCartProduct, Map<Long, List<CartOfferProduct>> cartProductOfferMap) {
        Purchase purchase = new Purchase(listCartProduct.get(0).getCommerceId());
        purchase.setCartProducts(listCartProduct);
        purchase.setCartOfferProducts(findOffersCommerceId(purchase.getCommerceId(), cartProductOfferMap));
        return purchase;
    }

    private List<CartOfferProduct> findOffersCommerceId(Long commerceId, Map<Long, List<CartOfferProduct>> cartProductOfferMap) {
        return cartProductOfferMap.get(commerceId);
    }

    public void removeProductById(Long productId) {
        this.cartProducts.removeIf(cp -> cp.getProductId().equals(productId));
    }

    public void removeOfferById(Long offerId) {
        this.cartOfferProducts.removeIf(offer -> offer.getId().equals(offerId));
    }
}
