package desapp.grupo.e.model.dto;

public class ApiError {

    private String error;

    public ApiError() {
        // Para el mapping de Jackson
    }

    public ApiError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
