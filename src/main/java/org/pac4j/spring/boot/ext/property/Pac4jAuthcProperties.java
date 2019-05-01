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
package org.pac4j.spring.boot.ext.property;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.spring.boot.ext.Pac4jExtConstants;

public class Pac4jAuthcProperties {

	/** 登录地址：会话不存在时访问的地址 */
	private String loginUrl = "/login";
	private String loginUrlPatterns = "/login";
	/** 重定向地址：会话注销后的重定向地址 */
	private String redirectUrl = "/";
	/** 系统主页：登录成功后跳转路径 */
	private String successUrl = "/index";;
	/** 未授权页面：无权限时的跳转路径 */
	private String unauthorizedUrl = "/error";
	/** 异常页面：认证失败时的跳转路径 */
	private String failureUrl = "/error";

    /** The Name of Authc Client. */
	private String clientName = "jwt-authc";
	   
	/** the username parameter name. Defaults to "username". */
	private String usernameParameterName = Pac4jConstants.USERNAME;
	/** the password parameter name. Defaults to "password". */
	private String passwordParameterName = Pac4jConstants.PASSWORD;
	private String callbackUrl;
	 
	private boolean postOnly = true;

    private String retryTimesKeyParameter = Pac4jExtConstants.RETRY_TIMES_KEY_PARAM_NAME;
    private String retryTimesKeyAttribute = Pac4jExtConstants.RETRY_TIMES_KEY_ATTRIBUTE_NAME;
	/** Maximum number of retry to login . */
	private int retryTimesWhenAccessDenied = 3;
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginUrlPatterns() {
		return loginUrlPatterns;
	}

	public void setLoginUrlPatterns(String loginUrlPatterns) {
		this.loginUrlPatterns = loginUrlPatterns;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUsernameParameterName() {
		return usernameParameterName;
	}

	public void setUsernameParameterName(String usernameParameterName) {
		this.usernameParameterName = usernameParameterName;
	}

	public String getPasswordParameterName() {
		return passwordParameterName;
	}

	public void setPasswordParameterName(String passwordParameterName) {
		this.passwordParameterName = passwordParameterName;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public boolean isPostOnly() {
		return postOnly;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
	
	public String getRetryTimesKeyParameter() {
		return retryTimesKeyParameter;
	}

	public void setRetryTimesKeyParameter(String retryTimesKeyParameter) {
		this.retryTimesKeyParameter = retryTimesKeyParameter;
	}
	
	public String getRetryTimesKeyAttribute() {
		return retryTimesKeyAttribute;
	}

	public void setRetryTimesKeyAttribute(String retryTimesKeyAttribute) {
		this.retryTimesKeyAttribute = retryTimesKeyAttribute;
	}

	public int getRetryTimesWhenAccessDenied() {
		return retryTimesWhenAccessDenied;
	}

	public void setRetryTimesWhenAccessDenied(int retryTimesWhenAccessDenied) {
		this.retryTimesWhenAccessDenied = retryTimesWhenAccessDenied;
	}

}
