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

import org.pac4j.core.config.Config;
import org.pac4j.j2e.filter.CallbackFilter;
import org.pac4j.j2e.filter.LogoutFilter;
import org.pac4j.j2e.filter.SecurityFilter;
import org.pac4j.spring.boot.ext.Pac4jPathBuilder;
import org.pac4j.spring.boot.ext.property.Pac4jJ2eProperties;
import org.pac4j.spring.boot.ext.property.Pac4jLogoutProperties;
import org.pac4j.spring.boot.ext.property.Pac4jProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({CallbackFilter.class, SecurityFilter.class, LogoutFilter.class })
@ConditionalOnProperty(prefix = Pac4jJ2eProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jJ2eProperties.class, Pac4jLogoutProperties.class})
public class Pac4jJ2eConfiguration {

	@Autowired
	private Pac4jProperties pac4jProperties;
	@Autowired
	private Pac4jLogoutProperties logoutProperties;
	@Autowired
	private ServerProperties serverProperties;
	@Autowired
	private Pac4jPathBuilder pathBuilder;
	
	/**
	 * 账号注销过滤器 ：处理账号注销
	 */
	@Bean
	@ConditionalOnProperty(prefix = Pac4jLogoutProperties.PREFIX, value = "local-logout", havingValue = "true")
	public FilterRegistrationBean<LogoutFilter> j2eLocalLogoutFilter(Config config){
		
		FilterRegistrationBean<LogoutFilter> filterRegistration = new FilterRegistrationBean<LogoutFilter>();
		
		LogoutFilter logoutFilter = new LogoutFilter();
	    
		// Whether the centralLogout must be performed（是否注销统一身份认证）
        logoutFilter.setCentralLogout(logoutProperties.isCentralLogout());
		// Security Configuration
        logoutFilter.setConfig(config);
        // Default logourl url
        logoutFilter.setDefaultUrl( pathBuilder.getLogoutURL(serverProperties.getServlet().getContextPath()) );
        // Whether the application logout must be performed（是否注销本地应用身份认证）
        logoutFilter.setLocalLogout(logoutProperties.isLocalLogout());
        // Pattern that logout urls must match（注销登录路径规则，用于匹配登录请求操作）
        logoutFilter.setLogoutUrlPattern(logoutProperties.getLogoutUrlPattern());
        
        filterRegistration.setFilter(logoutFilter);
        
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
	    filterRegistration.addUrlPatterns("/logout");
	    
	    return filterRegistration;
	}
	
	/**
	 * 账号注销过滤器 ：处理账号注销
	 */
	@Bean
	@ConditionalOnProperty(prefix = Pac4jLogoutProperties.PREFIX, value = "central-logout", havingValue = "true")
	public FilterRegistrationBean<LogoutFilter> j2eCentralLogoutFilter(Config config){
		
		FilterRegistrationBean<LogoutFilter> filterRegistration = new FilterRegistrationBean<LogoutFilter>();
		
		LogoutFilter logoutFilter = new LogoutFilter();
	    
		// Whether the centralLogout must be performed（是否注销统一身份认证）
        logoutFilter.setCentralLogout(true);
		// Security Configuration
        logoutFilter.setConfig(config);
        // Default logourl url
        logoutFilter.setDefaultUrl( pathBuilder.getLogoutURL(serverProperties.getServlet().getContextPath()) );
        // Whether the application logout must be performed（是否注销本地应用身份认证）
        logoutFilter.setLocalLogout(logoutProperties.isLocalLogout());
        // Pattern that logout urls must match（注销登录路径规则，用于匹配登录请求操作）
        logoutFilter.setLogoutUrlPattern(logoutProperties.getLogoutUrlPattern());
        
        filterRegistration.setFilter(logoutFilter);
        
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
	    filterRegistration.addUrlPatterns("/logout");
	    
	    return filterRegistration;
	}
	
	/**
	 * 回调过滤器 ：处理登录后的回调访问
	 */
	@Bean
	public FilterRegistrationBean<CallbackFilter> j2eCallbackFilter(Config config){
		
		FilterRegistrationBean<CallbackFilter> filterRegistration = new FilterRegistrationBean<CallbackFilter>();
		
	    CallbackFilter callbackFilter = new CallbackFilter();
	    
	    // Security Configuration
        callbackFilter.setConfig(config);
        // Default url after login if none was requested（登录成功后的重定向地址，等同于shiro的successUrl）
        callbackFilter.setDefaultUrl( pathBuilder.getLoginURL(serverProperties.getServlet().getContextPath()) );
        // Whether multiple profiles should be kept
        callbackFilter.setMultiProfile(pac4jProperties.isMultiProfile());
        
        filterRegistration.setFilter(callbackFilter);
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1001);
        filterRegistration.addUrlPatterns("/callback"); 
	    
	    return filterRegistration;
	}
	
	/**
	 * 权限控制过滤器 ：实现权限认证
	 */
	@Bean
	public FilterRegistrationBean<SecurityFilter> j2eSecurityFilter(Config config){
		
		FilterRegistrationBean<SecurityFilter> filterRegistration = new FilterRegistrationBean<SecurityFilter>();
		
		SecurityFilter securityFilter = new SecurityFilter();  
		
		// List of authorizers
		securityFilter.setAuthorizers(pac4jProperties.getAuthorizers());
		// List of clients for authentication
		securityFilter.setClients(pac4jProperties.getClients());
		// Security configuration
		securityFilter.setConfig(config);
		securityFilter.setMatchers(pac4jProperties.getMatchers());
		// Whether multiple profiles should be kept
		securityFilter.setMultiProfile(pac4jProperties.isMultiProfile());
		
        filterRegistration.setFilter(securityFilter);

        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1002);
        filterRegistration.addUrlPatterns("/*");
	    
	    return filterRegistration;
	}
	
	
}
