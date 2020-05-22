package desapp.grupo.e.webservice.beans;

import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import desapp.grupo.e.service.login.LoginService;

@Configuration
public class ConfigBean {

    @Autowired
    private UserRepository userRepository;

    @Bean("loginServiceBean")
    public LoginService loginService() {
        return new LoginService(userRepository);
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }
}
