package app.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.login.LoginService;
import service.persistence.daos.CustomerDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class ConfigBean {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("ComprandoEnCasa");
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public CustomerDao customerDao() {
        return new CustomerDao(entityManager());
    }

    @Bean
    // Este método inyecta en el código un nuevo LoginService utilizando el nombre del método
    public LoginService loginService() {
        return new LoginService(customerDao());
    }
}
