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
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.pac4j.config.client.PropertiesConfigFactory;
import org.pac4j.core.authorization.authorizer.CheckHttpMethodAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.HttpConstants.HTTP_METHOD;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.session.J2ESessionStore;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.http.adapter.J2ENopHttpActionAdapter;
import org.pac4j.http.authorization.authorizer.IpRegexpAuthorizer;
import org.pac4j.j2e.filter.CallbackFilter;
import org.pac4j.j2e.filter.LogoutFilter;
import org.pac4j.j2e.filter.SecurityFilter;
import org.pac4j.spring.boot.ext.Pac4jPathBuilder;
import org.pac4j.spring.boot.ext.property.Pac4jProperties;
import org.pac4j.spring.boot.utils.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({CallbackFilter.class, SecurityFilter.class, LogoutFilter.class })
@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jProperties.class, ServerProperties.class })
public class Pac4jAutoConfiguration implements WebMvcConfigurer {

	@Bean
	protected Pac4jPathBuilder pac4jPathBuilder() {
		return new Pac4jPathBuilder();
	}
	
	@Bean
	@ConditionalOnMissingBean
	protected SessionStore<J2EContext> sessionStore() {
		return new J2ESessionStore();
	}
	
	@Bean
	@ConditionalOnMissingBean
	protected HttpActionAdapter<Object, J2EContext> httpActionAdapter() {
		return J2ENopHttpActionAdapter.INSTANCE;
	}
	
	@Bean
	public Config config(Pac4jProperties pac4jProperties, Clients clients, 
			HttpActionAdapter<Object, J2EContext> httpActionAdapter,SessionStore<J2EContext> sessionStore) {
		

		PropertiesConfigFactory configFactory = new PropertiesConfigFactory(pac4jProperties.getCallbackUrl(), pac4jProperties.getClientsProperties());
		
		final Config config = configFactory.build();
		
		config.getClients().getClients().addAll(clients.getClients());
		
		//final Config config = new Config(clients);
		if(StringUtils.hasText(pac4jProperties.getAllowedIpRegexpPattern())) {	
			config.addAuthorizer("isIPAuthenticated", new IpRegexpAuthorizer(pac4jProperties.getAllowedIpRegexpPattern()));
		}
		if(ArrayUtils.isNotEmpty(pac4jProperties.getAllowedHttpMethods())) {	
			String[] allowedHttpMethods = pac4jProperties.getAllowedHttpMethods();
			List<HTTP_METHOD> methods = new ArrayList<HTTP_METHOD>();
			for (String method : allowedHttpMethods) {
				methods.add(HTTP_METHOD.valueOf(method));
			}
			config.addAuthorizer("isMethodAuthenticated", new CheckHttpMethodAuthorizer(methods));
		}
		
		/*excludePath
		excludeRegex
		excludeBranch
		
		[] methods
		private String headerName;
	    private String expectedValue;*/
	    
	    
		//config.addMatcher("path", new AntPathMatcher().excludePath("").excludeBranch("").excludeRegex(""));
		//config.addMatcher("header", new HeaderMatcher());
		//config.addMatcher("method", new HttpMethodMatcher());
		
		config.setClients(clients);
		config.setHttpActionAdapter(httpActionAdapter);
		config.setSessionStore(sessionStore);
		
		return config;
	}
	
}