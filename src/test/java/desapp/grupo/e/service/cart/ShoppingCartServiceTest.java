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

    private Long userId;
    private ProductRepository productRepository;
    private OfferRepository offerRepository;
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    public void setUp() {
        this.userId = 1L;
        this.productRepository = mock(ProductRepository.class);
        this.offerRepository = mock(OfferRepository.class);
        this.shoppingCartService = new ShoppingCartService(productRepository, offerRepository);
    }

    @Test
    public void getShoppingCartFromUserThatDoesNotAddProductShouldReturnAShoopingCartEmpty() {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUser(userId);

        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
        Assertions.assertTrue(shoppingCart.getCartOfferProducts().isEmpty());
    }

    @Test
    public void addProductToShoppingCart() {
        Long productId = 1L;
        Product product = ProductBuilder.aProduct().anyProduct().withId(productId).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        shoppingCartService.addProduct(userId, productId, 1);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUser(userId);
        Assertions.assertEquals(1, shoppingCart.getCartProducts().size());
    }

    @Test
    public void whenTryAddProductInexistentToShoppingCartShouldThrowResourceNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.addProduct(userId, productId, 1));
    }

    @Test
    public void removeProductFromShoppingCart() {
        Long productId = 1L;
        Product product = ProductBuilder.aProduct().anyProduct().withId(productId).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        shoppingCartService.addProduct(userId, productId, 1);

        shoppingCartService.removeProduct(userId, productId);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUser(userId);
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

        shoppingCartService.addOffer(userId, offerId, 1);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUser(userId);
        Assertions.assertEquals(1, shoppingCart.getCartOfferProducts().size());
    }

    @Test
    public void addOfferInexistentShouldThrowResourceNotFound() {
        Long anyLong = 0L;
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.addOffer(userId, anyLong, 1));
    }

    @Test
    public void removeOfferFromShoppingCartById() {
        Long offerId = 1L;
        Offer offer = OfferBuilder.aOffer().withId(offerId)
                .withProduct(
                        ProductBuilder.aProduct().anyProduct().build()
                ).build();
        when(offerRepository.findById(eq(offerId))).thenReturn(Optional.of(offer));
        shoppingCartService.addOffer(userId, offerId, 1);

        shoppingCartService.removeOffer(userId, offerId);

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUser(userId);
        Assertions.assertTrue(shoppingCart.getCartOfferProducts().isEmpty());
    }
}
