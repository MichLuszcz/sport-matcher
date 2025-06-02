package paint.projekt.sport_matcher.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity (not recommended for production)
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Allow all requests without authentication
            )
            .httpBasic().disable() // Disable basic auth
            .formLogin().disable(); // Disable form login

        return http.build();
    }
}

