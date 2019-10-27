/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.oauth2.provider.endpoint;

import xyz.luvtk.auth.dto.GenericResponse;
import xyz.luvtk.auth.dto.TokenDto;
import xyz.luvtk.auth.utils.ServiceResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.*;

/**
 * <p>
 * Endpoint for token requests as described in the OAuth2 spec. Clients post requests with a <code>grant_type</code>
 * parameter (e.g. "authorization_code") and other parameters as determined by the grant type. Supported grant types are
 * handled by the provided {@link #setTokenGranter(org.springframework.security.oauth2.provider.TokenGranter) token
 * granter}.
 * </p>
 *
 * <p>
 * Clients must be authenticated using a Spring Security {@link Authentication} to access this endpoint, and the client
 * id is extracted from the authentication token. The best way to arrange this (as per the OAuth2 spec) is to use HTTP
 * basic authentication for this endpoint with standard Spring Security support.
 * </p>
 *
 * @author Dave Syer
 *
 */
@FrameworkEndpoint
public class TokenEndpoint extends AbstractEndpoint {
	private static final Logger logger = LoggerFactory.getLogger(org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.class);

	private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();

	private Set<HttpMethod> allowedRequestMethods = new HashSet<HttpMethod>(Arrays.asList(HttpMethod.POST));

	@RequestMapping(value = "/oauth/token", method=RequestMethod.GET)
	@ResponseBody
	public GenericResponse<TokenDto> getAccessToken(Principal principal, @RequestParam
			Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
		if (!allowedRequestMethods.contains(HttpMethod.GET)) {
			throw new HttpRequestMethodNotSupportedException("GET");
		}
		return postAccessToken(principal, parameters);
	}

	@RequestMapping(value = "/oauth/token", method=RequestMethod.POST)
	@ResponseBody
	public GenericResponse<TokenDto> postAccessToken(Principal principal, @RequestParam
			Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
		GenericResponse<TokenDto> response = new GenericResponse<>();
		if (!(principal instanceof Authentication)) {
			logger.error("There is no client authentication. Try adding an appropriate authentication filter.");
			return response.success(false).code(ServiceResultCode.INTERNAL_SERVER_ERROR).msg("No client authentication");
		}

		String clientId = getClientId(principal);
		ClientDetails authenticatedClient = getClientDetailsService().loadClientByClientId(clientId);

		TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);

		if (clientId != null && !clientId.equals("")) {
			// Only validate the client details if a client authenticated during this
			// request.
			if (!clientId.equals(tokenRequest.getClientId())) {
				// double check to make sure that the client ID in the token request is the same as that in the
				// authenticated client
				logger.error("Given client ID does not match authenticated client");
				return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg("Given client ID does not match authenticated client");
			}
		}
		if (authenticatedClient != null) {
			oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);
		}
		if (!StringUtils.hasText(tokenRequest.getGrantType())) {
			logger.error("Missing grant type");
			return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg("Missing grant type");
		}
		if (tokenRequest.getGrantType().equals("implicit")) {
			logger.error("Implicit grant type not supported from token endpoint");
			return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg("Implicit grant type not supported from token endpoint");
		}

		if (isAuthCodeRequest(parameters)) {
			// The scope was requested or determined during the authorization step
			if (!tokenRequest.getScope().isEmpty()) {
				logger.debug("Clearing scope fromUser incoming token request");
				tokenRequest.setScope(Collections.<String> emptySet());
			}
		}

		if (isRefreshTokenRequest(parameters)) {
			// A refresh token has its own default scopes, so we should ignore any added by the factory here.
			tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
		}

		OAuth2AccessToken token = null;
		try {
			token = getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
		} catch (InvalidGrantException e) {
			if (e.getCause() instanceof BadCredentialsException) {
				return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg("Bad credentials");
			} else if (e.getCause() instanceof AccountStatusException) {
				return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg("Account status error");
			} else {
				return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg(e.getMessage());
			}
		} catch (Exception e) {
			logger.error("oops", e);
			return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg(e.getMessage());
		}
		if (token == null) {
			logger.error("Unsupported grant type: " + tokenRequest.getGrantType());
			return response.success(false).code(ServiceResultCode.INVALID_ERROR).msg("Unsupported grant type");
		}

		TokenDto tokenDto = new TokenDto();
		if (token.getRefreshToken() != null) {
			tokenDto.setRefreshToken(token.getRefreshToken().getValue());
			long reExpire = ((DefaultExpiringOAuth2RefreshToken) token.getRefreshToken()).getExpiration().getTime();
			tokenDto.setReExpiresIn((int) ((reExpire - System.currentTimeMillis()) / 1000));
		}
		tokenDto.setAccessToken(token.getValue());
		tokenDto.setExpiresIn(token.getExpiresIn());
		tokenDto.setScope(token.getScope());
		tokenDto.setJti(token.getAdditionalInformation().get("jti").toString());
		tokenDto.setTokenType(token.getTokenType());
		return response.success(true).result(tokenDto);
	}

	/**
	 * @param principal the currently authentication principal
	 * @return a client id if there is one in the principal
	 */
	protected String getClientId(Principal principal) {
		Authentication client = (Authentication) principal;
		if (!client.isAuthenticated()) {
			throw new InsufficientAuthenticationException("The client is not authenticated.");
		}
		String clientId = client.getName();
		if (client instanceof OAuth2Authentication) {
			// Might be a client and user combined authentication
			clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
		}
		return clientId;
	}

	private boolean isRefreshTokenRequest(Map<String, String> parameters) {
		return "refresh_token".equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
	}

	private boolean isAuthCodeRequest(Map<String, String> parameters) {
		return "authorization_code".equals(parameters.get("grant_type")) && parameters.get("code") != null;
	}

	public void setOAuth2RequestValidator(OAuth2RequestValidator oAuth2RequestValidator) {
		this.oAuth2RequestValidator = oAuth2RequestValidator;
	}

	public void setAllowedRequestMethods(Set<HttpMethod> allowedRequestMethods) {
		this.allowedRequestMethods = allowedRequestMethods;
	}
}
