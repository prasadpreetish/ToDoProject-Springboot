package com.todoProject.ToDoProject.config;

import com.todoProject.ToDoProject.filter.JWTGenerationFilter;
import com.todoProject.ToDoProject.filter.JWTValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(csrfConfig->csrfConfig.disable());

        http.sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterAfter(new JWTGenerationFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JWTValidatorFilter(), BasicAuthenticationFilter.class);
        http.authorizeHttpRequests(req-> req
                .requestMatchers("/","/register").permitAll()
                .requestMatchers("/apiLogin").permitAll()
                .requestMatchers("/todo/**").authenticated()
                .anyRequest().authenticated()
        );

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
