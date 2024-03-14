package br.com.amaral.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
	
	@Bean
    public <T> Class<T> entityClass() {
        return (Class<T>) Object.class;
    }

}
