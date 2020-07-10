package desapp.grupo.e.config.oauth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }
}