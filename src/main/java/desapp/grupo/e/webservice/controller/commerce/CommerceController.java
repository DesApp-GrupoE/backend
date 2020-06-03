package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.model.dto.commerce.CommerceDTO;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.service.commerce.CommerceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;

@RestController
public class CommerceController {
    private CommerceService commerceService;
    private static final String USER_ID = "user_id";
    private static final String URL_BASE = "/user/{" + USER_ID + "}/commerce";

    public CommerceController(CommerceService commerceService) {
        this.commerceService = commerceService;
    }

    @PostMapping(URL_BASE)
    public ResponseEntity<CommerceDTO> createCommerce(@PathVariable(USER_ID) Long userId,
                                                                @Valid @RequestBody CommerceDTO commerceDTO) {
        Commerce commerce = convertDtoToModel(commerceDTO);
        Commerce newCommerce = commerceService.save(userId, commerce);
        commerceDTO.setId(newCommerce.getId());
        return new ResponseEntity<>(commerceDTO, HttpStatus.CREATED);
    }

    public Commerce convertDtoToModel(CommerceDTO commerceDTO) {
        Commerce commerce = new Commerce();
        commerce.setName(commerceDTO.getName());
        commerce.setAddress(commerceDTO.getAddress());
        commerce.setAdressNumber(commerceDTO.getAddressNumber());
        commerce.setLocation(commerceDTO.getLocation());
        commerce.setPhone(commerceDTO.getPhone());
        commerce.setPurchaseTurns(commerceDTO.getPurchaseTurns());
        return commerce;
    }
}