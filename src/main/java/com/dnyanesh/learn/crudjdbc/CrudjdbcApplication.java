package com.dnyanesh.learn.crudjdbc;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class CrudjdbcApplication {

	public static void main(String[] args) throws Exception {
		System.out.println("hello world !!!!");
		
		//scfMPEKS.dnyanesh("other scheme");


		SpringApplication.run(CrudjdbcApplication.class, args);
	}

}
