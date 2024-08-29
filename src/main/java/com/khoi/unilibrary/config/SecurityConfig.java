package com.khoi.unilibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/home", "/login", "/forgot-password", "/reset-password").permitAll() // Allow public access to specified URLs
                        .anyRequest().authenticated() // Secure other URLs
                )
                .formLogin(form -> form
                        .loginPage("/login") // Specify the custom login page
                        .defaultSuccessUrl("/index", true) // Redirect after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL for logout
                        .logoutSuccessUrl("/login?logout") // Redirect after successful logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler mySuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/home");  // Redirect to /home after login
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
