package com.simple.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.simple.model.User;
import com.simple.service.UserRepository;


@RestController
//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 4800, allowCredentials = "false")
@CrossOrigin
@RequestMapping("/api/user")
public class UserRestController {

	public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping("/generate")
	public ResponseEntity<String> process(){
		logger.info("Generating 5 users...");
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
		}
		return new ResponseEntity<ArrayList<User>>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("id") long id){
		logger.info("Retrieving user id={}",id);
		Optional<User> user = repository.findById(id);
		if(user.isPresent()) {
			return new ResponseEntity<User>(user.get(),HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/search/", method = RequestMethod.GET)
	public ResponseEntity<?> getSearchedUser(@RequestParam(value="term", required=true) String term){
		logger.info("Searching for user term={}",term);
		ArrayList<User> usersFound = repository.searchUsers(term);
		logger.info("users Found: {}",usersFound.size());
		if (!(usersFound.isEmpty())) {
			return new ResponseEntity<ArrayList<User>>(usersFound,HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder){
		logger.info("Creating new user...");
        if ( !(repository.findUserWith(user.getFirstName(), user.getLastName())).isEmpty()) { 
            logger.error("Unable to create. A User with name {} {} already exist", user.getFirstName(),user.getLastName());
            return new ResponseEntity<String>("User already in the database!",HttpStatus.CONFLICT);
        }
		User newUser = new User(user.getFirstName(), user.getLastName());
		repository.save(newUser);
										//tutaj już newUser ma id z bazy wygenerowane
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
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
 
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        repository.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
