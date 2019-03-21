package com.stufusion.oauth2.exception;

public class Codes {

    public static class General {

        public static final String REQUEST_VALIDATION_FAILURE = "5000";

        public static final String NOT_FOUND = "6000";

        public static final String SYSTEM_ERROR = "9999";

        public static final String CONFLICT = "7000";

        public static final String AUTHORIZATION_FAILURE = "9000";
    }

    public static class User {
        public static final String INVALID_PASSWORD = "8001";
        public static final String USER_ALREADY_EXITS = "8002";
    }
    
    public static class Token {
        public static final String TOKEN_EXPIRED = "8003";
        
    }
}
