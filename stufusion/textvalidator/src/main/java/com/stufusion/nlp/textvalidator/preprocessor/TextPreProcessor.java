package com.stufusion.nlp.textvalidator.preprocessor;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.cleartk.token.tokenizer.PennTreebankTokenizer;
import org.tartarus.snowball.ext.PorterStemmer;

public class TextPreProcessor {
	PennTreebankTokenizer tokenizer = new PennTreebankTokenizer();
	StringBuffer finalText = new StringBuffer("");
	PorterStemmer stemmer = new PorterStemmer();

	public static String processText(String text, boolean useLowerCase, boolean stripAccents, boolean stemmingAndStopWordRemoval) throws IOException {

		if (useLowerCase)
		{
			text = text.toLowerCase();
		}
		
		if (stripAccents)
		{
			text = StringUtils.stripAccents(text);
		}

		if (stemmingAndStopWordRemoval)
		{
		text = applyStemmingAndStopWordRemoval(text);
		}

		return text;
	}

	public static String applyStemmingAndStopWordRemoval(String inputText) throws IOException {

		StemmerWrapperAnalyzer analyzer = new StemmerWrapperAnalyzer(new StandardAnalyzer());
		TokenStream ts = analyzer.tokenStream("fieldName", new StringReader(inputText));
		ts.reset();
		
		StringBuffer output = new StringBuffer();

		while (ts.incrementToken()) {
			CharTermAttribute ca = ts.getAttribute(CharTermAttribute.class);

			output.append(ca.toString());
			output.append(" ");
		}
		analyzer.close();
		
		return output.toString();
	}
}
