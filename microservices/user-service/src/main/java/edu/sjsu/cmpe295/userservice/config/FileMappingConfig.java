package edu.sjsu.cmpe295.userservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileMappingConfig implements WebMvcConfigurer {
    @Value("${image.upload.path}")
    private String uploadPath;

    @Value("${image.static.path}")
    private String staticPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String staticMapping = staticPath + "**";
        String localDirectory = "file:" + uploadPath;
        registry.addResourceHandler(staticMapping).addResourceLocations(localDirectory);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
