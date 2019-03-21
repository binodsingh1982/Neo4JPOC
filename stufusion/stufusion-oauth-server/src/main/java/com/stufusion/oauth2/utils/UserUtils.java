package com.stufusion.oauth2.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class UserUtils {

	private static SecureRandom secureRandomGenerator;
	private static final int randomNumberCount = 100000;
	private static final int randomNumberToBeAdded = 899999;

	static {
		try {
			secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getUniqueRandomNumber() {
		return String.valueOf(randomNumberCount + secureRandomGenerator.nextInt(randomNumberToBeAdded));
	}
	
	public static String getUserId(String fName, String lName) {
		String regex = "[^a-zA-Z]+";
		String userId = fName.replaceAll(regex, "").toUpperCase() + "-" + lName.replaceAll(regex, "").toUpperCase()
				+ "-" + getUniqueRandomNumber();
		/*if (userProfileDao.findByUserId(userId) != null) {
			getUserId(fName, lName);
		}*/
		return userId;
	}
}
