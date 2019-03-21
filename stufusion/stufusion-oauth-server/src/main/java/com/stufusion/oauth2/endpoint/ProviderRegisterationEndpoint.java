package com.stufusion.oauth2.endpoint;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.stufusion.oauth2.exception.ApiBusinessException;
import com.stufusion.oauth2.exception.ApiSystemException;
import com.stufusion.oauth2.service.LinkedInService;

@Controller
@RequestMapping("/api/provider")
public class ProviderRegisterationEndpoint {

	@Autowired
	LinkedInService linkedInService;

	// @Inject
	LinkedIn linkedIn;

	@RequestMapping(value = "/linkedin/token", method = RequestMethod.GET)
	public RedirectView getOauthToken() {
		return new RedirectView(linkedInService.getToken());
	}

	@RequestMapping(value = "/linkedin/callback", method = RequestMethod.GET)
	public IdResponse handleOauthResponse(@RequestParam(value = "error", defaultValue = "") String error,
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "state", defaultValue = "") String state,
			@RequestParam(value = "error_description", defaultValue = "") String error_description,
			HttpSession session) {

		if (error.length() > 0) {
			return IdResponse.get(null);
		}

		return linkedInService.processCallback(code, state);
	}

}
