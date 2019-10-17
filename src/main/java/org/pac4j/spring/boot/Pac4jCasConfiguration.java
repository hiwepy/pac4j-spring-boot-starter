package org.pac4j.spring.boot;

import org.jasig.cas.client.util.CommonUtils;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasProxyReceptor;
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
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AutoConfigureBefore(Pac4jClientsConfiguration.class)
@ConditionalOnClass({CasConfiguration.class})
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
	public CasProxyReceptor proxyReceptor() {
		CasProxyReceptor proxyReceptor = new CasProxyReceptor();
		return proxyReceptor;
	}
	
	@Bean
	public CasConfiguration casConfiguration(LogoutHandler<WebContext> logoutHandler, UrlResolver urlResolver,
			@Autowired(required = false) CasProxyReceptor proxyReceptor) {

		CasConfiguration configuration = new CasConfiguration(pac4jCasProperties.getLoginUrl(), pac4jCasProperties.getProtocol() );
		
		if(pac4jCasProperties.isAcceptAnyProxy() && StringUtils.hasText(pac4jCasProperties.getAllowedProxyChains())) {	
			configuration.setAcceptAnyProxy(pac4jCasProperties.isAcceptAnyProxy());
			configuration.setAllowedProxyChains(CommonUtils.createProxyList(pac4jCasProperties.getAllowedProxyChains()));
		}
		
		/**
		 * 批量设置参数
		 */
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		
		map.from(pac4jCasProperties.getEncoding()).to(configuration::setEncoding);
		map.from(pac4jCasProperties.getCustomParams()).to(configuration::setCustomParams);
		map.from(pac4jCasProperties.isGateway()).to(configuration::setGateway);
		map.from(pac4jCasProperties.getLoginUrl()).to(configuration::setLoginUrl);
		map.from(logoutHandler).to(configuration::setLogoutHandler);
		map.from(pac4jCasProperties.getMethod()).to(configuration::setMethod);
		map.from(pac4jCasProperties.getPostLogoutUrlParameter()).to(configuration::setPostLogoutUrlParameter);
		map.from(pac4jCasProperties.getPrefixUrl()).to(configuration::setPrefixUrl);
		map.from(pac4jCasProperties.getProtocol()).to(configuration::setProtocol);
		map.from(proxyReceptor).to(configuration::setProxyReceptor);
		map.from(pac4jCasProperties.isRenew()).to(configuration::setRenew);
		map.from(pac4jCasProperties.getRestUrl()).to(configuration::setRestUrl);
		map.from(pac4jCasProperties.getTolerance()).to(configuration::setTimeTolerance);
		map.from(urlResolver).to(configuration::setUrlResolver);
		
		return configuration;
	}

	/**
	 * 
	 * TODO
	 * @author 		： <a href="https://github.com/vindell">wandl</a>
	 * @param configuration
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = Pac4jClientNames.CAS_CLIENT, havingValue = "true")
	public CasClient casClient(CasConfiguration configuration) {
		return CasClientUtils.casClient(configuration, pac4jProperties, pac4jCasProperties, serverProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = Pac4jClientNames.DIRECT_CAS_CLIENT, havingValue = "true")
	public DirectCasClient directCasClient(CasConfiguration configuration) {
		return CasClientUtils.directCasClient(configuration, pac4jCasProperties);
	}
	
	@Bean 
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = Pac4jClientNames.DIRECT_CAS_PROXY_CLIENT, havingValue = "true")
	public DirectCasProxyClient directCasProxyClient(CasConfiguration configuration) {
		return CasClientUtils.directCasProxyClient(configuration, pac4jCasProperties, pac4jCasProperties.getPrefixUrl());
	}
	
	@Bean 
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = Pac4jClientNames.CAS_REST_BASIC_AUTH_CLIENT, havingValue = "true")
	public CasRestBasicAuthClient casRestBasicAuthClient(CasConfiguration configuration) {
		return CasClientUtils.casRestBasicAuthClient(configuration, pac4jCasProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = Pac4jClientNames.CAS_REST_FORM_CLIENT, havingValue = "true")
	public CasRestFormClient casRestFormClient(CasConfiguration configuration) {
		return CasClientUtils.casRestFormClient(configuration, pac4jCasProperties);
	}
	
}
