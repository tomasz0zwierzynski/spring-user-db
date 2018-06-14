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
	
	//	np.:
	// 	@Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
	//  public List<Person> find(@Param("lastName") String lastName);
	
	//@Query("SELECT * FROM users WHERE LOWER(firstname) = LOWER(:firstName) AND LOWER(lastname) = LOWER(:lastName)")
	//public List<User> findUser(@Param("firstName") String firstName, @Param("lastName") String lastName);

	//@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
	//User findByEmailAddress(String emailAddress);

}