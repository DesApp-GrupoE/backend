package app.controller;

import model.dto.ApiError;
import model.dto.user.CustomerDTO;
import model.user.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.login.LoginService;
import service.persistence.exception.DataErrorException;
import service.persistence.exception.UniqueClassException;

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
