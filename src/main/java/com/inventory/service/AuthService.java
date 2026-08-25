package com.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventory.dto.AuthResponse;
import com.inventory.dto.LoginRequest;
import com.inventory.dto.SignupRequest;
import com.inventory.model.User;
import com.inventory.repository.UserRepository;
import com.inventory.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	public AuthResponse signup(SignupRequest request) {
		if (request.getUsername() == null || request.getUsername().isBlank()
				|| request.getPassword() == null || request.getPassword().isBlank()) {
			throw new RuntimeException("Username and password are required");
		}

		if (userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username is already taken");
		}

		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("USER");
		userRepository.save(user);

		String token = jwtUtil.generateToken(user.getUsername());
		return new AuthResponse(token, user.getUsername(), "Signup successful");
	}

	public AuthResponse login(LoginRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}

		String token = jwtUtil.generateToken(user.getUsername());
		return new AuthResponse(token, user.getUsername(), "Login successful");
	}
}
