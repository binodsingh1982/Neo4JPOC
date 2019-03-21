package com.stufusion.oauth2.enums;

public enum AuthProviderType {

	LINKEDIN("LinkedIn"), GMAIL("Gmail");
	private String name;

	AuthProviderType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
