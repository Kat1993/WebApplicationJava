package com.capstone1.homelisting.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone1.homelisting.model.Users;
import com.capstone1.homelisting.repository.UserRepository;
@Service
@Transactional
public class UserServiceimpl implements UserService {


	private UserRepository userRepository;
	
	@Autowired 
	public UserServiceimpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public void save(Users user) {
		userRepository.save(user);
		
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		
		return userRepository.findAll();
	}

	@Override
	public void Update(Users user) {
	
		userRepository.findById(user.getId()).ifPresent(a->{
			
			a.setAddress(user.getAddress());
			a.setCity(user.getCity());
			a.setFirstName(user.getFirstName());
			a.setLastName(user.getLastName());
			a.setPhone(user.getPhone());
			a.setState(user.getState());
			
			
			
		});
		
		
	}

	

	@Override
	public Optional<Users> findByEmail(String email) {
		
		return userRepository.getByEmail(email);
	}

	@Override
	public Optional<Users> findById(long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public Users login(String email, String password) {
		// TODO Auto-generated method stub
		return userRepository.login(email, password);
	}

	@Override
	public void addcomment(Long id, String comment) {
	userRepository.findById(id).ifPresent(a->{
			
			a.setComment(comment);
		});
		
	}

	@Override
	public List<Users> searchByname(String name) {
		// TODO Auto-generated method stub
	return userRepository.searchByName(name);
		
	}

	

}
