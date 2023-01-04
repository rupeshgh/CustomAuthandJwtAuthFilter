package com.example.demo;

import com.example.demo.model.Roles;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}



	//Adding users and roles into tables
	@Bean
	CommandLineRunner run(UserService userService){

		return args -> {

			userService.saveRole(new Roles(null,"ROLE_USER"));
			userService.saveRole(new Roles(null,"ROLE_ADMIN"));


			userService.createUser(new User(null,"xyz1","123","xyz1@gmail.com",new ArrayList<>()));
			userService.createUser(new User(null,"xyz2","123","xyz2@gmail.com",new ArrayList<>()));
			userService.createUser(new User(null,"xyz3","123","xyz3@gmail.com",new ArrayList<>()));
			userService.createUser(new User(null,"xyz4","123","xyz4@gmail.com",new ArrayList<>()));
			userService.createUser(new User(null,"xyz5","123","xyz5@gmail.com",new ArrayList<>()));



			userService.addRoleToUser("xyz1@gmail.com","ROLE_ADMIN");
			userService.addRoleToUser("xyz2@gmail.com","ROLE_USER");
			userService.addRoleToUser("xyz3@gmail.com","ROLE_USER");
			userService.addRoleToUser("xyz4@gmail.com","ROLE_USER");
			userService.addRoleToUser("xyz5@gmail.com","ROLE_USER");



		};
	}

}
