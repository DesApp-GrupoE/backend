package desapp.grupo.e.service.auth;

import desapp.grupo.e.config.oauth2.TokenService;
import desapp.grupo.e.model.dto.auth.LoginRequestDTO;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.exception.EmailRegisteredException;
import desapp.grupo.e.service.exceptions.AuthenticationCode2FAException;
import desapp.grupo.e.service.mail.MailService;
import desapp.grupo.e.service.utils.RandomString;
import desapp.grupo.e.webservice.security.SecurityConstants;
import desapp.grupo.e.persistence.user.UserRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class AuthService {

    private UserRepository userRepository;
    private TotpService toptService;
    private Map<String, TokenDTO> emailToken;
    private Map<String, Authentication> emailAuthentication;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private MailService mailService;


    public AuthService(UserRepository userRepository, TotpService totpService,
                       BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.emailToken = new HashMap<>();
        this.emailAuthentication = new HashMap<>();
        this.toptService = totpService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
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
        this.mailService.sendWelcomeEmail(user.getEmail(), user.getFullName());
        return user;
    }

    public TokenDTO authenticate(LoginRequestDTO loginRequest) {
        User user = userRepository.getByEmail(loginRequest.getEmail());
        Authentication authentication = createAuthentication(loginRequest.getEmail(), loginRequest.getPassword());
        TokenDTO token = tokenService.createToken(authentication);
        if (!user.getAuth2fa()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return token;
        } else {
            saveToValidate2fa(user, authentication, token);
            return new TokenDTO(true);
        }
    }

    private void saveToValidate2fa(User user, Authentication authentication, TokenDTO token) {
        this.emailToken.put(user.getEmail(), token);
        this.emailAuthentication.put(user.getEmail(), authentication);
    }

    private Authentication createAuthentication(String email, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }

    public TokenDTO validateCode2FA(String email, String code) {
        User user = userRepository.getByEmail(email);
        Totp totp = this.toptService.createTotp(user.getSecret());
        if (!isValidLong(code) || !totp.verify(code)) {
            throw new AuthenticationCode2FAException(String.format("Code '%s' not valid", code));
        }
        Authentication authentication = this.emailAuthentication.remove(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.emailToken.remove(email);
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void addTokenToBlackList(String bearerToken) {
        this.tokenService.addTokenToBlackList(bearerToken);
    }

    @Transactional
    public void enabled2fa(Long userId) {
        userRepository.enable2fa(true, userId);
    }

    @Transactional
    public void disabled2fa(Long userId) {
        userRepository.enable2fa(false, userId);
    }

    @Transactional(readOnly = true)
    public String getSecret2fa(Long userId) {
        return userRepository.getSecretKey(userId);
    }
}
