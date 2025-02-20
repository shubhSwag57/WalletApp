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
                                .requestMatchers("/clients/registerUser").permitAll()
                                .requestMatchers("/clients/loginUser").permitAll()
                                .requestMatchers("/wallet-management/**").permitAll()
                                .requestMatchers("/clients/{clientId}/wallets/**").permitAll()
                                .requestMatchers("/clients/{clientId}/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

// Allow access to all wallet endpoints
                                // Allow public access to registration
                                .anyRequest().authenticated() // Secure all other endpoints
                )
                .httpBasic(withDefaults()); // Enable basic authentication

        return http.build();
    }
}