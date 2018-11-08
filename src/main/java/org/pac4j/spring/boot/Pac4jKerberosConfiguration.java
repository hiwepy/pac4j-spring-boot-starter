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

import org.pac4j.kerberos.client.direct.DirectKerberosClient;
import org.pac4j.kerberos.client.indirect.IndirectKerberosClient;
import org.pac4j.spring.boot.ext.property.Pac4jKerberosProperties;
import org.pac4j.spring.boot.ext.property.Pac4jProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore( name = {
	"org.pac4j.spring.boot.Pac4jWebFilterConfiguration"
})
@ConditionalOnWebApplication
@ConditionalOnClass({ DirectKerberosClient.class, IndirectKerberosClient.class })
@ConditionalOnProperty(prefix = Pac4jKerberosProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jKerberosProperties.class, Pac4jProperties.class, ServerProperties.class })
public class Pac4jKerberosConfiguration {

    @Bean
 	@ConditionalOnProperty(prefix = Pac4jKerberosProperties.PREFIX, value = "casClient", havingValue = "true")
 	public DirectKerberosClient directKerberosClient() {
 		
 	    final DirectKerberosClient kerberosClient = new DirectKerberosClient();
 	  
 		return kerberosClient;
 	}
    
    @Bean
 	@ConditionalOnProperty(prefix = Pac4jKerberosProperties.PREFIX, value = "casClient", havingValue = "true")
 	public IndirectKerberosClient indirectKerberosClient() {
 		
 	    final IndirectKerberosClient kerberosClient = new IndirectKerberosClient();
 	  
 		return kerberosClient;
 	}
	
}
