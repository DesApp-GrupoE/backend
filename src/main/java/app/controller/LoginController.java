package app.controller;

import model.dto.user.CustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @PostMapping("/sign-up")
    public ResponseEntity<CustomerDTO> signUp(@Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerDTO);
    }

}
