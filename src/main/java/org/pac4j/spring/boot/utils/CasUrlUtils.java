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
package org.pac4j.spring.boot.utils;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.CommonUtils;
import org.pac4j.spring.boot.Pac4jCasProperties;
import org.springframework.web.util.WebUtils;

public class CasUrlUtils {

	/**
	 * @param casProperties : Cas 服务端配置参数
	 * @param casServerPath : Cas 服务端地址，如 login、logout 等
	 * @param contextPath	: 本地应用名称
	 * @param serverUrl		: 回调地址
	 * @return
	 */
	public static String constructRedirectUrl(Pac4jCasProperties casProperties, String casServerPath, String contextPath, String serverUrl)  {

		StringBuilder casRedirectUrl = new StringBuilder(casProperties.getCasServerUrlPrefix());
		if (!casRedirectUrl.toString().endsWith("/")) {
			casRedirectUrl.append("/");
		}
		casRedirectUrl.append(casServerPath);
		// 构造完整的回调URL,i.e. https://localhost:8080/myapp/callback?client_name=cas
		String callbackUrl = CasUrlUtils.constructCallbackUrl(casProperties, contextPath, serverUrl);
		
		return CommonUtils.constructRedirectUrl(casRedirectUrl.toString(), casProperties.getServiceParameterName(), callbackUrl, casProperties.isRenew(), casProperties.isGateway());
		
	}
	
	/**
	 * 
	 * 构造完整的Cas注销URL,比如client项目的 https://localhost:8443/cas/logout?service=https://localhost:8080/myapp/callback?client_name=cas
	 * @param casProperties : Cas 服务端配置参数
	 * @param contextPath	: 本地应用名称
	 * @param serverUrl		: 回调地址
	 * @return
	 */
	public static String constructLogoutRedirectUrl(Pac4jCasProperties casProperties, String contextPath, String serverUrl){
		// 构造完整的回调URL,i.e. https://localhost:8080/myapp/callback?client_name=cas
		String callbackUrl = CasUrlUtils.constructCallbackUrl(casProperties, contextPath, serverUrl);
		// 使用 casServerLogoutUrl 构造完整的Cas登录URL
		return CommonUtils.constructRedirectUrl(casProperties.getCasServerLogoutUrl(), casProperties.getServiceParameterName(), callbackUrl, casProperties.isRenew(), casProperties.isGateway());
	}
	
	/**
	 * 
	 * 构造完整的Cas登录URL,i.e. https://localhost:8443/cas/login?service=https://localhost:8080/myapp/callback?client_name=cas
	 * @param casProperties : Cas 服务端配置参数
	 * @param contextPath	: 本地应用名称
	 * @param serverUrl		: 回调地址
	 * @return
	 */
	public static String constructLoginRedirectUrl(Pac4jCasProperties casProperties, String contextPath, String serverUrl){
		// 构造完整的回调URL,i.e. https://localhost:8080/myapp/callback?client_name=cas
		String callbackUrl = CasUrlUtils.constructCallbackUrl(casProperties, contextPath, serverUrl);
		// 使用 casServerLoginUrl 构造完整的Cas登录URL
		return CommonUtils.constructRedirectUrl(casProperties.getCasServerLoginUrl(), casProperties.getServiceParameterName(), callbackUrl, casProperties.isRenew(), casProperties.isGateway());
	}
	
	/**
	 * 
	 * 完整的回调URL,i.e. https://localhost:8080/myapp/callback?client_name=cas
	 * @param casProperties : Cas 服务端配置参数
	 * @param contextPath	: 本地应用名称
	 * @param serverUrl		: 回调地址
	 * @return
	 */
	public static String constructCallbackUrl(Pac4jCasProperties casProperties, String contextPath, String serverUrl) {

		contextPath = StringUtils.hasText(contextPath) ? contextPath : "/";
		if (contextPath.endsWith("/")) {
			contextPath = contextPath.substring(0, contextPath.length() - 1);
		}
		
		try {
			
			URL url = new URL(casProperties.getServerName());
			
			// 重定向地址：用于重新回到业务系统
			StringBuilder callbackUrl = new StringBuilder(url.getProtocol()).append("://").append(url.getHost())
					.append( url.getPort() != -1 ? ":" + url.getPort() : "").append(contextPath).append(serverUrl);

			return callbackUrl.toString();
			
		} catch (MalformedURLException e) {
			// 重定向地址：用于重新回到业务系统
			StringBuilder callbackUrl = new StringBuilder(casProperties.getServerName()).append(contextPath).append(serverUrl);
			return callbackUrl.toString();
		}

	}
	
	/**
	 * 
	 * 根据当前请求构造回调地址
	 * @param request		: ServletRequest
	 * @param response		: ServletResponse
	 * @param casProperties : Cas 服务端配置参数
	 * @return
	 */
	public static String constructCallbackUrl(ServletRequest request, ServletResponse response, Pac4jCasProperties casProperties) {
		return CommonUtils.constructServiceUrl(WebUtils.getNativeRequest(request, HttpServletRequest.class),
				WebUtils.getNativeResponse(response, HttpServletResponse.class), casProperties.getServerName(),
				casProperties.getServerName(), casProperties.getServiceParameterName(),
				casProperties.getArtifactParameterName(), casProperties.isEncodeServiceUrl());
	}
	
}
