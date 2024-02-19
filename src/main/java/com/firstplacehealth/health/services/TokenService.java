package com.firstplacehealth.health.services;

import com.firstplacehealth.health.models.UsersModel;

public interface TokenService {

	String generationToken(UsersModel usersModel);
	
	 String validateToken(String token);
}
