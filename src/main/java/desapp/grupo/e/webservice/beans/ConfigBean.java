package desapp.grupo.e.webservice.beans;

import desapp.grupo.e.persistence.category.alert.CategoryAlertRepository;
import desapp.grupo.e.persistence.purchase.PurchaseTurnRepository;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.persistence.commerce.CommerceRepository;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.service.auth.AuthService;
import desapp.grupo.e.service.auth.TotpService;
import desapp.grupo.e.service.category.alert.CategoryAlertService;
import desapp.grupo.e.service.login.UserDetailsServiceImpl;
import desapp.grupo.e.service.mail.MailService;
import desapp.grupo.e.service.mapper.CartProductMapper;
import desapp.grupo.e.service.mapper.CommerceMapperService;
import desapp.grupo.e.service.purchase.PurchaseTurnService;
import desapp.grupo.e.service.user.UserService;
import desapp.grupo.e.service.product.ProductService;
import desapp.grupo.e.service.commerce.CommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ConfigBean {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryAlertRepository categoryAlertRepository;
    @Autowired
    private CommerceRepository commerceRepository;
    @Autowired
    private PurchaseTurnRepository purchaseTurnRepository;
    @Autowired
    private MailService mailService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("toptServiceBean")
    public TotpService totpService() {
        return new TotpService();
    }

    @Bean("authServiceBean")
    public AuthService authService() {
        return new AuthService(userRepository, totpService(), bCryptPasswordEncoder(), mailService);
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public CategoryAlertService categoryAlertService() {
        return new CategoryAlertService(userRepository, categoryAlertRepository);
    }

    @Bean
    public ProductService productService() {
        return new ProductService(commerceRepository, productRepository);
    }

    @Bean
    public CommerceService commerceService() {
        return new CommerceService(userRepository, commerceRepository);
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public CommerceMapperService commerceMapperService() {
        return new CommerceMapperService();
    }

    @Bean("purchaseTurnServiceBean")
    public PurchaseTurnService purchaseTurnService() {
        return new PurchaseTurnService(purchaseTurnRepository);
    }

    @Bean("cartProductMapperBean")
    public CartProductMapper cartProductMapper() {
        return new CartProductMapper();
    }
}
