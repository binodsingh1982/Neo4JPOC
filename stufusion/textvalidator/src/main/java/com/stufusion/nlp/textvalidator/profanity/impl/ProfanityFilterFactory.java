package com.stufusion.nlp.textvalidator.profanity.impl;

import com.stufusion.nlp.textvalidator.profanity.ProfanityFilter;

public class ProfanityFilterFactory {
	public static final String REGEX_TYPE = "REGEX_TYPE";
	public static final String DICTIONARY_TYPE = "DICTIONARY_TYPE";

	public static ProfanityFilter createProfanityFilter(String type) {
		ProfanityFilter filter = null;

		if (type.equals(REGEX_TYPE)) {
			filter = new AbusiveWordPatternFilter();
			
		} //else if (type.equals(DICTIONARY_TYPE)) {
		else {
			filter = new DictionaryProfanityFilter();

		}

		return filter;
	}

}
