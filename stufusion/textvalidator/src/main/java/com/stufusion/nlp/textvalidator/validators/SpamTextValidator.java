package com.stufusion.nlp.textvalidator.validators;

import java.util.List;

import com.stufusion.nlp.constant.TextValidatorType;
import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.ProcessingExeption;
import com.stufusion.nlp.textvalidator.TextValidationOutput;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictionOutput;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictor;

public class SpamTextValidator implements TextValidator {

	private List<SpamPredictor> predictors;

	@Override
	public void initialize() throws InitializationExeption {
		checkInitialization();
		for (SpamPredictor predictor : predictors) {
			predictor.initialize();
		}

	}

	@Override
	public TextValidationOutput validateText(String candidateText) throws InitializationExeption, ProcessingExeption {
		checkInitialization();
		TextValidationOutput textValidationOutput = new TextValidationOutput();
		textValidationOutput.setType(TextValidatorType.SPAM);

		double totalSpamProbability = 0.0;

		for (SpamPredictor predictor : predictors) {
			SpamPredictionOutput output = predictor.getSpamPredictionOutput(candidateText);
			totalSpamProbability += output.isSpam() ? output.getProbability() : (1 - output.getProbability());
		}

		double probability = totalSpamProbability / predictors.size();

		if (probability > 0.5) {
			textValidationOutput.setResult(true);
			textValidationOutput.setScore(probability);
		} else {
			textValidationOutput.setResult(false);
			textValidationOutput.setScore(1 - probability);
		}

		return textValidationOutput;
	}

	@Override
	public void cleanUp() {
		if (predictors != null) {
			for (SpamPredictor predictor : predictors) {
				predictor.cleanUp();
			}
		}

		predictors.clear();
	}

	public void setPredictors(List<SpamPredictor> predictors) {
		this.predictors = predictors;
	}

	private void checkInitialization() throws InitializationExeption {
		if (predictors != null && predictors.size() > 0) {
			return;
		}
		throw new InitializationExeption("No spam predictor set");
	}

}
