package desapp.grupo.e.service.mapper;

import desapp.grupo.e.model.cart.CartProduct;
import desapp.grupo.e.model.dto.cart.CartProductDTO;
import desapp.grupo.e.model.user.Commerce;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartProductMapper {

    public List<CartProductDTO> mapListModelToDTO(List<CartProduct> cartProducts, List<Commerce> commerces) {
        return cartProducts.stream()
                .map(cartProduct -> mapToProductDTO(cartProduct, commerces))
                .collect(Collectors.toList());
    }

    private CartProductDTO mapToProductDTO(CartProduct cartProduct, List<Commerce> commerces) {
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setId(cartProduct.getId());
        cartProductDTO.setBrand(cartProduct.getBrand());
        cartProductDTO.setCommerceId(cartProduct.getCommerceId());
        cartProductDTO.setImg(cartProduct.getImg());
        cartProductDTO.setName(cartProduct.getName());
        cartProductDTO.setOff(cartProduct.getOff());
        cartProductDTO.setOfferId(cartProduct.getOfferId());
        cartProductDTO.setPrice(cartProduct.getPrice());
        cartProductDTO.setProductId(cartProduct.getProductId());
        cartProductDTO.setQuantity(cartProduct.getQuantity());
        Optional<Commerce> optCommerce = commerces.stream()
                .filter(c -> c.getId().equals(cartProduct.getCommerceId()))
                .findFirst();
        if(optCommerce.isPresent()) {
            Commerce commerce = optCommerce.get();
            cartProductDTO.setNameCommerce(commerce.getName());
        }
        return cartProductDTO;
    }
}
