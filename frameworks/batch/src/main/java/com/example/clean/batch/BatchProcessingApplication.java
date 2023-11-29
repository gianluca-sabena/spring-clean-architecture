package com.example.clean.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"com.example.clean.batch", "com.example.clean.adapter"})
@SpringBootApplication(scanBasePackages = "com.example.clean")
public class BatchProcessingApplication {
	// @Autowired
	// MyService myService;

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));
	}
}