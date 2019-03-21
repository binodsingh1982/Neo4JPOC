package com.stufusion.nlp.textvalidator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.stufusion.nlp.model.GenericResponse;
import com.stufusion.nlp.textvalidator.InitializationExeption;
import com.stufusion.nlp.textvalidator.ProcessingExeption;
import com.stufusion.nlp.textvalidator.mgr.TextValidatorManager;
import com.stufusion.nlp.textvalidator.service.TextValidatorService;
import com.stufusion.nlp.utility.ResponseUtils;

/**
 * @author Sunil
 *
 */
@RestController
@Component
@RequestMapping("/textValidator")
public class TextValidatorController {

	@Autowired
	TextValidatorService textValidatorService;

	@Autowired
	TextValidatorManager textValidatorManager;

	@RequestMapping(value = "/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GenericResponse validateText(@RequestBody List<String> validateTextList)
			throws ProcessingExeption, InitializationExeption {
		GenericResponse response = ResponseUtils.getGenericResponse("Text Validator responded", HttpStatus.OK);
		response.setTextValidationOutputs(textValidatorService.validateText(validateTextList));
		return response;
	}

	@RequestMapping(value = "/validateText", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GenericResponse createUser(@RequestBody String validateTextList)
			throws ProcessingExeption, InitializationExeption {
		GenericResponse response = ResponseUtils.getGenericResponse("Text Validator responded", HttpStatus.OK);
		response.setTextValidationOutputs(textValidatorManager.doAllTextValidation(validateTextList));
		return response;
	}

}
