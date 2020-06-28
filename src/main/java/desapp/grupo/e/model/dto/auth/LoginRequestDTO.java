package desapp.grupo.e.model.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequestDTO {

    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String password;
    private String secret;
    private Boolean auth2fa;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean getAuth2fa() {
        return auth2fa;
    }

    public void setAuth2fa(Boolean auth2fa) {
        this.auth2fa = auth2fa;
    }

}
