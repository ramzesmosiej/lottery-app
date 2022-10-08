package com.participant.participant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients("com.openfeign.openfeign")
@SpringBootApplication
@EntityScan(basePackages = "com.shared2.classes")
public class ParticipantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParticipantApplication.class, args);
	}

}
