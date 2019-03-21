/**
 * 
 */
package com.stufusion.nlp.textvalidator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.google.gson.Gson;
import com.stufusion.nlp.AppConfig;
import com.stufusion.nlp.model.GenericResponse;

/**
 * @author Sunil
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
@ActiveProfiles(profiles = "test")
@Rollback(true)
public abstract class AbstractBaseSetupTest {

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected Gson gson;

	protected MockMvc mockMVC;

	@BeforeClass
	public static void setErrorLogging() {
		LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.GLOBAL_LOGGER_NAME, LogLevel.ERROR);
	}

	@Before
	public void before() {
		mockMVC = MockMvcBuilders.webAppContextSetup(wac).alwaysExpect(status().isOk()).build();
	}

	/**
	 * @param requestJson
	 * @param relativeUrl
	 * @param expectedResponseResult
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public MvcResult sendRequest(String requestJson, String relativeUrl, HttpStatus expectedResponseResult)
			throws Exception, UnsupportedEncodingException {

		MvcResult result = mockMVC
				.perform(post(relativeUrl).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
				.andReturn();
		Assert.assertNotNull(result);// Change assert statement to use full
		String contentAsString = result.getResponse().getContentAsString();
		GenericResponse responseObj = gson.fromJson(contentAsString, GenericResponse.class);

		Assert.assertNotNull(responseObj);

		Assert.assertEquals("Result code of response not matched", expectedResponseResult.value(),
				responseObj.getResponseData().getResultCode().intValue());
		return result;
	}

}
