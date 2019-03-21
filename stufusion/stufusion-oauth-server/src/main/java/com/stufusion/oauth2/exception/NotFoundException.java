package com.stufusion.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.stufusion.oauth2.exception.ApiError.ApiErrorBuilder;

public class NotFoundException extends ApiBusinessException {

    private static final long serialVersionUID = 6378957393812862835L;

    private int httpStatusCode = HttpStatus.NOT_FOUND.value();

    public NotFoundException(String msg) {
        super(ApiErrorBuilder.get().errorCode(Codes.General.NOT_FOUND).message(msg).build(), msg);
    }

    public NotFoundException(ApiError error, String msg) {
        super(error, msg);
    }

    public NotFoundException(ApiError error, String msg, Throwable cause) {
        super(error, msg, cause);
    }

    public NotFoundException(ApiError error, Throwable cause) {
        super(error, null, cause);
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
