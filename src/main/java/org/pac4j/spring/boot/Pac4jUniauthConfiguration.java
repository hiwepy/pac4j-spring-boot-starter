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
import org.pac4j.http.client.direct.CookieClient;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(Pac4jAutoConfiguration.class)
@ConditionalOnClass({ CookieClient.class, ParameterClient.class, HeaderClient.class, JwtAuthenticator.class })
@ConditionalOnProperty(prefix = Pac4UniauthProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4UniauthProperties.class, Pac4jProperties.class })
public class Pac4jUniauthConfiguration {
	
	
	
	@Bean
	public AccessTokenClient uniauthClient(Pac4jProperties pac4jProperties, Pac4UniauthProperties uniauthProperties) {
		
		AccessTokenAuthenticator authenticator = new AccessTokenAuthenticator(uniauthProperties.getLoginUrl());
		
		AccessTokenClient client = new AccessTokenClient();
		
		client.setAuthenticator(authenticator);
		// pac4jProperties.getCustomParams()
		client.setName(uniauthProperties.getClientName());
		client.setParameterName(uniauthProperties.getAuthorizationParamName());
		client.setSupportGetRequest(uniauthProperties.isSupportGetRequest());
		client.setSupportPostRequest(uniauthProperties.isSupportPostRequest());
		
		return client;
	}

}
