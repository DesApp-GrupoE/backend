package desapp.grupo.e.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import desapp.grupo.e.service.log.Log;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"desapp.grupo.e.persistence"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Log.debug("Aplicación iniciada correctamente");
    }
}
