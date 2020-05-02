package desapp.grupo.e.model.user;

import desapp.grupo.e.model.builder.CustomerBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    public void createUserCostumerHasRoleCUSTOMER_ByDefault() {
        Customer customer =  CustomerBuilder.aCustomer().anyCustomer().build();

        Assertions.assertEquals(Role.CUSTOMER.name(), customer.getRole().name());
    }
}
