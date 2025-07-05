package com.mentale.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
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

	/**
	 * Login via e-mail e senha (qualquer tipo de usuário)
	 */
	public String login(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new InvalidCredentialsException("Email ou senha inválidos"));

		if (user.getPassword() == null || !passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidCredentialsException("Email ou senha inválidos");
		}

		// Agora usamos o generateToken com role
		return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
	}

	/**
	 * Login via Google OAuth2
	 */
	public GoogleIdToken.Payload verifyGoogleToken(String idTokenString) throws Exception {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(),
				GsonFactory.getDefaultInstance())
				.setAudience(Collections
						.singletonList("1059352211050-2qvdmvg53lid9jvmdj11a1mbke9d15iq.apps.googleusercontent.com"))
				.build();

		GoogleIdToken idToken = verifier.verify(idTokenString);
		if (idToken != null) {
			return idToken.getPayload();
		} else {
			throw new Exception("Token Google inválido");
		}
	}

	/**
	 * Utilitário de geração direta de token (caso queira reaproveitar)
	 */
	public String generateToken(String email, String role) {
		return jwtUtil.generateToken(email, role);
	}
}
