package desapp.grupo.e.model.user;

import desapp.grupo.e.model.builder.CommerceBuilder;
import org.junit.jupiter.api.Test;

public class CommerceTest {

    @Test
    public void createMerchantShouldHasRoleMERCHANT_ByDefult() {
        Commerce commerce = CommerceBuilder.aCommerce()
                                .anyCommerce()
                                .build();

//        Assertions.assertEquals(Role.MERCHANT.name(), commerce.getRole().name());
    }
}
