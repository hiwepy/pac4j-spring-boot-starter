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

import org.pac4j.core.ext.client.TokenClient;
import org.pac4j.core.ext.credentials.extractor.SignatureParameterExtractor;
import org.pac4j.core.ext.credentials.extractor.TokenParameterExtractor;
import org.pac4j.ext.uniauth.UniauthSignatureAuthenticator;
import org.pac4j.ext.uniauth.UniauthSignatureClient;
import org.pac4j.ext.uniauth.UniauthSignatureProfile;
import org.pac4j.ext.uniauth.UniauthSignatureProfileDefinition;
import org.pac4j.ext.uniauth.UniauthTokenAuthenticator;
import org.pac4j.ext.uniauth.UniauthTokenClient;
import org.pac4j.ext.uniauth.UniauthTokenProfile;
import org.pac4j.ext.uniauth.UniauthTokenProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(Pac4jAutoConfiguration.class)
@ConditionalOnClass({ TokenClient.class, UniauthTokenClient.class })
@ConditionalOnProperty(prefix = Pac4jUniauthProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jUniauthProperties.class, Pac4jProperties.class })
public class Pac4jUniauthConfiguration {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected UniauthTokenAuthenticator uniauthTokenAuthenticator(Pac4TokenProperties tokenProperties) {
		
		UniauthTokenAuthenticator authenticator = new UniauthTokenAuthenticator(tokenProperties.getProfileUrl());
		
		authenticator.setCharset(tokenProperties.getCharset());
		authenticator.setCustomHeaders(tokenProperties.getCustomHeaders());
		authenticator.setCustomParams(tokenProperties.getCustomParams());
		authenticator.setEncodeParams(tokenProperties.isEncodeParams());
		authenticator.setProfileDefinition(new UniauthTokenProfileDefinition(tokenProperties.getProfileUrl(), x -> new UniauthTokenProfile()));
		authenticator.setParameterName(tokenProperties.getTokenParamName());
		authenticator.setPassOriginParams(tokenProperties.isPassOriginParams());
		return authenticator;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jUniauthProperties.PREFIX, value = Pac4jClientNames.CAS_CLIENT, havingValue = "true")
	public UniauthTokenClient uniauthClient(Pac4jUniauthProperties uniauthProperties) {
		
		Pac4TokenProperties token = uniauthProperties.getToken();
		
		UniauthTokenClient client = new UniauthTokenClient();
		
		client.setAuthenticator(this.uniauthTokenAuthenticator(token));
		client.setCredentialsExtractor(new TokenParameterExtractor(token.getTokenParamName(), 
				token.isSupportGetRequest(), token.isSupportPostRequest(), token.getCharset()));
		// pac4jProperties.getCustomParams()
		client.setName(token.getClientName());
		client.setParameterName(token.getTokenParamName());
		client.setSupportGetRequest(token.isSupportGetRequest());
		client.setSupportPostRequest(token.isSupportPostRequest());
		
		logger.debug("Client Inited : {}", client.toString());
		
		return client;
	}
	
	protected UniauthSignatureAuthenticator uniauthSignatureAuthenticator(Pac4SignatureProperties signatureProperties) {
		
		UniauthSignatureAuthenticator authenticator = new UniauthSignatureAuthenticator();
		authenticator.setCharset(signatureProperties.getCharset());
		authenticator.setProfileDefinition(new UniauthSignatureProfileDefinition( x -> new UniauthSignatureProfile()));
		
		return authenticator;
	}
	
	@Bean
	public UniauthSignatureClient uniauthSignatureClient(Pac4jUniauthProperties uniauthProperties, 
			UniauthSignatureAuthenticator uniauthSignatureAuthenticator) {
		
		Pac4SignatureProperties signature = uniauthProperties.getSignature();
		UniauthSignatureClient client = new UniauthSignatureClient();
		
		client.setAuthenticator(uniauthSignatureAuthenticator);
		client.setCredentialsExtractor(new SignatureParameterExtractor(signature.getPayloadParamName(),  signature.getSignatureParamName(),
				signature.isSupportGetRequest(), signature.isSupportPostRequest(), signature.getCharset()));
		client.setName(signature.getClientName());
		client.setSupportGetRequest(signature.isSupportGetRequest());
		client.setSupportPostRequest(signature.isSupportPostRequest());
		
		logger.debug("Client Inited : {}", client.toString());
		
		return client;
	}

}
