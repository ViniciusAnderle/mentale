package com.mentale.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.mentale.dto.GoogleAuthRequest;
import com.mentale.exceptions.InvalidCredentialsException;
import com.mentale.exceptions.UserAlreadyExistsException;
import com.mentale.model.User;
import com.mentale.service.AuthService;
import com.mentale.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
		try {
			Thread.sleep(500);
			User user = userService.register(request.getEmail(), request.getPassword());

			// Gera token com role
			String token = authService.generateToken(user.getEmail(), user.getRole().name());

			ResponseCookie cookie = ResponseCookie.from("jwt", token).httpOnly(true).secure(false).path("/")
					.maxAge(60 * 60) // 1 hora
					.sameSite("Strict").build();

			return ResponseEntity.ok().header("Set-Cookie", cookie.toString())
					.body(Map.of("message", "Usuário cadastrado com sucesso", "email", user.getEmail()));

		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			return ResponseEntity.internalServerError().body(Map.of("error", "Erro interno inesperado."));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
		try {
			Thread.sleep(500); // Proteção simples contra brute-force
			String token = authService.login(request.getEmail(), request.getPassword());

			ResponseCookie cookie = ResponseCookie.from("jwt", token).httpOnly(true).secure(false).path("/")
					.maxAge(60 * 60).sameSite("Strict").build();

			return ResponseEntity.ok().header("Set-Cookie", cookie.toString())
					.body(Map.of("message", "Login realizado com sucesso"));

		} catch (InvalidCredentialsException e) {
			return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			return ResponseEntity.internalServerError().body(Map.of("error", "Erro interno inesperado"));
		}
	}

	@PostMapping("/oauth/google")
	public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleAuthRequest request) {
		String idToken = request.getIdToken();

		try {
			GoogleIdToken.Payload payload = authService.verifyGoogleToken(idToken);
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			// Cadastra ou busca
			User user = userService.findByEmail(email).orElseGet(() -> userService.registerOAuthUser(email, name));

			// Gera token com role do usuário
			String jwt = authService.generateToken(user.getEmail(), user.getRole().name());

			ResponseCookie cookie = ResponseCookie.from("jwt", jwt).httpOnly(true).secure(false).path("/")
					.maxAge(60 * 60).sameSite("Strict").build();

			return ResponseEntity.ok().header("Set-Cookie", cookie.toString())
					.body(Map.of("message", "Login via Google realizado com sucesso"));

		} catch (Exception e) {
			return ResponseEntity.status(401).body(Map.of("error", "Token Google inválido"));
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("jwt", "").httpOnly(true).secure(false).path("/").maxAge(0)
				.sameSite("Strict").build();

		return ResponseEntity.ok().header("Set-Cookie", cookie.toString())
				.body(Map.of("message", "Logout realizado com sucesso"));
	}

	@GetMapping("/check")
	public ResponseEntity<?> checkSession(HttpServletRequest request) {
		return ResponseEntity.ok(Map.of("status", "ok"));
	}

	@Data
	public static class AuthRequest {

		@NotBlank(message = "Email é obrigatório")
		private String email;

		@NotBlank(message = "Senha é obrigatória")
		private String password;

		public String getEmail() {
			// TODO Auto-generated method stub
			return email;
		}

		public String getPassword() {
			// TODO Auto-generated method stub
			return password;
		}
	}


}
