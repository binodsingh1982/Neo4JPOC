package com.stufusion.nlp.constant;

/**
 * @author Sunil
 *
 */
public enum TextValidatorType {

	SPAM("Spam"), PROFANITY("Profanity");

	private String value;

	/**
	 * @param value
	 */
	TextValidatorType(String value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}
}
