package desapp.grupo.e.webservice.controller.purchase;

import desapp.grupo.e.config.oauth2.model.CurrentUser;
import desapp.grupo.e.config.oauth2.model.UserPrincipal;
import desapp.grupo.e.model.dto.purchase.PurchaseDTO;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.service.commerce.CommerceService;
import desapp.grupo.e.service.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PurchaseController {

    private static final String CART_ID = "cartId";
    private final String URL_CREATE_PURCHASE = "/purchase/{" + CART_ID + "}";
    private final String URL_BASE_USER = "/user/purchase";
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CommerceService commerceService;


    @GetMapping(URL_BASE_USER)
    public ResponseEntity<List<PurchaseDTO>> getPurchases(@CurrentUser UserPrincipal userPrincipal) {
        List<Purchase> purchases = purchaseService.getAllPurchases(userPrincipal.getId());
        List<Commerce> commerces = commerceService.getAllCommerceById(getIdsCommerce(purchases));
        List<PurchaseDTO> purchaseDTOS = purchases.stream().map(p -> this.mapToDto(p, commerces)).collect(Collectors.toList());
        return ResponseEntity.ok(purchaseDTOS);
    }

    @PostMapping(URL_CREATE_PURCHASE)
    public ResponseEntity createPurchase(@CurrentUser UserPrincipal userPrincipal,
                                         @PathVariable(CART_ID) String cartId,
                                         @RequestBody List<PurchaseRequestDTO> purchaseRequestDTOS) {
        purchaseService.createPurchases(userPrincipal.getId(), purchaseRequestDTOS, cartId);
        return ResponseEntity.ok(HttpStatus.OK);
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        purchaseDTO.setDate(purchase.getDate().format(dateTimeFormatter));
        return purchaseDTO;
    }
}
