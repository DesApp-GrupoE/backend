package desapp.grupo.e.helper;

import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDummyDataInitializer {

    @Value("${spring.profiles.active}")
    private String typeDeploy;
    @Autowired
    private ProductRepository productRepository;

    public void initDatabaseWithData() {
        if(!"dev".equalsIgnoreCase(typeDeploy)) {
            return; // Finalize execution
        }
        Product product1 = ProductBuilder.aProduct()
                .withIdCommerce(1L)
                .withName("Fideos").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .withImg("https://http2.mlstatic.com/fideo-lucchetti-tallarin-500-grs-D_NQ_NP_952769-MLA31029340446_062019-F.jpg")
                .build();
        Product product2 = ProductBuilder.aProduct()
                .withIdCommerce(1L)
                .withName("Coca Cola 2.25L").withBrand("Coca Cola")
                .withStock(100).withPrice(120.0)
                .withImg("https://www.licoreraexpress.com/wp-content/uploads/2018/01/Coca-Cola-Bottle-2L-1.jpg")
                .build();

        this.productRepository.save(product1);
        this.productRepository.save(product2);
    }

}
