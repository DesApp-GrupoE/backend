package desapp.grupo.e.helper;

import desapp.grupo.e.model.builder.commerce.CommerceBuilder;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.user.UserRepository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDummyDataInitializer {

    @Value("${spring.profiles.active}")
    private String typeDeploy;
    @Autowired
    private UserRepository userRepository;

    public void initDatabaseWithData() {
        if(!"dev".equalsIgnoreCase(typeDeploy)) {
            return; // Finalize execution
        }
        
        User user1 = UserBuilder.aUser()
                .withName("Pepe")
                .withSurname("Pepe")
                .withEmail("pepito@test.com")
                .withPassword("12345")
                .build();
        
        Commerce commerce1 = CommerceBuilder.aCommerce()
                .withName("Lo de Minguito")
                .withAddress("Brandsen")
                .withAddressNumber(300L)
                .withLocation("Quilmes")
                .withPhone(1155443322L)
                .build();


        Product product1 = ProductBuilder.aProduct()
                .withName("Fideos").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .withImg("https://http2.mlstatic.com/fideo-lucchetti-tallarin-500-grs-D_NQ_NP_952769-MLA31029340446_062019-F.jpg")
                .build();
        Product product2 = ProductBuilder.aProduct()
                .withName("Coca Cola 2.25L").withBrand("Coca Cola")
                .withStock(100).withPrice(120.0)
                .withImg("https://www.licoreraexpress.com/wp-content/uploads/2018/01/Coca-Cola-Bottle-2L-1.jpg")
                .build();
				
      
      	List<Product> products = Arrays.asList(product1, product2);
      	commerce1.setProducts(products);
      	user1.setCommerce(commerce1);
          
        this.userRepository.save(user1);
    }

}