package desapp.grupo.e.webservice.controller.cart;

import com.sun.mail.iap.Response;
import desapp.grupo.e.model.dto.cart.CartRequestDto;
import desapp.grupo.e.model.dto.purchase.PurchaseDTO;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.service.cart.ShoppingCartService;
import desapp.grupo.e.service.commerce.CommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ShoppingCartController {

    private static final String BASE_URL = "/cart";
    private static final String CART_ID = "cartId";
    private static final String URL_CART = BASE_URL + "/{" + CART_ID + "}";

    private static final String URL_PRODUCT = URL_CART + "/product";
    private static final String PRODUCT_ID = "productId";
    private static final String URL_PRODUCT_ID = URL_PRODUCT + "/{"+ PRODUCT_ID + "}";

    private static final String URL_OFFER = URL_CART + "/offer";
    private static final String OFFER_ID = "offerId";
    private static final String URL_OFFER_ID = URL_OFFER + "/{"+ OFFER_ID + "}";

    private ShoppingCartService shoppingCartService;
    @Autowired
    private CommerceService commerceService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping(BASE_URL)
    public ResponseEntity createShoppingCart() {
        String key = shoppingCartService.createShoppingCart();
        Map<String, String> keyCart =  new HashMap<>();
        keyCart.put("key", key);
        return ResponseEntity.ok(keyCart);
    }

    @GetMapping(URL_CART)
    public ResponseEntity getShoppingCart(@PathVariable(CART_ID) String cartId) {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartByKey(cartId));
    }

    @PostMapping(URL_PRODUCT)
    public ResponseEntity addProduct(@PathVariable(CART_ID) String cartId,
                                        @Valid @RequestBody CartRequestDto cartRequest) {
        shoppingCartService.addProduct(cartId, cartRequest.getId(), cartRequest.getQuantity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(URL_PRODUCT)
    public ResponseEntity updateProductQuantity(@PathVariable(CART_ID) String cartId,
                                                @Valid @RequestBody CartRequestDto cartRequest) {
        shoppingCartService.updateProductQuantity(cartId, cartRequest.getId(), cartRequest.getQuantity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(URL_PRODUCT_ID)
    public ResponseEntity removeProduct(@PathVariable(CART_ID) String cartId,
                                            @PathVariable(PRODUCT_ID) Long productId) {
        shoppingCartService.removeProduct(cartId, productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(URL_OFFER)
    public ResponseEntity addOffer(@PathVariable(CART_ID) String cartId,
                                    @Valid @RequestBody CartRequestDto cartRequest) {
        shoppingCartService.addOffer(cartId, cartRequest.getId(), cartRequest.getQuantity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(URL_OFFER)
    public ResponseEntity updateQuantityOffer(@PathVariable(CART_ID) String cartId,
                                        @Valid @RequestBody CartRequestDto cartRequest) {
        shoppingCartService.updateOfferQuantity(cartId, cartRequest.getId(), cartRequest.getQuantity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(URL_OFFER_ID)
    public ResponseEntity removeOffer(@PathVariable(CART_ID) String cartId,
                                        @PathVariable(OFFER_ID) Long offerId) {
        shoppingCartService.removeOffer(cartId, offerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(URL_CART + "/generate-purchase")
    public ResponseEntity<List<PurchaseDTO>> generatePurchases(@PathVariable(CART_ID) String cartId) {
        List<Purchase> purchases = this.shoppingCartService.generatePurchases(cartId);
        List<Commerce> commerces = commerceService.getAllCommerceById(getIdsCommerce(purchases));
        List<PurchaseDTO> purchaseDTOS = purchases.stream().map(p -> this.mapToDto(p, commerces)).collect(Collectors.toList());
        return ResponseEntity.ok(purchaseDTOS);
    }

    private List<Long> getIdsCommerce(List<Purchase> purchases) {
        return purchases.stream().map(Purchase::getCommerceId).collect(Collectors.toList());
    }

    private PurchaseDTO mapToDto(Purchase purchase, List<Commerce> commerces) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setUserId(purchase.getId());
        purchaseDTO.setCommerceId(purchase.getCommerceId());
        purchaseDTO.setProducts(purchase.getCartProducts());
        Commerce commerce = commerces.stream().filter(c -> c.getId().equals(purchase.getCommerceId())).findFirst().get();
        purchaseDTO.setNameCommerce(commerce.getName());
        if(purchase.getDate() != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            purchaseDTO.setDate(purchase.getDate().format(dateTimeFormatter));
        }
        return purchaseDTO;
    }

}
