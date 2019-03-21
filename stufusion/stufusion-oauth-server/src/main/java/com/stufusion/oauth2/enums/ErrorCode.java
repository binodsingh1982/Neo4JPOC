package com.stufusion.oauth2.enums;

public enum ErrorCode {

	INVALID_PASSWORD(4001), INVALID_EMAIL(4002), USER_ALREADY_EXISTS(4003), INVALID_ROLE(4004), INVALID_USER(4005), ACCESS_TOKEN_ERROR(4006);
	private int errorCode;

	ErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
