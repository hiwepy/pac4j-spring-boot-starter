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
package org.pac4j.spring.boot;

import java.util.ArrayList;
import java.util.List;

import org.pac4j.spring.boot.ext.property.Pac4jOAuthGenericProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(Pac4jOAuthProperties.PREFIX)
public class Pac4jOAuthProperties {

	public static final String PREFIX = "shiro.pac4j.oauth";
	
	/** Whether Enable Pac4j OAuth. */
	private boolean enabled = false;
	
	@NestedConfigurationProperty
	private List<Pac4jOAuthGenericProperties> enerics = new ArrayList<Pac4jOAuthGenericProperties>();
    
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Pac4jOAuthGenericProperties> getEnerics() {
		return enerics;
	}

	public void setEnerics(List<Pac4jOAuthGenericProperties> enerics) {
		this.enerics = enerics;
	}

}
