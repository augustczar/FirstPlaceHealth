package com.firstplacehealth.health.enums;

public enum UsersRoles {

	ADMIN_USER("admin"),
	COMMON_USER("user");
	
	private String role;
	
	UsersRoles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
