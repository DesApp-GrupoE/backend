package desapp.grupo.e.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import desapp.grupo.e.service.log.Log;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Log.debug("Aplicación iniciada correctamente");
    }
}
