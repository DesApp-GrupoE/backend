package desapp.grupo.e.service.login;

import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.utils.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.exception.EmailRegisteredException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Transactional
    public User signUp(User user) {
        Optional<User> optUser = this.userRepository.findByEmail(user.getEmail());
        if(optUser.isPresent()) {
            throw new EmailRegisteredException(String.format("Email %s was already registered", user.getEmail()));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        RandomString randomString = new RandomString();
        String secretKey = randomString.nextStringOnlyCharacters(15);
        user.setSecret(secretKey);
        this.userRepository.save(user);
        return user;
    }
}
