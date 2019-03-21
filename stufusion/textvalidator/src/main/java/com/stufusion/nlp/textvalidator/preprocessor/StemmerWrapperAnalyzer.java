package com.stufusion.nlp.textvalidator.preprocessor;

import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.PorterStemFilter;

public class StemmerWrapperAnalyzer extends AnalyzerWrapper {

	private Analyzer baseAnalyzer;

	public StemmerWrapperAnalyzer(Analyzer baseAnalyzer) {
		super(baseAnalyzer.getReuseStrategy());
		this.baseAnalyzer = baseAnalyzer;
	}

	@Override
	public void close() {
		baseAnalyzer.close();
		super.close();
	}

	@Override
	protected Analyzer getWrappedAnalyzer(String fieldName) {
		return baseAnalyzer;
	}

	@Override
	protected TokenStreamComponents wrapComponents(String fieldName, TokenStreamComponents components) {
		TokenStream ts = components.getTokenStream();
		Set<String> filteredTypes = new HashSet<>();
		filteredTypes.add("<NUM>");
		// TypeTokenFilter numberFilter = new TypeTokenFilter(ts,
		// filteredTypes);

		PorterStemFilter porterStem = new PorterStemFilter(ts);
		return new TokenStreamComponents(components.getTokenizer(), porterStem);
	}
}