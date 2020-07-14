package desapp.grupo.e.service.mapper;

import desapp.grupo.e.model.dto.cart.CartProductDTO;
import desapp.grupo.e.model.dto.purchase.PurchaseDTO;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.model.user.Commerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseMapper {

    @Autowired
    private CartProductMapper cartProductMapper;

    public List<PurchaseDTO> mapListModelToDto(List<Purchase> purchases, List<Commerce> commerces) {
        return purchases.stream()
                .map(p -> this.mapToDto(p, commerces))
                .collect(Collectors.toList());
    }


    private PurchaseDTO mapToDto(Purchase purchase, List<Commerce> commerces) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setUserId(purchase.getId());
        purchaseDTO.setCommerceId(purchase.getCommerceId());
        List<CartProductDTO> productDTOS = purchase.getCartProducts().stream()
                .map(product -> this.cartProductMapper.mapToProductDTO(product, commerces))
                .collect(Collectors.toList());
        purchaseDTO.setProducts(productDTOS);
        Optional<Commerce> optCommerce = commerces.stream()
                .filter(c -> c.getId().equals(purchase.getCommerceId()))
                .findFirst();
        if(optCommerce.isPresent()) {
            Commerce commerce = optCommerce.get();
            purchaseDTO.setNameCommerce(commerce.getName());
        }
        if(purchase.getDate() != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            purchaseDTO.setDate(purchase.getDate().format(dateTimeFormatter));
        }
        return purchaseDTO;
    }
}
