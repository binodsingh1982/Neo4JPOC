package com.stufusion.nlp.textvalidator.profanity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.profanity.ProfanityFilter;
import com.stufusion.nlp.textvalidator.profanity.ProfanityFilterOutput;

public class AbusiveWordPatternFilter extends AbstractProfanityFilter implements ProfanityFilter {

	public static String[] swearWords = { "f.*?u.*?k", "s.*?h.*?t", "bi.*?h", "bas.*?d", "m.*?f.*?", "c.*?nt", "as.*?s",
			"s.*?ck", "w.*?nk", "co.*?on", "wo.*?g", "ni.*?g.*?r", "c.*?c.*?k", "penis", "vagina", "c.*?um",
			"p.*?i.*?s", "p.*?orn", "ar.*?se", "nexon", "ho.*?r.*?ny", "dil.*?do", "doggystyle", "cl.*?it", "fann.*?y",
			"ho.*?re.*?", "kn.*?ob", "mastur.*?", "hitler", "n.*?uts", "sob.*?", "shag.*?", "sl.*?ut.*?", "testi.*?",
			"t.*?wa.*?t", "viagr.*?a", "wil.*?ly", "wil.*?lie", "jism", "dog.*?gy", "donkeyri.*?b", "breas.*?t",
			"bl.*?wjo.*?b", "b.*?b", "beastiality", "an.*?al", "cawk", "pus.*?s.*?", "rim.*?m", "ejaculate",
			"ejakulate", "er.*?ct", "horni", "horna", "se.*?x", "se.*?ck", "ga.*?y", "fk", "we*?nis" };

	public ProfanityFilterOutput getBadWords(String candidateText) {
		List<String> badWordList = new ArrayList<>();
		String[] allTokens = this.getTokens(candidateText);

		for (String token : allTokens) {
			for (String swearWord : swearWords) {
				Pattern pat = Pattern.compile(swearWord);
				Matcher mat = pat.matcher(token);
				while (mat.find()) {
					if (token.length() == (mat.end() - mat.start())) {
						badWordList.add(token);
					}
				}
			}
		}
		ProfanityFilterOutput output = new ProfanityFilterOutput();
		output.setBadWordList(badWordList);
		return output;
	}

	@Override
	public void initialize() throws InitializationExeption {

	}

	@Override
	public void cleanUp() {

	}

}
