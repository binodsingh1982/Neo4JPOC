package com.stufusion.nlp.textvalidator;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import com.stufusion.nlp.textvalidator.service.TextValidatorService;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TextValidatorTest extends AbstractBaseSetupTest {

	@Autowired
	private TextValidatorService textValidatorService;

	@Test
	@DirtiesContext
	public void testCreateUserProfile() {

		String candidateText = "This is this asshole question fuck,,what is this ass   mass ";
		List<TextValidationOutput> textValidationOutputs = null;
		List<String> inputList = new ArrayList<>();
		inputList.add(candidateText);
		textValidationOutputs = textValidatorService.validateText(inputList);

		Assert.assertEquals(textValidationOutputs.get(0).getResult(), true);
		Assert.assertEquals(textValidationOutputs.get(1).getResult(), false);

	}

}
