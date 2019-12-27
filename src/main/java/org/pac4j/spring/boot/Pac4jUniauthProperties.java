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
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ConfigurationProperties(Pac4jUniauthProperties.PREFIX)
@Getter
@Setter
@ToString
public class Pac4jUniauthProperties {

	public static final String PREFIX = "pac4j.uniauth";

	/** Whether Enable Pac4j Uniauth（浙江音乐学院单点认证）. */
	private boolean enabled = false;
	
	@NestedConfigurationProperty
	private Pac4TokenProperties token = new Pac4TokenProperties();
	@NestedConfigurationProperty
	private Pac4SignatureProperties signature = new Pac4SignatureProperties();
    
    
}
