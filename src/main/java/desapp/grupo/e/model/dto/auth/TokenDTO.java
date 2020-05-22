package desapp.grupo.e.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {

    private String type;
    private String token;
    @JsonProperty("expires_in")
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
