package com.stufusion.nlp.textvalidator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stufusion.nlp.textvalidator.mgr.TextValidatorManager;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictor;
import com.stufusion.nlp.textvalidator.spamdetector.SpamPredictorFactory;

public class SimpleTextValidatorTest {

	private SpamPredictor spamDetector = null;

	@BeforeClass
	public static void initCalculator() throws MalformedURLException, IOException {

	}

	@Before
	public void beforeEachTest() throws InitializationExeption {

		spamDetector = SpamPredictorFactory.createSpamPredictor(SpamPredictorFactory.SHORT_TEXT_TYPE);
		spamDetector.initialize();
	}

	@After
	public void afterEachTest() {

	}

	@Test
	public void testSimpleSpamText() throws InitializationExeption, ProcessingExeption {
		TextValidatorManager textValidatorManager = new TextValidatorManager();
		String testStr = "Java is a general-purpose computer programming language that is concurrent, class-based, object-oriented,[14] and specifically designed to have as few implementation dependencies as possible. It is intended to let application developers write once, run anywhere (WORA),[15] meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.";

		List<TextValidationOutput> output = textValidatorManager.doAllTextValidation(testStr);
		boolean isSpam = output.get(0).getResult();

		assertEquals(isSpam, false);

		testStr = "Would you be interested in buying a camera";
		output = textValidatorManager.doAllTextValidation(testStr);
		isSpam = output.get(0).getResult();

		// assertEquals(isSpam, true);
	}
}
