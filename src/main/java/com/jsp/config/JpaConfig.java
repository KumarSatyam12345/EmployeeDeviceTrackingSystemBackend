package com.jsp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) mappings.
 * Implements the {@link WebMvcConfigurer} interface to customize web MVC configurations.
 */
@Configuration
public class JpaConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings to allow cross-origin requests.
     *
     * @param registry The {@link CorsRegistry} to add CORS mappings to.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Applies CORS settings to all endpoints under /api/**
                .allowedOrigins("*")  // Allows requests from all origins.
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specifies allowed HTTP methods.
                .allowedHeaders("*"); // Allows all headers in requests.
    }
}
