package com.PGmitra.app.Config; // Adjust package as per your project structure

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Marks this class as a source of bean definitions
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // <--- 1. Apply CORS to all paths starting with /api/
                .allowedOrigins("http://localhost:3000") // <--- 2. Specify your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // <--- 3. Allow necessary HTTP methods
                .allowedHeaders("*") // <--- 4. Allow all headers (e.g., Content-Type, Authorization)
                .allowCredentials(true) // <--- 5. Allow sending of cookies, HTTP authentication, client-side SSL certificates
                .maxAge(3600); // <--- 6. How long the pre-flight response can be cached by the browser (in seconds)
    }
}