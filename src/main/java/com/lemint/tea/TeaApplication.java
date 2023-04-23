package com.lemint.tea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class TeaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeaApplication.class, args);
	}

}
