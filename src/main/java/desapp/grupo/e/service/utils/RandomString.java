package desapp.grupo.e.service.utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

public class RandomString {

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "0123456789";
    private static final String alphanumeric = upper + lower + digits;
    private Random random;

    public RandomString() {
        this.random = new SecureRandom();
    }

    public String nextString(int length) {
        if(length < 1) {
            throw new IllegalArgumentException("Integer debe ser un número positivo");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }
        return sb.toString();
    }

}