package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.model.dto.commerce.CommerceDTO;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.service.commerce.CommerceService;
import desapp.grupo.e.service.mapper.CommerceMapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
public class CommerceController {

    private final CommerceService commerceService;
    private final CommerceMapperService commerceMapperService;
    private static final String USER_ID = "user_id";
    private static final String COMMERCE_ID = "commerce_id";
    private static final String URL_BASE = "/user/{" + USER_ID + "}/commerce";

    public CommerceController(CommerceService commerceService, CommerceMapperService commerceMapperService) {
        this.commerceService = commerceService;
        this.commerceMapperService = commerceMapperService;
    }

    @GetMapping(URL_BASE)
    @Transactional(readOnly = true)
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
        Commerce commerce = this.commerceMapperService.convertDtoToModel(commerceDTO);
        Commerce newCommerce = commerceService.save(userId, commerce);
        commerceDTO.setId(newCommerce.getId());
        return new ResponseEntity<>(commerceDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(URL_BASE + "/{" + COMMERCE_ID + "}")
    public ResponseEntity deleteCommerce(@PathVariable(USER_ID) Long userId, @PathVariable(COMMERCE_ID) Long commerceId) {
        commerceService.removeById(userId, commerceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}