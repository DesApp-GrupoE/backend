package model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompradorTest {

    @Test
    public void creacionUsuarioComprador() {
        Customer customer =  new Customer();

        Assertions.assertEquals(UserRol.CUSTOMER.name(), customer.getRole().name());
    }
}
