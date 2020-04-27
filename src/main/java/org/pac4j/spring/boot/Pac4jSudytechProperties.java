/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
@ConfigurationProperties(Pac4jSudytechProperties.PREFIX)
@Getter
@Setter
@ToString
public class Pac4jSudytechProperties {

	public static final String PREFIX = "pac4j.sudytech";

	/** Whether Enable Pac4j Sudytech（苏迪科技：微信门户公众号统一认证）. */
	private boolean enabled = false;

    private String clientName = "sudytech";
    
	private boolean supportGetRequest = true;
	private boolean supportPostRequest = true;
	
	/**
	 * The location of the client login URL, i.e. https://localhost:8080/myapp/login
	 */
	private String loginUrl;
	private String secretKey;
	private String dlmSessionKey = "com.sudytech.sso.client.api.user";
	private String publicPaths;
	
	
    
}
