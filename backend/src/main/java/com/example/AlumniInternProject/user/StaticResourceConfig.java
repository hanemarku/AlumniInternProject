package com.example.AlumniInternProject.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user-photos/**")
                .addResourceLocations("file:///C:/Users/DELL/SpringBoot/AlumniInternProject/backend/user-photos/");
    }
}
