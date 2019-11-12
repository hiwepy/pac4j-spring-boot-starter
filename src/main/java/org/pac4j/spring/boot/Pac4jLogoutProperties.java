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

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(Pac4jLogoutProperties.PREFIX)
@Getter
@Setter
@ToString
public class Pac4jLogoutProperties {

	public static final String PREFIX = "pac4j.logout";
	
	/** Pattern that logout urls must match（注销登录路径规则，用于匹配登录请求操作） */
	private String pathPattern = "/**/logout/pac4j";
	/** Whether the application logout must be performed（是否注销本地应用身份认证）. 默认 true */
	private boolean localLogout = true;
	/** Whether the centralLogout must be performed（是否注销统一身份认证）. 默认 true */
	private boolean centralLogout = true;
	/** Whether the Session must be destroyed（是否销毁Session）. 默认 true */
	private boolean destroySession = true;
	/** 
	 * The location of the default access URL, i.e. https://localhost:8080/myapp/index.html 
	 */
	private String defaultUrl;
}
