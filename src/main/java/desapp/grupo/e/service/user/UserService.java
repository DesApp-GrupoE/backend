package desapp.grupo.e.service.user;

import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", idUser)));
    }
}
