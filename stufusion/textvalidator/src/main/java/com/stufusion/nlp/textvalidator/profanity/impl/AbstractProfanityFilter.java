package com.stufusion.nlp.textvalidator.profanity.impl;

import com.stufusion.nlp.textvalidator.profanity.ProfanityFilter;

public abstract class AbstractProfanityFilter implements ProfanityFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.stufusion.nlp.textvalidator.profanity.ProfanityFilter#getTokens(java.
	 * lang.String)
	 */
	@Override
	public String[] getTokens(String input) {
		if (!input.isEmpty()) {
			String regex = "[?,_;\\s]+";
			String[] values = input.replaceAll("^" + regex, "").split(regex);

			return values;
		}

		return null;
	}

	@Override
	public String getCleanText(String input) {
		if (!input.isEmpty()) {
			String regex = "[?,_;\\s]+";
			String cleanText = input.replaceAll(regex, " ");
			//remove non alphabets :- f**k to f  k
//			cleanText = cleanText.replaceAll("[^a-z]", " "); // it has only lower case

			return cleanText.trim();
		}

		return null;
	}

}
