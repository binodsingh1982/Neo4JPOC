package com.stufusion.nlp.textvalidator.spamdetector.svm;

import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.ml.CleartkAnnotator;
import org.cleartk.ml.Feature;
import org.cleartk.ml.feature.extractor.CleartkExtractor;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Count;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Covered;
import org.cleartk.ml.feature.extractor.CoveredTextExtractor;
import org.cleartk.token.type.Token;


public class DocClassificationAnnotator extends CleartkAnnotator<String> {

	private CleartkExtractor<DocumentAnnotation, Token> extractor;

	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);

		// Create an extractor that gives word counts for a document
		this.extractor = new CleartkExtractor<DocumentAnnotation, Token>(Token.class, new CoveredTextExtractor<Token>(),
				new Count(new Covered()));

	}

	public void process(JCas jCas) throws AnalysisEngineProcessException {
		// use the extractor to create features for the document
		DocumentAnnotation doc = (DocumentAnnotation) jCas.getDocumentAnnotationFs();
		List<Feature> features = this.extractor.extract(jCas, doc);

		String category = this.classifier.classify(features);
		UsenetDocument document = new UsenetDocument(jCas, 0, jCas.getDocumentText().length());
		document.setCategory(category);
		document.addToIndexes();
	}
}
