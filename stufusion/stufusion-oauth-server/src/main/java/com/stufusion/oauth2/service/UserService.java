package com.stufusion.oauth2.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.stufusion.oauth2.model.UserReq;
import com.stufusion.oauth2.model.UserResp;

public interface UserService extends UserDetailsService {

    public Long createUser(UserReq user);

    Long createUserWithEmailVerification(UserReq userReq);

    UserResp getUser(String email);

    UserResp getUser(Long id);

    List<UserResp> getUsers();

}
