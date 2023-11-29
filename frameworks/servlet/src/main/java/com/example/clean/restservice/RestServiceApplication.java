package com.example.clean.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.example.clean")
public class RestServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

}
