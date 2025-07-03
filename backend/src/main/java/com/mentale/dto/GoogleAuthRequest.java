package com.mentale.dto;

import lombok.Data;

@Data
public class GoogleAuthRequest {
	private String idToken;

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

}
