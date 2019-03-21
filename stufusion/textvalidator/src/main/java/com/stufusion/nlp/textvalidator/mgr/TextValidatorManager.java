package com.stufusion.nlp.textvalidator.mgr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.ProcessingExeption;
import com.stufusion.nlp.textvalidator.TextValidationOutput;
import com.stufusion.nlp.textvalidator.validators.TextValidationFactory;
import com.stufusion.nlp.textvalidator.validators.TextValidator;

@Component
public class TextValidatorManager {

	public List<TextValidationOutput> doAllTextValidation(String candidateText)
			throws InitializationExeption, ProcessingExeption {
		List<TextValidationOutput> outputs = new ArrayList<>();
		List<TextValidator> validators = createAndInitializeAllValidators(candidateText);

		for (TextValidator textValidator : validators) {
			outputs.add(textValidator.validateText(candidateText));
		}

		cleanUpValidators(validators);

		return outputs;
	}

	private List<TextValidator> createAndInitializeAllValidators(String candidateText) throws InitializationExeption {
		List<TextValidator> validators = new ArrayList<>();

		TextValidator spamValidator = TextValidationFactory.createTextValidator(TextValidationFactory.SPAM_TYPE,
				candidateText, null);
		spamValidator.initialize();

		TextValidator prafanityValidator = TextValidationFactory
				.createTextValidator(TextValidationFactory.PROFANITY_TYPE, candidateText, null);
		prafanityValidator.initialize();

		validators.add(spamValidator);
		validators.add(prafanityValidator);
		return validators;
	}

	private void cleanUpValidators(List<TextValidator> validators) {
		for (TextValidator textValidator : validators) {
			textValidator.cleanUp();
		}
	}

}
