package com.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// NOTE: CORS is now configured centrally in com.inventory.config.SecurityConfig
// (needed there so it applies before the Spring Security filter chain runs).
@SpringBootApplication
public class InventoryMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryMangementApplication.class, args);
	}

}
