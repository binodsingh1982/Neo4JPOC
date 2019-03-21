package com.stufusion.nlp.textvalidator.controllers;

import java.util.Locale;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.stufusion.nlp.constant.CommonConstants;
import com.stufusion.nlp.model.GenericResponse;
import com.stufusion.nlp.textvalidator.BusinessException;
import com.stufusion.nlp.textvalidator.SystemException;
import com.stufusion.nlp.utility.ResponseUtils;

/**
 * @author Sunil
 *
 */
@ControllerAdvice
public class ExceptionController {
	final static Logger LOGGER = Logger.getLogger(ExceptionController.class);

	@Autowired
	private Gson gson;

	/**
	 * @param businessException
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public GenericResponse handleBusinessException(BusinessException businessException) {
		businessException.printStackTrace();
		LOGGER.error("Error description BusinessException:	" + ExceptionUtils.getStackTrace(businessException));
		GenericResponse genResponse = null;
		genResponse = ResponseUtils.getGenericResponse(businessException.getExceptionCode(),
				HttpStatus.INTERNAL_SERVER_ERROR, Locale.getDefault());

		LOGGER.debug("Response from BusinessException:	" + gson.toJson(genResponse));
		return genResponse;
	}

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(SystemException.class)
	@ResponseBody
	public GenericResponse handleSysException(SystemException exception) {
		exception.printStackTrace();
		boolean isDeployement = false;

		if (isDeployement) {
			sendErrorMail(exception);
		}

		LOGGER.error("Error description SystemException:		" + ExceptionUtils.getStackTrace(exception));
		GenericResponse genResponse = ResponseUtils.getGenericResponse(CommonConstants.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR, Locale.getDefault());
		LOGGER.error("Response from SystemException:	" + gson.toJson(genResponse));
		return genResponse;
	}

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public GenericResponse handleException(Exception exception) {
		exception.printStackTrace();
		boolean isDeployement = false;

		if (isDeployement) {
			sendErrorMail(exception);
		}

		LOGGER.error("Error description SystemException:        " + ExceptionUtils.getStackTrace(exception));
		GenericResponse genResponse = ResponseUtils.getGenericResponse(CommonConstants.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR, Locale.getDefault());
		LOGGER.error("Response from SystemException:    " + gson.toJson(genResponse));
		return genResponse;
	}

	@Async
	public void sendErrorMail(Exception exception) {
		// send email here via client
	}
}
