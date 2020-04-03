package com.capstone1.homelisting.service;

import java.util.List;
import java.util.Optional;

import com.capstone1.homelisting.model.Users;

public interface UserService {

	
	
	void save(Users user);
	void delete(Long id);
	List<Users> findAll();
	void Update(Users user);
	void addcomment(Long id, String comment);
	List<Users> searchByname(String name);
	Optional<Users> findByEmail(String email) ;
	Optional<Users> findById( long id);
	Users login(String email, String password);
	
	
}
