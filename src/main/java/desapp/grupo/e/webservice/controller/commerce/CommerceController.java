package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.model.dto.commerce.CommerceDTO;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.service.commerce.CommerceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;

@RestController
public class CommerceController {

    private CommerceService commerceService;
    private static final String USER_ID = "user_id";
    private static final String COMMERCE_ID = "commerce_id";
    private static final String URL_BASE = "/user/{" + USER_ID + "}/commerce";

    public CommerceController(CommerceService commerceService) {
        this.commerceService = commerceService;
    }

    @GetMapping(URL_BASE)
    public ResponseEntity<CommerceDTO> getCommerceByUser(@PathVariable(USER_ID) Long userId) {
        Commerce commerce = commerceService.getCommerceByUser(userId);
        if(commerce == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.ok(new CommerceDTO(commerce));
    }

    @PostMapping(URL_BASE)
    public ResponseEntity<CommerceDTO> createCommerce(@PathVariable(USER_ID) Long userId,
                                                                @Valid @RequestBody CommerceDTO commerceDTO) {
        Commerce commerce = convertDtoToModel(commerceDTO);
        Commerce newCommerce = commerceService.save(userId, commerce);
        commerceDTO.setId(newCommerce.getId());
        return new ResponseEntity<>(commerceDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(URL_BASE + "/{" + COMMERCE_ID + "}")
    public ResponseEntity deleteCommerce(@PathVariable(USER_ID) Long userId, @PathVariable(COMMERCE_ID) Long commerceId) {
        commerceService.removeById(userId, commerceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Commerce convertDtoToModel(CommerceDTO commerceDTO) {
        Commerce commerce = new Commerce();
        commerce.setName(commerceDTO.getName());
        commerce.setAddress(commerceDTO.getAddress());
        commerce.setAddressNumber(commerceDTO.getAddressNumber());
        commerce.setLocation(commerceDTO.getLocation());
        commerce.setPhone(commerceDTO.getPhone());
        commerce.setLatitude(commerceDTO.getLatitude());
        commerce.setLongitude(commerceDTO.getLongitude());
        return commerce;
    }
}