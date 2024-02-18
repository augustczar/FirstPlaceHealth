package com.firstplacehealth.health.dtos;

import com.firstplacehealth.health.enums.UsersRoles;

public record RegisterDto(String login, String password, UsersRoles role) {

}
