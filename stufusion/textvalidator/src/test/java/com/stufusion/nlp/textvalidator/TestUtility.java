package com.stufusion.nlp.textvalidator;

import com.stufusion.nlp.textvalidator.validators.Utility;

public class TestUtility {

	private static String Model_Dir = "models";
	private static String Test_Dir = "test_files";

	public static String getModelPath(String modelName) {
		return Utility.getCurrentDir() + Model_Dir + Utility.SEPERATOR + modelName;
	}

	public static String getTestFilePath(String fileName) {
		return Utility.getCurrentDir() + Test_Dir + Utility.SEPERATOR + fileName;
	}

}
