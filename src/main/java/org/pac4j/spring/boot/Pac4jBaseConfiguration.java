package org.pac4j.spring.boot;

import org.pac4j.core.ext.http.callback.QueryParameterCallbackUrlExtResolver;
import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.ajax.DefaultAjaxRequestResolver;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.url.DefaultUrlResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.logout.handler.DefaultLogoutHandler;
import org.pac4j.core.logout.handler.LogoutHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://blog.csdn.net/u010004082/article/details/79744481?utm_source=blogxgwz9
@Configuration
@ConditionalOnClass({ DefaultAjaxRequestResolver.class})
@EnableConfigurationProperties({ ServerProperties.class, Pac4jProperties.class, Pac4jLogoutProperties.class })
public class Pac4jBaseConfiguration {

	@Bean
	@ConditionalOnMissingBean
    public LogoutHandler logoutHandler(Pac4jLogoutProperties logoutProperties){
		DefaultLogoutHandler logoutHandler = new DefaultLogoutHandler();
		logoutHandler.setDestroySession(logoutProperties.isDestroySession());
		return logoutHandler;
	}

	@Bean
	@ConditionalOnMissingBean
	protected AjaxRequestResolver ajaxRequestResolver() {
		return new DefaultAjaxRequestResolver();
	}

	@Bean
	@ConditionalOnMissingBean
	protected CallbackUrlResolver callbackUrlResolver(Pac4jProperties pac4jProperties) {
		return new QueryParameterCallbackUrlExtResolver(pac4jProperties.isCallbackUrlFixed(),
				pac4jProperties.getCallbackUrl(),
				pac4jProperties.getCustomParams());
	}

	@Bean
	@ConditionalOnMissingBean
	protected UrlResolver urlResolver(Pac4jProperties pac4jProperties) {
		return new DefaultUrlResolver(pac4jProperties.isCompleteRelativeUrl());
	}

}
