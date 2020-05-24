package desapp.grupo.e.webservice.beans;

import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.category.alert.CategoryAlertService;
import desapp.grupo.e.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import desapp.grupo.e.service.login.LoginService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ConfigBean {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("loginServiceBean")
    public LoginService loginService() {
        return new LoginService(userRepository, bCryptPasswordEncoder());
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public CategoryAlertService categoryAlertService() {
        return new CategoryAlertService(userRepository);
    }
}
