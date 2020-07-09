package desapp.grupo.e.webservice.security;

import desapp.grupo.e.service.login.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Solo permito que sea publicos los endpoints agregados en los matchers
        http.cors()
            .and().csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST,
                    "/auth/sign-up",
                    "/auth/login",
                    "/auth/code-otp",
                    "/cart",
                    "/cart/**/*",
                    "/products"
                ).permitAll()
                .antMatchers(HttpMethod.GET,
                    "/cart/*",
                    "/products",
                    "/commerce-sector"
                ).permitAll()
                .antMatchers(HttpMethod.PUT,
                        "/cart/*"
                ).permitAll()
                .antMatchers(HttpMethod.DELETE,
                    "/cart/*"
                ).permitAll()
                .anyRequest().authenticated()
            .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), getApplicationContext()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), getApplicationContext()))
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
            .and()
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(List.of("GET", "POST","PUT", "DELETE"));
        corsConfiguration.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Indico que usará UserDetailService para obtener el usuario y BCrypt para chequear que el password sea correcto
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}