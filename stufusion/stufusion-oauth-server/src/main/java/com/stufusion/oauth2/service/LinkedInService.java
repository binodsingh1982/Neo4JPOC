package com.stufusion.oauth2.service;

import org.springframework.stereotype.Service;

import com.stufusion.oauth2.endpoint.IdResponse;
import com.stufusion.oauth2.exception.ApiBusinessException;
import com.stufusion.oauth2.exception.ApiSystemException;

@Service
public interface LinkedInService {

	public String getToken() throws ApiSystemException;

	public IdResponse processCallback(String code, String state) throws ApiSystemException, ApiBusinessException;
}
