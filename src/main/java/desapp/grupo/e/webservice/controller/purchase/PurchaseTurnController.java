package desapp.grupo.e.webservice.controller.purchase;

import desapp.grupo.e.model.dto.purchase.PurchaseTurnDTO;
import desapp.grupo.e.model.purchase.PurchaseTurn;
import desapp.grupo.e.service.purchase.PurchaseTurnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PurchaseTurnController {

    private static final String URL_BASE = "/purchase-turn";
    private static final String ID_TURN = "idTurn";
    private static final String URL_DELETE = "/purchase-turn/{"+ID_TURN +"}";
    private PurchaseTurnService purchaseTurnService;

    public PurchaseTurnController(PurchaseTurnService purchaseTurnService) {
        this.purchaseTurnService = purchaseTurnService;
    }

    @PostMapping(URL_BASE)
    public ResponseEntity<PurchaseTurnDTO> createPurchaseTurn(@Valid @RequestBody PurchaseTurnDTO purchaseTurnDTO) {
        PurchaseTurn purchaseTurn = mapDtoToModel(purchaseTurnDTO);
        PurchaseTurn purchaseTurnCreated = this.purchaseTurnService.createPurchaseTurn(purchaseTurn);
        purchaseTurnDTO.setId(purchaseTurnCreated.getId());
        return new ResponseEntity<>(purchaseTurnDTO, HttpStatus.CREATED);
    }

    @GetMapping(URL_BASE)
    public ResponseEntity<List<PurchaseTurnDTO>> getPurchaseTurn(@RequestParam Long commerceId,
                                                                 @RequestParam(name = "dateFrom") String dateFromStr,
                                                                 @RequestParam(name = "dateTo") String dateToStr) {
        LocalDateTime dateFrom = this.parseDate(dateFromStr);
        LocalDateTime dateTo = this.parseDate(dateToStr);
        List<PurchaseTurn> purchaseTurns = this.purchaseTurnService.getPurchaseTurns(commerceId, dateFrom, dateTo);
        return ResponseEntity.ok(purchaseTurns.stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList())
        );
    }

    @GetMapping(URL_BASE + "/free")
    public ResponseEntity<List<PurchaseTurnDTO>> getFreePurchaseTurn(@RequestParam Long commerceId,
                                                                 @RequestParam(name = "dateFrom") String dateFromStr,
                                                                 @RequestParam(name = "dateTo") String dateToStr) {
        LocalDateTime dateFrom = this.parseDate(dateFromStr);
        LocalDateTime dateTo = this.parseDate(dateToStr);
        List<PurchaseTurn> purchaseTurns = this.purchaseTurnService.getFreePurchaseTurns(commerceId, dateFrom, dateTo);
        return ResponseEntity.ok(purchaseTurns.stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping(URL_DELETE)
    public ResponseEntity<PurchaseTurnDTO> deleteTurn(@PathVariable(ID_TURN) Long idTurn) {
        this.purchaseTurnService.deleteTurnById(idTurn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private LocalDateTime parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter).atStartOfDay();
    }

    private PurchaseTurnDTO mapModelToDto(PurchaseTurn purchaseTurn) {
        PurchaseTurnDTO purchaseTurnDTO = new PurchaseTurnDTO();
        purchaseTurnDTO.setId(purchaseTurn.getId());
        purchaseTurnDTO.setIdCommerce(purchaseTurn.getIdCommerce());
        purchaseTurnDTO.setDeliveryType(purchaseTurn.getDeliveryType());
        purchaseTurnDTO.setIdUser(purchaseTurn.getIdUser());
        purchaseTurnDTO.setAddress(purchaseTurn.getDeliveryAddress());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        purchaseTurnDTO.setDate(purchaseTurn.getDateTurn().format(dateTimeFormatter));
        return purchaseTurnDTO;
    }

    private PurchaseTurn mapDtoToModel(PurchaseTurnDTO purchaseTurnDTO) {
        PurchaseTurn purchaseTurn = new PurchaseTurn();
        purchaseTurn.setIdCommerce(purchaseTurnDTO.getIdCommerce());
        purchaseTurn.setDeliveryType(purchaseTurnDTO.getDeliveryType());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        purchaseTurn.setDateTurn(LocalDateTime.parse(purchaseTurnDTO.getDate(), dateTimeFormatter));
        return purchaseTurn;
    }
}
