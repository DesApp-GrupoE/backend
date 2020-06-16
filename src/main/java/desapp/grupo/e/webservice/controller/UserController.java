package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserByToken(@RequestHeader("Authorization") String token) {
        User user = userService.getUserByToken(token);
        return ResponseEntity.ok(new UserDTO(user));
    }
}
