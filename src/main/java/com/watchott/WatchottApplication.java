package com.watchott;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WatchottApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchottApplication.class, args);
	}

}
