package com.capstone1.homelisting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capstone1.homelisting.model.Users;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
	Optional<Users> getByEmail(String email);
	@Query("FROM Users WHERE email=?1 AND password=?2")
	 Users login(String email, String password);
	@Query("FROM Users WHERE firstName =?1 OR lastName=?1 OR email=?1")
	List<Users> searchByName(String name);
	

}
