package desapp.grupo.e.app.controller;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.model.dto.user.CustomerDTO;
import desapp.grupo.e.model.user.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import desapp.grupo.e.service.login.LoginService;
import desapp.grupo.e.service.persistence.exception.DataErrorException;
import desapp.grupo.e.service.persistence.exception.UniqueClassException;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer newCustomer;
        try {
            newCustomer = this.loginService.signUp(new Customer(customerDTO));
        } catch (DataErrorException e) {
            ApiError apiError = new ApiError("Unexpected error. Please, try again.");
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UniqueClassException e) {
            ApiError apiError = new ApiError(e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(new CustomerDTO(newCustomer));
    }

}
