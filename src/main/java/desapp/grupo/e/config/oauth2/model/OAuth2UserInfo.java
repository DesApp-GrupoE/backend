package desapp.grupo.e.config.oauth2.model;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getFullName();

    public abstract String getName();

    public abstract String getSurname();

    public abstract String getEmail();

    public abstract String getImageUrl();
}