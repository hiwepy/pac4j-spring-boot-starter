/*
 * Copyright (c) 2017, vindell (https://github.com/vindell).
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
package org.pac4j.spring.boot.ext;

import org.pac4j.spring.boot.Pac4jCasProperties;
import org.pac4j.spring.boot.Pac4jProperties;
import org.pac4j.spring.boot.utils.CasUrlUtils;
import org.pac4j.spring.boot.utils.Pac4jUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class Pac4jPathBuilder {
	
	@Autowired
	private Pac4jProperties pac4jProperties;
	@Autowired
	private Pac4jCasProperties casProperties;
	
	public String getLoginURL(String contextPath) {
		
		if(casProperties != null && casProperties.isCasClient()) {
			
			
			//Pac4jUrlUtils.constructCallbackUrl( serverProperties.getContextPath(), pac4jProperties.getSuccessUrl())
			return CasUrlUtils.constructLoginRedirectUrl(casProperties, contextPath, CasUrlUtils.constructCallbackUrl(casProperties, contextPath, pac4jProperties.getLoginUrl()));
		}
		
		return null;
	}
	
	public String getLogoutURL(String contextPath) {
		
		if(casProperties != null && casProperties.isCasClient()) {
			
			//Pac4jUrlUtils.constructLogoutRedirectUrl(pac4jProperties, contextPath, pac4jProperties.getLoginUrl())
			
			return CasUrlUtils.constructLogoutRedirectUrl(casProperties, contextPath, pac4jProperties.getLoginUrl());
		}
		
		return null;
	}

}
