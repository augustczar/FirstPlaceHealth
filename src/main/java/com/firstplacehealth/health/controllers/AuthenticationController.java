package com.firstplacehealth.health.controllers;

import org.hibernate.dialect.function.array.H2ArrayContainsFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstplacehealth.health.dtos.AuthenticationDto;
import com.firstplacehealth.health.dtos.RegisterDto;
import com.firstplacehealth.health.models.UsersModel;
import com.firstplacehealth.health.services.UsersService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/authuser")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UsersService  usersService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto authenticationDto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto registerDto){
		
		if (usersService.findByLogin(registerDto.login()) != null)
			return ResponseEntity.badRequest().build();
		
		String encryptedPasswword = new  BCryptPasswordEncoder().encode(registerDto.password());
		UsersModel newUser = new UsersModel(registerDto.login(), encryptedPasswword, registerDto.role());
		
		this.usersService.save(newUser);
		
		return ResponseEntity.ok().build();
	}
}
