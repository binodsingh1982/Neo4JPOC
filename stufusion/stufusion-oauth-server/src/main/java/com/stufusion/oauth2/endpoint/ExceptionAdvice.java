package com.stufusion.oauth2.endpoint;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stufusion.oauth2.exception.AbstractApiException;
import com.stufusion.oauth2.exception.ApiError;
import com.stufusion.oauth2.exception.ApiError.ApiErrorBuilder;
import com.stufusion.oauth2.exception.Codes;

/**
 * @author sanjay.singh
 *
 */
@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    final static Logger LOG = Logger.getLogger(ExceptionAdvice.class);

    /**
     * @param exception
     * @return
     */
    @ExceptionHandler(AbstractApiException.class)
    @ResponseBody
    public ApiError handleAbstractApiException(HttpServletResponse response, AbstractApiException exception) {
        LOG.debug("Somethinng went wrong in application", exception);
        response.setStatus(exception.getHttpStatusCode());
        return exception.getApiError();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(HttpServletResponse response, Exception exception) {
        LOG.debug("Somethinng went wrong in application", exception);
        return ApiErrorBuilder.get().errorCode(Codes.General.SYSTEM_ERROR).message(exception.getMessage()).build();
    }

}
