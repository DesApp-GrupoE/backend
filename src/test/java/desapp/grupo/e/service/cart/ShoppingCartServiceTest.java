package desapp.grupo.e.service.cart;

import desapp.grupo.e.model.builder.product.OfferBuilder;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.cart.ShoppingCart;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.product.OfferRepository;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartServiceTest {

    private String keyCart;
    private ProductRepository productRepository;
    private OfferRepository offerRepository;
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    public void setUp() {
        this.productRepository = mock(ProductRepository.class);
        this.offerRepository = mock(OfferRepository.class);
        this.shoppingCartService = new ShoppingCartService(productRepository, offerRepository);
        this.keyCart = this.shoppingCartService.createShoppingCart();
    }

    @Test
    public void createShoppingCartShouldReturnAKey() {
        String key = this.shoppingCartService.createShoppingCart();

        Assertions.assertEquals(15, key.length());
    }

    @Test
    public void getShoppingCartFromUserThatDoesNotAddProductShouldReturnAShoopingCartEmpty() {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByKey(keyCart);

        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
    }

    @Test
    public void addProductToShoppingCart() {
        Long productId = 1L;
        Product product = ProductBuilder.aProduct().anyProduct().withId(productId).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        shoppingCartService.addProduct(keyCart, productId, 1);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByKey(keyCart);
        Assertions.assertEquals(1, shoppingCart.getCartProducts().size());
    }

    @Test
    public void whenTryAddProductInexistentToShoppingCartShouldThrowResourceNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.addProduct(keyCart, productId, 1));
    }

    @Test
    public void removeProductFromShoppingCart() {
        Long productId = 1L;
        Product product = ProductBuilder.aProduct().anyProduct().withId(productId).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        shoppingCartService.addProduct(keyCart, productId, 1);

        shoppingCartService.removeProduct(keyCart, productId);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByKey(keyCart);
        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
    }

    @Test
    public void addOfferToShoppingCart() {
        Long offerId = 1L;
        Offer offer = OfferBuilder.aOffer().withId(offerId)
                .withProduct(
                        ProductBuilder.aProduct().anyProduct().build()
                ).build();
        when(offerRepository.findById(eq(offerId))).thenReturn(Optional.of(offer));

        shoppingCartService.addOffer(keyCart, offerId, 1);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByKey(keyCart);
        Assertions.assertEquals(1, shoppingCart.getCartProducts().size());
    }

    @Test
    public void addOfferInexistentShouldThrowResourceNotFound() {
        Long anyLong = 0L;
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.addOffer(keyCart, anyLong, 1));
    }

    @Test
    public void removeOfferFromShoppingCartById() {
        Long offerId = 1L;
        Offer offer = OfferBuilder.aOffer().withId(offerId)
                .withProduct(
                        ProductBuilder.aProduct().anyProduct().build()
                ).build();
        when(offerRepository.findById(eq(offerId))).thenReturn(Optional.of(offer));
        shoppingCartService.addOffer(keyCart, offerId, 1);

        shoppingCartService.removeOffer(keyCart, offerId);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByKey(keyCart);
        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
    }

    @Test
    public void updateQuantityProduct() {
        Long productId = 1L;
        Product product = ProductBuilder.aProduct().anyProduct().withId(productId).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        shoppingCartService.addProduct(keyCart, productId, 1);

        shoppingCartService.updateProductQuantity(keyCart, productId, 5);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByKey(keyCart);
        Assertions.assertEquals(5, shoppingCart.getCartProducts().get(0).getQuantity());
    }
}
