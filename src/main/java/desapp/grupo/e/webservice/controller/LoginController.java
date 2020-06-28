package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.model.dto.auth.Login2FARequestDTO;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import desapp.grupo.e.service.login.LoginService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private LoginService loginService;
    private AuthService authService;

    public LoginController(LoginService loginService, AuthService authService) {
        this.loginService = loginService;
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        User newUser = this.loginService.signUp(new User(userDTO));
        return ResponseEntity.ok(new UserDTO(newUser));
    }

    @PostMapping("/logout")
    public ResponseEntity logOut(@RequestHeader(value="Authorization") String token) {
        this.authService.addTokenToBlackList(token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/code-otp")
    public ResponseEntity verifyCode(@Valid @RequestBody Login2FARequestDTO login2FARequestDTO) {
        return ResponseEntity.ok(authService.validateCode2FA(login2FARequestDTO.getEmail(), login2FARequestDTO.getCode()));
    }

}
