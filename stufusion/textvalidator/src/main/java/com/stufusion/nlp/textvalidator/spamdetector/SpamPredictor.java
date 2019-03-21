package com.stufusion.nlp.textvalidator.spamdetector;

import com.stufusion.nlp.textvalidator.InitializationExeption;

public interface SpamPredictor{

	public void setModelPath(String modelPath);
	public void initialize() throws InitializationExeption;
	public SpamPredictionOutput getSpamPredictionOutput(String candidateText) throws InitializationExeption;
	public void cleanUp();
}
