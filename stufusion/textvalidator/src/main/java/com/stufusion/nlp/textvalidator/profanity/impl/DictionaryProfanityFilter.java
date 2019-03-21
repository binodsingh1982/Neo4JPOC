package com.stufusion.nlp.textvalidator.profanity.impl;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.profanity.ProfanityFilter;
import com.stufusion.nlp.textvalidator.profanity.ProfanityFilterOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DictionaryProfanityFilter extends AbstractProfanityFilter implements ProfanityFilter {
	Set<String> badWords = new HashSet<>();

	@Override
	public void initialize() throws InitializationExeption {
		// load dictionary
		loadBadWordsDictionary("com/stufusion/nlp/profanity/English_obscene_bad_words_en_US.txt");
		loadBadWordsDictionary("com/stufusion/nlp/profanity/hindi_obscene_bad_words.txt");
	}

	@Override
	public void cleanUp() {
		badWords.clear();
	}

	@Override
	public ProfanityFilterOutput getBadWords(String message) {
		List<String> badWordList = new ArrayList<>();

		// get clean text message
		// TODO : we don't need stemming and lemmitization is some cases
		String msg = getCleanText(message);

		if (msg == null || msg.length() == 0)
			return null;

		for(String badWord : badWords)
		{
			int countMatches = getMatchCunt(msg, badWord);

			if (countMatches > 0){
				// if found, remove all occurrence of that bad word - for multi word scenario
				// maybe 3 asshole word - at start, mid and end
				// TODO: which is faster and better ?
				//				msg = msg.replaceAll(badWord, "").replaceAll("\\s+", " ");
//				msg = msg.replaceAll(badWord, "^");
				// replace(/  +/g, ' ') is faster than others

				while (countMatches > 0) {
					badWordList.add(badWord);
					countMatches--;
				}
			}

		}

		ProfanityFilterOutput output = new ProfanityFilterOutput();
		output.setBadWordList(badWordList);
		return output;
	}

	// ----------- using string builder   -------------------
/*	@Override
	public ProfanityFilterOutput getBadWords(String message) {
		List<String> badWordList = new ArrayList<>();

		// get clean text message
		// TODO : we don't need stemming and lemmitization is some cases
		StringBuilder msg = new StringBuilder(getCleanText(message));

		if (msg == null || msg.length() == 0)
			return null;

		for(String badWord : badWords)
		{
			int startIndex = msg.indexOf(badWord);
			if (startIndex > -1){
				// if found, remove all occurrence of that bad word - for multi word scenario
				// TODO : right only removing first occurrence of that bad word
				msg.delete(startIndex, startIndex + badWord.length()-1);
				badWordList.add(badWord);
			}

		}

		ProfanityFilterOutput output = new ProfanityFilterOutput();
		output.setBadWordList(badWordList);
		return output;
	}*/

	public static int getMatchCunt(String source, String subItem){
		String pattern = "\\b" + subItem + "\\b"; // to match word boundary
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);

		int count = 0;
		while (m.find())
		{
			count++;
		}

		m.replaceAll("^^");

		return count;
	}

/*	@Override
	public ProfanityFilterOutput getBadWords(String message) {
		List<String> badWordList = new ArrayList<>();

		// tokenize
		String[] tokens = getTokens(message);

		if (tokens == null || tokens.length == 0)
			return null;

		for (int i = 0; i < tokens.length; i++) {
			String word = tokens[i];
			if (checkBadWord(word)) {
				badWordList.add(word);
			}

		}

		ProfanityFilterOutput output = new ProfanityFilterOutput();
		output.setBadWordList(badWordList);
		return output;
	}

	private boolean checkBadWord(String word) {
		if (word.isEmpty())
			return false;

		return badWords.contains(word);
	}*/

	/**
	 * Setup a profanity filter
	 *
	 * @param filePath path of file
	 * @throws InitializationExeption
	 */
	private void loadBadWordsDictionary(String filePath) throws InitializationExeption {
		String line;
		BufferedReader in = null;
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		try {
			in = new BufferedReader(new InputStreamReader(stream));
			while ((line = in.readLine()) != null) {
				// for each bad word
				badWords.add(line);
			}

		} catch (IOException e) { // readLine
			throw new InitializationExeption(e);
		} finally {
			try {
				if (in !=null) in.close();
			} catch (IOException e) {

			}
		}
	}
}
