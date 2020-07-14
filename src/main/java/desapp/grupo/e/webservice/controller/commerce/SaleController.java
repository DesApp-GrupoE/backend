package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.config.oauth2.model.CurrentUser;
import desapp.grupo.e.config.oauth2.model.UserPrincipal;
import desapp.grupo.e.model.dto.purchase.PurchaseDTO;
import desapp.grupo.e.model.dto.purchase.SaleDTO;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.commerce.CommerceService;
import desapp.grupo.e.service.commerce.SaleService;
import desapp.grupo.e.service.mapper.PurchaseMapper;
import desapp.grupo.e.service.mapper.SaleMapper;
import desapp.grupo.e.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SaleController {

    private static final String URL_BASE = "/commerce/sales";
    @Autowired
    private SaleService saleService;
    @Autowired
    private UserService userService;
    @Autowired
    private SaleMapper saleMapper;

    @GetMapping(URL_BASE)
    public ResponseEntity<List<SaleDTO>> getSales(@CurrentUser UserPrincipal userPrincipal) {
        List<Purchase> purchases = saleService.getPurchaseFromCommerceByUser(userPrincipal.getId());
        List<Long> userIds = getUserIds(purchases);
        List<User> users = userService.findAllUser(userIds);
        return ResponseEntity.ok(saleMapper.mapListModelToDto(purchases, users));
    }

    private List<Long> getUserIds(List<Purchase> purchases) {
        return purchases.stream()
                    .map(Purchase::getUserId)
                    .distinct()
                    .collect(Collectors.toList());
    }
}
