package org.pac4j.spring.boot;

import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.CommonUtils;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.direct.DirectCasClient;
import org.pac4j.cas.client.direct.DirectCasProxyClient;
import org.pac4j.cas.client.rest.CasRestBasicAuthClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.logout.handler.LogoutHandler;
import org.pac4j.spring.boot.utils.CasClientUtils;
import org.pac4j.spring.boot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass({ SingleSignOutHttpSessionListener.class, CasConfiguration.class})
@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jCasProperties.class, Pac4jProperties.class, ServerProperties.class })
public class Pac4jCasConfiguration {
	
	@Autowired
	private Pac4jProperties pac4jProperties;
	@Autowired
	private Pac4jCasProperties pac4jCasProperties;
	@Autowired
	private ServerProperties serverProperties;
	
	/**
	 * 单点登录Session监听器
	@Bean(name = "singleSignOutHttpSessionListener")
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> registration = new ServletListenerRegistrationBean<SingleSignOutHttpSessionListener>(
				new SingleSignOutHttpSessionListener());
		registration.setOrder(1);
		return registration;
	}	 */
	
	@Bean
    public CasConfiguration casConfiguration(LogoutHandler<WebContext> logoutHandler, UrlResolver urlResolver) {

		CasConfiguration configuration = new CasConfiguration(pac4jCasProperties.getCasServerLoginUrl(), pac4jCasProperties.getCasProtocol() );
		
		if(pac4jCasProperties.isAcceptAnyProxy() && StringUtils.hasText(pac4jCasProperties.getAllowedProxyChains())) {	
			configuration.setAcceptAnyProxy(pac4jCasProperties.isAcceptAnyProxy());
			configuration.setAllowedProxyChains(CommonUtils.createProxyList(pac4jCasProperties.getAllowedProxyChains()));
		}
		
		if(StringUtils.hasText(pac4jCasProperties.getEncoding())) {	
			configuration.setEncoding(pac4jCasProperties.getEncoding());
		}
		configuration.setGateway(pac4jCasProperties.isGateway());
		configuration.setLoginUrl(pac4jCasProperties.getCasServerLoginUrl());
		configuration.setLogoutHandler(logoutHandler);
		if(StringUtils.hasText(pac4jCasProperties.getServiceParameterName())) {	
			configuration.setPostLogoutUrlParameter(pac4jCasProperties.getServiceParameterName());
		}
		configuration.setPrefixUrl(pac4jCasProperties.getCasServerUrlPrefix());
		configuration.setProtocol(pac4jCasProperties.getCasProtocol());
		//configuration.setRenew(pac4jProperties.isRenew());
		configuration.setRestUrl(pac4jCasProperties.getCasServerRestUrl());
		configuration.setTimeTolerance(pac4jCasProperties.getTolerance());
		configuration.setUrlResolver(urlResolver);
		
		return configuration;
	}

	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = "cas-client", havingValue = "true")
	public CasClient casClient(CasConfiguration configuration) {
		return CasClientUtils.casClient(configuration, pac4jProperties, pac4jCasProperties, serverProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = "direct-cas-client", havingValue = "true")
	public DirectCasClient directCasClient(CasConfiguration configuration) {
		return CasClientUtils.directCasClient(configuration, pac4jCasProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = "direct-cas-proxy-client", havingValue = "true")
	public DirectCasProxyClient directCasProxyClient(CasConfiguration configuration) {
		return CasClientUtils.directCasProxyClient(configuration, pac4jCasProperties, pac4jCasProperties.getCasServerUrlPrefix());
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = "cas-rest-basic-auth-client", havingValue = "true")
	public CasRestBasicAuthClient casRestBasicAuthClient(CasConfiguration configuration) {
		return CasClientUtils.casRestBasicAuthClient(configuration, pac4jCasProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = "cas-rest-form-client", havingValue = "true")
	public CasRestFormClient casRestFormClient(CasConfiguration configuration) {
		return CasClientUtils.casRestFormClient(configuration, pac4jCasProperties);
	}
	
}
