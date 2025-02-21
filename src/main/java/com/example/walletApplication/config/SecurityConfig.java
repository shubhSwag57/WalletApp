package com.example.walletApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for APIs
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/clients/registerUser").permitAll()
                                .requestMatchers("/clients/loginUser").permitAll()
                                .requestMatchers("/wallet-management/**").permitAll()
                                .requestMatchers("/clients/{clientId}/wallets/**").permitAll()
                                .requestMatchers("/clients/{clientId}/**").permitAll()
                                .requestMatchers("/swagger/**","/v3/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs/swagger-config").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                                .requestMatchers("/swagger-ui/swagger-ui.css", "/swagger-ui/index.css", "/swagger-ui/swagger-ui-bundle.js", "/swagger-ui/swagger-ui-standalone-preset.js").permitAll()
                                .anyRequest().authenticated() // Secure all other endpoints
                )
                .httpBasic(withDefaults()); // Enable basic authentication

        return http.build();
    }
}