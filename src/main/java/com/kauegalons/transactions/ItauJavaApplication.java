package com.kauegalons.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ItauJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItauJavaApplication.class, args);
	}

}
