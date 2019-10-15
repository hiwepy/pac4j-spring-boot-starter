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

import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ Clients.class })
@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jProperties.class, ServerProperties.class })
@SuppressWarnings("rawtypes")
public class Pac4jClientsConfiguration {

	@Bean
	public Clients clients (
			Pac4jProperties pac4jProperties,
			List<Client> clientList,
			List<AuthorizationGenerator> authorizationGenerators,
			AjaxRequestResolver ajaxRequestResolver,
			CallbackUrlResolver callbackUrlResolver,
			UrlResolver urlResolver) {
		
		Clients clients = new Clients(pac4jProperties.getCallbackUrl(), clientList);
		
		clients.setAjaxRequestResolver(ajaxRequestResolver);
		clients.setAuthorizationGenerators(authorizationGenerators);
		clients.setCallbackUrl(pac4jProperties.getCallbackUrl());
		clients.setCallbackUrlResolver(callbackUrlResolver);
		clients.setClients(clientList);
		clients.setDefaultSecurityClients(pac4jProperties.getClients());
		clients.setUrlResolver(urlResolver);
		
		return clients;
	}
	
}

