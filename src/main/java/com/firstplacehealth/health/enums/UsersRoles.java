package com.firstplacehealth.health.enums;

public enum UsersRoles {

	ADMIN_USER("ADMIN"),
	COMMON_USER("USER");
	
	private String role;
	
	UsersRoles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
