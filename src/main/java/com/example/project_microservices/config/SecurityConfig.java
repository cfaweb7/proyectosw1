package com.example.project_microservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.ws.rs.HttpMethod;

import java.util.List;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      // Usa la config CORS anterior
          .cors(Customizer.withDefaults())               // usa el WebMvcConfigurer

      // Desactiva CSRF en APIs
      .csrf(csrf -> csrf.disable())
      // Permite sin autenticación OPTIONS y los endpoints públicos
      .authorizeHttpRequests(auth -> auth
          .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()   // ← preflight libre
          .requestMatchers("/api/auth/**").permitAll()              // login / register
          .anyRequest().authenticated()
      )
      // Quita httpBasic si no lo necesitas
      .httpBasic(AbstractHttpConfigurer::disable);
    return http.build();
  }
}



