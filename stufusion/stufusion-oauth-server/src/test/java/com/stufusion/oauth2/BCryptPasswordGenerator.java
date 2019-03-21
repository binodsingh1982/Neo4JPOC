package com.stufusion.oauth2;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordGenerator {
	
	@Test
	public void encryptPassword(){
		System.out.println(new BCryptPasswordEncoder().encode("secret"));
	}

}
