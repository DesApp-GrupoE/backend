package desapp.grupo.e;

import desapp.grupo.e.config.oauth2.properties.ApplicationProperties;
import desapp.grupo.e.helper.DatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import desapp.grupo.e.service.log.Log;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class Application {

    @Autowired
    private DatabaseInitializer databaseInitializer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Log.debug("Aplicación iniciada correctamente");
    }

    @Bean
    public CommandLineRunner insertDataInDb() {
        return (args) -> {
            databaseInitializer.initDatabaseExtensions();
            databaseInitializer.initDatabaseWithData();
        };
    }
}
