package com.simple;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.simple.model.User;
import com.simple.service.UserRepository;

@SpringBootApplication
public class SpringUserDbApplication {

	@Autowired
	UserRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringUserDbApplication.class, args);
	}
	
	@Component
	public class CommandLineRunnerBean implements CommandLineRunner {	
	    public void run(String... args) {
	    	//clearing database
	    	repository.deleteAll();
	    	//creating some users
	    	repository.saveAll(Arrays.asList(new User("Piotrek", "Kwadrat"), new User("Franek", "Kwadrat"),
					new User("Dawid", "Tensor"), new User("Tomasz", "Zappa"),
					new User("Anna", "Kopycka"), new User("Random", "Guy")));
	    }
	} 
}
