package com.stufusion.oauth2.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiError {
    private String uri;

    private String message;

    private String errorCode;

    @JsonIgnore
    private List<String> messageParms;

    public static ApiErrorBuilder get() {
        return new ApiErrorBuilder();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getMessageParms() {
        return messageParms;
    }

    public void setMessageParms(List<String> messageParms) {
        this.messageParms = messageParms;
    }

    public static class ApiErrorBuilder {

        private ApiError apiErrorCode;

        public static ApiErrorBuilder get() {
            ApiErrorBuilder builder = new ApiErrorBuilder();
            builder.apiErrorCode = new ApiError();
            builder.apiErrorCode.messageParms = new ArrayList<>();
            return builder;
        }

        public static ApiErrorBuilder get(ApiError apiErrorCode) {
            ApiErrorBuilder builder = new ApiErrorBuilder();
            builder.apiErrorCode = apiErrorCode;
            return builder;
        }

        public ApiErrorBuilder uri(String uri) {
            apiErrorCode.uri = uri;
            return this;
        }

        public ApiErrorBuilder message(String message) {
            apiErrorCode.message = message;
            return this;
        }

        public ApiErrorBuilder errorCode(String errorCode) {
            apiErrorCode.errorCode = errorCode;
            return this;
        }

        public ApiErrorBuilder params(String... params) {
            Arrays.stream(params).forEach(apiErrorCode.messageParms::add);
            return this;
        }

        public ApiError build() {
            return apiErrorCode;
        }

    }
}
