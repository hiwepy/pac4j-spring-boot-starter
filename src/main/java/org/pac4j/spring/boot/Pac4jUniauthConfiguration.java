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
import org.pac4j.core.ext.credentials.extractor.TokenParameterExtractor;
import org.pac4j.spring.boot.uniauth.UniauthProfile;
import org.pac4j.spring.boot.uniauth.UniauthProfileDefinition;
import org.pac4j.spring.boot.uniauth.UniauthTokenAuthenticator;
import org.pac4j.spring.boot.uniauth.UniauthTokenClient;
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
	
	@Bean
	public UniauthTokenAuthenticator uniauthAuthenticator(Pac4jUniauthProperties uniauthProperties) {
		
		UniauthTokenAuthenticator authenticator = new UniauthTokenAuthenticator(uniauthProperties.getLoginUrl());
		
		authenticator.setCustomHeaders(uniauthProperties.getCustomHeaders());
		authenticator.setCustomParams(uniauthProperties.getCustomParams());
		authenticator.setProfileDefinition(new UniauthProfileDefinition(uniauthProperties.getLoginUrl(), x -> new UniauthProfile()));
		authenticator.setParameterName(uniauthProperties.getAuthorizationParamName());
		return authenticator;
	}
	
	@Bean
	public UniauthTokenClient uniauthClient(Pac4jUniauthProperties uniauthProperties, UniauthTokenAuthenticator uniauthAuthenticator) {
		
		UniauthTokenClient client = new UniauthTokenClient();
		
		client.setAuthenticator(uniauthAuthenticator);
		client.setCredentialsExtractor(new TokenParameterExtractor(uniauthProperties.getAuthorizationParamName(), 
				uniauthProperties.isSupportGetRequest(), uniauthProperties.isSupportPostRequest(), uniauthProperties.getCharset()));
		// pac4jProperties.getCustomParams()
		client.setName(uniauthProperties.getClientName());
		client.setParameterName(uniauthProperties.getAuthorizationParamName());
		client.setSupportGetRequest(uniauthProperties.isSupportGetRequest());
		client.setSupportPostRequest(uniauthProperties.isSupportPostRequest());
		
		logger.debug("Client Inited : {}", client.toString());
		
		return client;
	}

}
