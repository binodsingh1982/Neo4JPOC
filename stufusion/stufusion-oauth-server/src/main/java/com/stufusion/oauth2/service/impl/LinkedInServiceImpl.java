package com.stufusion.oauth2.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.stufusion.oauth2.endpoint.IdResponse;
import com.stufusion.oauth2.entity.User;
import com.stufusion.oauth2.entity.UserAuthProvider;
import com.stufusion.oauth2.enums.AuthProviderType;
import com.stufusion.oauth2.exception.ApiBusinessException;
import com.stufusion.oauth2.exception.ApiSystemException;
import com.stufusion.oauth2.model.LinkedInAccessTokenResponse;
import com.stufusion.oauth2.model.LinkedInUserProfileResponse;
import com.stufusion.oauth2.repository.UserAuthProviderRepository;
import com.stufusion.oauth2.repository.UserRepository;
import com.stufusion.oauth2.service.LinkedInService;
import com.stufusion.oauth2.utils.UserUtils;

@Service
@Transactional
public class LinkedInServiceImpl implements LinkedInService {

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private UserRepository userDAO;

	@Autowired
	private UserAuthProviderRepository userAuthProviderDAO;

	@Autowired
	Gson gson;

	@Value("${auth.provider.linkedin.client.id}")
	private String linkedInClientId;
	@Value("${auth.provider.linkedin.secret}")
	private String linkedInSecret;
	@Value("${auth.provider.linkedin.oauth}")
	private String linkedInOauthUrl;
	@Value("${auth.provider.linkedin.access.token.url}")
	private String linkedInAccessTokenUrl;
	@Value("${auth.provider.linkedin.scope}")
	private String linkedInScope;
	@Value("${auth.provider.linkedin.redirect}")
	private String linkedInRedirectUrl;
	@Value("${auth.provider.linkedin.state}")
	private String linkedInState;
	@Value("${auth.provider.linkedin.profile.info.url}")
	private String linkedInProfileInfoUrl;

	@Override
	public String getToken() throws ApiSystemException {

		StringBuilder sb = new StringBuilder().append(linkedInOauthUrl).append("?client_id=").append(linkedInClientId)
				.append("&response_type=code").append("&scope=").append(linkedInScope).append("&redirect_uri=")
				.append(linkedInRedirectUrl).append("&state=").append(linkedInState);

		return URIBuilder.fromUri(sb.toString()).build().toString();
	}

	@Override
	public IdResponse processCallback(String code, String state) throws ApiSystemException, ApiBusinessException {

		ImmutableMap<String, String> map = ImmutableMap.<String, String>builder().put("code", code)
				.put("client_id", linkedInClientId).put("client_secret", linkedInSecret)
				.put("redirect_uri", linkedInRedirectUrl).put("state", state).put("grant_type", "authorization_code")
				.build();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> requestEntity = null;
		try {
			requestEntity = new HttpEntity<String>(getHttpRequestBody(map), httpHeaders);
		} catch (IOException e) {
			throw new ApiSystemException("Error from linkedIn");
		}

		ResponseEntity<String> entityAccessToken = exchange(linkedInAccessTokenUrl, HttpMethod.POST, requestEntity,
				String.class);

		if (!entityAccessToken.getStatusCode().is2xxSuccessful()) {
			throw new ApiSystemException("Error from linkedIn");
		}
		LinkedInAccessTokenResponse tokenResponse = gson.fromJson(entityAccessToken.getBody(),
				LinkedInAccessTokenResponse.class);
		httpHeaders.add("Authorization", "Bearer " + tokenResponse.getAccess_token());
		requestEntity = new HttpEntity<String>(null, httpHeaders);

		ResponseEntity<String> entity = exchange(linkedInProfileInfoUrl, HttpMethod.GET, requestEntity, String.class);

		if (!entity.getStatusCode().is2xxSuccessful()) {
			throw new ApiSystemException("Error from linkedIn");
		}
		LinkedInUserProfileResponse userResponse = gson.fromJson(entity.getBody(), LinkedInUserProfileResponse.class);

		User user;

		user = userDAO.findByEmail(userResponse.getEmailAddress());
		if (user == null) {
			user = new User();
			user.setEmail(userResponse.getEmailAddress());
			user.setEnabled(true);
			user.setFirstName(userResponse.getFirstName());
			user.setLastName(userResponse.getLastName());
			user.setUserId(UserUtils.getUserId(userResponse.getFirstName(), userResponse.getLastName()));
			user.addUserAuthProvider(new UserAuthProvider(AuthProviderType.LINKEDIN, userResponse.getId(), user));
			user = userDAO.save(user);
		} else {
			UserAuthProvider userAuthProvider = null;
			for (UserAuthProvider authProvider : user.getUserAuthProviders()) {
				if (authProvider.getAuthProviderType().equals(AuthProviderType.LINKEDIN)) {
					userAuthProvider = authProvider;
				}
			}
			if (userAuthProvider == null) {
				userAuthProviderDAO.save(new UserAuthProvider(AuthProviderType.LINKEDIN, userResponse.getId(), user));
			}
		}
		user.setUserId(user.getUserId());
		// TODO: send authToken of our server
		return IdResponse.get(user.getId());
	}

	private String getHttpRequestBody(ImmutableMap<String, String> map) throws IOException {
		Iterator<String> parameterIterator = map.keySet().iterator();
		String parameterName;
		StringBuffer requestParams = new StringBuffer();
		while (parameterIterator.hasNext()) {
			parameterName = parameterIterator.next();
			requestParams.append(URLEncoder.encode(parameterName, "UTF-8"));
			requestParams.append("=").append(URLEncoder.encode(map.get(parameterName), "UTF-8"));
			requestParams.append("&");
		}
		return requestParams.toString();
	}

	<T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType,
			Object... uriVariables) throws ApiSystemException {
		ApiSystemException exception = null;
		try {
			return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
		} catch (Exception e) {
			exception = new ApiSystemException("Exception while talking to LinkedIn ");
		}
		throw exception;
	}

}
