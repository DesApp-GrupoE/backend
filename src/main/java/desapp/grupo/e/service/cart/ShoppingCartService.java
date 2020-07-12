package desapp.grupo.e.service.cart;

import desapp.grupo.e.model.cart.CartProduct;
import desapp.grupo.e.model.cart.ShoppingCart;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.persistence.product.OfferRepository;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.service.utils.RandomString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {

    private ProductRepository productRepository;
    private OfferRepository offerRepository;
    private Map<String, ShoppingCart> shoppingCartCache;
    private RandomString randomString;

    public ShoppingCartService(ProductRepository productRepository, OfferRepository offerRepository) {
        this.shoppingCartCache = new HashMap<>();
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.randomString = new RandomString();
    }

    public String createShoppingCart() {
        String keyShoppingCart = this.randomString.nextString(15);
        shoppingCartCache.put(keyShoppingCart, new ShoppingCart());
        return keyShoppingCart;
    }

    public ShoppingCart getShoppingCartByKey(String keyShoppingCart) {
        if(!shoppingCartCache.containsKey(keyShoppingCart)) {
            throw new ResourceNotFoundException(String.format("Shopping Cart %s not found", keyShoppingCart));
        }
        return shoppingCartCache.get(keyShoppingCart);
    }

    public void addProduct(String keyShoppingCart, Long productId, Integer quantity) {
        Product product = findProduct(productId);
        CartProduct cartProduct = mapToCartProduct(product, quantity, null, null);
        ShoppingCart shoppingCart = getShoppingCartByKey(keyShoppingCart);
        shoppingCart.addProduct(cartProduct);
    }

    private CartProduct mapToCartProduct(Product product, Integer quantity, Long offerId, Integer off) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProductId(product.getId());
        cartProduct.setCommerceId(product.getIdCommerce());
        cartProduct.setPrice(product.getPrice());
        cartProduct.setQuantity(quantity);
        cartProduct.setName(product.getName());
        cartProduct.setBrand(product.getBrand());
        cartProduct.setImg(product.getImg());
        cartProduct.setOfferId(offerId);
        cartProduct.setOff(off);
        return cartProduct;
    }

    private Product findProduct(Long productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product %s not found", productId)));
    }

    public void removeProduct(String keyShoppingCart, Long productId) {
        ShoppingCart shoppingCart = getShoppingCartByKey(keyShoppingCart);
        shoppingCart.removeProductById(productId);
    }

    public void addOffer(String keyShoppingCart, Long offerId, Integer quantity) {
        Offer offer = findOffer(offerId);
        List<CartProduct> cartProducts = mapToCartOfferProduct(offer, quantity);
        ShoppingCart shoppingCart = getShoppingCartByKey(keyShoppingCart);
        cartProducts.forEach(shoppingCart::addProduct);
    }

    private Offer findOffer(Long offerId) {
        return this.offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Offer %s not found", offerId)));
    }

    private List<CartProduct> mapToCartOfferProduct(Offer offer, Integer quantity) {
        return offer.getProducts().stream()
                .map(product -> mapToCartProduct(product, quantity, offer.getId(), offer.getOff()))
                .collect(Collectors.toList());
    }

    public void removeOffer(String keyShoppingCart, Long offerId) {
        ShoppingCart shoppingCart = getShoppingCartByKey(keyShoppingCart);
        shoppingCart.removeOfferById(offerId);
    }

    public void updateProductQuantity(String keyCart, Long productId, Integer quantity) {
        ShoppingCart shoppingCart = getShoppingCartByKey(keyCart);
        shoppingCart.updateProductQuantity(productId, quantity);
    }

    public void updateOfferQuantity(String keyCart, Long productId, Integer quantity) {
        ShoppingCart shoppingCart = getShoppingCartByKey(keyCart);
        shoppingCart.updateOfferQuantity(productId, quantity);
    }

    public List<Purchase> generatePurchases(String cartId) {
        ShoppingCart shoppingCart = getShoppingCartByKey(cartId);
        return shoppingCart.generatePurchase();
    }

    public void deleteCart(String cartId) {
        this.shoppingCartCache.remove(cartId);
    }
}
