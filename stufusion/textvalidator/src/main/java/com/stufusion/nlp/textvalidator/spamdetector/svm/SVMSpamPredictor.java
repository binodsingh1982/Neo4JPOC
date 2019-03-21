package com.stufusion.nlp.textvalidator.spamdetector.svm;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.ml.CleartkAnnotator;
import org.cleartk.ml.jar.GenericJarClassifierFactory;
import org.cleartk.opennlp.tools.SentenceAnnotator;
import org.cleartk.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictionOutput;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictor;

public class SVMSpamPredictor implements SpamPredictor {
	private AnalysisEngine engine = null;

	private static String SPAM_CATEGORY = "spam";
	private String modelPath;

	public void initialize() throws InitializationExeption {

		// NLP pre-processing components
		try {
			AggregateBuilder builder = new AggregateBuilder();

			builder.add(SentenceAnnotator.getDescription());
			builder.add(TokenAnnotator.getDescription()); // Tokenization
			builder.add(DefaultSnowballStemmer.getDescription("English")); // Stemming

			// Simple document classification annotator
			builder.add(AnalysisEngineFactory.createEngineDescription(DocClassificationAnnotator.class,
					CleartkAnnotator.PARAM_IS_TRAINING, false, GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH,
					modelPath));

			AnalysisEngineDescription aggregate = builder.createAggregateDescription();
			engine = AnalysisEngineFactory.createEngine(aggregate);

		} catch (ResourceInitializationException e) {
			throw new InitializationExeption(e);
		}

	}

	@Override
	public SpamPredictionOutput getSpamPredictionOutput(String candidateText) throws InitializationExeption {
		if (engine == null)
			throw new InitializationExeption("Model not initialized");

		JCas jCas;
		try {
			jCas = engine.newJCas();
			jCas.setDocumentText(candidateText);
			engine.process(jCas);
		} catch (ResourceInitializationException | AnalysisEngineProcessException e) {
			throw new InitializationExeption(e);
		}

		UsenetDocument document = JCasUtil.select(jCas, UsenetDocument.class).iterator().next();
		SpamPredictionOutput spamPredictionOutput = new SpamPredictionOutput();
		spamPredictionOutput.setProbability(1.0);

		if (document.getCategory().equals(SPAM_CATEGORY)) {
			spamPredictionOutput.setSpam(true);
		} else {
			spamPredictionOutput.setSpam(false);
		}

		return spamPredictionOutput;
	}

	@Override
	public void cleanUp() {
		engine.destroy();
	}

	@Override
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}
