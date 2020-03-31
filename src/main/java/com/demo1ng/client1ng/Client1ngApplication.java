package com.demo1ng.client1ng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo1ng.client1ng")
public class Client1ngApplication {

	public static void main(String[] args) {
		SpringApplication.run(Client1ngApplication.class, args);
	}

}
