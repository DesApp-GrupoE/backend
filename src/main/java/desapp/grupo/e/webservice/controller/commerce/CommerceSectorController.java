package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.model.user.CommerceSector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommerceSectorController {

    @GetMapping("/commerce-sector")
    public ResponseEntity getCommerceSectors() {
        return ResponseEntity.ok(CommerceSector.values());
    }
}
