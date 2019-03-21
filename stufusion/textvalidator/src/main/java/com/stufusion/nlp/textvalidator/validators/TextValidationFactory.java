package com.stufusion.nlp.textvalidator.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.stufusion.nlp.textvalidator.profanity.ProfanityFilter;
import com.stufusion.nlp.textvalidator.profanity.impl.ProfanityFilterFactory;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictor;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictorFactory;

public class TextValidationFactory {
	public static final String SPAM_TYPE = "SPAM";
	public static final String PROFANITY_TYPE = "PROFANITY";
	public static final String SENTENCT_STRUCTURE_TYPE = "SYNTACTIC";
	
	private static int safeShortTextLength = 200;
	private static int maxShortTextLength = 400;
	
	public static TextValidator createTextValidator(String validatorType, String candidateText, Map<String,String> options) {
		TextValidator textValidator = null;
		if (validatorType.equals(SPAM_TYPE)) {
			int length = candidateText.length(); 
			List<SpamPredictor> predictors = new ArrayList<>();
			
			if (length < safeShortTextLength)
			{
				predictors.add(SpamPredictorFactory.createSpamPredictor(SpamPredictorFactory.SHORT_TEXT_TYPE));
			}
			else if (length < maxShortTextLength)
			{
				predictors.add(SpamPredictorFactory.createSpamPredictor(SpamPredictorFactory.GENERIC_TYPE));	
			}
			else
			{
				predictors.add(SpamPredictorFactory.createSpamPredictor(SpamPredictorFactory.LONG_TEXT_TYPE));
			}
			
			SpamTextValidator spamTextValidator = new SpamTextValidator();
			spamTextValidator.setPredictors(predictors);
			
			textValidator = spamTextValidator;
			
		} else if (validatorType.equals(PROFANITY_TYPE)) {
				List<ProfanityFilter> filters = new ArrayList<>();
				
				filters.add(ProfanityFilterFactory.createProfanityFilter(ProfanityFilterFactory.REGEX_TYPE));
				filters.add(ProfanityFilterFactory.createProfanityFilter(ProfanityFilterFactory.DICTIONARY_TYPE));
				
				ProfanityTextValidator profanityTextValidator = new ProfanityTextValidator();
				profanityTextValidator.setFilters(filters);
				
				textValidator = profanityTextValidator;
		}

		return textValidator;
	}

}
