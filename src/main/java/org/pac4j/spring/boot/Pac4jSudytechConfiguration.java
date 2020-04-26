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

import org.pac4j.ext.sudytech.client.SudytechWxClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sudytech.auth.basic.BasicEnv;
import com.sudytech.auth.basic.ids.login.spi.AppStoreLoginNameResolver;

@Configuration
@AutoConfigureBefore(Pac4jAutoConfiguration.class)
@ConditionalOnClass({ AppStoreLoginNameResolver.class, BasicEnv.class })
@ConditionalOnProperty(prefix = Pac4jSudytechProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jSudytechProperties.class, Pac4jProperties.class })
public class Pac4jSudytechConfiguration {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Bean
	public SudytechWxClient sudytechWxClient(Pac4jSudytechProperties sudytechProperties) {
		
		SudytechWxClient client = new SudytechWxClient();
		
		client.setLoginUrl(sudytechProperties.getLoginUrl());
		// pac4jProperties.getCustomParams()
		client.setName(sudytechProperties.getClientName());
		client.setSupportGetRequest(sudytechProperties.isSupportGetRequest());
		client.setSupportPostRequest(sudytechProperties.isSupportPostRequest());
		
		logger.debug("Client Inited : {}", client.toString());
		
		return client;
	}

}
