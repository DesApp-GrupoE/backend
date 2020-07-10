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

    public LoginRequestDTO() {
        // Mapping de Jackson
    }

    public LoginRequestDTO(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

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

}
