package com.stufusion.nlp.textvalidator.spamdetector;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.TestUtility;

public class EmailTextSpamDetectorTest {
	private static String TEST_SPAM_FOLDER = "emails/spam";
	private static String TEST_HAM_FOLDER = "emails/ham";
	private SpamPredictor spamPredictor = null;

	@Before
	public void setup() throws InitializationExeption {
		spamPredictor = SpamPredictorFactory.createSpamPredictor(SpamPredictorFactory.LONG_TEXT_TYPE);
		spamPredictor.initialize();
	}

	@After
	public void cleanUp() {
		spamPredictor.cleanUp();
	}
	
	@Test
	public void testSimpleText() throws InitializationExeption {
		String testStr = "the purpose of the email is to recap the kickoff meeting held on yesterday with members from commercial and volume managment concernig the entex account : effective january 2000 , thu nguyen ( x 37159 ) in the volume managment group , will take over the responsibility of allocating the entex contracts . howard and thu began some training this month and will continue to transition the account over the next few months . entex will be thu ' s primary account especially during these first few months as she learns the allocations process and the contracts .";

		boolean isSpam = spamPredictor.getSpamPredictionOutput(testStr).isSpam();
		assertEquals(isSpam, false);

		testStr = "we want to transfer to overseas=5b$50=2c000=2e000=2e00=5bfifty million united states dollars=5dfrom a security company in spain=2ci want to ask you to quietly look for a reliable and honest person who will be capable and fit to provide either an existing bank account or to set up a new bank account immediately to receive this money=2ceven an empty account can serve to receive this money=2cas long as you will remain honest to me till the end of this important business transaction=2ei want to believe that you will never let me down either now or in future=2e";
		isSpam = spamPredictor.getSpamPredictionOutput(testStr).isSpam();
		assertEquals(isSpam, true);
	}
	
	 
	@Test
	public void testSpamEmail() throws IOException, InitializationExeption {
		String folderPath = TestUtility.getTestFilePath(TEST_SPAM_FOLDER);
		File path = new File(folderPath);
		Integer misMatchCount = 0;

		Integer totalCount = 0;
		File[] files = path.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String contents = new String(Files.readAllBytes(files[i].toPath()));
				String orginalCategory = files[i].getParentFile().getName();
				boolean isOriginalTextSpam = orginalCategory.equals("spam");

				boolean isPredictedTextSpam = spamPredictor.getSpamPredictionOutput(contents).isSpam();

				if (isOriginalTextSpam != isPredictedTextSpam)
					misMatchCount++;
				totalCount++;
			}
		}

		// TODO: add asserts
		System.out.println("Spam Email: Total mismatch found = " + misMatchCount.toString());
		System.out.println("Spam Email : Total Count = " + totalCount.toString());
	}

	@Test
	public void testHamEmail() throws IOException, InitializationExeption {
		String folderPath = TestUtility.getTestFilePath(TEST_HAM_FOLDER);
		File path = new File(folderPath);
		Integer misMatchCount = 0;

		Integer totalCount = 0;
		File[] files = path.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String contents = new String(Files.readAllBytes(files[i].toPath()));
				String orginalCategory = files[i].getParentFile().getName();

				boolean isOriginalTextSpam = orginalCategory.equals("spam");

				boolean isPredictedTextSpam = spamPredictor.getSpamPredictionOutput(contents).isSpam();

				if (isOriginalTextSpam != isPredictedTextSpam)
					misMatchCount++;
				totalCount++;
			}
		}

		// TODO: add asserts
		System.out.println("Ham Email: Total mismatch found = " + misMatchCount.toString());
		System.out.println("Ham Email : Total Count = " + totalCount.toString());
	}
}
