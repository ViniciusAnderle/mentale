package com.mentale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mentale.exceptions.InvalidCredentialsException;
import com.mentale.model.User;
import com.mentale.repository.UserRepository;
import com.mentale.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new InvalidCredentialsException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Usuário ou senha inválidos");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

}
