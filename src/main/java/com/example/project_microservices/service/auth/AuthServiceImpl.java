package com.example.project_microservices.service.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_microservices.dto.auth.LoginRequest;
import com.example.project_microservices.dto.auth.RegisterRequest;
import com.example.project_microservices.dto.auth.RespAuth;
import com.example.project_microservices.model.auth.User;
import com.example.project_microservices.repository.auth.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService{
	
    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public RespAuth Register(RegisterRequest request) {
		// TODO Auto-generated method stub
		 RespAuth resp = new RespAuth();
		    try {
		        User user = new User();
		        user.setUsername(request.getUsername());
		        user.setPassword(encoder.encode(request.getPassword()));
		        user.setEmail(request.getEmail());

		        User nuevo = userRepository.save(user);
		        resp.objeto = nuevo;
		        resp.codigo = 0;
		        resp.mensaje = "Usuario registrado exitosamente";
		    } catch (Exception ex) {
		        resp.codigo = 1;
		        resp.mensaje = "Error: " + ex.getMessage();
		        System.out.println("Registro recibido: " + request.getUsername() + ", " + request.getEmail() +", " + request.getPassword());
		    }
		    return resp;
	}

	@Override
	public RespAuth Login(LoginRequest request) {
		RespAuth resp = new RespAuth();
	    try {
	        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

	        if (userOpt.isPresent()) {
	            User user = userOpt.get();

	            // Validar contraseña (en caso uses BCrypt, aquí se compara encriptada)
	            if (encoder.matches(request.getPassword(), user.getPassword())) {
	                resp.codigo = 0;
	                resp.mensaje = "Inicio de sesión exitoso";
	                resp.objeto = user;
	            } else {
	                resp.codigo = 1;
	                resp.mensaje = "Contraseña incorrecta";
	            }
	        } else {
	            resp.codigo = 1;
	            resp.mensaje = "Usuario no encontrado";
	        }

	    } catch (Exception e) {
	        resp.codigo = 1;
	        resp.mensaje = "Error: " + e.getMessage();
	    }

	    return resp;
	}
	
}
