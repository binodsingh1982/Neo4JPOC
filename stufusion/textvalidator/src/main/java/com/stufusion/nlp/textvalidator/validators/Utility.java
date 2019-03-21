package com.stufusion.nlp.textvalidator.validators;

import java.io.File;

public class Utility {
	public static final String SEPERATOR = "/";

	public static String getCurrentDir() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 1);

		return path;
	}
}
