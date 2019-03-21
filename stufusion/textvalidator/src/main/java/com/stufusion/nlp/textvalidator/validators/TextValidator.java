package com.stufusion.nlp.textvalidator.validators;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.ProcessingExeption;
import com.stufusion.nlp.textvalidator.TextValidationOutput;

public interface TextValidator {
	public void initialize() throws InitializationExeption;

	public TextValidationOutput validateText(String candidateText) throws ProcessingExeption, InitializationExeption;

	public void cleanUp();
}
