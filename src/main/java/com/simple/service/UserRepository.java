package com.simple.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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
	
	//@Query(value = "SELECT DISTINCT u.firstname->>'firstName' FROM public.users u WHERE u.firstname->>'firstName'", nativeQuery = true)
	//List<User> findUser(String firstName);
	
	@Query(value = "SELECT * FROM public.users u WHERE u.firstname = ?1", nativeQuery = true)
	List<User> findUser(String firstName);
	
	@Query(value = "SELECT * FROM public.users u WHERE u.firstname = ?1 AND u.lastname = ?2", nativeQuery = true)
	List<User> findUserWith(String firstName, String lastName);
	
	//@Query(value = "SELECT * FROM public.users u WHERE CONCAT_WS('.', u.firstname, u.lastname) LIKE '%?1%'", nativeQuery = true)
	//ArrayList<User> searchUsers(String searchArgument);
	
	@Query(value = "SELECT * FROM public.users u WHERE u.firstname LIKE %?1% OR u.lastname LIKE %?1%", nativeQuery = true)
	ArrayList<User> searchUsers(String searchArgument);
}