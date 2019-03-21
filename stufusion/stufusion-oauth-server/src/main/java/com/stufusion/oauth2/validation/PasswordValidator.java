package com.stufusion.oauth2.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	private static Matcher matcher;
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[@#$%]).{8,20})";
	private static Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	public static boolean validate(final String password) {
		matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
