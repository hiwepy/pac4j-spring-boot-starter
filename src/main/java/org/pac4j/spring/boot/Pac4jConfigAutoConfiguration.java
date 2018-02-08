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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.authorization.authorizer.CheckHttpMethodAuthorizer;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.HttpConstants.HTTP_METHOD;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.session.J2ESessionStore;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.http.AjaxRequestResolver;
import org.pac4j.core.http.DefaultAjaxRequestResolver;
import org.pac4j.core.http.HttpActionAdapter;
import org.pac4j.core.http.J2ENopHttpActionAdapter;
import org.pac4j.core.http.UrlResolver;
import org.pac4j.demo.spring.CustomAuthorizer;
import org.pac4j.http.authorization.authorizer.IpRegexpAuthorizer;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.client.indirect.IndirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.oauth.client.FacebookClient;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.oidc.client.GoogleOidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.client.SAML2ClientConfiguration;
import org.pac4j.spring.boot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.nimbusds.jose.JWSAlgorithm;

@Configuration
@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jProperties.class, ServerProperties.class })
@SuppressWarnings("rawtypes")
public class Pac4jConfigAutoConfiguration {

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
	protected SessionStore<J2EContext> sessionStore() {
		return new J2ESessionStore();
	}
	
	@Bean
	@ConditionalOnMissingBean
	protected HttpActionAdapter<Object, J2EContext> httpActionAdapter() {
		return J2ENopHttpActionAdapter.INSTANCE;
	}
	
	@Bean
	public Config config(@Autowired(required = false) @Qualifier("defaultClient") Client defaultClient,
			List<Client> clientList, AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver,
			HttpActionAdapter<Object, J2EContext> httpActionAdapter,SessionStore<J2EContext> sessionStore) {

		final Clients clients = new Clients();
		
		clients.setAjaxRequestResolver(ajaxRequestResolver);
		clients.setCallbackUrl(pac4jProperties.getCallbackUrl());
		clients.setClients(clientList);
		clients.setClientNameParameter(pac4jProperties.getClientParameterName());
		if(defaultClient != null) {
			clients.setDefaultClient(defaultClient);
		}
		clients.setUrlResolver(urlResolver);
		
		final Config config = new Config(clients);
		
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
	
	@Bean
    public Config config(List<Client> clientList, AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver,
			HttpActionAdapter<Object, J2EContext> httpActionAdapter,SessionStore<J2EContext> sessionStore) {
		
		
		
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

        final OidcConfiguration oidcConfiguration = new OidcConfiguration();
        oidcConfiguration.setClientId("167480702619-8e1lo80dnu8bpk3k0lvvj27noin97vu9.apps.googleusercontent.com");
        oidcConfiguration.setSecret("MhMme_Ik6IH2JMnAT6MFIfee");
        oidcConfiguration.setPreferredJwsAlgorithm(JWSAlgorithm.PS384);
        oidcConfiguration.addCustomParam("prompt", "consent");
        
        final GoogleOidcClient oidcClient = new GoogleOidcClient(oidcConfiguration);
        oidcClient.setAuthorizationGenerator((ctx, profile) -> { profile.addRole("ROLE_ADMIN"); return profile; });

        final SAML2ClientConfiguration cfg = new SAML2ClientConfiguration(new ClassPathResource("samlKeystore.jks"),
                "pac4j-demo-passwd",
                "pac4j-demo-passwd",
                new ClassPathResource("metadata-okta.xml"));
        cfg.setMaximumAuthenticationLifetime(3600);
        cfg.setServiceProviderEntityId("http://localhost:8080/callback?client_name=SAML2Client");
        cfg.setServiceProviderMetadataResource(new FileSystemResource(new File("sp-metadata.xml").getAbsoluteFile()));
        final SAML2Client saml2Client = new SAML2Client(cfg);

        final FacebookClient facebookClient = new FacebookClient("145278422258960", "be21409ba8f39b5dae2a7de525484da8");
        final TwitterClient twitterClient = new TwitterClient("CoxUiYwQOSFDReZYdjigBA",
                "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");
        // HTTP
        final FormClient formClient = new FormClient("http://localhost:8080/loginForm.jsp", new SimpleTestUsernamePasswordAuthenticator());
        final IndirectBasicAuthClient indirectBasicAuthClient = new IndirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator());

        // CAS
        final CasConfiguration configuration = new CasConfiguration("https://casserverpac4j.herokuapp.com/login");
        final CasClient casClient = new CasClient(configuration);
        // casClient.setGateway(true);

        // REST authent with JWT for a token passed in the url as the token parameter
        final SecretSignatureConfiguration secretSignatureConfiguration = new SecretSignatureConfiguration(salt);
        final SecretEncryptionConfiguration secretEncryptionConfiguration = new SecretEncryptionConfiguration(salt);
        final JwtAuthenticator authenticator = new JwtAuthenticator();
        authenticator.setSignatureConfiguration(secretSignatureConfiguration);
        authenticator.setEncryptionConfiguration(secretEncryptionConfiguration);
        ParameterClient parameterClient = new ParameterClient("token", authenticator);
        parameterClient.setSupportGetRequest(true);
        parameterClient.setSupportPostRequest(false);

        // basic auth
        final DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator());

        final Clients clients = new Clients("http://localhost:8080/callback", oidcClient, saml2Client, facebookClient,
                twitterClient, formClient, indirectBasicAuthClient, casClient, parameterClient, directBasicAuthClient);

        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        config.addAuthorizer("custom", new CustomAuthorizer());
        
        
        final Clients clients = new Clients();
        
		clients.setAjaxRequestResolver(ajaxRequestResolver);
		clients.setCallbackUrl(pac4jProperties.getServerCallbackUrl());
		clients.setClients(clientList);
		clients.setClientNameParameter(pac4jProperties.getClientParameterName());
		clients.setDefaultClient(casClient);
		clients.setUrlResolver(urlResolver);
		
		final Config config = new Config(clients);
		
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
