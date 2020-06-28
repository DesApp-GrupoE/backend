package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.model.dto.commerce.CommerceDTO;
import desapp.grupo.e.model.dto.commerce.CommerceHourDTO;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.CommerceHour;
import desapp.grupo.e.service.commerce.CommerceService;
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
import java.util.List;
import java.util.stream.Collectors;

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
        commerce.setPhone(commerceDTO.getPhone());
        commerce.setLatitude(commerceDTO.getLatitude());
        commerce.setLongitude(commerceDTO.getLongitude());
        commerce.setSectors(commerceDTO.getSectors());
        List<CommerceHour> hours = commerceDTO.getHours().stream()
                .map(this::convertHourDtoToModel)
                .collect(Collectors.toList());
        commerce.setHours(hours);
        return commerce;
    }

    private CommerceHour convertHourDtoToModel(CommerceHourDTO commerceHourDTO) {
        CommerceHour commerceHour = new CommerceHour();
        commerceHour.setDay(commerceHourDTO.getDay());
        commerceHour.setOpenAt(commerceHourDTO.getOpenAt());
        commerceHour.setCloseAt(commerceHourDTO.getCloseAt());
        commerceHour.setOpenToday(commerceHourDTO.getOpenToday());
        return commerceHour;
    }
}