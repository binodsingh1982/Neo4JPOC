
package com.stufusion.nlp.textvalidator.profanity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.profanity.impl.AbusiveWordPatternFilter;

public class TestPatternFilter {
	private ProfanityFilter badWordsPatternFilter;

	@Before
	public void setup() throws InitializationExeption {
		// setup
		badWordsPatternFilter = new AbusiveWordPatternFilter();
		assertNotNull(badWordsPatternFilter);
		
		badWordsPatternFilter.initialize();
	}

	@After
	public void cleanUp() {
		badWordsPatternFilter.cleanUp();
	}


	@Test
	public void testGetBadWordList() {
		String input = "?? This is this assHole question fuck,,what is this Ass   mass ?\n"
				+ "Binomial requires the shit_response to be a fuckingcategorical SHIT;ass  ";

		List<String> result = badWordsPatternFilter.getBadWords(input.toLowerCase()).getBadWordList();
		assertNotNull(result);
		assertEquals("Expected", 5, result.size());
		assertEquals("Expected ", "fuck", result.get(0));
		assertEquals("Expected ", "ass", result.get(1));
		assertEquals("Expected ", "shit", result.get(2));
		assertEquals("Expected ", "shit", result.get(3));
		assertEquals("Expected ", "ass", result.get(4));
	}

	@Test
	public void testGetBadWordList_Mix() {
		String input = "?? This asshole question saala,,what is this Ass   mass ?\n"
				+ "Binomial requires the shit_response to be a fuckingcategorical shit; "
				+ "rosy palm and her 5 sisters  ";

		List<String> result = badWordsPatternFilter.getBadWords(input.toLowerCase()).getBadWordList();
		assertNotNull(result);
		assertEquals("Expected", 3, result.size());
	}
}
