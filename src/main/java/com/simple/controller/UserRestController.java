package com.simple.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.simple.model.User;
import com.simple.service.UserRepository;


@RestController
@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 4800, allowCredentials = "false")
@RequestMapping("/api/user")
public class UserRestController {

	public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping("/generate")
	public ResponseEntity<String> process(){
		logger.info("Generating 5 customers...");
		// save a list of Customers // SaveAll???
		repository.saveAll(Arrays.asList(new User("Piotrek", "Kwadrat"), new User("Franek", "Kwadrat"),
										new User("Dawid", "Tensor"), new User("Tomasz", "Zappa"),
										new User("Anna", "Kopycka"), new User("Random", "Guy")));
		return new ResponseEntity<String>("Generated example users.",HttpStatus.CREATED);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(){
		logger.info("Retrieving all users.");
		ArrayList<User> result = new ArrayList<>();
		for(User user : repository.findAll()){
			result.add(user);
			//logger.info(user.toString());
		}
		return new ResponseEntity<ArrayList<User>>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("id") long id){
		logger.info("Retrieving user id={}",id);
		Optional<User> user = repository.findById(id);
		return new ResponseEntity<User>(user.get(),HttpStatus.OK);
	}
	
	//TODO: Zapytanie o istniejacy rekord
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
		logger.info("Creating new customer...");
        //if ( !(repository.findUser(user.getFirstName(), user.getLastName())).isEmpty()) { 
        //    logger.error("Unable to create. A User with name {} {} already exist", user.getFirstName(),user.getLastName());
        //    return new ResponseEntity<String>("User already in the database!",HttpStatus.CONFLICT);
        //}
		User newUser = new User(user.getFirstName(), user.getLastName());
		repository.save(newUser);
		//Jak teraz od razu odebrac dodany obiekt z bazy wzbogacony o id, ktore jest automatycznie generowane
		//Odpowiedz: jest juz z automatu to
    	return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
							
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUserById(@PathVariable("id") long id, @RequestBody User updatedUser){
		logger.info("Updating user id={}",id);
		Optional<User> user = repository.findById(id);
		user.get().setFirstName(updatedUser.getFirstName());
		user.get().setLastName(updatedUser.getLastName());
		repository.save(user.get());
		return new ResponseEntity<User>(user.get(),HttpStatus.OK);
	}
	
		/*
	//Tego narazie nie robie w ogóle
	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("lastname") String lastName){
		String result = "";
		for(Customer cust: repository.findByLastName(lastName)){
			result += cust.toString() + "<br>"; 
		}
		return result;
	}
	*/
	
	//@RequestMapping("/add") //We're dealing with it right now
	
	
	
}