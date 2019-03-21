package com.stufusion.nlp.textvalidator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stufusion.nlp.constant.TextValidatorType;
import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.ProcessingExeption;
import com.stufusion.nlp.textvalidator.TextValidationOutput;
import com.stufusion.nlp.textvalidator.mgr.TextValidatorManager;

/**
 * @author Sunil
 *
 */
@Service
public class TextValidatorService {

	@Autowired
	TextValidatorManager textValidatorManager;

	public List<TextValidationOutput> validateText(List<String> validateTextList) {
		List<TextValidationOutput> validationOutputs = new ArrayList<>();
		TextValidationOutput spamOutput = new TextValidationOutput(TextValidatorType.SPAM, false, 0);
		TextValidationOutput profanityOutput = new TextValidationOutput(TextValidatorType.PROFANITY, false, 0);
		for (String str : validateTextList) {
			try {
				List<TextValidationOutput> validations = textValidatorManager.doAllTextValidation(str);
				if (validations != null) {
					for (TextValidationOutput textVal : validations) {
						if (textVal.getType() == TextValidatorType.PROFANITY && profanityOutput.getResult() == false) {
							profanityOutput = textVal;
						} else if (textVal.getType() == TextValidatorType.SPAM
								&& Double.compare(spamOutput.getScore(), textVal.getScore()) < 0) {
							spamOutput = textVal;
						}
					}
				}
			} catch (InitializationExeption e) {
			} catch (ProcessingExeption e) {
			}
		}
		validationOutputs.add(profanityOutput);
		validationOutputs.add(spamOutput);
		return validationOutputs;
	}

}
