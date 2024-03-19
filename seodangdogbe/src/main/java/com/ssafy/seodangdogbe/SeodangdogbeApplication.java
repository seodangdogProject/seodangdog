package com.ssafy.seodangdogbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SeodangdogbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeodangdogbeApplication.class, args);
	}

}
