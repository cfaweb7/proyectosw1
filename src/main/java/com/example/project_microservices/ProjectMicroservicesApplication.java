package com.example.project_microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.context.event.ApplicationReadyEvent;


@SpringBootApplication
public class ProjectMicroservicesApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ProjectMicroservicesApplication.class, args);
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowedOriginPatterns("*") // Cambia por tus dominios reales
            .allowCredentials(true);
}
}
