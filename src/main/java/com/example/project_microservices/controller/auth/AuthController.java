package com.example.project_microservices.controller.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_microservices.dto.auth.LoginRequest;
import com.example.project_microservices.dto.auth.RegisterRequest;
import com.example.project_microservices.dto.auth.RespAuth;
import com.example.project_microservices.model.auth.User;
import com.example.project_microservices.repository.auth.UserRepository;
import com.example.project_microservices.service.auth.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public RespAuth register(@RequestBody RegisterRequest request) {
        return authService.Register(request);
    }

    @PostMapping("/login")
    public RespAuth login(@RequestBody LoginRequest request) {
        return authService.Login(request);
    }
    
    @GetMapping("/usuarios")
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<User> getUsuarioPorId(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok("Usuario eliminado");
        }).orElse(ResponseEntity.notFound().build());
    }
}
