package com.stufusion.oauth2.service;

public interface TokenVerificationService {

    String create(Long userId);

    boolean verifyToken(String token);

}
