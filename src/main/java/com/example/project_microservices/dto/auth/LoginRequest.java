package com.example.project_microservices.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
	 	private String username;
	    private String password;
}
