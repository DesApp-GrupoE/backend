package desapp.grupo.e.webservice.beans;

import desapp.grupo.e.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import desapp.grupo.e.service.login.LoginService;
import desapp.grupo.e.persistence.daos.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class ConfigBean {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("ComprandoEnCasa");
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public UserDao customerDao() {
        return new UserDao(entityManager());
    }

    @Bean
    // Este método inyecta en el código un nuevo LoginService utilizando el nombre del método
    public LoginService loginService() {
        return new LoginService(userRepository);
    }
}
