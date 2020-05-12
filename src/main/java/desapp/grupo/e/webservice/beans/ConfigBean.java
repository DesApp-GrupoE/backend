package desapp.grupo.e.webservice.beans;

import desapp.grupo.e.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import desapp.grupo.e.service.login.LoginService;

@Configuration
public class ConfigBean {

    @Autowired
    private UserRepository userRepository;

    @Bean
    // Este método inyecta en el código un nuevo LoginService utilizando el nombre del método
    public LoginService loginService() {
        return new LoginService(userRepository);
    }
}
