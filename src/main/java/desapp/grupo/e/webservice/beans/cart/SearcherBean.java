package desapp.grupo.e.webservice.beans.cart;

import desapp.grupo.e.persistence.product.ProductRepositoryJdbcImpl;
import desapp.grupo.e.service.search.PositionStackService;
import desapp.grupo.e.service.search.ProductSearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SearcherBean {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public ProductRepositoryJdbcImpl productRepositoryJDBC() {
        return new ProductRepositoryJdbcImpl(jdbcTemplate);
    }

    @Bean(name = "positionStackServiceBean")
    public PositionStackService positionStackService() {
        return new PositionStackService(restTemplate());
    }

    @Bean(name = "productSearchServiceBean")
    public ProductSearcherService productSearcherService() {
        return new ProductSearcherService(productRepositoryJDBC(), positionStackService());
    }
}
