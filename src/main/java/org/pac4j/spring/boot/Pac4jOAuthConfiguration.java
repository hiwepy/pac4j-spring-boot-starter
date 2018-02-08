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

import org.pac4j.oauth.client.FacebookClient;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.client.TwitterClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "casClient", havingValue = "true")
	public FacebookClient facebookClient() {
		
		final FacebookClient facebookClient = new FacebookClient("145278422258960", "be21409ba8f39b5dae2a7de525484da8");
	    
		return facebookClient;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "casClient", havingValue = "true")
	public TwitterClient twitterClient() {
		
		final TwitterClient twitterClient = new TwitterClient("CoxUiYwQOSFDReZYdjigBA",
		            "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");
	    
		return twitterClient;
		
	}
	
}
