package desapp.grupo.e.service.cart;

import desapp.grupo.e.model.cart.CartOfferProduct;
import desapp.grupo.e.model.cart.CartProduct;
import desapp.grupo.e.model.cart.ShoppingCart;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.product.OfferRepository;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {

    private ProductRepository productRepository;
    private OfferRepository offerRepository;
    private Map<Long, ShoppingCart> shoppingCartCache;

    public ShoppingCartService(ProductRepository productRepository, OfferRepository offerRepository) {
        this.shoppingCartCache = new HashMap<>();
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
    }

    public ShoppingCart getShoppingCartByUser(Long userId) {
        if(!shoppingCartCache.containsKey(userId)) {
            shoppingCartCache.put(userId, new ShoppingCart(userId));
        }
        return shoppingCartCache.get(userId);
    }

    public void addProduct(Long userId, Long productId, Integer quantity) {
        Product product = findProduct(productId);
        CartProduct cartProduct = mapToCartProduct(product, quantity);
        ShoppingCart shoppingCart = getShoppingCartByUser(userId);
        shoppingCart.addProduct(cartProduct);
    }

    private CartProduct mapToCartProduct(Product product, Integer quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProductId(product.getId());
        cartProduct.setPrice(product.getPrice());
        cartProduct.setQuantity(quantity);
        cartProduct.setName(product.getName());
        cartProduct.setBrand(product.getBrand());
        cartProduct.setImg(product.getImg());
        return cartProduct;
    }

    private Product findProduct(Long productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product %s not found", productId)));
    }

    public void removeProduct(Long userId, Long productId) {
        ShoppingCart shoppingCart = getShoppingCartByUser(userId);
        shoppingCart.removeProductById(productId);
    }

    public void addOffer(Long userId, Long offerId, Integer quantity) {
        Offer offer = findOffer(offerId);
        CartOfferProduct cartOfferProduct = mapToCartOfferProduct(offer, quantity);
        ShoppingCart shoppingCart = getShoppingCartByUser(userId);
        shoppingCart.addOffer(cartOfferProduct);
    }

    private Offer findOffer(Long offerId) {
        return this.offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Offer %s not found", offerId)));
    }

    private CartOfferProduct mapToCartOfferProduct(Offer offer, Integer quantity) {
        List<CartProduct> cartProducts = offer.getProducts().stream()
                .map(product -> mapToCartProduct(product, 1))
                .collect(Collectors.toList());
        CartOfferProduct cartOfferProduct = new CartOfferProduct(offer.getIdCommerce(), offer.getId(), offer.getOff());
        cartOfferProduct.setId(offer.getId());
        cartOfferProduct.setQuantity(quantity);
        cartOfferProduct.setCartProducts(cartProducts);
        return cartOfferProduct;
    }

    public void removeOffer(Long userId, Long offerId) {
        ShoppingCart shoppingCart = getShoppingCartByUser(userId);
        shoppingCart.removeOfferById(offerId);
    }
}
