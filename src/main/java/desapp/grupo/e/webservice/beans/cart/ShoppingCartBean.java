package desapp.grupo.e.webservice.beans.cart;

import desapp.grupo.e.persistence.product.OfferRepository;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.service.cart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingCartBean {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OfferRepository offerRepository;

    @Bean
    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartService(productRepository, offerRepository);
    }
}
