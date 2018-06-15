package com.simple.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.simple.model.User;

public interface UserRepository extends CrudRepository<User, Long>,UserRepositoryCustom{
	//List<User> findByLastName(String lastName);
	//nie bardzo rozumiem co tutaj mam zdeklarowac, skoro wszystko jest w -||- Custom i Impl
	//w dokuemntacji jest custop queries

	//@Query("SELECT * FROM users WHERE LOWER(firstname) = LOWER(:firstName) AND LOWER(lastname) = LOWER(:lastName)")
	//public List<User> findUserWith(@Param("firstName") String firstName, @Param("lastName") String lastName);

	//@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
	//User findByEmailAddress(String emailAddress);

	//@Query("SELECT id, firstname, lastname FROM users")
	//public User[] findAllusers();
	
	//@Query(value = "SELECT u.id, u.firstname, u.lastname FROM users u where u.firstname = :firstName and u.lastname = :lastName")
	//public List<User> findUserWith(@Param("firstName") String firstName, @Param("lastName") String lastName); 
	
}