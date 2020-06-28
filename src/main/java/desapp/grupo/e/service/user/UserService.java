package desapp.grupo.e.service.user;

import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.auth.AuthService;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;

public class UserService {

    private UserRepository userRepository;
    private AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public User getUserById(Long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", idUser)));
    }

    public User getUserByToken(String token) {
        // getEmailByToken puede tirar exception, pero no acá. Ya que este método se usa en JWTAuthorizationFilter,
        // primero debe tirarla ahí y a este método no debe llegar
        String email = authService.getEmailByToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with email '%s' not found", email)));
    }

}
