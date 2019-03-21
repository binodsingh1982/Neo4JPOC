package com.stufusion.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.stufusion.oauth2.exception.ApiError.ApiErrorBuilder;

public class ApiBusinessException extends AbstractApiException {

    private static final long serialVersionUID = 2796453897192710255L;

    private int httpStatusCode = HttpStatus.CONFLICT.value();

    public ApiBusinessException(ApiError error, String msg, Throwable cause) {
        super(error, msg, cause);
    }

    public ApiBusinessException(String errorCode, String msg) {
        super(ApiErrorBuilder.get().errorCode(errorCode).message(msg).build(), msg, null);
    }

    public ApiBusinessException(ApiError error, String msg) {
        super(error, msg, null);
    }

    public ApiBusinessException(ApiError error, Throwable cause) {
        super(error, null, cause);
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
