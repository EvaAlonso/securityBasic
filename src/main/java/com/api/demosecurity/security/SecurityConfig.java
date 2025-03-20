package com.api.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails juan = User.builder()
                .username("Juan")
                .password("{noop}123")
                .roles("PROFESOR")
                .build();

        UserDetails pedro = User.builder()
                .username("pedro")
                .password("{noop}123")
                .roles("DIRECTOR")
                .build();
        return  new InMemoryUserDetailsManager(juan, pedro);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/students").hasRole("PROFESOR")
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasRole("PROFESOR")
                        .requestMatchers(HttpMethod.POST, "/api/students").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/students").hasRole("DIRECTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("DIRECTOR")
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
