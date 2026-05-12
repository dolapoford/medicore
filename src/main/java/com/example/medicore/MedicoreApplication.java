package com.example.medicore;

import com.example.medicore.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class MedicoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicoreApplication.class, args);
	}

}
