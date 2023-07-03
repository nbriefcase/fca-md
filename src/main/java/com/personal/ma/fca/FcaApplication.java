package com.personal.ma.fca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // Disabling the Auto-Configuration
public class FcaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcaApplication.class, args);
	}

}
