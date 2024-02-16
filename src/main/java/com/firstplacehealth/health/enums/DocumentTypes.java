package com.firstplacehealth.health.enums;

public enum DocumentTypes {

	CPF(0),
	RG(1),
	DRIVING_LICENSE(2);
	
	private int code;
	
	private DocumentTypes(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static DocumentTypes valueOf(int code) {
		for(DocumentTypes value : DocumentTypes.values()) {
			if(code == value.getCode()) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid DocumentTypes code");
	}
}
