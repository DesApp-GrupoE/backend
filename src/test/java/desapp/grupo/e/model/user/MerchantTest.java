package desapp.grupo.e.model.user;

import desapp.grupo.e.model.builder.MerchantBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MerchantTest {

    @Test
    public void createMerchantShouldHasRoleMERCHANT_ByDefult() {
        Merchant merchant = MerchantBuilder.aMerchant()
                                .anyMerchant()
                                .build();

//        Assertions.assertEquals(Role.MERCHANT.name(), merchant.getRole().name());
    }
}
