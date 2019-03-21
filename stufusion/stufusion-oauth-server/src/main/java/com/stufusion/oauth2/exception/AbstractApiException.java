package com.stufusion.oauth2.exception;

public abstract class AbstractApiException extends RuntimeException {

    private static final long serialVersionUID = 4054883770161866123L;

    private ApiError apiError;

    public AbstractApiException(ApiError error) {
        this.apiError = error;
    }

    public AbstractApiException(ApiError error, String msg, Throwable cause) {
        super(msg, cause);
        this.apiError = error;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }

    abstract public int getHttpStatusCode();

}
