package desapp.grupo.e.model.dto.auth;

import javax.validation.constraints.NotNull;

public class Login2FARequestDTO {

    @NotNull
    private String email;
    @NotNull
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
