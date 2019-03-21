package com.stufusion.nlp.utility;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.stufusion.nlp.model.GenericResponse;
import com.stufusion.nlp.model.ResponseData;

/**
 * @author Sunil
 *
 */
@Component
public class ResponseUtils {

    private static MessageSource messageSource;

    /**
     * @param description
     * @param result
     * @return
     */
    public static ResponseData getResponseData(String description, HttpStatus result) {
        ResponseData responseData = new ResponseData();
        responseData.setDescription(description);
        responseData.setResultCode(result.value());
        return responseData;

    }

    public static ResponseData getResponseData(String description, HttpStatus result, Locale locale) {
        ResponseData responseData = new ResponseData();
        String DEFAULT_MESSAGE = result == HttpStatus.OK ? "Success" : "Fail";
        responseData.setDescription(messageSource.getMessage(description, null, DEFAULT_MESSAGE, locale));
        responseData.setResultCode(result.value());
        return responseData;

    }

    /**
     * @param responseDataDescription
     * @param responseCode
     * @return
     */
    public static GenericResponse getGenericResponse(String responseDataDescription, HttpStatus responseCode) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResponseData(getResponseData(responseDataDescription, responseCode));
        return genericResponse;
    }

    /**
     * @param responseDataResult
     * @param keyDescription
     * @param locale
     * @return
     */
    public static GenericResponse getGenericResponse(String keyDescription, HttpStatus responseDataResult, Locale locale) {
        GenericResponse genericResponse = new GenericResponse();
        String DEFAULT_MESSAGE = responseDataResult == HttpStatus.OK ? "Success" : "Fail";
        genericResponse.setResponseData(getResponseData(
                messageSource.getMessage(keyDescription, null, DEFAULT_MESSAGE, locale), responseDataResult));
        return genericResponse;
    }
}