package com.mentale.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Psychologist {

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private User user;

	@OneToMany(mappedBy = "psychologist")
	private List<StandardUser> patients;

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

	public List<StandardUser> getPatients() {
		return patients;
	}

	public void setPatients(List<StandardUser> patients) {
		this.patients = patients;
	}

}
