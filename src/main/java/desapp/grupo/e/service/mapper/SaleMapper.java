package desapp.grupo.e.service.mapper;

import desapp.grupo.e.model.dto.cart.CartProductDTO;
import desapp.grupo.e.model.dto.purchase.SaleDTO;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleMapper {

    @Autowired
    private CartProductMapper cartProductMapper;

    public List<SaleDTO> mapListModelToDto(List<Purchase> purchases, List<User> users) {
        return purchases.stream()
                .map(purchase -> mapToDto(purchase, users))
                .collect(Collectors.toList());
    }

    private SaleDTO mapToDto(Purchase purchase, List<User> users) {
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(purchase.getId());
        saleDTO.setDeliveryType(purchase.getDeliveryType());
        List<CartProductDTO> cartProductDTOS = purchase.getCartProducts().stream()
                .map(cartProduct -> cartProductMapper.mapToProductDTO(cartProduct, new ArrayList<>()))
                .collect(Collectors.toList());
        saleDTO.setProducts(cartProductDTOS);
        saleDTO.setTurnId(purchase.getTurnId());
        saleDTO.setUserId(purchase.getUserId());
        Optional<User> userOpt = users.stream()
                .filter(user -> purchase.getUserId().equals(user.getId()))
                .findFirst();
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            saleDTO.setUserName(user.getFullName());
        }
        if(purchase.getDate() != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            saleDTO.setDate(purchase.getDate().format(dateTimeFormatter));
        }
        return saleDTO;
    }
}
