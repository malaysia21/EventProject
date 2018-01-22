package events.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {


        return new WebMvcConfigurerAdapter() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        //.allowedOrigins("http://domain2.com")
                        .allowedMethods("PUT", "DELETE", "POST", "GET")
                       .allowedHeaders("/**")
                        .exposedHeaders("/**")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }
}