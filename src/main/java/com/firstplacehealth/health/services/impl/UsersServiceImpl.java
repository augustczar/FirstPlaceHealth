package com.firstplacehealth.health.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firstplacehealth.health.models.UsersModel;
import com.firstplacehealth.health.repositories.UsersRepository;
import com.firstplacehealth.health.services.UsersService;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	UsersRepository usersRepository;

	@Override
	public void save(UsersModel newUser) {
		usersRepository.save(newUser);
	}

	@Override
	public Object findByLogin(String login) {
		return usersRepository.findByLogin(login);
	}
	
}
