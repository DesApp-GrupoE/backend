package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.config.oauth2.model.CurrentUser;
import desapp.grupo.e.config.oauth2.model.UserPrincipal;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final String URL_BASE = "/user";
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(URL_BASE + "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @GetMapping(URL_BASE + "/me")
    public ResponseEntity<UserDTO> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUserById(userPrincipal.getId());
        return ResponseEntity.ok(new UserDTO(user));
    }
}
