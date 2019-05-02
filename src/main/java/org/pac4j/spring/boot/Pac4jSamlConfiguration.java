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

import java.io.File;

import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.config.SAML2Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@AutoConfigureBefore( name = {
	"org.pac4j.spring.boot.Pac4jWebFilterConfiguration"
})
@ConditionalOnWebApplication
@ConditionalOnClass({ SAML2Configuration.class, SAML2Client.class})
@ConditionalOnProperty(prefix = Pac4jSamlProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jSamlProperties.class, Pac4jProperties.class, ServerProperties.class })
public class Pac4jSamlConfiguration {

	@Bean
    public SAML2Configuration samlConfiguration() {
		
		final SAML2Configuration configuration = new SAML2Configuration(new ClassPathResource("samlKeystore.jks"),
                "pac4j-demo-passwd",
                "pac4j-demo-passwd",
                new ClassPathResource("metadata-okta.xml"));
        configuration.setMaximumAuthenticationLifetime(3600);
        configuration.setServiceProviderEntityId("http://localhost:8080/callback?client_name=SAML2Client");
        configuration.setServiceProviderMetadataResource(new FileSystemResource(new File("sp-metadata.xml").getAbsoluteFile()));
	    
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
	@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "saml2-client", havingValue = "true")
	public SAML2Client saml2Client(SAML2Configuration samlConfiguration, UrlResolver urlResolver) {
		
	    final SAML2Client saml2Client = new SAML2Client(samlConfiguration);
	    
	    /*saml2Client.setAjaxRequestResolver(ajaxRequestResolver);
	    saml2Client.setAuthenticator(authenticator);
	    saml2Client.setAuthorizationGenerator(authorizationGenerator);
	    saml2Client.setAuthorizationGenerators(authorizationGenerators);
	    saml2Client.setAuthorizationGenerators(authorizationGenerators);
	    saml2Client.setCallbackUrl(callbackUrl);
	    saml2Client.setConfiguration(configuration);
	    saml2Client.setCredentialsExtractor(credentialsExtractor);
	    saml2Client.setIncludeClientNameInCallbackUrl(includeClientNameInCallbackUrl);
	    saml2Client.setLogoutActionBuilder(logoutActionBuilder);
	    saml2Client.setProfileCreator(profileCreator);
	    saml2Client.setRedirectActionBuilder(redirectActionBuilder);*/
	    saml2Client.setUrlResolver(urlResolver);
	    
		return saml2Client;
	}
	
    
    
    
}
