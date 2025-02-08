package com.loginex.logx.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginex.logx.domain.user.User;
import com.loginex.logx.dto.LoginRequestDTO;
import com.loginex.logx.dto.LoginResponseDTO;
import com.loginex.logx.dto.RegisterRequestDTO;
import com.loginex.logx.exceptions.ErrorMessage;
import com.loginex.logx.exceptions.UserNotFoundException;
import com.loginex.logx.infra.security.TokenService;
import com.loginex.logx.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserRepository repository;
	private final PasswordEncoder passEncoder;
	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequestDTO body) {
		User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new UserNotFoundException("Email não cadastrado"));
		
		if (passEncoder.matches(body.password(), user.getPassword())) {
			String token = this.tokenService.generateToken(user);
			return ResponseEntity.ok(new LoginResponseDTO(user.getName(), token));		
		}
		return ResponseEntity.badRequest().body(new ErrorMessage("Senha inválida"));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO body) {
		Optional<User> user = this.repository.findByEmail(body.email());
		
		if(user.isEmpty()) {
			User newUser = new User();
			newUser.setPassword(passEncoder.encode(body.password()));
			newUser.setEmail(body.email());
			newUser.setName(body.name());
			this.repository.save(newUser);
			
			String token = this.tokenService.generateToken(newUser);
			return ResponseEntity.ok(new LoginResponseDTO(newUser.getName(), token));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Email já está cadastrado"));
	}
	
}
