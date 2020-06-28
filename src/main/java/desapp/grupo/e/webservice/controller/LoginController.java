package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.model.dto.auth.Login2FARequestDTO;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import desapp.grupo.e.service.login.LoginService;

import javax.validation.Valid;

@RestController
public class LoginController {

    private static final String URL_BASE = "/auth";
    private LoginService loginService;
    private AuthService authService;

    public LoginController(LoginService loginService, AuthService authService) {
        this.loginService = loginService;
        this.authService = authService;
    }

    @PostMapping(URL_BASE + "/sign-up")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        User newUser = this.loginService.signUp(new User(userDTO));
        return ResponseEntity.ok(new UserDTO(newUser));
    }

    @PostMapping(URL_BASE + "/logout")
    public ResponseEntity logOut(@RequestHeader(value="Authorization") String token) {
        this.authService.addTokenToBlackList(token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(URL_BASE + "/code-otp")
    public ResponseEntity verifyCode(@Valid @RequestBody Login2FARequestDTO login2FARequestDTO) {
        return ResponseEntity.ok(authService.validateCode2FA(login2FARequestDTO.getEmail(), login2FARequestDTO.getCode()));
    }

}
