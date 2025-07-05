package com.mentale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentale.model.Psychologist;

public interface PsychologistRepository extends JpaRepository<Psychologist, Long> {
}
