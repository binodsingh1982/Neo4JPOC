package com.stufusion.nlp.textvalidator.profanity;

import com.stufusion.nlp.textvalidator.InitializationExeption;

public interface ProfanityFilter {

	void initialize() throws InitializationExeption;

	void cleanUp();

	ProfanityFilterOutput getBadWords(String message);

	String[] getTokens(String input);

	String getCleanText(String input);
}