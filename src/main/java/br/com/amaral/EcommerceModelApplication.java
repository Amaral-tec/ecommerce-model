package br.com.amaral;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.logging.LogManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EntityScan(basePackages = "br.com.amaral.model")
@ComponentScan(basePackages = {"br.*"})
@EnableJpaRepositories(basePackages = {"br.com.amaral.repository"})
@EnableTransactionManagement
public class EcommerceModelApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		
		SpringApplication.run(EcommerceModelApplication.class, args);
	}

	/**
	 * Competing methods and messaging
	 */
	@Override
	@Bean
	public Executor getAsyncExecutor() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();	
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assyncrono Thread");
		executor.initialize();
		
		return executor;
	}
	
	private static void configureLogging() {
        try (InputStream configFile = EcommerceModelApplication.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
