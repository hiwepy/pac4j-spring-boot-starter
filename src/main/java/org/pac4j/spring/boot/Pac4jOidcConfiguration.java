/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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

import java.util.Optional;

import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.logout.handler.LogoutHandler;
import org.pac4j.oidc.client.GoogleOidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.JWSAlgorithm;

@Configuration
@AutoConfigureBefore(Pac4jAutoConfiguration.class)
@ConditionalOnClass({ OidcConfiguration.class, GoogleOidcClient.class })
@ConditionalOnProperty(prefix = Pac4jOidcProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jOidcProperties.class, Pac4jProperties.class, ServerProperties.class })
public class Pac4jOidcConfiguration {

	@Bean
    public OidcConfiguration oidcConfiguration(LogoutHandler logoutHandler, UrlResolver urlResolver) {
		
		/*final OidcConfiguration oidcConfiguration = new OidcConfiguration();
	    oidcConfiguration.setClientId("test");
	    oidcConfiguration.setSecret("secret");
	    oidcConfiguration.setUseNonce(true);
	    oidcConfiguration.setDiscoveryURI("http://localhost:5000/.well-known/openid-configuration");
	    oidcConfiguration.setScope("openid api1");
	    oidcConfiguration.setClientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
	    oidcConfiguration.addCustomParam("prompt", "consent");
	    final OidcClient oidcClient = new OidcClient(oidcConfiguration);
	    oidcClient.setName("test");
	    oidcClient.setCallbackUrl("http://localhost:8080/callback");*/

	    final OidcConfiguration configuration = new OidcConfiguration();
	    configuration.setClientId("167480702619-8e1lo80dnu8bpk3k0lvvj27noin97vu9.apps.googleusercontent.com");
	    configuration.setSecret("MhMme_Ik6IH2JMnAT6MFIfee");
	    configuration.setPreferredJwsAlgorithm(JWSAlgorithm.PS384);
	    configuration.addCustomParam("prompt", "consent");
	    
	    /*configuration.setCallbackUrl(callbackUrl);
	    configuration.setClientAuthenticationMethod(clientAuthenticationMethod);
	    configuration.setClientAuthenticationMethodAsString(auth);
	    configuration.setClientId(clientId);
	    configuration.setConnectTimeout(connectTimeout);
	    configuration.setCustomParams(customParams);
	    configuration.setDiscoveryURI(discoveryURI);
	    configuration.setLogoutUrl(logoutUrl);
	    configuration.setMaxClockSkew(maxClockSkew);
	    configuration.setPreferredJwsAlgorithm(preferredJwsAlgorithm);
	    configuration.setProviderMetadata(providerMetadata);
	    configuration.setReadTimeout(readTimeout);
	    configuration.setResourceRetriever(resourceRetriever);
	    configuration.setResponseMode(responseMode);
	    configuration.setResponseType(responseType);
	    configuration.setScope(scope);
	    configuration.setSecret(secret);
	    configuration.setUseNonce(useNonce);*/
		
		return configuration;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "casClient", havingValue = "true")
	public GoogleOidcClient oidcClient(OidcConfiguration oidcConfiguration) {
		
		final GoogleOidcClient oidcClient = new GoogleOidcClient(oidcConfiguration);
	    oidcClient.setAuthorizationGenerator((ctx, sessionStore, profile) -> { 
	    	profile.addRole("ROLE_ADMIN"); 
	    	return Optional.ofNullable(profile); 
	    });
	    
		return oidcClient;
	}
	
    
    
    
}
