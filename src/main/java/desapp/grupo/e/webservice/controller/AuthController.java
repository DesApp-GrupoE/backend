package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.config.oauth2.model.CurrentUser;
import desapp.grupo.e.config.oauth2.model.UserPrincipal;
import desapp.grupo.e.model.dto.auth.Login2FARequestDTO;
import desapp.grupo.e.model.dto.auth.LoginRequestDTO;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private static final String URL_BASE = "/auth";
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(URL_BASE + "/sign-up")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        User newUser = this.authService.signUp(new User(userDTO));
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).body(new UserDTO(newUser));
    }

    @PostMapping(URL_BASE + "/login")
    public ResponseEntity<TokenDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(this.authService.authenticate(loginRequest));
    }

    @PostMapping(URL_BASE + "/logout")
    public ResponseEntity logOut(@RequestHeader(value="Authorization") String token) {
        this.authService.addTokenToBlackList(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_BASE + "/code-otp")
    public ResponseEntity<TokenDTO> verifyCode(@Valid @RequestBody Login2FARequestDTO login2FARequestDTO) {
        return ResponseEntity.ok(authService.validateCode2FA(login2FARequestDTO.getEmail(), login2FARequestDTO.getCode()));
    }

    @GetMapping(URL_BASE + "/2fa")
    public ResponseEntity getSecret2fa(@CurrentUser UserPrincipal userPrincipal) {
        String secret = authService.getSecret2fa(userPrincipal.getId());
        Map<String, String> map = new HashMap<>();
        map.put("secret", secret);
        return ResponseEntity.ok(map);
    }

    @PostMapping(URL_BASE + "/2fa/enabled")
    public ResponseEntity enable2FA(@CurrentUser UserPrincipal userPrincipal) {
        authService.enabled2fa(userPrincipal.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_BASE + "/2fa/disabled")
    public ResponseEntity disable2fa(@CurrentUser UserPrincipal userPrincipal) {
        authService.disabled2fa(userPrincipal.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
