package com.stufusion.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.stufusion.oauth2.exception.ApiError.ApiErrorBuilder;

public class ApiSystemException extends AbstractApiException {

    private static final long serialVersionUID = 623744130268063606L;

    private int httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public ApiSystemException(String msg) {
        super(ApiErrorBuilder.get().errorCode(Codes.General.SYSTEM_ERROR).message(msg).build(), msg, null);
    }

    public ApiSystemException(ApiError error, String msg, Throwable cause) {
        super(error, msg, cause);
    }

    public ApiSystemException(ApiError error, String msg) {
        super(error, msg, null);
    }

    public ApiSystemException(ApiError error, Throwable cause) {
        super(error, null, cause);
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
