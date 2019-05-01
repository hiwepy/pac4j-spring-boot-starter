/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.pac4j.spring.boot;

import java.util.List;

import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.http.client.direct.CookieClient;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.jwt.config.encryption.EncryptionConfiguration;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.spring.boot.ext.authentication.UsernamePasswordCaptchaAuthenticator;
import org.pac4j.spring.boot.ext.credentials.extractor.PostRequestAuthenticationExtractor;
import org.pac4j.spring.boot.ext.property.Pac4jJwtProperties;
import org.pac4j.spring.boot.ext.property.Pac4jProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;

@Configuration
@ConditionalOnClass({ CookieClient.class, ParameterClient.class, HeaderClient.class, JwtAuthenticator.class })
@ConditionalOnProperty(prefix = Pac4jJwtProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jJwtProperties.class, Pac4jProperties.class, ServerProperties.class })
public class Pac4jJwtConfiguration {

	@Autowired
	private Pac4jJwtProperties jwtProperties;

	@Bean
	@ConditionalOnMissingBean
	public EncryptionConfiguration encryptionConfiguration() {
		SecretEncryptionConfiguration encryptionConfiguration = new SecretEncryptionConfiguration(
				jwtProperties.getEncryptSecret(), JWEAlgorithm.parse(jwtProperties.getJweAlgorithm().value()),
				EncryptionMethod.parse(jwtProperties.getEncryption().value()));
		return encryptionConfiguration;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SignatureConfiguration signatureConfiguration() {
		return new SecretSignatureConfiguration(jwtProperties.getSignSecret(),
				JWSAlgorithm.parse(jwtProperties.getJwsAlgorithm().value()));
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtAuthenticator jwtAuthenticator(List<SignatureConfiguration> signatureConfigurations,
			List<EncryptionConfiguration> encryptionConfigurations) {

		JwtAuthenticator authenticator = new JwtAuthenticator();
		//authenticator.setEncryptionConfiguration(encryptionConfiguration);
		authenticator.setEncryptionConfigurations(encryptionConfigurations);
		//authenticator.setExpirationTime(expirationTime);
		//authenticator.setProfileDefinition(profileDefinition);
		//authenticator.setRealmName(realmName);
		//authenticator.setSignatureConfiguration(signatureConfiguration);
		authenticator.setSignatureConfigurations(signatureConfigurations);
		
		return authenticator;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public UsernamePasswordCaptchaAuthenticator usernamePasswordAuthenticator() {
		
		UsernamePasswordCaptchaAuthenticator authenticator = new UsernamePasswordCaptchaAuthenticator();
		
		authenticator.setCaptchaRequired(captchaRequired);
		authenticator.setCaptchaResolver(captchaResolver);
		authenticator.setFailureCounter(failureCounter);
		authenticator.setRetryTimesKeyAttribute(retryTimesKeyAttribute);
		authenticator.setRetryTimesWhenAccessDenied(retryTimesWhenAccessDenied);
		
		return authenticator;
	}
	
	
	@Bean
	@ConditionalOnMissingBean
	public PostRequestAuthenticationExtractor credentialsExtractor() {
		
		PostRequestAuthenticationExtractor credentialsExtractor = new PostRequestAuthenticationExtractor();
		
		return credentialsExtractor;
		
	}
	
	@Bean
 	public FormClient jwtAuthcClient(AjaxRequestResolver ajaxRequestResolver,
 			CallbackUrlResolver callbackUrlResolver,
 			PostRequestAuthenticationExtractor credentialsExtractor,
 			UrlResolver urlResolver) {

		UsernamePasswordCaptchaAuthenticator usernamePasswordAuthenticator = new UsernamePasswordCaptchaAuthenticator();
		FormClient client = new FormClient();
		
		client.setAjaxRequestResolver(ajaxRequestResolver);
		client.setAuthenticator(usernamePasswordAuthenticator);
		//client.setAuthorizationGenerator(authorizationGenerator);
		//client.setAuthorizationGenerators(authorizationGenerators);
		client.setCallbackUrl(jwtProperties.getCallbackUrl());
		client.setCallbackUrlResolver(callbackUrlResolver);
		client.setCredentialsExtractor(credentialsExtractor);
		if(jwtProperties.getCustomProperties() != null ) {
			client.setCustomProperties(jwtProperties.getCustomProperties());
		}
		client.setLoginUrl(jwtProperties.getLoginUrl());
		//client.setLogoutActionBuilder(NoLogoutActionBuilder.INSTANCE);
		client.setName(jwtProperties.getAuthcClientName());
		client.setPasswordParameter(jwtProperties.getPasswordParameterName());
		//client.setProfileCreator(profileCreator);
		//client.setRedirectActionBuilder(redirectActionBuilder);
		client.setUrlResolver(urlResolver);
		client.setUsernameParameter(jwtProperties.getUsernameParameterName());
		
 		return client;
 	}
	
	@Bean
	public CookieClient jwtCookieAuthzClient(JwtAuthenticator jwtAuthenticator) {

		CookieClient client = new CookieClient(jwtProperties.getAuthorizationCookieName(), jwtAuthenticator);
		
		//client.setAuthenticator(jwtAuthenticator);
		//client.setAuthorizationGenerator(authorizationGenerator);
		//client.setAuthorizationGenerators(authorizationGenerators);
		//client.setCredentialsExtractor(credentialsExtractor);
		if(jwtProperties.getCustomProperties() != null ) {
			client.setCustomProperties(jwtProperties.getCustomProperties());
		}
		client.setName(jwtProperties.getCookieClientName());
		client.setCookieName(jwtProperties.getAuthorizationCookieName());
		//client.setProfileCreator(profileCreator);
		
		return client;
	}

	@Bean
	public HeaderClient jwtHeaderAuthzClient(JwtAuthenticator jwtAuthenticator) {

		HeaderClient client = new HeaderClient(jwtProperties.getAuthorizationHeaderName(), jwtAuthenticator);
		
		//client.setAuthenticator(jwtAuthenticator);
		//client.setAuthorizationGenerator(authorizationGenerator);
		//client.setAuthorizationGenerators(authorizationGenerators);
		//client.setCredentialsExtractor(credentialsExtractor);
		if(jwtProperties.getCustomProperties() != null ) {
			client.setCustomProperties(jwtProperties.getCustomProperties());
		}
		client.setName(jwtProperties.getHeaderClientName());
		client.setHeaderName(jwtProperties.getAuthorizationHeaderName());
		client.setPrefixHeader(jwtProperties.getAuthorizationHeaderPrefix());
		//client.setProfileCreator(profileCreator);

		return client;
	}

	@Bean
	public ParameterClient jwtParamAuthzClient(JwtAuthenticator jwtAuthenticator) {
		
		// REST authent with JWT for a token passed in the url as the token parameter

		ParameterClient client = new ParameterClient(jwtProperties.getAuthorizationParamName(), jwtAuthenticator);
		//client.setAuthenticator(jwtAuthenticator);
		//client.setAuthorizationGenerator(authorizationGenerator);
		//client.setAuthorizationGenerators(authorizationGenerators);
		//client.setCredentialsExtractor(credentialsExtractor);
		if(jwtProperties.getCustomProperties() != null ) {
			client.setCustomProperties(jwtProperties.getCustomProperties());
		}
		client.setName(jwtProperties.getParamClientName());
		client.setParameterName(jwtProperties.getAuthorizationParamName());
		//client.setProfileCreator(profileCreator);
		client.setSupportGetRequest(jwtProperties.isSupportGetRequest());
		client.setSupportPostRequest(jwtProperties.isSupportPostRequest());
		
		return client;
	}

}
