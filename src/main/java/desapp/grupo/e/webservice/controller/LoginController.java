package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import desapp.grupo.e.service.login.LoginService;
import desapp.grupo.e.persistence.exception.UniqueClassException;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserDTO userDTO) {
        User newUser;
        try {
            newUser = this.loginService.signUp(new User(userDTO));
        } catch (UniqueClassException e) {
            ApiError apiError = new ApiError(e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(new UserDTO(newUser));
    }

}
