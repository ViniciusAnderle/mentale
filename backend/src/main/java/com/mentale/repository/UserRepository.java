package com.mentale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentale.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
