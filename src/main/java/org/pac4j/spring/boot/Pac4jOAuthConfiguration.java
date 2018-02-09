/*
 * Copyright (c) 2010-2020, vindell (https://github.com/vindell).
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

import org.pac4j.core.http.AjaxRequestResolver;
import org.pac4j.core.http.DefaultAjaxRequestResolver;
import org.pac4j.core.http.UrlResolver;
import org.pac4j.oauth.client.FacebookClient;
import org.pac4j.oauth.client.GenericOAuth20Client;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.client.QQClient;
import org.pac4j.oauth.client.SinaWeiboClient;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.oauth.client.WeiXinClient;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.spring.boot.ext.Pac4jRelativeUrlResolver;
import org.pac4j.spring.boot.ext.property.Pac4jOAuthGenericProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import com.github.scribejava.apis.SinaWeiboApi20;
import com.github.scribejava.core.builder.api.DefaultApi20;

@Configuration
@AutoConfigureBefore( name = {
	"org.pac4j.spring.boot.Pac4jWebFilterConfiguration"
})
@ConditionalOnWebApplication
@ConditionalOnClass({ OAuth20Client.class, DefaultApi20.class, SinaWeiboApi20.class })
@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jOAuthProperties.class, Pac4jProperties.class, ServerProperties.class })
public class Pac4jOAuthConfiguration {

	@Autowired
	private Pac4jOAuthProperties oauthProperties; 
	@Autowired
	private Pac4jProperties pac4jProperties;
	@Autowired
	private ServerProperties serverProperties;
	
	@Bean
	@ConditionalOnMissingBean
	protected AjaxRequestResolver ajaxRequestResolver() {
		return new DefaultAjaxRequestResolver();
	}
	
	@Bean
	@ConditionalOnMissingBean
	protected UrlResolver urlResolver() {
		return new Pac4jRelativeUrlResolver(serverProperties.getContextPath());
	}
	
	@Bean
    public OAuth20Configuration oauth20Configuration( UrlResolver urlResolver) {
		
		OAuth20Configuration configuration = new OAuth20Configuration();
		
		return configuration;
	}
	
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "generic", havingValue = "true")
	public GenericOAuth20Client genericOAuth20Client(OAuth20Configuration oauth20Configuration,
			AjaxRequestResolver ajaxRequestResolver,
			UrlResolver urlResolver) {
		
		List<Pac4jOAuthGenericProperties> enerics = oauthProperties.getEnerics();
		if(!CollectionUtils.isEmpty(enerics)) {
			
			for (Pac4jOAuthGenericProperties properties : enerics) {
				
				final GenericOAuth20Client oauth20Client = new GenericOAuth20Client();
			    
				oauth20Client.setAjaxRequestResolver(ajaxRequestResolver);
				oauth20Client.setAuthUrl(properties.getAuthUrl());
				oauth20Client.setCallbackUrl(properties.getProfileUrl());
				oauth20Client.setConfiguration(oauth20Configuration);
				oauth20Client.setCustomParams(properties.getCustomParams());
				oauth20Client.setIncludeClientNameInCallbackUrl(pac4jProperties.isIncludeClientNameInCallbackUrl());
				oauth20Client.setProfileAttrs(properties.getProfileAttrs());
				oauth20Client.setSecret(properties.getSecret());
				oauth20Client.setTokenUrl(properties.getTokenUrl());
				oauth20Client.setUrlResolver(urlResolver);
				
			}
			
		}
		
		return null;
		
	}
	

	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "casClient", havingValue = "true")
	public FacebookClient facebookClient() {
		
		final FacebookClient facebookClient = new FacebookClient("145278422258960", "be21409ba8f39b5dae2a7de525484da8");
	    
		return facebookClient;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "twitter", havingValue = "true")
	public TwitterClient twitterClient() {
		
		final TwitterClient twitterClient = new TwitterClient("CoxUiYwQOSFDReZYdjigBA",
		            "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");
	    
		return twitterClient;
		
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "twitter", havingValue = "true")
	public WeiXinClient weiXinClient() {
		
		final WeiXinClient weiXinClient = new WeiXinClient("CoxUiYwQOSFDReZYdjigBA",
		            "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");
	    
		return weiXinClient;
		
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "twitter", havingValue = "true")
	public SinaWeiboClient sinaWeiboClient() {
		
		final SinaWeiboClient weiboClient = new SinaWeiboClient("CoxUiYwQOSFDReZYdjigBA",
		            "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");
	    
		return weiboClient;
		
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "twitter", havingValue = "true")
	public QQClient qqClient() {
		
		final QQClient qqClient = new QQClient("CoxUiYwQOSFDReZYdjigBA",
		            "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");
	    
		return qqClient;
		
	}
	
}
