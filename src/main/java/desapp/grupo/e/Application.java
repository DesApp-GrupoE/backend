package desapp.grupo.e;

import desapp.grupo.e.helper.DatabaseDummyDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import desapp.grupo.e.service.log.Log;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    private DatabaseDummyDataInitializer dummyDataInitializer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Log.debug("Aplicación iniciada correctamente");
    }

    @Bean
    public CommandLineRunner insertDataInDb() {
        return (args) -> dummyDataInitializer.initDatabaseWithData();
    }
}
