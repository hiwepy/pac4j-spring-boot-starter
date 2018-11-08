package org.pac4j.spring.boot;

import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.ajax.DefaultAjaxRequestResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.spring.boot.ext.Pac4jRelativeUrlResolver;
import org.pac4j.spring.boot.ext.property.Pac4jProperties;
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

// https://blog.csdn.net/u010004082/article/details/79744481?utm_source=blogxgwz9
@Configuration
@AutoConfigureBefore( name = {
	"org.pac4j.spring.boot.Pac4jWebFilterConfiguration"
})
@ConditionalOnWebApplication
@ConditionalOnClass({ SingleSignOutHttpSessionListener.class, CasConfiguration.class})
@ConditionalOnProperty(prefix = Pac4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ ServerProperties.class, Pac4jProperties.class })
public class Pac4jBaseConfiguration {
	
	@Autowired
	private ServerProperties serverProperties;
	@Autowired
	private Pac4jProperties pac4jProperties;
	
	@Bean
	@ConditionalOnMissingBean
	protected AjaxRequestResolver ajaxRequestResolver() {
		return new DefaultAjaxRequestResolver();
	}
	
	@Bean
	@ConditionalOnMissingBean
	protected UrlResolver urlResolver() {
		return new Pac4jRelativeUrlResolver(serverProperties.getServlet().getContextPath());
	}
	
}
