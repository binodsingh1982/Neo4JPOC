package com.stufusion.nlp.textvalidator.profanity;

import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.profanity.impl.DictionaryProfanityFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestProfanityFilter {
	private ProfanityFilter badWordsFilter;

	@Before
	public void setup() throws InitializationExeption {
		badWordsFilter = new DictionaryProfanityFilter();
		assertNotNull(badWordsFilter);

		badWordsFilter.initialize();
	}

	@After
	public void cleanUp() {
	}

	@Test
	public void testLoad() {

		// assertEquals(expected, actual);
	}

	@Test
	public void testGetTokens() {
		String input = "?? This is this asshole question fuck,,what is this ass   mass ?\n"
				+ "Binomial requires the shit_response to be a fuckingcategorical shit;ass  ";

		String[] tokens = badWordsFilter.getTokens(input);
		assertNotNull(tokens);
		assertEquals("Expected", 22, tokens.length);
		assertEquals("Expected ", "This", tokens[0]);
		// System.out.println(Arrays.toString(tokens));
	}

	@Test
	// test:- asshole
	public void testGetMatchCunt()
	{
		String text = "?? This is this asshole question fuck";
		int count = DictionaryProfanityFilter.getMatchCunt(text, "asshole");
		assertEquals(1, count);

		text = "?? This is this asshole question fuck";
		count = DictionaryProfanityFilter.getMatchCunt(text, "ass");
		assertEquals(0, count);

		text = "?? This is this assholes question fuck";
		count = DictionaryProfanityFilter.getMatchCunt(text, "asshole");
		assertEquals(0, count);

	}

	@Test
	public void testGetBadWordList1() {
		String input = "?? This is the assHole answer the ass the asshole";

		List<String> result = badWordsFilter.getBadWords(input.toLowerCase()).getBadWordList();
		assertNotNull(result);
		assertEquals("Expected", 3, result.size());
		assertEquals("Expected ", "ass", result.get(0));
		assertEquals("Expected ", "asshole", result.get(1));
		assertEquals("Expected ", "asshole", result.get(2));
	}

	@Test
	public void testGetBadWordList() {
		String input = "?? This is the assHole question fuck,,what is this Ass   mass ?\n"
				+ "Binomial requires the shit_response to be a fuckingcategorical SHIT;ass  ";

		//  this is the asshole question fuck what is this ass mass binomial requires the shit response to be a fuckingcategorical shit ass

		List<String> result = badWordsFilter.getBadWords(input.toLowerCase()).getBadWordList();
		assertNotNull(result);
		assertEquals("Expected", 6, result.size());
		assertEquals("Expected ", "fuck", result.get(0));
		assertEquals("Expected ", "shit", result.get(1));
		assertEquals("Expected ", "shit", result.get(2));
		assertEquals("Expected ", "ass", result.get(3));
		assertEquals("Expected ", "ass", result.get(4));
		assertEquals("Expected ", "asshole", result.get(5));
	}

	@Ignore
	// FIXME
	public void testGetBadWordList_Mix() {
		String input = "?? This asshole question saala,,what is this Ass   mass ?\n"
				+ "Binomial requires the shit_response to be a fuckingcategorical shit; "
				+ "rosy palm and her 5 sisters  ";

		List<String> result = badWordsFilter.getBadWords(input.toLowerCase()).getBadWordList();
		assertNotNull(result);
		assertEquals("Expected", 6, result.size());
	}

	@Test
	// alabama hot pocket
	public void testGetMultiBadWord() {
		String input = "?? This is this     alabama hot pocket   question,,what is this    Ass   mass ?\n"
				+ "Binomial requires the    shit_response to be a fuckingcategorical     SHIT;ass     ";

		//this is this alabama hot pocket question what is this ass mass binomial requires the shit response to be a fuckingcategorical shit ass

		List<String> result = badWordsFilter.getBadWords(input.toLowerCase()).getBadWordList();
		assertNotNull(result);
		assertEquals("Expected", 5, result.size());
		assertEquals("Expected ", "alabama hot pocket", result.get(0));
		assertEquals("Expected ", "shit", result.get(1));
		assertEquals("Expected ", "shit", result.get(2));
		assertEquals("Expected ", "ass", result.get(3));
		assertEquals("Expected ", "ass", result.get(4));
	}

}
