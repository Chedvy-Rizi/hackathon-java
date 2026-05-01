package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // מאפשר את כל הנתיבים
                // מגדיר בדיוק את הפורטים המותרים - מונע שגיאות אבטחה של הדפדפן
                .allowedOrigins("http://localhost:5173", "http://localhost:5174") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // מומלץ ל-true אם מעבירים טוקנים של התחברות
    }
}