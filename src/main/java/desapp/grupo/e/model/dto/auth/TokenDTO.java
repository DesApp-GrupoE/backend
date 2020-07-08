package desapp.grupo.e.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {

    private String type;
    private String token;
    @JsonProperty("expires_in")
    private long expiresIn;
    private boolean authWith2fa;

    public TokenDTO() {
        // Para el mapping de Jackson
    }

    public TokenDTO(Boolean authWith2fa) {
        this.authWith2fa = authWith2fa;
    }

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

    public Boolean getAuthWith2fa() {
        return this.authWith2fa;
    }

    public void setAuthWith2fa(Boolean authWith2fa) {
        this.authWith2fa = authWith2fa;
    }

}
