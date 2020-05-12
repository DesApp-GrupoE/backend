package desapp.grupo.e.service.login;

import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.exception.UniqueClassException;

import java.util.Optional;

@Service
public class LoginService {

    private UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(User customer) throws UniqueClassException {
        Optional<User> optUser = this.userRepository.findByEmail(customer.getEmail());
        if(optUser.isPresent()) {
            throw new UniqueClassException("Email was already registered");
        }
        this.userRepository.save(customer);
        return customer;
    }
}
