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

	public static String constructCallbackUrl(String contextPath, String serverUrl) {
		contextPath = StringUtils.hasText(contextPath) ? contextPath : "/";
		if (contextPath.endsWith("/")) {
			contextPath = contextPath.substring(0, contextPath.length() - 1);
		}
		StringBuilder callbackUrlBuilder = new StringBuilder(contextPath).append(serverUrl);
		return callbackUrlBuilder.toString();
	}
	
	public static String constructCallbackUrl(Pac4jCasProperties casProperties) {
		String callbackUrl = casProperties.getServerCallbackUrl();
		StringBuilder callbackUrlBuilder = new StringBuilder(callbackUrl).append((callbackUrl.contains("?") ? "&" : "?")).append(casProperties.getClientParameterName()).append("=").append(casProperties.getClientName());
		return callbackUrlBuilder.toString();
	}
	
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
	
	public static String constructRedirectUrl(Pac4jCasProperties casProperties, String casServerPath, String contextPath, String serverUrl)  {

		StringBuilder casRedirectUrl = new StringBuilder(casProperties.getCasServerUrlPrefix());
		if (!casRedirectUrl.toString().endsWith("/")) {
			casRedirectUrl.append("/");
		}
		casRedirectUrl.append(casServerPath);
		
		String callbackUrl = CasUrlUtils.constructCallbackUrl(casProperties, contextPath, serverUrl);
		
		return CommonUtils.constructRedirectUrl(casRedirectUrl.toString(), casProperties.getServiceParameterName(), callbackUrl, casProperties.isRenew(), casProperties.isGateway());
		
	}
	
	public static String constructLogoutRedirectUrl(Pac4jCasProperties casProperties, String contextPath, String serverUrl){
		String callbackUrl = CasUrlUtils.constructCallbackUrl(casProperties, contextPath, serverUrl);
		return CommonUtils.constructRedirectUrl(casProperties.getCasServerLogoutUrl(), casProperties.getServiceParameterName(), callbackUrl, casProperties.isRenew(), casProperties.isGateway());
	}
	
	public static String constructLoginRedirectUrl(Pac4jCasProperties casProperties, String contextPath, String serverUrl){
		String callbackUrl = CasUrlUtils.constructCallbackUrl(casProperties, contextPath, serverUrl);
		return CommonUtils.constructRedirectUrl(casProperties.getCasServerLoginUrl(), casProperties.getServiceParameterName(), callbackUrl, casProperties.isRenew(), casProperties.isGateway());
	}
	
	public static String constructServiceUrl(ServletRequest request, ServletResponse response, Pac4jCasProperties casProperties) {
		 
		return CommonUtils.constructServiceUrl(WebUtils.getNativeRequest(request, HttpServletRequest.class),
				WebUtils.getNativeResponse(response, HttpServletResponse.class), casProperties.getServerName(),
				casProperties.getServerName(), casProperties.getServiceParameterName(),
				casProperties.getArtifactParameterName(), casProperties.isEncodeServiceUrl());
		
	}
	
}
