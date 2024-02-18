package com.firstplacehealth.health.services;

import com.firstplacehealth.health.models.UsersModel;

public interface UsersService {

	void save(UsersModel newUser);

	Object findByLogin(String login);

}
