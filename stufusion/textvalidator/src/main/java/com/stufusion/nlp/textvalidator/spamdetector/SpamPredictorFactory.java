package com.stufusion.nlp.textvalidator.spamdetector;

import com.stufusion.nlp.textvalidator.spamdetector.maxent.MaxEntSpamPredictor;

public class SpamPredictorFactory {
	private static String shortTextSpamModelPath = "com/stufusion/nlp/spam/models/spam_sms_u.bin";
	private static String longTextSpamModelPath = "com/stufusion/nlp/spam/models/spam_email_u.bin";
	private static String genericSpamModelPath = "com/stufusion/nlp/spam/models/spam_generic_u.bin";
	private static String svmSpamModelPath = "file:///D:/NLPWork/TextValidator/src/main/resources/com/stufusion/nlp/spam/models/spam_email_svm.jar";

	public static final String SHORT_TEXT_TYPE = "SHORT_TEXT_TYPE";
	public static final String LONG_TEXT_TYPE = "LONG_TEXT_TYPE";
	public static final String GENERIC_TYPE = "GENERIC_TYPE";

	public static SpamPredictor createSpamPredictor(String type) {
		SpamPredictor predictor = new MaxEntSpamPredictor();
		String modelPath = genericSpamModelPath;
		predictor.setModelPath(modelPath);
		return predictor;
	}

}
