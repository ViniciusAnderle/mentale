package com.mentale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mentale.exceptions.UserAlreadyExistsException;
import com.mentale.model.User;
import com.mentale.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User register(String email, String password) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new UserAlreadyExistsException("Usuário já existe!");
		}
		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User registerOAuthUser(String email, String name) {
		User user = new User();
		user.setEmail(email);
		user.setUsername(name);
		user.setPassword(null); // sem senha
		return userRepository.save(user);
	}

}