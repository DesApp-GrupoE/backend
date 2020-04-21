package model.user;

import model.builder.CustomerBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    public void createUserCostumerHasRoleCUSTOMER_ByDefault() {
        Customer customer =  new CustomerBuilder().anyCustomer().build();

        Assertions.assertEquals(Role.CUSTOMER.name(), customer.getRole().name());
    }
}
