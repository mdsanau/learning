package com.mynewblog33;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Myblog33Application {
	public static void main(String[] args) {
		SpringApplication.run(Myblog33Application.class, args);


		System.out.println("done");
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
