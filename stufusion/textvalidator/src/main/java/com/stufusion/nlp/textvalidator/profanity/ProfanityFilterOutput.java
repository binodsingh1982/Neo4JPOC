package com.stufusion.nlp.textvalidator.profanity;

import java.util.List;

public class ProfanityFilterOutput {
	private List<String> badWordList;

	public List<String> getBadWordList() {
		return badWordList;
	}
	public void setBadWordList(List<String> badWordList) {
		this.badWordList = badWordList;
	}
}
