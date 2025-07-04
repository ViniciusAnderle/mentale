package com.mentale.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(String email, String role) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + 1000 * 60 * 60); // 1 hora

		Key key = Keys.hmacShaKeyFor(secret.getBytes());

		return Jwts.builder().setSubject(email).claim("role", role) // 🔥 aqui está o segredo!
				.setIssuedAt(now).setExpiration(expiry).signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public boolean validateToken(String token) {
		try {
			Key key = Keys.hmacShaKeyFor(secret.getBytes());
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	public String extractRole(String token) {
		Key key = Keys.hmacShaKeyFor(secret.getBytes());
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return claims.get("role", String.class);
	}

	public String extractEmail(String token) {
		Key key = Keys.hmacShaKeyFor(secret.getBytes());
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}
