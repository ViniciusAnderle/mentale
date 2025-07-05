package com.mentale.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mentale.exceptions.UserAlreadyExistsException;
import com.mentale.model.StandardUser;
import com.mentale.model.User;
import com.mentale.model.UserRole;
import com.mentale.repository.StandardUserRepository;
import com.mentale.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StandardUserRepository standardUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User register(String email, String password) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new UserAlreadyExistsException("Usuário já existe!");
		}

		// Cria usuário base com role STANDARD
		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(UserRole.STANDARD);

		User savedUser = userRepository.save(user);

		// Cria StandardUser e vincula ao User (sem psicólogo ainda)
		StandardUser standardUser = new StandardUser();
		standardUser.setUser(savedUser);
		standardUser.setPsychologist(null); // ou atribuir depois

		standardUserRepository.save(standardUser);

		return savedUser;
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User registerOAuthUser(String email, String name) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(null); // sem senha
		user.setRole(UserRole.STANDARD);

		User savedUser = userRepository.save(user);

		StandardUser su = new StandardUser();
		su.setUser(savedUser);
		standardUserRepository.save(su);

		return savedUser;
	}
}
