package com.ericsson.projectd20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Projectd20Application {

	public static void main(String[] args) {
		SpringApplication.run(Projectd20Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/build-failure-rate/*").allowedOrigins("http://localhost:8081");
				registry.addMapping("/build-recovery-time-v2/*").allowedOrigins("http://localhost:8081");
				registry.addMapping("/jobs").allowedOrigins("http://localhost:8081");
			}
		};
	}
}
