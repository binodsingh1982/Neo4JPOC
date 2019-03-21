package com.stufusion.nlp.textvalidator.spamdetector.maxent;

import java.io.IOException;
import java.io.InputStream;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.preprocessor.TextPreProcessor;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictionOutput;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictor;

import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.FeatureGenerator;

public class MaxEntSpamPredictor implements SpamPredictor {
	private DocumentCategorizerME categorizer = null;
	private static String SPAM_CATEGORY = "spam";
	private String modelPath;

	public void initialize() throws InitializationExeption {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(modelPath);
		if (stream == null) {
			throw new InitializationExeption("Model not found :" + modelPath);
		}
		try {
			DoccatModel model = new DoccatModel(stream);
			FeatureGenerator bowfeatureGenerator = new BagOfWordsFeatureGenerator();
			FeatureGenerator nGramFeatureGenerator = new N2GramFeatureGenerator();

			categorizer = new DocumentCategorizerME(model, bowfeatureGenerator, nGramFeatureGenerator);
		} catch (IOException e) {
			throw new InitializationExeption(e);
		}
	}

	@Override
	public SpamPredictionOutput getSpamPredictionOutput(String candidateText) throws InitializationExeption {
		if (categorizer == null)
			throw new InitializationExeption("Model not initialized");

		try {
			candidateText = TextPreProcessor.processText(candidateText, true, true, true);
		} catch (IOException e) {
			throw new InitializationExeption(e);
		}

		double[] outcomes = categorizer.categorize(candidateText);
		SpamPredictionOutput spamPredictionOutput = new SpamPredictionOutput();

		String tt = categorizer.getBestCategory(outcomes);

		// There are only 2 categories, hence 0 and 1 indexes.
		int bestCategoryIndex = outcomes[0] > 0.5 ? 0 : 1;
		spamPredictionOutput.setProbability(outcomes[bestCategoryIndex]);
		spamPredictionOutput.setSpam(false);

		if (categorizer.getCategory(bestCategoryIndex).equals(SPAM_CATEGORY)) {
			if (spamPredictionOutput.getProbability() > 0.55) {
				spamPredictionOutput.setSpam(true);
			}
			else
			{
				spamPredictionOutput.setSpam(false);
				// Set a minimum probability ( >0.5) for ham , in case spam score is relatively less
				spamPredictionOutput.setProbability(0.51);
			}
		} else {
			spamPredictionOutput.setSpam(false);
		}

		return spamPredictionOutput;
	}

	@Override
	public void cleanUp() {

	}

	@Override
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}
