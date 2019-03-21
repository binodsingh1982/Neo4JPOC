package com.stufusion.nlp.textvalidator.validators;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.stufusion.nlp.constant.TextValidatorType;
import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.ProcessingExeption;
import com.stufusion.nlp.textvalidator.TextValidationOutput;
import com.stufusion.nlp.textvalidator.preprocessor.TextPreProcessor;
import com.stufusion.nlp.textvalidator.profanity.ProfanityFilter;

public class ProfanityTextValidator implements TextValidator {

	private List<ProfanityFilter> filters;

	@Override
	public void initialize() throws InitializationExeption {
		checkInitialization();
		for (ProfanityFilter filter : filters) {
			filter.initialize();
		}

	}

	@Override
	public TextValidationOutput validateText(String candidateText) throws ProcessingExeption, InitializationExeption {
		checkInitialization();
		TextValidationOutput textValidationOutput = new TextValidationOutput();
		textValidationOutput.setType(TextValidatorType.PROFANITY);

		try {
			candidateText = TextPreProcessor.processText(candidateText, true, true, false);
		} catch (IOException e) {
			throw new ProcessingExeption(e);
		}

		HashSet<String> badWords = new HashSet<>();

		for (ProfanityFilter filter : filters) {
			badWords.addAll(filter.getBadWords(candidateText).getBadWordList());
		}

		if (badWords.size() > 0) {
			textValidationOutput.setResult(true);
			textValidationOutput.setScore(100);
		} else {
			textValidationOutput.setResult(false);
			textValidationOutput.setScore(0);
		}

		return textValidationOutput;
	}

	public void setFilters(List<ProfanityFilter> filters) {
		this.filters = filters;
	}

	@Override
	public void cleanUp() {
		for (ProfanityFilter filter : filters) {
			filter.cleanUp();
		}
	}

	private void checkInitialization() throws InitializationExeption {
		if (filters != null && filters.size() > 0) {
			return;
		}
		throw new InitializationExeption("No profanity filter set");
	}

}
