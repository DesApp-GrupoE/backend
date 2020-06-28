package desapp.grupo.e.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {

    private String type;
    private String token;
    @JsonProperty("expires_in")
    private long expiresIn;
    private Boolean tokenActivated;
    private String secretKey;

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

    public Boolean getTokenActivated() {
        return this.tokenActivated;
    }

    public void setTokenActivated(Boolean tokenActivated) {
        this.tokenActivated = tokenActivated;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
