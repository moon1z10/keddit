package hodlene.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(@Value("${app.cors.allowedOrigins}") String allowedOrigins) {
        System.out.println("WebConfig corsConfigurer("+allowedOrigins+")");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nullable CorsRegistry registry) {
                if (registry == null) return;
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}