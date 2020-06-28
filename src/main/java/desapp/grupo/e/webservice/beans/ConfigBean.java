package desapp.grupo.e.webservice.beans;

import desapp.grupo.e.persistence.category.alert.CategoryAlertRepository;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.persistence.commerce.CommerceRepository;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.service.auth.AuthService;
import desapp.grupo.e.service.category.alert.CategoryAlertService;
import desapp.grupo.e.service.login.UserDetailsServiceImpl;
import desapp.grupo.e.service.user.UserService;
import desapp.grupo.e.service.product.ProductService;
import desapp.grupo.e.service.commerce.CommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import desapp.grupo.e.service.login.LoginService;
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

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("loginServiceBean")
    public LoginService loginService() {
        return new LoginService(userRepository, bCryptPasswordEncoder());
    }

    @Bean("authServiceBean")
    public AuthService authService() {
        return new AuthService(userRepository);
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository, authService());
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
}
