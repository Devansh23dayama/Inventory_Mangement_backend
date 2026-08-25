package com.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.inventory.model.User;
import com.inventory.repository.UserRepository;

/**
 * Seeds a demo login so the app is usable immediately without signing up first.
 *   username: admin
 *   password: admin123
 */
@Component
public class DataSeeder implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		if (!userRepository.existsByUsername("admin")) {
			User demo = new User();
			demo.setUsername("admin");
			demo.setPassword(passwordEncoder.encode("admin123"));
			demo.setRole("USER");
			userRepository.save(demo);
			System.out.println("=================================================");
			System.out.println(" Demo login seeded -> username: admin | password: admin123");
			System.out.println("=================================================");
		}
	}
}
