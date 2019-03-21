package com.stufusion.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.stufusion.oauth2.exception.ApiError.ApiErrorBuilder;

public class RequestValidationException extends ApiBusinessException {

    private static final long serialVersionUID = -1465376620558723713L;

    private int httpStatusCode = HttpStatus.BAD_REQUEST.value();

    public RequestValidationException(String msg) {
        super(ApiErrorBuilder.get().errorCode(Codes.General.NOT_FOUND).message(msg).build(), msg, null);
    }

    public RequestValidationException(String errorCode, String msg) {
        super(ApiErrorBuilder.get().errorCode(errorCode).message(msg).build(), msg, null);
    }

    public RequestValidationException(ApiError error, Throwable cause) {
        super(error, null, cause);
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
