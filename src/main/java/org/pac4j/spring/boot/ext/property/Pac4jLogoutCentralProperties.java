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

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(Pac4jLogoutCentralProperties.PREFIX)
public class Pac4jLogoutCentralProperties {

	public static final String PREFIX = "pac4j.logout.local";
	
	/** 注销后跳转的地址 */
	private String defaultUrl;
	/** Pattern that logout urls must match（注销登录路径规则，用于匹配登录请求操作） */
	private String logoutUrlPattern;
	/** Whether the application logout must be performed（是否注销本地应用身份认证）. 默认 true */
	private boolean localLogout = true;
	/** Whether the centralLogout must be performed（是否注销统一身份认证）. 默认 true */
	private boolean centralLogout = true;
	/** Whether the Session must be destroyed（是否销毁Session）. 默认 true */
	private boolean destroySession = true;

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public String getLogoutUrlPattern() {
		return logoutUrlPattern;
	}

	public void setLogoutUrlPattern(String logoutUrlPattern) {
		this.logoutUrlPattern = logoutUrlPattern;
	}

	public boolean isLocalLogout() {
		return localLogout;
	}

	public void setLocalLogout(boolean localLogout) {
		this.localLogout = localLogout;
	}

	public boolean isCentralLogout() {
		return centralLogout;
	}

	public void setCentralLogout(boolean centralLogout) {
		this.centralLogout = centralLogout;
	}

	public boolean isDestroySession() {
		return destroySession;
	}

	public void setDestroySession(boolean destroySession) {
		this.destroySession = destroySession;
	}

}
