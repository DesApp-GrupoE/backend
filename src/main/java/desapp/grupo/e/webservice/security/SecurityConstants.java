package desapp.grupo.e.webservice.security;

public class SecurityConstants {

    public static final String SECRET = "S3cr3tP4ZZw0rdUnqfy";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/sign-up";

    private SecurityConstants() {
        // Esta clase no se debe instanciar
    }
}