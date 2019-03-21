package com.stufusion.nlp.textvalidator.spamdetector;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.TestUtility;

public class ShortTextSpamDetectorTest {
	private static String TEST_SPAM_FILE = "test_spam.txt";
	private static final String SPAM_CATEGORY = "spam";
	private SpamPredictor spamDetector = null;

	@Before
	public void setup() throws InitializationExeption {
		spamDetector = SpamPredictorFactory.createSpamPredictor(SpamPredictorFactory.SHORT_TEXT_TYPE);
		spamDetector.initialize();
	}

	@After
	public void cleanUp() {
		spamDetector.cleanUp();
	}

	@Test
	public void testSimpleText() throws InitializationExeption {
		String testStr = "What is WordPress and codeIgnitor";

		boolean isSpam = spamDetector.getSpamPredictionOutput(testStr).isSpam();
		assertEquals(isSpam, false);

		testStr = "this is answer";
		isSpam = spamDetector.getSpamPredictionOutput(testStr).isSpam();
		assertEquals(isSpam, false);
	}
	
	@Test
	public void testShortTextFromFile() throws IOException, InitializationExeption {
		String filePath = TestUtility.getTestFilePath(TEST_SPAM_FILE);
		Integer misMatchCount = 0;
		Integer totalCount = 0;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(new File(filePath)));
			for (String line; (line = br.readLine()) != null;) {
				String[] temp = line.split("\t");

				if (temp.length != 2 || temp.length == 0) {
					System.out.println("only key found");
				} else {
					boolean isOriginalTextSpam = temp[0].equals(SPAM_CATEGORY);

					boolean isPredictedTextSpam = spamDetector.getSpamPredictionOutput(temp[1]).isSpam();

					if (isOriginalTextSpam != isPredictedTextSpam)
						misMatchCount++;
					totalCount++;
				}

			}

			// TODO: add asserts
			System.out.println("Short Text: Total mismatch found = " + misMatchCount.toString());
			System.out.println("Short Text: Total Count = " + totalCount.toString());

		} finally {
			if (br != null) {
				br.close();
			}
		}
	}
}
