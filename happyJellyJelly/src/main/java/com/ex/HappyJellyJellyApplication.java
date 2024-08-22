package com.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HappyJellyJellyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyJellyJellyApplication.class, args);
	}

}
