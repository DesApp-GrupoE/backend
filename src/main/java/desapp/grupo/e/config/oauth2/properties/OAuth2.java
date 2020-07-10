package desapp.grupo.e.config.oauth2.properties;

import java.util.ArrayList;
import java.util.List;

public final class OAuth2 {
    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
        return authorizedRedirectUris;
    }

    public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
        this.authorizedRedirectUris = authorizedRedirectUris;
        return this;
    }
}