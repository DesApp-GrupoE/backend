package desapp.grupo.e.webservice.controller.cart;

import desapp.grupo.e.model.dto.cart.CartRequestDto;
import desapp.grupo.e.service.cart.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;

@RestController
public class ShoppingCartController {

    private static final String BASE_URL = "/cart";
    private static final String URL_PRODUCT = BASE_URL + "/product";
    private static final String PRODUCT_ID = "productId";
    private static final String URL_PRODUCT_ID = URL_PRODUCT + "/{"+ PRODUCT_ID + "}";

    private static final String URL_OFFER = BASE_URL + "/offer";
    private static final String OFFER_ID = "offerId";
    private static final String URL_OFFER_ID = URL_OFFER + "/{"+ OFFER_ID + "}";

    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping(BASE_URL)
    public ResponseEntity getShoppingCart() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        return ResponseEntity.ok(shoppingCartService.getShoppingCartByKey(sessionId));
    }

    @PostMapping(URL_PRODUCT)
    public ResponseEntity addProduct(@Valid @RequestBody CartRequestDto cartRequest) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        shoppingCartService.addProduct(sessionId, cartRequest.getId(), cartRequest.getQuantity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(URL_PRODUCT_ID)
    public ResponseEntity removeProduct(@PathVariable(PRODUCT_ID) Long productId) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        shoppingCartService.removeProduct(sessionId, productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(URL_OFFER)
    public ResponseEntity addOffer(@Valid @RequestBody CartRequestDto cartRequest) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        shoppingCartService.addOffer(sessionId, cartRequest.getId(), cartRequest.getQuantity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(URL_OFFER_ID)
    public ResponseEntity removeOffer(@PathVariable(OFFER_ID) Long offerId) {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        shoppingCartService.removeOffer(sessionId, offerId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
