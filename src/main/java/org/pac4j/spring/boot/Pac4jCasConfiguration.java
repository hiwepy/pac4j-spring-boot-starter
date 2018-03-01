package org.pac4j.spring.boot;

import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.CommonUtils;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.direct.DirectCasClient;
import org.pac4j.cas.client.direct.DirectCasProxyClient;
import org.pac4j.cas.client.rest.CasRestBasicAuthClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.logout.CasLogoutHandler;
import org.pac4j.cas.logout.DefaultCasLogoutHandler;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.http.UrlResolver;
import org.pac4j.spring.boot.ext.Pac4jRelativeUrlResolver;
import org.pac4j.spring.boot.utils.CasClientUtils;
import org.pac4j.spring.boot.utils.CasUrlUtils;
import org.pac4j.spring.boot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AutoConfigureBefore( name = {
	"org.pac4j.spring.boot.Pac4jWebFilterConfiguration"
})
@ConditionalOnWebApplication
@ConditionalOnClass({ SingleSignOutHttpSessionListener.class, CasConfiguration.class})
@ConditionalOnProperty(prefix = Pac4jCasProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jCasProperties.class, ServerProperties.class })
public class Pac4jCasConfiguration {
	
	@Autowired
	private Pac4jCasProperties pac4jProperties;
	@Autowired
	private ServerProperties serverProperties;
	
	/**
	 * 单点登录Session监听器
	 */
	@Bean(name = "singleSignOutHttpSessionListener")
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> registration = new ServletListenerRegistrationBean<SingleSignOutHttpSessionListener>(
				new SingleSignOutHttpSessionListener());
		registration.setOrder(1);
		return registration;
	}
	
	@Bean
	@ConditionalOnMissingBean
    public CasLogoutHandler<WebContext> logoutHandler(){
		return new DefaultCasLogoutHandler<WebContext>();
	}
	
	@Bean
	@ConditionalOnMissingBean
	protected UrlResolver urlResolver() {
		return new Pac4jRelativeUrlResolver(serverProperties.getServlet().getContextPath());
	}
	
	@Bean
    public CasConfiguration casConfiguration(CasLogoutHandler<WebContext> logoutHandler, UrlResolver urlResolver) {

		// 完整的cas登录地址,比如client项目的https://passport.xxx.com/login?service=https://client.xxx.com
		String serverLoginUrl = CasUrlUtils.constructLoginRedirectUrl(pac4jProperties, serverProperties.getServlet().getContextPath(), pac4jProperties.getServerCallbackUrl());
		
		CasConfiguration configuration = new CasConfiguration(serverLoginUrl, pac4jProperties.getCasProtocol() );
		
		if(pac4jProperties.isAcceptAnyProxy() && StringUtils.hasText(pac4jProperties.getAllowedProxyChains())) {	
			configuration.setAcceptAnyProxy(pac4jProperties.isAcceptAnyProxy());
			configuration.setAllowedProxyChains(CommonUtils.createProxyList(pac4jProperties.getAllowedProxyChains()));
		}
		
		if(StringUtils.hasText(pac4jProperties.getEncoding())) {	
			configuration.setEncoding(pac4jProperties.getEncoding());
		}
		configuration.setGateway(pac4jProperties.isGateway());
		configuration.setLoginUrl(pac4jProperties.getCasServerLoginUrl());
		configuration.setLogoutHandler(logoutHandler);
		if(StringUtils.hasText(pac4jProperties.getServiceParameterName())) {	
			configuration.setPostLogoutUrlParameter(pac4jProperties.getServiceParameterName());
		}
		configuration.setPrefixUrl(pac4jProperties.getCasServerUrlPrefix());
		configuration.setProtocol(pac4jProperties.getCasProtocol());
		//configuration.setRenew(pac4jProperties.isRenew());
		configuration.setRestUrl(pac4jProperties.getCasServerRestUrl());
		configuration.setTimeTolerance(pac4jProperties.getTolerance());
		configuration.setUrlResolver(urlResolver);
		
		return configuration;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "casClient", havingValue = "true")
	public CasClient casClient(CasConfiguration configuration) {
		return CasClientUtils.casClient(configuration, pac4jProperties, serverProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "directCasClient", havingValue = "true")
	public DirectCasClient directCasClient(CasConfiguration configuration) {
		return CasClientUtils.directCasClient(configuration, pac4jProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "directCasProxyClient", havingValue = "true")
	public DirectCasProxyClient directCasProxyClient(CasConfiguration configuration) {
		return CasClientUtils.directCasProxyClient(configuration, pac4jProperties, pac4jProperties.getCasServerUrlPrefix());
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "casRestBasicAuthClient", havingValue = "true")
	public CasRestBasicAuthClient casRestBasicAuthClient(CasConfiguration configuration) {
		return CasClientUtils.casRestBasicAuthClient(configuration, pac4jProperties);
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "casRestFormClient", havingValue = "true")
	public CasRestFormClient casRestFormClient(CasConfiguration configuration) {
		return CasClientUtils.casRestFormClient(configuration, pac4jProperties);
	}
	
}
