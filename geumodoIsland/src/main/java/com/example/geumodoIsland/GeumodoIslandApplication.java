package com.example.geumodoIsland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GeumodoIslandApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeumodoIslandApplication.class, args);
	}
}
