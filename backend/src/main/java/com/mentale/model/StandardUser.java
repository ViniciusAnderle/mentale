package com.mentale.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StandardUser {

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "psychologist_id")
	private Psychologist psychologist;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Psychologist getPsychologist() {
		return psychologist;
	}

	public void setPsychologist(Psychologist psychologist) {
		this.psychologist = psychologist;
	}
	
	
}
