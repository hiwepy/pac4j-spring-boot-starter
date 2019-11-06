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

import org.pac4j.core.ext.client.AccessTokenClient;
import org.pac4j.core.ext.credentials.authenticator.AccessTokenAuthenticator;
import org.pac4j.core.ext.credentials.extractor.TokenParameterExtractor;
import org.pac4j.core.ext.profile.AccessTokenProfileDefinition;
import org.pac4j.core.ext.profile.TokenProfile;
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
@ConditionalOnClass({ AccessTokenClient.class})
@ConditionalOnProperty(prefix = Pac4UniauthProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4UniauthProperties.class, Pac4jProperties.class })
public class Pac4jUniauthConfiguration {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public AccessTokenAuthenticator authenticator(Pac4UniauthProperties uniauthProperties) {
		
		AccessTokenAuthenticator authenticator = new AccessTokenAuthenticator();
		
		authenticator.setCharset(uniauthProperties.getCharset());
		authenticator.setConnectTimeout(uniauthProperties.getConnectTimeout());
		authenticator.setCustomHeaders(uniauthProperties.getCustomHeaders());
		authenticator.setCustomParams(uniauthProperties.getCustomParams());
		authenticator.setProfileDefinition(new AccessTokenProfileDefinition(uniauthProperties.getLoginUrl(), x -> new TokenProfile()));
		authenticator.setReadTimeout(uniauthProperties.getReadTimeout());
		authenticator.setSupportGetRequest(uniauthProperties.isSupportGetRequest());
		authenticator.setSupportPostRequest(uniauthProperties.isSupportPostRequest());
		
		return authenticator;
	}
	
	@Bean
	public AccessTokenClient uniauthClient(Pac4jProperties pac4jProperties, Pac4UniauthProperties uniauthProperties) {
		
		AccessTokenClient client = new AccessTokenClient();
		
		client.setAuthenticator(authenticator(uniauthProperties));
		client.setCredentialsExtractor(new TokenParameterExtractor(uniauthProperties.getAuthorizationParamName(), 
				uniauthProperties.isSupportGetRequest(), uniauthProperties.isSupportPostRequest()));
		// pac4jProperties.getCustomParams()
		client.setName(uniauthProperties.getClientName());
		client.setParameterName(uniauthProperties.getAuthorizationParamName());
		client.setSupportGetRequest(uniauthProperties.isSupportGetRequest());
		client.setSupportPostRequest(uniauthProperties.isSupportPostRequest());
		
		logger.debug("Client Inited : {}", client.toString());
		
		return client;
	}

}
