package com.mentale.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = extractTokenFromCookie(request);

		if (token != null && jwtUtil.validateToken(token)) {
			String email = jwtUtil.extractEmail(token);
			String role = jwtUtil.extractRole(token); // STANDARD, PSYCHOLOGIST, MASTER

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null,
					List.of(authority));

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/auth/login") || path.startsWith("/auth/register") || path.startsWith("/auth/logout")
				|| path.startsWith("/auth/oauth/google") || path.startsWith("/auth/check");
	}

	private String extractTokenFromCookie(HttpServletRequest request) {
		if (request.getCookies() == null)
			return null;

		return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("jwt")).map(Cookie::getValue)
				.findFirst().orElse(null);
	}
}
