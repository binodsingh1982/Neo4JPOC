package com.stufusion.nlp.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.stufusion.nlp.textvalidator.TextValidationOutput;

/**
 * @author Sunil
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class GenericResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResponseData responseData;
	
	List<TextValidationOutput> textValidationOutputs;
	
	
	

	/**
	 * @return the textValidationOutputs
	 */
	public List<TextValidationOutput> getTextValidationOutputs() {
		return textValidationOutputs;
	}

	/**
	 * @param textValidationOutputs the textValidationOutputs to set
	 */
	public void setTextValidationOutputs(List<TextValidationOutput> textValidationOutputs) {
		this.textValidationOutputs = textValidationOutputs;
	}

	/**
	 * @return the responseData
	 */
	public ResponseData getResponseData() {
		return responseData;
	}

	/**
	 * @param responseData
	 *            the responseData to set
	 */
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}
}