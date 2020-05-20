package desapp.grupo.e.service.login;

import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.exception.EmailRegisteredException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginService {

    private UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User signUp(User user) {
        Optional<User> optUser = this.userRepository.findByEmail(user.getEmail());
        if(optUser.isPresent()) {
            throw new EmailRegisteredException(String.format("Email %s was already registered", user.getEmail()));
        }
        this.userRepository.save(user);
        return user;
    }
}
