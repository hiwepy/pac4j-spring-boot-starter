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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.pac4j.config.client.PropertiesConfigFactory;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.spring.boot.ext.property.Pac4jProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

@Configuration
@AutoConfigureBefore( name = {
	"org.pac4j.spring.boot.Pac4jWebFilterConfiguration"
})
@ConditionalOnWebApplication
@ConditionalOnClass({ Clients.class })
@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jProperties.class, ServerProperties.class })
@SuppressWarnings("rawtypes")
public class Pac4jClientsConfiguration  implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;

	@Autowired
	private Pac4jProperties pac4jProperties;
	
	@Bean
	public Clients clients(@Autowired(required = false) @Qualifier("defaultClient") Client defaultClient,
			@Autowired(required = false) @Qualifier("oauth20Clients") List<Client> oauth20Clients,
			AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final List<Client> clientList = new ArrayList<Client>();
		Map<String, Client> beansOfType = getApplicationContext().getBeansOfType(Client.class);
		if (!ObjectUtils.isEmpty(beansOfType)) {
			Iterator<Entry<String, Client>> ite = beansOfType.entrySet().iterator();
			while (ite.hasNext()) {
				clientList.add(ite.next().getValue());
			}
		}
		
		if(oauth20Clients != null) {
			clientList.addAll(oauth20Clients);
		}
		
		final Clients clients = new Clients(pac4jProperties.getCallbackUrl(), clientList);
		
		clients.setAjaxRequestResolver(ajaxRequestResolver);
		clients.setCallbackUrl(pac4jProperties.getCallbackUrl());
		clients.setClients(clientList);
		/*clients.setClientNameParameter(pac4jProperties.getClientParameterName());
		if(defaultClient != null) {
			clients.setDefaultClient(defaultClient);
		}*/
		clients.setUrlResolver(urlResolver);
		
		return clients;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}

