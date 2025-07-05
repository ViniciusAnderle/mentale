package com.mentale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentale.model.StandardUser;

public interface StandardUserRepository extends JpaRepository<StandardUser, Long> {
}
