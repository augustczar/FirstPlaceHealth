package com.firstplacehealth.health.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.firstplacehealth.health.models.UsersModel;


public interface UsersRepository  extends JpaRepository<UsersModel, UUID>{
 
	UserDetails findByLogin(String login);
}
