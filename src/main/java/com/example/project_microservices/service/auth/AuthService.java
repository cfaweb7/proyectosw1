package com.example.project_microservices.service.auth;

import com.example.project_microservices.dto.auth.LoginRequest;
import com.example.project_microservices.dto.auth.RegisterRequest;
import com.example.project_microservices.dto.auth.RespAuth;
import com.example.project_microservices.model.auth.User;

public interface AuthService {
	
	public RespAuth Register(RegisterRequest request);
	
	public RespAuth Login(LoginRequest request);
}
