package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.auth.AuthService;
import desapp.grupo.e.service.log.Log;

import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import desapp.grupo.e.service.login.LoginService;
import desapp.grupo.e.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private LoginService loginService;
    private AuthService authService;
    @Autowired
    private UserService userService;

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

    @PostMapping("/code")
    public ResponseEntity verifyCode(@RequestParam String email, @RequestParam String code) {

            User user = userService.getUserByEmail(email);
            Totp totp = new Totp(user.getSecret());
            
            if (!isValidLong(code) || !totp.verify(code)) {
                 return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            TokenDTO  tokenEmail = authService.getTokenByEmail(user.getEmail());
            return new ResponseEntity(tokenEmail, HttpStatus.OK);
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
