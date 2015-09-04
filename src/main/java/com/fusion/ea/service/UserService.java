package com.fusion.ea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusion.ea.entity.User;
import com.fusion.ea.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findByUserName(String name) throws Exception {
		return userRepository.findByUserName(name);
	}

	public void save(User user) throws Exception {
		userRepository.save(user);
	}

}
